package com.neu.reviewerfinder.database;
import java.util.List;

import com.neu.reviewerfinder.shared.model.Author;
import com.neu.reviewerfinder.shared.model.Paper;
import com.neu.reviewerfinder.shared.model.Publication;

public interface ArchivedClass {
	public ArchivedClass getPublication(String ID);
	public String getPublicationID(String name,String conferenceID, String paperID, String authorID);
	public List<Paper> getPapers(String ID);
	public List<Author> getAuthors(String ID);
	public List<Publication> getConferences(String ID);
}
