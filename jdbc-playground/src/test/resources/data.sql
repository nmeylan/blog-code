-- Insert data into EditorEntity
INSERT INTO EditorEntity (name, email, phoneNumber, streetAddress, city, state, postalCode, country)
VALUES
('John Doe', 'john.doe@editors.com', '1234567890', '123 Main St', 'New York', 'NY', '10001', 'USA'),
('Jane Smith', 'jane.smith@editors.com', '9876543210', '456 Oak Ave', 'Los Angeles', 'CA', '90001', 'USA'),
('Emily Johnson', 'emily.johnson@editors.com', '1122334455', '789 Pine Rd', 'Chicago', 'IL', '60601', 'USA'),
('Michael Brown', 'michael.brown@editors.com', '6677889900', '101 Maple St', 'Houston', 'TX', '77001', 'USA'),
('Sarah Davis', 'sarah.davis@editors.com', '5544332211', '202 Cedar Ln', 'Phoenix', 'AZ', '85001', 'USA');

-- Insert data into AuthorEntity
INSERT INTO AuthorEntity (firstName, lastName, dateOfBirth, nationality, biography, email, phoneNumber, website,
streetAddress, city, state, postalCode, country, numberOfBooksPublished, firstPublicationDate, primaryGenre, editor_id, isActive, lastUpdated)
VALUES
('Mark', 'Twain', '1835-11-30', 'American', 'Famous author of Adventures of Huckleberry Finn.', 'mark.twain@authors.com', '1234509876', NULL,
 '1 Author St', 'Hannibal', 'MO', '63401', 'USA', 20, '1876-01-01', 'Fiction', 1, TRUE, CURRENT_DATE),
('Charles', 'Dickens', '1812-02-07', 'British', 'Renowned author of Great Expectations.', 'charles.dickens@authors.com', '9876543210', NULL,
 '2 Author Ave', 'London', NULL, 'SW1A', 'UK', 15, '1836-01-01', 'Classics', 2, TRUE, CURRENT_DATE),
('Jane', 'Austen', '1775-12-16', 'British', 'Pioneer of romantic fiction.', 'jane.austen@authors.com', '4567891230', NULL,
 '3 Author Blvd', 'Winchester', NULL, 'SO23', 'UK', 6, '1811-01-01', 'Romance', 3, TRUE, CURRENT_DATE),
('Leo', 'Tolstoy', '1828-09-09', 'Russian', 'Author of War and Peace.', 'leo.tolstoy@authors.com', '5678901234', NULL,
 '4 Author Dr', 'Tula', NULL, '300041', 'Russia', 10, '1869-01-01', 'Historical Fiction', 4, TRUE, CURRENT_DATE),
('Agatha', 'Christie', '1890-09-15', 'British', 'Queen of mystery novels.', 'agatha.christie@authors.com', '7890123456', NULL,
 '5 Author Ln', 'Torquay', NULL, 'TQ1', 'UK', 66, '1920-01-01', 'Mystery', 5, TRUE, CURRENT_DATE);

-- Insert data into BookDetailsEntity
INSERT INTO BookDetailsEntity (numberOfPages, language, weight, dimensions)
VALUES
(300, 'English', 0.5, '8.5 x 5.5 x 1.0'),
(500, 'French', 0.7, '9.0 x 6.0 x 1.5'),
(450, 'German', 0.6, '8.0 x 5.0 x 1.2'),
(250, 'Spanish', 0.4, '7.5 x 4.5 x 1.0'),
(600, 'Russian', 1.0, '10.0 x 7.0 x 2.0');

-- Insert data into BookEntity
INSERT INTO BookEntity (title, isbn, price, author_id, secondary_author_id, details_id)
VALUES
('Adventures of Huckleberry Finn', '978-0-141-43948-6', 9.99, 1, NULL, 1),
('Great Expectations', '978-0-486-29977-3', 14.99, 2, NULL, 2),
('Pride and Prejudice', '978-1-593-08512-1', 11.99, 3, NULL, 3),
('War and Peace', '978-0-679-72938-6', 19.99, 4, NULL, 4),
('Murder on the Orient Express', '978-0-425-19807-6', 12.99, 5, NULL, 5);

-- Insert data into CategoryEntity
INSERT INTO CategoryEntity (name, description)
VALUES
('Fiction', 'Fictional works that entertain and tell stories.'),
('Classics', 'Timeless works of literature.'),
('Romance', 'Stories of love and relationships.'),
('Mystery', 'Books involving suspense and solving crimes.'),
('Historical', 'Works that depict historical events.');

-- Insert data into book_category
INSERT INTO book_category (book_id, category_id)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 5),
(5, 4);

-- Insert data into CustomerEntity
INSERT INTO CustomerEntity (name, email, address, phoneNumber)
VALUES
('Alice Johnson', 'alice.johnson@gmail.com', '789 Elm St, Springfield, IL, USA', '1234567890'),
('Bob Smith', 'bob.smith@yahoo.com', '456 Pine St, Austin, TX, USA', '9876543210'),
('Catherine Brown', 'catherine.brown@hotmail.com', '123 Oak St, Denver, CO, USA', '1122334455'),
('David Wilson', 'david.wilson@live.com', '321 Maple St, Seattle, WA, USA', '6677889900'),
('Eva Green', 'eva.green@gmail.com', '654 Cedar Ln, Miami, FL, USA', '5544332211');

-- Insert data into OrderEntity
INSERT INTO OrderEntity (orderDate, status, customer_id)
VALUES
('2024-12-01 10:00:00', 'Completed', 1),
('2024-12-02 12:00:00', 'Pending', 2),
('2024-12-03 14:00:00', 'Shipped', 3),
('2024-12-03 15:00:00', 'Completed', 4),
('2024-12-03 16:00:00', 'Cancelled', 5);

-- Insert data into OrderItemEntity
INSERT INTO OrderItemEntity (quantity, price, book_id, order_id)
VALUES
(2, 19.98, 1, 1),
(1, 14.99, 2, 2),
(3, 35.97, 3, 3),
(1, 19.99, 4, 4),
(1, 12.99, 5, 5);
