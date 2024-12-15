package com.example.LibraryManagement;

import javax.persistence.*;

@Entity
@Table(name = "Books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Books_Sequence")
	@SequenceGenerator(name = "Books_Sequence", sequenceName = "BookID_SEQ", initialValue = 200, allocationSize = 10)
    @Column(name = "Book_ID")
    private int id;

    private String name;
    private String author;
    private double cost;
    @Column(name = "Is_Book_Available", nullable = false)
    private boolean isAvailable = true;
    @Column(name = "Borrowed_By")
    private String borrowedBy; // Stores the borrower's name

    @ManyToOne
    @JoinColumn(name = "Library_ID")
    private Library library;

    // Constructors
    public Books() {}

    public Books(String name, String author, double cost, Library library) {
        this.name = name;
        this.author = author;
        this.cost = cost;
        this.library = library;
        this.isAvailable = true; // Default to available
    }
    
    // Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getBorrowedBy() {
		return borrowedBy;
	}

	public void setBorrowedBy(String borrowedBy) {
		this.borrowedBy = borrowedBy;
	}

}
