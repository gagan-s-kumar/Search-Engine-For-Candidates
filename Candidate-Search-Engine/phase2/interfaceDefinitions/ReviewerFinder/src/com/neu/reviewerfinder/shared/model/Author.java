package com.neu.reviewerfinder.shared.model;
import java.util.List;

public interface Author {
	public Author getAuthor(String ID);
	public String getAuthorID(String name);
	public List<Author> getAuthors(String school);
	public List<String> getCoAuthors(String ID);
} 
