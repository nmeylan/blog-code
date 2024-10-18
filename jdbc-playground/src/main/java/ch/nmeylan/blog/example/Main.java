package ch.nmeylan.blog.example;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class Main {

    // Generate jdbcTemplate Insert statement from jpa Entity class
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException {
        // Change me, provide your entity class
        Class<?> entityClass = Main.class.getClassLoader().loadClass("ch.nmeylan.blog.example.ItemEntity");
        // Change me, provide the name of your variable containing your List<Entity>
        String itemName = "items";

        RelationNode relationNode = new RelationNode(entityClass, "");
        buildRelationGraph(relationNode);
        StringBuilder stringBuilder = new StringBuilder();
        printRelationGraph(relationNode, stringBuilder, 0);
        System.out.println("Relations graph");
        System.out.println(stringBuilder);

        // Getting relations in reverse order
        LinkedList<RelationNode> queue = new LinkedList<RelationNode>();
        Stack<RelationNode> stack = new Stack<RelationNode>();
        queue.add(relationNode);
        while (!queue.isEmpty()) {
            RelationNode node = queue.poll();
            stack.push(node);
            queue.addAll(node.relations);
        }
        Set<Class<?>> entitiesClasses = new LinkedHashSet<>();
        while (!stack.isEmpty()) {
            entitiesClasses.add(stack.pop().getSelf());
        }
        System.out.println("\n\n*************************");
        System.out.println("     COPY code below      ");
        System.out.println("*************************\n\n");
        for (Class<?> entity : entitiesClasses) {
            StringBuilder insertStatement = generateInsertStatement(entity);
            System.out.println(generateBatchUpdateStatement(entity, insertStatement.toString(), itemName));
        }
    }

    private static void buildRelationGraph(RelationNode relationNode) {
        for (Field field : relationNode.getSelf().getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (annotation.annotationType().equals(JoinColumn.class)) {
                    RelationNode nestedRelation = relationNode.addRelation(field.getType(), ((JoinColumn) annotation).name());
                    buildRelationGraph(nestedRelation);
                }
            }
        }
    }

    private static void printRelationGraph(RelationNode relationNode, StringBuilder stringBuilder, int padding) {
        String nodeName = relationNode.getSelf().getSimpleName();
        padding += nodeName.length();
        stringBuilder.append(nodeName);
        if (!relationNode.column.isEmpty()) {
            stringBuilder.append("(").append(relationNode.column).append(")");
        }
        for (RelationNode nestedRelation : relationNode.getRelations()) {
            stringBuilder.append("\n").append(String.format("%" + padding + "s", "-->"));
            printRelationGraph(nestedRelation, stringBuilder, padding);
        }
    }

    private static StringBuilder generateInsertStatement(Class<?> entityClass) {
        List<String> columns = new ArrayList<>();
        for (Field field : entityClass.getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (annotation.annotationType().equals(Column.class)) {
                    Column column = (Column) annotation;
                    columns.add(column.name());
                } else if (annotation.annotationType().equals(JoinColumn.class)) {
                    JoinColumn column = (JoinColumn) annotation;
                    columns.add(column.name());

                }
            }
        }
        StringBuilder insertStatement = new StringBuilder();
        insertStatement.append("INSERT INTO").append(" ").append(entityClass.getAnnotation(Table.class).name())
            .append(" ( ")
            .append(columns.stream().collect(Collectors.joining(", "))).append(" )")
            .append(" VALUES ").append(" ( ").append(columns.stream().map((v) -> ":" + v).collect(Collectors.joining(", "))).append(" )")
            .append(" RETURNING id");
        return insertStatement;
    }

    private static String generateBatchUpdateStatement(Class<?> entityClass, String insertStatement, String itemsName) throws NoSuchFieldException, NoSuchMethodException {
        StringBuilder batchUpdateStatement = new StringBuilder();
        batchUpdateStatement.append("String query = \"").append(insertStatement).append("\";\n");
        batchUpdateStatement
            .append("SqlParameterSource[] batchParams = new SqlParameterSource[").append(itemsName).append(".size()];\n")
            .append("int i = 0;\n")
            .append("for (").append(entityClass.getSimpleName()).append(" entry : ").append(itemsName).append(") {\n");

        for (Method method : entityClass.getDeclaredMethods()) {
            if (method.getAnnotation(PrePersist.class) != null) {
                batchUpdateStatement.append("    entry.").append(method.getName()).append("();\n");
            }
        }
        batchUpdateStatement.append("    MapSqlParameterSource params = new MapSqlParameterSource();\n");
        for (Field field : entityClass.getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                String callGetter = "entry." + getGetterMethodForField(entityClass, field.getName()).getName() + "()";
                if (annotation.annotationType().equals(Column.class)) {
                    Column column = (Column) annotation;
                    batchUpdateStatement.append("    params.addValue(\"").append(column.name()).append("\", ");
                    if (field.getType().equals(Instant.class)) {
                        batchUpdateStatement.append(callGetter).append(" != null ? ").append("Timestamp.from(").append(callGetter).append(")").append(" : null");
                    } else if (field.getType().isEnum()) {
                        batchUpdateStatement.append(callGetter).append(".name()");
                    } else {
                        batchUpdateStatement.append(callGetter);
                    }
                    batchUpdateStatement.append(");\n");
                } else if (annotation.annotationType().equals(JoinColumn.class)) {
                    JoinColumn column = (JoinColumn) annotation;
                    Class<?> relation = field.getType();
                    Optional<Field> maybeIdField = Arrays.stream(relation.getDeclaredFields()).filter(f -> f.getAnnotation(Id.class) != null).findFirst();
                    if (maybeIdField.isEmpty()) {
                        throw new RuntimeException(column.name() + ": can't find relation id field");
                    } else {
                        Field idField = maybeIdField.get();
                        batchUpdateStatement.append("    params.addValue(\"").append(column.name()).append("\", ")
                            .append(callGetter).append(" != null ?")
                            .append(callGetter).append(".")
                            .append(getGetterMethodForField(entityClass, idField.getName()).getName()).append("()").append(" : null");
                    }
                    batchUpdateStatement.append(");\n");
                }
            }
        }
        batchUpdateStatement.append("    batchParams[i++] = params;\n");
        batchUpdateStatement.append("}\n");
        batchUpdateStatement.append("namedParameterJdbcTemplate.query(query, batchParams, (resultSet, i) -> {\n")
            .append("    return resultSet.getString(\"id\");\n")
            .append("});\n");
        return batchUpdateStatement.toString();
    }

    public static Method getGetterMethodForField(Class obj, String fieldName)
        throws NoSuchMethodException, NoSuchFieldException {
        return obj.getDeclaredMethod(getGetterMethodNameForField(fieldName,
            obj.getDeclaredField(fieldName)));
    }

    public static String getGetterMethodNameForField(Object obj, Field field) {
        if (field.getType() == Boolean.class
            || field.getType() == boolean.class) {
            String isGetterName = "is"
                + field.getName().substring(0, 1).toUpperCase()
                + field.getName().substring(1);
            try {
                obj.getClass().getDeclaredMethod(isGetterName);
                return isGetterName;
            } catch (NoSuchMethodException nsme) {
            }
        }
        return "get" + field.getName().substring(0, 1).toUpperCase()
            + field.getName().substring(1);
    }

    static class RelationNode {
        Class<?> self;
        List<RelationNode> relations;
        String column;

        public RelationNode(Class<?> self, String column) {
            this.self = self;
            this.column = column;
            relations = new ArrayList<>();
        }

        public Class<?> getSelf() {
            return self;
        }


        public List<RelationNode> getRelations() {
            return relations;
        }

        public String getColumn() {
            return column;
        }

        public RelationNode addRelation(Class<?> relationClass, String column) {
            RelationNode relation = new RelationNode(relationClass, column);
            relations.add(relation);
            return relation;
        }
    }
}

