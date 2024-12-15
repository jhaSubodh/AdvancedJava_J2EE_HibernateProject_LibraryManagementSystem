package com.example.LibraryManagement;

import java.util.InputMismatchException;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class BookManager {
	private final EntityManager entityMg;
	private final EntityTransaction entityTrans;
	private final Scanner scanner = new Scanner(System.in);

	public BookManager(EntityManager entityMg) {
		this.entityMg = entityMg;
		this.entityTrans = entityMg.getTransaction();
	}

	public void manageBooks() {
		int choice;
		try {
			do {
				System.out.println("\nBook Management");
				System.out.println("1. Add Book");
				System.out.println("2. Update Book");
				System.out.println("3. Delete Book");
				System.out.println("4. Find Book");
				System.out.println("5. Borrow Book");
				System.out.println("6. Return Book");
				System.out.println("7. Back to Main Menu");
				System.out.print("Enter your choice: ");
				choice = scanner.nextInt();

				switch (choice) {
				case 1:
					addBook();
					break;
				case 2:
					updateBook();
					break;
				case 3:
					deleteBook();
					break;
				case 4:
					findBook();
					break;
				case 5:
					borrowBook();
					break;
				case 6:
					returnBook();
					break;
				case 7:
					System.out.println("Returning to Main Menu...");
					LibraryManagementSystem.main(null);
					break;
				default:
					System.out.println("Invalid choice.");
				}
			} while (choice != 7);
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input. Please enter valid option.");
			scanner.nextLine();
			manageBooks();
		}
	}

	private void addBook() {
		try {
			System.out.print("Enter Library ID to associate this book: ");
			int libraryId = scanner.nextInt();
			Library library = entityMg.find(Library.class, libraryId);

			if (library != null) {
				scanner.nextLine();
				System.out.print("Enter Book Name: ");
				String name = scanner.nextLine();
				System.out.print("Enter Author: ");
				String author = scanner.nextLine();
				System.out.print("Enter Cost: ");
				double cost = scanner.nextDouble();

				Books book = new Books(name, author, cost, library);

				entityTrans.begin();
				entityMg.persist(book);
				entityTrans.commit();
				System.out.println("Book added to Library successfully.");
			} else {
				System.out.println("Library not found.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input. Please enter valid option.");
			addBook();
		}
	}

	private void updateBook() {
		try {
			System.out.print("Enter Book ID to update: ");
			int bookId = scanner.nextInt();

			// Fetch the book entity by ID
			Books book = entityMg.find(Books.class, bookId);

			if (book != null) {
				System.out.println("Current Book Details:");
				System.out.println("1. Name: " + book.getName());
				System.out.println("2. Author: " + book.getAuthor());
				System.out.println("3. Cost: " + book.getCost());

				// Prompt for updated details
				System.out.println("\nEnter new details (leave blank to keep existing values):");

				System.out.print("New Book Name (Current: " + book.getName() + "): ");
				scanner.nextLine(); // Clear the input buffer
				String newName = scanner.nextLine();
				if (!newName.isEmpty()) {
					book.setName(newName);
				}

				System.out.print("New Author Name (Current: " + book.getAuthor() + "): ");
				String newAuthor = scanner.nextLine();
				if (!newAuthor.isEmpty()) {
					book.setAuthor(newAuthor);
				}

				System.out.print("New Cost (Current: " + book.getCost() + "): ");
				String costInput = scanner.nextLine();
				if (!costInput.isEmpty()) {
					double newCost = Double.parseDouble(costInput);
					book.setCost(newCost);
				}

				// Update the entity in the database
				entityTrans.begin();
				entityMg.merge(book); // Merges the updated book back into persistence context
				entityTrans.commit();

				System.out.println("Book details updated successfully!");
			} else {
				System.out.println("Book with ID " + bookId + " not found.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input. Please enter valid option.");
			updateBook();
		}
	}

	private void deleteBook() {
		try {
			System.out.print("Enter Book ID to Delete: ");
			int id = scanner.nextInt();
			Books book = entityMg.find(Books.class, id);

			if (book != null) {
				entityTrans.begin();
				entityMg.remove(book);
				entityTrans.commit();
				System.out.println("Book deleted successfully.");
			} else {
				System.out.println("Book not found.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input. Please enter valid option.");
			deleteBook();
		}
	}

	private void findBook() {
		try {
			System.out.print("Enter Book ID to Find: ");
			int id = scanner.nextInt();
			Books book = entityMg.find(Books.class, id);

			if (book != null) {
				System.out.println("Book Details:");
				System.out.println("ID: " + book.getId() + ", Name: " + book.getName() + ", Author: " + book.getAuthor()
						+ ", Cost: " + book.getCost());
			} else {
				System.out.println("Book not found.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input. Please enter valid option.");
			findBook();
		}
	}

	private void borrowBook() {
		try {
			System.out.print("Enter Book ID to borrow: ");
			int bookId = scanner.nextInt();

			// Fetch the book entity by ID
			Books book = entityMg.find(Books.class, bookId);

			if (book != null) {
				// Check if the book is available
				if (book.isAvailable()) {
					System.out.print("Enter your name to borrow the book: ");
					scanner.nextLine(); // Clear input buffer
					String borrowerName = scanner.nextLine();

					// Update book status
					book.setAvailable(false);
					book.setBorrowedBy(borrowerName);

					// Begin transaction and commit changes
					entityTrans.begin();
					entityMg.merge(book);
					entityTrans.commit();

					System.out.println("Book '" + book.getName() + "' has been borrowed by " + borrowerName + ".");
				} else {
					System.out.println("Sorry, the book '" + book.getName() + "' is already borrowed by "
							+ book.getBorrowedBy() + ".");
				}
			} else {
				System.out.println("Book with ID " + bookId + " not found.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input. Please enter valid option.");
			borrowBook();
		}
	}

	private void returnBook() {
		try {
			System.out.print("Enter Book ID to return: ");
			int bookId = scanner.nextInt();

			// Fetch the book entity
			Books book = entityMg.find(Books.class, bookId);

			if (book != null) {
				if (!book.isAvailable()) { // Check if the book is already borrowed
					entityTrans.begin();
					book.setAvailable(true);
					entityTrans.commit();
					System.out.println("Book '" + book.getName() + "' has been returned successfully.");
				} else {
					System.out.println("Book is already available.");
				}
			} else {
				System.out.println("Book with ID " + bookId + " not found.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input. Please enter valid option.");
			returnBook();
		}
	}
}
