Library Management System

This is a Library Management System built using Java, Hibernate, and PostgreSQL to manage books, libraries, and borrowing activities. The project demonstrates core concepts of Object-Relational Mapping (ORM) using Hibernate and showcases the usage of One-to-Many relationships between two entities: `Library` and `Books`.

#### Features:

1. Library Management:
   - Add, update, delete, and find libraries.
   - Each library can have multiple books associated with it (One-to-Many relationship).

2. Book Management:
   - Add, update, delete, find, and borrow books.
   - Books are associated with a specific library and have attributes like `name`, `author`, `cost`, and `availability status`.
   
3. Borrowing and Returning Books:
   - Users can borrow a book if it is available.
   - Once borrowed, the book's status is updated to unavailable.
   - Users can also return borrowed books to update their availability.

4. Database Interaction:
   - The system uses Hibernate to interact with a PostgreSQL database to store and manage entities such as `Library` and `Books`.
   - Cascade operations are used to propagate changes across entities (e.g., if a library is deleted, its associated books are also removed).

5. Menu-driven Interface:
   - The program provides an interactive, menu-driven command-line interface that allows users to manage libraries and books.
   - Users can choose actions such as adding a library, updating books, or borrowing books based on their input.

Technologies Used:

- Java (for core logic)
- Hibernate ORM (for database interaction)
- PostgreSQL (as the database management system)
- Maven (for project dependency management)

Setting Up:

1. Clone the repository:
   
   git clone https://github.com/your-username/Library-Management-System.git

2. Set up PostgreSQL:
   - Ensure you have PostgreSQL installed and a database created.
   - Configure the `hibernate.cfg.xml` file with your PostgreSQL credentials.

3. Build and run the project:
   - Use Maven to build and run the project:
      1) mvn clean install
      2) mvn exec:java

4. Start interacting with the menu and manage libraries and books.
