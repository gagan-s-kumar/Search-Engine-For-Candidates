package com.neu.reviewerfinder.shared.model;
import java.util.List;

public interface Paper {
	public Paper getPaper(String ID);
	public String getPaperID(String title,String year,String conferenceID, String authorID,String[] keyWords);
	public int getPaperYear(String ID);
	public List<Author> getAuthors(String ID);
	public List<String> getCitations(String ID);
	public List<String> getKeyWords(String ID);
}
