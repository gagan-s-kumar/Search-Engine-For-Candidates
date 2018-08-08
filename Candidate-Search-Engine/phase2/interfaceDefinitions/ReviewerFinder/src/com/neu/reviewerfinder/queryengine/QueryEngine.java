package com.neu.reviewerfinder.queryengine;
import java.util.List;

import com.neu.reviewerfinder.shared.model.Author;

public interface QueryEngine<JSON> {
	public String getData(JSON json);
	public void setData(JSON json);
	public JSON makeJSON(Object[] objArr);
	public List<Author> getAuthors(String confName,int minYear);
	public List<Author> getAuthors(String[] keyWords);
	public List<Author> getAuthors(String[] confNames, int minPaperCount, int consecutiveYearCount);
	public List<Author> getAuthors(String authorName);
	public List<Author> getAuthors(String pubID, String conferenceID, int year);
}
