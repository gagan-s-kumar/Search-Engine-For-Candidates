package com.neu.reviewerfinder.hibernate.dao;

import java.util.List;

import com.neu.reviewerfinder.hibernate.conf.PaperConf;
import com.neu.reviewerfinder.queryengine.Criteria;
/**
 * @author DJ
 * This is the Hibernate DAO Interface for
 * Paper
 */
public interface PaperDao {
	void createAndStorePaper(String title, String publisher,String year,String paperKey);
	void storePapers(List<PaperConf> paperConfList);
	List<PaperConf> getPapersByCriteria(Criteria input);
}
