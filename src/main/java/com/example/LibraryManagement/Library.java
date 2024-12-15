package com.example.LibraryManagement;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "library")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "Library_Sequence")
	@SequenceGenerator(name = "Library_Sequence", sequenceName = "LibraryID_SEQ", initialValue = 100, allocationSize = 10)
    @Column(name = "Library_ID")
    private int id;

    private String name;
    private String location;
    private String phoneNumber;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL)
    private List<Books> books;

    // Constructors
    public Library() {}

    public Library(String name, String location, String phoneNumber) {
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Books> getBooks() {
		return books;
	}

	public void setBooks(List<Books> books) {
		this.books = books;
	}
 
}
