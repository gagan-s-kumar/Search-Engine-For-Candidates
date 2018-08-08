package com.neu.reviewerfinder.hibernate.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.neu.reviewerfinder.hibernate.HibernateSetup;
import com.neu.reviewerfinder.hibernate.conf.AuthorConf;
/**
 * @author DJ
 * This is the Hibernate DAO Class for Author
 * It flushes batches of Authors to the DB
 * Performs specific functions on the DB for retrieval
 */
public class AuthorDaoImpl implements AuthorDao{
	@Override
	public void createAndStoreAuthor(String name, String key) {
        Session session = HibernateSetup.getSessionFactory().getCurrentSession();
        session.beginTransaction();
 
        AuthorConf theAuthor = new AuthorConf();
        theAuthor.setName(name);
        theAuthor.setPaper_key(key);
        session.save(theAuthor);
        session.getTransaction().commit();
       
    }
	@Override
	public void storeAuthors(List<AuthorConf> authorConfList) {
		Session session = null;
		try {
			session = HibernateSetup.getSessionFactory().getCurrentSession();
	        session.beginTransaction();
	        Iterator<AuthorConf> it = authorConfList.iterator();
	        while(it.hasNext()){
	        	AuthorConf theAuthor = it.next();
	        	session.persist(theAuthor);
	        }
		}catch (HibernateException e) {
			throw new HibernateException("Cannot save member", e);
		} 
	}

    @Override
    public List<AuthorConf> getAuthorsByPaperKeys(List<String> paperKeys) {
        Session session = HibernateSetup.getSessionFactory().getCurrentSession();
        session.beginTransaction();
		Criteria c = session.createCriteria(AuthorConf.class);
		c.add(Restrictions.in("paper_key", paperKeys));
	    @SuppressWarnings("unchecked")
        List<AuthorConf> result = c.list();
        session.getTransaction().commit();
        return result;
    }
    
    @Override
    public List<AuthorConf> getAuthorsByName(String name) {
        Session session = HibernateSetup.getSessionFactory().getCurrentSession();
        session.beginTransaction();
		Criteria c = session.createCriteria(AuthorConf.class);
		c.add(Restrictions.like("name", "%" + name + "%"));
        @SuppressWarnings("unchecked")
        List<AuthorConf> result = c.list();
        session.getTransaction().commit();
        return result;
    }
}
