package com.neu.reviewerfinder.queryengine;

import java.util.ArrayList;
/**
 * Interface for defining search criteria
 * @author Surekha
 *
 */
public interface Criteria {
	/**
	 * Retrieve this criteria
	 * @return
	 */
	Criteria getCriteria();
	/**
	 * Returns list of conferences/journals to search Authors from
	 * @return list
	 */
	ArrayList<String> getPublishers();
	/**
	 * Sets list of conferences to search Authors from
	 * @param publishers
	 */
	void setPublishers(ArrayList<String> publishers);
	/**
	 * Returns the author name for similar authors
	 * @return author name
	 */
	String getAuthorName();
	/**
	 * Sets the author name for similar authors
	 * @param authorName
	 */
	void setAuthorName(String authorName);
	/**
	 * Returns the list of keywords which paper title should contain
	 * @return list
	 */
	ArrayList<String> getTitleKeywords();
	/**
	 * Sets the list of keywords which paper title should contain
	 * @param keywords
	 */
	void setTitleKeywords(ArrayList<String> keywords);
	/**
	 * Returns the year to querying authors
	 * @return
	 */
	int getYearSince();
	/**
	 * Sets the year to querying authors
	 * @param yearSince
	 */
	void setYearSince(int yearSince);
	/**
	 * Returns the minimum number of papers that should be published
	 * @return number
	 */
	int getMinimumPapersPublished();
	/**
	 * Sets the minimum number of papers that should be published
	 * @return number
	 */
	void setMinimumPapersPublished(int minimumPapersPublished);
	/**
	 * Returns the number of years for which author shouldn't be part
	 * of a committee
	 * @return number
	 */
	int getConsecutiveYearConstraint();
	/**
	 * Sets the number of years for which author shouldn't be part
	 * of a committee
	 * @param consecutiveYearConstraint
	 */
	void setConsecutiveYearConstraint(int consecutiveYearConstraint);
	/**
	 * Returns the query number (internal use)
	 * @return number
	 */
	int getQueryNum();
	/**
	 * Sets the query number (internal use)
	 * @param queryNum
	 */
	void setQueryNum(int queryNum);
}
