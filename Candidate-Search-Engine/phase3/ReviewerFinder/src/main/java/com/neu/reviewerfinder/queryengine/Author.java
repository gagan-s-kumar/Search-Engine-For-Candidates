package com.neu.reviewerfinder.queryengine;

import java.util.ArrayList;

/**
 * Interface for Author. Used for storing search results. 
 * @author Surekha
 *
 */
public interface Author {
	/**
	 * Returns the Author object given it's Id
	 * @param Id
	 * @return
	 */
	Author getAuthor(String Id);
	/**
	 * Returns this Author's name
	 * @return Author Name
	 */
	String getAuthorName();
	/**
	 * Sets this Author's name
	 * @param authorName
	 */
	void setAuthorName(String authorName);
	/**
	 * Returns this Author's Id
	 * @return Author Id
	 */
	Long getAuthorId();
	/**
	 * Sets this Author's Id
	 * @param id
	 */
	void setAuthorId(Long id);
	/**
	 * Returns number of papers published in conference
	 * @return number
	 */
	int getConferencePaperCount();
	/**
	 * Sets number of papers published in conference
	 * @param conferencePaperCount
	 */
	void setConferencePaperCount(int conferencePaperCount);
	/**
	 * Returns number of papers published in journal
	 * @return number
	 */
	int getJournalPaperCount();
	/**
	 * Sets number of papers published in journal
	 * @param journalPaperCount
	 */
	void setJournalPaperCount(int journalPaperCount);
	/**
	 * Returns list of papers published by this author
	 * @return list
	 */
	ArrayList<String> getPaperKeys();
	/**
	 * Sets list of papers published by this author
	 * @param paperKeys
	 */
	void setPaperKeys(ArrayList<String> paperKeys);

} 
