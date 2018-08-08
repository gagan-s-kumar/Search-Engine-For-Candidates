package com.neu.reviewerfinder.hibernate.dao;

import com.neu.reviewerfinder.hibernate.conf.PaperConf;
import com.neu.reviewerfinder.queryengine.Criteria;
import java.util.List;

/**
 * @author DJ This is the Hibernate DAO Interface for Paper
 */
public interface PaperDao {
  void createAndStorePaper(String title, String publisher, String year, String paperKey);

  void storePapers(List<PaperConf> paperConfList);

  List<PaperConf> getPapersByCriteria(Criteria input);
}
