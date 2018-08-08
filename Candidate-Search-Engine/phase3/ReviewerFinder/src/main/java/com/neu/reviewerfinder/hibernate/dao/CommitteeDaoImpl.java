package com.neu.reviewerfinder.hibernate.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.neu.reviewerfinder.hibernate.HibernateSetup;
import com.neu.reviewerfinder.hibernate.conf.CommitteeConf;
/**
 * @author DJ
 * This is the Hibernate DAO Class for Committee
 * It flushes batches of Committee to the DB
 * Performs specific functions on the DB for retrieval
 */
public class CommitteeDaoImpl implements CommitteeDao{
	@Override
	public void createAndStoreCommittee(String conference, String name, Integer year, Boolean G_flag, Boolean P_flag) {
        Session session = HibernateSetup.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        CommitteeConf theCommittee = new CommitteeConf();
        theCommittee.setConference(conference);
        theCommittee.setName(name);
        theCommittee.setYear(year);
        theCommittee.setG_flag(G_flag);
        theCommittee.setP_flag(P_flag);

        session.save(theCommittee);
        session.getTransaction().commit();
    }
	@Override
    public List<CommitteeConf> getOrderedCommittees() {
        Session session = HibernateSetup.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
		List<CommitteeConf> result = session.createCriteria(CommitteeConf.class)
										    .addOrder(Order.asc("name"))
										    .addOrder(Order.asc("conference"))
										    .addOrder(Order.asc("year"))
											.list();
        session.getTransaction().commit();
        return result;
    }
	@Override
	public void storeCommittees(List<CommitteeConf> committeeConfList) {
		Session session = null;
		try {
			session = HibernateSetup.getSessionFactory().getCurrentSession();
	        session.beginTransaction();
	        Iterator<CommitteeConf> it = committeeConfList.iterator();  
	        while(it.hasNext()){
	        	CommitteeConf theCommittee = it.next();
	        	session.persist(theCommittee);
	        }
		}catch (HibernateException e) {
			throw new HibernateException("Cannot save member", e);
		} 
	}
}
