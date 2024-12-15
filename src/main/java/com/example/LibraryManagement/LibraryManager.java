package com.example.LibraryManagement;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class LibraryManager {
	private final EntityManager entityMg;
	private final EntityTransaction entityTrans;
	private final Scanner scanner = new Scanner(System.in);

	public LibraryManager(EntityManager entityMg) {
		this.entityMg = entityMg;
		this.entityTrans = entityMg.getTransaction();
	}

	public void manageLibrary() {
		int choice;
		try {
			do {
				System.out.println("\nLibrary Management");
				System.out.println("1. Add Library");
				System.out.println("2. Update Library");
				System.out.println("3. Delete Library");
				System.out.println("4. Find Library");
				System.out.println("5. Check Available Books");
				System.out.println("6. Back to Main Menu");
				System.out.print("Enter your choice: ");
				choice = scanner.nextInt();

				switch (choice) {
				case 1:
					addLibrary();
					break;
				case 2:
					updateLibrary();
					break;
				case 3:
					deleteLibrary();
					break;
				case 4:
					findLibrary();
					break;
				case 5:
					checkAllTheBooks();
					break;
				case 6:
					System.out.println("Returning to Main Menu...");
					LibraryManagementSystem.main(null);
					break;
				default:
					System.out.println("Invalid choice.");
				}
			} while (choice != 6);
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input. Please enter valid option.");
			scanner.nextLine();
			manageLibrary();
		}
	}

	private void addLibrary() {
		scanner.nextLine();
		System.out.print("Enter Library Name: ");
		String name = scanner.nextLine();
		System.out.print("Enter Location: ");
		String location = scanner.nextLine();
		System.out.print("Enter Phone Number: ");
		String phone = scanner.nextLine();

		Library library = new Library(name, location, phone);

		/*
		 * System.out.print("Enter the number of books for this library: "); int
		 * bookCount = scanner.nextInt();
		 * 
		 * for (int i = 0; i < bookCount; i++) {
		 * System.out.println("Enter details for Book " + (i + 1) + ":");
		 * System.out.print("Book Name: "); String bookName = scanner.nextLine();
		 * System.out.print("Author: "); String author = scanner.nextLine();
		 * System.out.print("Cost: "); double cost = scanner.nextDouble();
		 * 
		 * Books book = new Books(bookName, author, cost, library);
		 * library.getBooks().add(book); }
		 */

		entityTrans.begin();
		entityMg.persist(library);
		entityTrans.commit();
		System.out.println("Library added successfully.");
	}

	private void updateLibrary() {
		try {
			System.out.print("Enter Library ID to Update: ");
			int id = scanner.nextInt();
			Library library = entityMg.find(Library.class, id);

			if (library != null) {
				scanner.nextLine();
				System.out.print("Enter New Name: ");
				library.setName(scanner.nextLine());
				System.out.print("Enter New Location: ");
				library.setLocation(scanner.nextLine());
				System.out.print("Enter New Phone Number: ");
				library.setPhoneNumber(scanner.nextLine());

				entityTrans.begin();
				entityMg.merge(library);
				entityTrans.commit();
				System.out.println("Library updated successfully.");
			} else {
				System.out.println("Library not found.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input. Please enter valid option.");
			updateLibrary();
		}
	}

	private void deleteLibrary() {
		try {
			System.out.print("Enter Library ID to Delete: ");
			int id = scanner.nextInt();
			Library library = entityMg.find(Library.class, id);

			if (library != null) {
				entityTrans.begin();
				entityMg.remove(library);
				entityTrans.commit();
				System.out.println("Library deleted successfully.");
			} else {
				System.out.println("Library not found.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input. Please enter valid option.");
			deleteLibrary();
		}
	}

	private void findLibrary() {
		try {
			System.out.print("Enter Library ID to Find: ");
			int id = scanner.nextInt();
			Library library = entityMg.find(Library.class, id);

			if (library != null) {
				System.out.println("Library details:");
				System.out.println("ID: " + library.getId() + ", Name: " + library.getName() + ", Location: "
						+ library.getLocation() + ", Phone: " + library.getPhoneNumber());
			} else {
				System.out.println("Library not found.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input. Please enter valid option.");
			findLibrary();
		}
	}

	private void checkAllTheBooks() {
		try {
			System.out.print("Enter Library ID to view all books: ");
			int libraryId = scanner.nextInt();

			// Fetch the library entity
			Library library = entityMg.find(Library.class, libraryId);

			if (library != null) {
				List<Books> booksList = library.getBooks();

				if (booksList != null && !booksList.isEmpty()) {
					System.out.println("Books in Library: " + library.getName());
					for (Books book : booksList) {
						System.out.println("Book ID: " + book.getId());
						System.out.println("Book Name: " + book.getName());
						System.out.println("Author: " + book.getAuthor());
						System.out.println("Cost: " + book.getCost());
						System.out.println("Available: " + (book.isAvailable() ? "Yes" : "No"));
						System.out.println("--------------------------------");
					}
				} else {
					System.out.println("No books found in this library.");
				}
			} else {
				System.out.println("Library with ID " + libraryId + " not found.");
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input. Please enter valid option.");
			checkAllTheBooks();
		}
	}

}
