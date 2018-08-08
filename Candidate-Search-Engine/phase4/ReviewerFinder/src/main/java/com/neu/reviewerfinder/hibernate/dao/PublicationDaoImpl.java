package com.neu.reviewerfinder.hibernate.dao;

import com.neu.reviewerfinder.hibernate.HibernateSetup;
import com.neu.reviewerfinder.hibernate.conf.PublicationConf;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author DJ This is the Hibernate DAO Class for Publication It flushes batches of Publication to
 *         the DB Performs specific functions on the DB for retrieval
 */
public class PublicationDaoImpl implements PublicationDao {
  @Override
  public void createAndStorePublication(String publisher, String publicationKey, String volume,
      String detail, Boolean isJournal) {
    Session session = HibernateSetup.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    PublicationConf thePublication = new PublicationConf();
    thePublication.setPublisher(publisher);
    thePublication.setPublication_key(publicationKey);
    thePublication.setVolume(volume);
    thePublication.setDetail(detail);
    thePublication.setIsJournal(isJournal);
    session.save(thePublication);
    session.getTransaction().commit();
  }

  @Override
  public List<PublicationConf> getPublications() {
    Session session = HibernateSetup.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    @SuppressWarnings("unchecked")
    List<PublicationConf> result = session.createCriteria(PublicationConf.class).list();
    session.getTransaction().commit();
    return result;
  }

  @Override
  public void storePublications(List<PublicationConf> publicationConfList) {
    Session session = null;
    try {
      session = HibernateSetup.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      Iterator<PublicationConf> it = publicationConfList.iterator();
      while (it.hasNext()) {
        PublicationConf thePublication = it.next();
        session.persist(thePublication);
      }
    } catch (HibernateException e) {
      throw new HibernateException("Cannot save member", e);
    }
  }
}
