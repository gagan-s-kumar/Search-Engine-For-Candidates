package com.neu.reviewerfinder.queryengine;

import java.util.ArrayList;
/**
 * Concrete class for search criteria
 * @author Surekha
 *
 */
public class CriteriaImpl implements Criteria {

	private ArrayList<String> publishers;
	private String authorName;
	private ArrayList<String> keywords;
	private int yearSince;
	private int minimumPapersPublished;
	private int consecutiveYearConstraint;
	private int queryNum;
	
	/**
	 * Constructor
	 */
	public CriteriaImpl() {
		publishers = null;
		authorName = null;
		keywords = null;
		yearSince = Integer.MAX_VALUE;
		minimumPapersPublished = Integer.MAX_VALUE;
		consecutiveYearConstraint = Integer.MAX_VALUE;
		queryNum = Integer.MAX_VALUE;
	}
	
	public Criteria getCriteria() {
		return this;
	}

	public ArrayList<String> getPublishers() {
		return publishers;
	}

	public void setPublishers(ArrayList<String> publishers) {
		this.publishers = publishers;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public ArrayList<String> getTitleKeywords() {
		return keywords;
	}

	public void setTitleKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}

	public int getYearSince() {
		return yearSince;
	}

	public void setYearSince(int yearSince) {
		this.yearSince = yearSince;
	}

	public int getMinimumPapersPublished() {
		return minimumPapersPublished;
	}

	public void setMinimumPapersPublished(int minimumPapersPublished) {
		this.minimumPapersPublished = minimumPapersPublished;
	}

	public int getConsecutiveYearConstraint() {
		return consecutiveYearConstraint;
	}

	public void setConsecutiveYearConstraint(int consecutiveYearConstraint) {
		this.consecutiveYearConstraint = consecutiveYearConstraint;
	}

	public int getQueryNum() {
		return queryNum;
	}

	public void setQueryNum(int queryNum) {
		this.queryNum = queryNum;
	}
}
