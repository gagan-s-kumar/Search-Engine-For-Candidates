package com.neu.reviewerfinder.hibernate.dao;

import com.neu.reviewerfinder.hibernate.HibernateSetup;
import com.neu.reviewerfinder.hibernate.conf.PaperConf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * @author DJ This is the Hibernate DAO Class for Paper It flushes batches of Paper to the DB
 *         Performs specific functions on the DB for retrieval
 */
public class PaperDaoImpl implements PaperDao {

  @Override
  public void createAndStorePaper(String title, String publisher, String year, String paperKey) {
    Session session = HibernateSetup.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    PaperConf thePaper = new PaperConf();
    thePaper.setPaper_key(paperKey);;
    thePaper.setYear(Integer.parseInt(year));
    thePaper.setPublisher(publisher);
    thePaper.setTitle(title);
    session.save(thePaper);
    session.getTransaction().commit();
  }

  @Override
  public void storePapers(List<PaperConf> paperConfList) {
    Session session = null;
    try {
      session = HibernateSetup.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      Iterator<PaperConf> it = paperConfList.iterator();
      while (it.hasNext()) {
        PaperConf thePaper = it.next();
        session.persist(thePaper);
      }
    } catch (HibernateException e) {
      throw new HibernateException("Cannot save member", e);
    }
  }

  @Override
  public List<PaperConf> getPapersByCriteria(com.neu.reviewerfinder.queryengine.Criteria input) {
    Session session = HibernateSetup.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    Criteria c = session.createCriteria(PaperConf.class);
    if (input.getPublishers() != null && !input.getPublishers().isEmpty())
      c.add(Restrictions.in("publisher", input.getPublishers()));

    if (input.getYearSince() != Integer.MAX_VALUE)
      c.add(Restrictions.ge("year", input.getYearSince()));

    if (input.getTitleKeywords() != null && !input.getTitleKeywords().isEmpty()) {
      String keywordStr = String.join("|", input.getTitleKeywords());
      c.add(Restrictions.sqlRestriction("title RLIKE \"" + keywordStr + "\""));
    }
    try {
      @SuppressWarnings("unchecked")
      List<PaperConf> papers = c.list();
      session.getTransaction().commit();
      return papers;
    } catch (Exception e) {
      return new ArrayList<PaperConf>();
    }
  }
}
