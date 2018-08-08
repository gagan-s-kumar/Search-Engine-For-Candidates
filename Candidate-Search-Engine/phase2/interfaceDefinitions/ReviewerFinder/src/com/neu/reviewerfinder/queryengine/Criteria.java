package com.neu.reviewerfinder.queryengine;

public interface Criteria {
	public Criteria getCriteria();
	public void setConferenceID(String ID);
	public void setAuthorID(String ID);
	public void setPaperID(String ID);
	public void setYear(int year);
	public void setConsecutiveConstraint(boolean flag);
}
