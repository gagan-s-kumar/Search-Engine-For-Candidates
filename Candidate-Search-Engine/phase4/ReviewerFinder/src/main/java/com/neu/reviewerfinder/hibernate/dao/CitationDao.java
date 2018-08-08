package com.neu.reviewerfinder.hibernate.dao;

import com.neu.reviewerfinder.hibernate.conf.CitationConf;
import java.util.List;

/**
 * @author DJ This is the Hibernate DAO Interface for Citation
 */
public interface CitationDao {
  void createAndStoreCitation(String paperCiteKey, String paperCitedKey);

  void storeCitations(List<CitationConf> citationConfList);

  List<CitationConf> getCitationsByPaperCitedKey(List<String> paperKeys);

  List<CitationConf> getCitationsByPaperCiteKey(List<String> paperKeys);
}
