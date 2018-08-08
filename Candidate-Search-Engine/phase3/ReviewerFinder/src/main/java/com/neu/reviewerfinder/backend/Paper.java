package com.neu.reviewerfinder.backend;

import java.util.ArrayList;
/**
 * @author DJ
 * This is the Paper Wrapper Class
 * All the inproceedings from the Dblp.xml file 
 * are extracted using the parser classes.
 */
public class Paper {
	
	//Declaration of tag names
	public String key; 
	public String title;
	public int year;
	public String conference;
	public String conferenceKey;
	public ArrayList<String> authors;
	public ArrayList<String> citations;
	
	//Declaration of indexes
	public static final int OTHER = 0;
	public static final int INPROCEEDING = 1;
	public static final int AUTHOR = 2;
	public static final int TITLE = 3;
	public static final int YEAR = 4;
	public static final int CITE = 5;
	public static final int CONFERENCE = 6;
	public static final int CONFERENCEURL = 7;

	/**
	 * This is the getElement method
	 * It returns the index of the tag 
	 * to be extracted for respective data
	 * @param tag name
	 * @return tag index
	 */
	public static int getElement(String name) {
		if (name.equals("inproceedings") || name.equals("article")) {
			return INPROCEEDING;
		} else if (name.equals("author")) {
			return AUTHOR;
		} else if (name.equals("title") || name.equals("sub") || name.equals("sup") || name.equals("i")||name.equals("tt")) {
			return TITLE;
		} else if (name.equals("year")) {
			return YEAR;
		} else if (name.equals("cite")) {
			return CITE;
		} else if (name.equals("booktitle")) {
			return CONFERENCE;
		} else if (name.equals("url")){
			return CONFERENCEURL;
		}else {
			return OTHER;
		}
	}
	
	/**
	 * This is the Paper Constructor
	 * It initializes the tag name variables with default values.
	 */
	public Paper() {
		key = "";
		title = "";
		conference = "";
		year = 0;
		conferenceKey = "";
		authors = new ArrayList<String>();
		citations = new ArrayList<String>();
	}
}
