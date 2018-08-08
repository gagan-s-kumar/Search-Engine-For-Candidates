package com.neu.reviewerfinder.backend;

import java.util.ArrayList;
/**
 * @author DJ
 * This is the Article Parsing Class
 * The Article Info from the Dblp.xml file 
 * are extracted using these tags and indexes.
 */
public class Article {
	
	//Declaration of tag name variables
	public String key;
	public String title;
	public int year;
	public String journal;
	public String volume;
	public String publisher;
	public ArrayList<String> authors;

	//Declaration of tag name indexes
	public static final int OTHER = 0;
	public static final int ARTICLE = 1;
	public static final int AUTHOR = 2;
	public static final int TITLE = 3;
	public static final int JOURNAL = 4;
	public static final int VOLUME = 5;
	public static final int YEAR = 6;
	public static final int PUBLISHER = 7;
	
	/**
	 * This is the getElement method
	 * It returns the index of the tag 
	 * to be extracted for respective data
	 * @param tag name
	 * @return tag index
	 */
	public static int getElement(String name) {
		if (name.equals("article")) {
			return ARTICLE;
		} else if (name.equals("author")) {
			return AUTHOR;
		} else if (name.equals("title") || name.equals("sub") || name.equals("sup") || name.equals("i")||name.equals("tt")) {
			return TITLE;
		} else if (name.equals("year")) {
			return YEAR;
		} else if (name.equals("publisher")) {
			return PUBLISHER;
		} else if (name.equals("journal")) {
			return JOURNAL;
		} else if (name.equals("volume")){
			return VOLUME;
		}else {
			return OTHER;
		}
	}

	/**
	 * This is the Article Constructor
	 * It initializes the tag name variables with default values.
	 */
	public Article() {
		key = "";
		title = "";
		journal = "";
		year = 0;
		volume = "";
		authors = new ArrayList<String>();
		publisher = "";
	}
}
