package com.neu.reviewerfinder.hibernate.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.neu.reviewerfinder.hibernate.HibernateSetup;
import com.neu.reviewerfinder.hibernate.conf.CitationConf;
/**
 * @author DJ
 * This is the Hibernate DAO Class for Citation
 * It flushes batches of Citation to the DB
 * Performs specific functions on the DB for retrieval
 */
public class CitationDaoImpl implements CitationDao{
	@Override
	public void createAndStoreCitation(String paperCiteKey, String paperCitedKey) {
        Session session = HibernateSetup.getSessionFactory().getCurrentSession();
        session.beginTransaction();
 
        CitationConf theCitation = new CitationConf();
        theCitation.setPaper_cite_key(paperCiteKey);;
        theCitation.setPaper_cited_key(paperCitedKey);
        session.save(theCitation);
        session.getTransaction().commit();
    }
	
	@Override
	public void storeCitations(List<CitationConf> citationConfList) {
		Session session = null;
		try {
			session = HibernateSetup.getSessionFactory().getCurrentSession();
	        session.beginTransaction();
	        Iterator<CitationConf> it = citationConfList.iterator();
	        while(it.hasNext()){
	        	CitationConf theCitation = it.next();
	        	session.persist(theCitation);
	        }
		}catch (HibernateException e) {
			throw new HibernateException("Cannot save member", e);
		} 
	}
	
	@Override
    public List<CitationConf> getCitationsByPaperCitedKey(List<String> paperKeys) {
        Session session = HibernateSetup.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
		List<CitationConf> result = session.createCriteria(CitationConf.class)
										   .add(Restrictions.in("paper_cited_key", paperKeys))
										   .list();
        session.getTransaction().commit();
        return result;
    }
	
	@Override
	public List<CitationConf> getCitationsByPaperCiteKey(List<String> paperKeys) {
		Session session = HibernateSetup.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<CitationConf> result = session.createCriteria(CitationConf.class)
										   .add(Restrictions.in("paper_cite_key", paperKeys))
										   .list();
        session.getTransaction().commit();
        return result;
	}
}
