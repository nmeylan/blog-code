package ch.nmeylan.blog.example;


import ch.nmeylan.blog.example.spi.UserService;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static ch.nmeylan.blog.example.UserServiceBenchmark.BenchmarkContext.endExclusive;
import static ch.nmeylan.blog.example.UserServiceBenchmark.BenchmarkContext.startInclusive;

public class UserServiceBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkContext extends AbstractBenchmarkContext {
        static long startInclusive = LocalDate.of(1980, 01, 01).toEpochDay();
        static long endExclusive = LocalDate.now().toEpochDay();
        UserService userService;

        @Setup
        public void setup() {
            // This will startup springboot application
            super.setup(BenchmarkApplication.class);
            // Getting the bean we want to test
            userService = appContext.getBean(UserService.class);
        }
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void insert(BenchmarkContext context) {
        context.userService.insert(new UserEntity(UUID.randomUUID().toString(), "firstname", "lastname", UUID.randomUUID() +"@exampl.com", randomDate()));
    }

    public static LocalDate randomDate() {
        long startEpochDay = startInclusive;
        long endEpochDay = endExclusive;
        long randomDay = ThreadLocalRandom
                .current()
                .nextLong(startEpochDay, endEpochDay);

        return LocalDate.ofEpochDay(randomDay);
    }

    public static void main(String[] args) throws RunnerException {
        // Configuration JMH
        Options opt = new OptionsBuilder()
                .include(UserServiceBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(1)
                .warmupTime(new TimeValue(10, TimeUnit.SECONDS))
                .threads(1)
                .measurementIterations(2)
                .measurementTime(new TimeValue(20, TimeUnit.SECONDS))
                .build();

        new Runner(opt).run();
    }
}
