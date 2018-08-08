package com.neu.reviewerfinder.queryengine;

import java.util.ArrayList;

/**
 * Concrete Author Object.
 * 
 * @author Surekha
 *
 */
public class AuthorDetails implements Author {

  private String authorName;
  private Long authorId;
  private ArrayList<String> paperKeys;
  private int conferencePaperCount;
  private int journalPaperCount;
  private String affiliation;
  private String country;
  private String dept;

  /**
   * Constructor method
   */
  public AuthorDetails() {
    authorName = null;
    authorId = Long.MIN_VALUE;
    paperKeys = null;
    conferencePaperCount = 0;
    journalPaperCount = 0;
    affiliation = null;
    country = null;
    dept = null;
  }

  /**
   * Parameterized constructor
   * 
   * @param authorName
   * @param authorId
   */
  public AuthorDetails(String authorName, Long authorId) {
    super();
    this.authorName = authorName;
    this.authorId = authorId;
  }

  public AuthorDetails getAuthor(String authorId) {
    if (this.authorName.equals(authorId)) {
      return this;
    }
    return null;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public Long getAuthorId() {
    return authorId;
  }

  public void setAuthorId(Long authorId) {
    this.authorId = authorId;
  }

  public int getConferencePaperCount() {
    return conferencePaperCount;
  }

  public void setConferencePaperCount(int conferencePaperCount) {
    this.conferencePaperCount = conferencePaperCount;
  }

  public int getJournalPaperCount() {
    return journalPaperCount;
  }

  public void setJournalPaperCount(int journalPaperCount) {
    this.journalPaperCount = journalPaperCount;
  }

  public ArrayList<String> getPaperKeys() {
    return paperKeys;
  }

  public void setPaperKeys(ArrayList<String> paperKeys) {
    this.paperKeys = paperKeys;
  }

  public String getAffiliation() {
    return affiliation;
  }

  public void setAffiliation(String affiliation) {
    this.affiliation = affiliation;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getDept() {
    return dept;
  }

  public void setDept(String dept) {
    this.dept = dept;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof AuthorDetails)) {
      return false;
    }

    AuthorDetails a = (AuthorDetails) o;
    return this.getAuthorName().equals(a.getAuthorName());
  }

  @Override
  public String toString() {
    return String
        .format("ID: " + this.getAuthorId() + " Name: " + this.getAuthorName() + " Paper Keys: "
            + String.join(", ", this.getPaperKeys()) + " Conference Papers Published: "
            + this.getConferencePaperCount() + " Journal Papers Published: "
            + this.getJournalPaperCount() + " Affiliation: " + this.getAffiliation() + " Country: "
            + this.getCountry() + " Department: " + this.getDept());
  }
}
