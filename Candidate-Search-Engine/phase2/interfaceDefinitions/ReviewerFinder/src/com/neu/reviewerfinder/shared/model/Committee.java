package com.neu.reviewerfinder.shared.model;
import java.util.List;

public interface Committee {
	public Committee getCommittee(String ID);
	public int getPaperYear(String ID);
	public List<Author> getEditors(String ID);
}
