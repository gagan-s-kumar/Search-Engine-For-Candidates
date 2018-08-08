package com.neu.reviewerfinder.hibernate.dao;

import java.util.List;

import com.neu.reviewerfinder.hibernate.conf.PublicationConf;
/**
 * @author DJ
 * This is the Hibernate DAO Interface for
 * Publication
 */
public interface PublicationDao {
	void createAndStorePublication(String publisher,String publicationKey, String volume,String detail,Boolean isJournal);
	List<PublicationConf> getPublications();
	void storePublications(List<PublicationConf> publicationConfList);
}
