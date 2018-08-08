package com.neu.reviewerfinder.hibernate.dao;

import com.neu.reviewerfinder.hibernate.HibernateSetup;
import com.neu.reviewerfinder.hibernate.conf.AuthorInfoConf;
import java.util.Iterator;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class AuthorInfoDaoImpl implements AuthorInfoDao {

  @Override
  public void createAndStoreAuthor(String name, String affiliation, String country, String dept,
      int year) {
    Session session = HibernateSetup.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    AuthorInfoConf theAuthor = new AuthorInfoConf();
    theAuthor.setName(name);
    theAuthor.setAffiliation(affiliation);
    theAuthor.setCountry(country);
    theAuthor.setDept(dept);
    theAuthor.setYear(year);
    session.save(theAuthor);
    session.getTransaction().commit();
  }

  @Override
  public void storeAuthors(List<AuthorInfoConf> authorInfoConfList) {
    Session session = null;
    try {
      session = HibernateSetup.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      Iterator<AuthorInfoConf> it = authorInfoConfList.iterator();
      while (it.hasNext()) {
        AuthorInfoConf theAuthor = it.next();
        session.persist(theAuthor);
      }
    } catch (HibernateException e) {
      throw new HibernateException("Cannot save member", e);
    }
  }

  @Override
  public List<AuthorInfoConf> getAuthorsInfo() {
    Session session = HibernateSetup.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    @SuppressWarnings("unchecked")
    List<AuthorInfoConf> result = session.createCriteria(AuthorInfoConf.class).list();
    session.getTransaction().commit();
    return result;
  }
}
