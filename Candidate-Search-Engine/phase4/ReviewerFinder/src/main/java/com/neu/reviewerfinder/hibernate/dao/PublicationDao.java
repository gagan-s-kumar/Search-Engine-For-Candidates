package com.neu.reviewerfinder.hibernate.dao;

import com.neu.reviewerfinder.hibernate.conf.PublicationConf;
import java.util.List;

/**
 * @author DJ This is the Hibernate DAO Interface for Publication
 */
public interface PublicationDao {
  void createAndStorePublication(String publisher, String publicationKey, String volume,
      String detail, Boolean isJournal);

  List<PublicationConf> getPublications();

  void storePublications(List<PublicationConf> publicationConfList);
}
