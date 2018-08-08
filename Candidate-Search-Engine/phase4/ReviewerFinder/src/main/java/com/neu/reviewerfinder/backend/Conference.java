package com.neu.reviewerfinder.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DJ This is the Conference Parsing Class The Proceeding Info from the Dblp.xml file are
 *         extracted using these tags and indexes.
 */
public class Conference {

  // Declaration of tag name variables
  public String key;
  public String name;
  public String detail;
  public String volume;
  public String url;
  public List<String> authors;

  // Declaration of tag name indexes
  public static final int OTHER = 0;
  public static final int PROCEEDING = 1;
  public static final int PUBLISHER = 2;
  public static final int DETAIL = 3;
  public static final int VOLUME = 4;
  public static final int AUTHOR = 5;
  public static final int URL = 6;

  /**
   * This is the getElement method It returns the index of the tag to be extracted for respective
   * data
   * 
   * @param tag name
   * @return tag index
   */
  public static int getElement(String name) {
    if (name.equals("proceedings")) {
      return PROCEEDING;
    } else if (name.equals("booktitle") || name.equals("publisher")) {
      return PUBLISHER;
    } else if (name.equals("title")) {
      return DETAIL;
    } else if (name.equals("volume") || name.equals("isbn")) {
      return VOLUME;
    } else if (name.equals("editor")) {
      return AUTHOR;
    } else if (name.equals("url")) {
      return URL;
    } else {
      return OTHER;
    }
  }

  /**
   * This is the Conference Constructor It initializes the tag name variables with default values.
   */
  public Conference() {
    this.key = "";
    this.name = "";
    this.detail = "";
    this.volume = "";
    this.url = "";
    this.authors = new ArrayList<String>();
  }
}
