package com.neu.reviewerfinder.shared.model;
import java.util.List;

public interface Publication {
	public Publication getConference(String ID);
	public String getConferenceID(String name);
	public List<Publication> getConferences(String detail);
}
