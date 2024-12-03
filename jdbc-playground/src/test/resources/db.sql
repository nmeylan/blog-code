CREATE TABLE AuthorEntity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstName TEXT,
    lastName TEXT,
    dateOfBirth DATE,
    nationality TEXT,
    biography TEXT,
    email TEXT,
    phoneNumber TEXT,
    website TEXT,
    streetAddress TEXT,
    city TEXT,
    state TEXT,
    postalCode TEXT,
    country TEXT,
    numberOfBooksPublished INT,
    firstPublicationDate DATE,
    primaryGenre TEXT,
    editor_id BIGINT,
    isActive BOOLEAN,
    lastUpdated DATE,
    FOREIGN KEY (editor_id) REFERENCES EditorEntity (id)
);

CREATE TABLE BookDetailsEntity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numberOfPages INT,
    language TEXT,
    weight DOUBLE,
    dimensions TEXT
);

CREATE TABLE BookEntity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title TEXT,
    isbn TEXT,
    price DOUBLE,
    author_id BIGINT NOT NULL,
    secondary_author_id BIGINT,
    details_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES AuthorEntity (id),
    FOREIGN KEY (secondary_author_id) REFERENCES AuthorEntity (id),
    FOREIGN KEY (details_id) REFERENCES BookDetailsEntity (id)
);

CREATE TABLE CategoryEntity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name TEXT,
    description TEXT
);

CREATE TABLE book_category (
    book_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, category_id),
    FOREIGN KEY (book_id) REFERENCES BookEntity (id),
    FOREIGN KEY (category_id) REFERENCES CategoryEntity (id)
);

CREATE TABLE CustomerEntity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name TEXT,
    email TEXT,
    address TEXT,
    phoneNumber TEXT
);

CREATE TABLE EditorEntity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name TEXT,
    email TEXT,
    phoneNumber TEXT,
    streetAddress TEXT,
    city TEXT,
    state TEXT,
    postalCode TEXT,
    country TEXT
);

CREATE TABLE OrderEntity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    orderDate DATETIME,
    status TEXT,
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES CustomerEntity (id)
);

CREATE TABLE OrderItemEntity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    quantity INT,
    price DOUBLE,
    book_id BIGINT NOT NULL,
    order_id BIGINT,
    FOREIGN KEY (book_id) REFERENCES BookEntity (id),
    FOREIGN KEY (order_id) REFERENCES OrderEntity (id)
);
