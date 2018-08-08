package com.neu.reviewerfinder.hibernate.dao;

import com.neu.reviewerfinder.hibernate.HibernateSetup;
import com.neu.reviewerfinder.hibernate.conf.UserConf;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * @author DJ This is the Hibernate DAO Class for User It flushes User to the DB Performs specific
 *         functions on the DB for retrieval
 */
public class UserDaoImpl implements UserDao {

  @Override
  public void createAndStoreUser(String username, String password) {
    Session session = HibernateSetup.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    UserConf user = new UserConf();
    user.setUsername(username);
    user.setPassword(password);
    session.save(user);
    session.getTransaction().commit();
  }

  @Override
  public List<UserConf> getUsers() {
    Session session = HibernateSetup.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    @SuppressWarnings("unchecked")
    List<UserConf> result = session.createCriteria(UserConf.class).list();
    session.getTransaction().commit();
    session.flush();
    return result;
  }

  @Override
  public void storeUsers(List<UserConf> userConfList) {
    Session session = null;
    try {
      session = HibernateSetup.getSessionFactory().getCurrentSession();
      session.beginTransaction();
      Iterator<UserConf> it = userConfList.iterator();
      while (it.hasNext()) {
        UserConf user = it.next();
        session.persist(user);
      }
    } catch (HibernateException e) {
      throw new HibernateException("Cannot save member", e);
    }
  }

  @Override
  public boolean checkIfUserExists(String username, String password) {
    Session session = HibernateSetup.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    Criteria criteria = session.createCriteria(UserConf.class);
    criteria.add(Restrictions.eq("username", username));
    criteria.add(Restrictions.eq("password", password));
    criteria.setProjection(Projections.rowCount());
    long count = (Long) criteria.uniqueResult();
    session.getTransaction().commit();
    if (count != 0) {
      return true;
    }
    return false;
  }
}
