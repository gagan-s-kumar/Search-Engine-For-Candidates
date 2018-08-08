package com.neu.reviewerfinder.backend;
/**
 * @author DJ
 * This is the XML Element Parsing Class
 * The types of papers from the Dblp.xml file 
 * are extracted using these tags and indexes.
 */
public class Element {
	
	//Declaration of the paper type indexes
	public static final int OTHER = 0;
	public static final int INPROCEEDING = 1;
	public static final int PROCEEDING = 2;
	public static final int ARTICLE = 3;
	
	/**
	 * This is the getElement method
	 * It returns the index of the tag 
	 * to be extracted for respective data
	 * @param tag name
	 * @return tag index
	 */
	public static int getElement(String name) {
		if (name.equals("inproceedings")) {
			return 1;
		} else if (name.equals("proceedings")) {
			return 2;
		} else if (name.equals("article")) {
			return 3;
		} else {
			return 0;
		}
	}
}
