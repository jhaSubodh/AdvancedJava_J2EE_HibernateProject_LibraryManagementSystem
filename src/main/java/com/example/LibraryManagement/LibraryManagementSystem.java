package com.example.LibraryManagement;

import java.util.InputMismatchException;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        EntityManagerFactory entityMF = Persistence.createEntityManagerFactory("subodh");
        EntityManager entityMg = entityMF.createEntityManager();

        LibraryManager libraryManager = new LibraryManager(entityMg);
        BookManager bookManager = new BookManager(entityMg);

        Scanner scanner = new Scanner(System.in);
        int choice;
        
        try {
        do {
            System.out.println("\nMain Menu");
            System.out.println("1. Library Management");
            System.out.println("2. Book Management");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1: libraryManager.manageLibrary(); break;
                case 2: bookManager.manageBooks(); break;
                case 3: System.out.println("Exiting..."); 
                        System.exit(0);
                        break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 3);
        }catch(InputMismatchException e) {
        	System.out.println("Invalid Input. Please enter valid option.");
        	LibraryManagementSystem.main(null);
        }

        entityMg.close();
        entityMF.close();
        scanner.close();
    }
}


