package com.neu.reviewerfinder.hibernate;

import org.hibernate.SessionFactory;
// import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * @author DJ This is the Hibernate Setup Class Creates the Session Factory Loads the Hibernate
 *         Properties from the hibernate.cfg.xml Adds Annotated Table Classes
 */
public class HibernateSetup {

  // Declaration
  private final static SessionFactory sessionFactory = buildSessionFactory();
  private static int hibernateBatchSize;

  private static SessionFactory buildSessionFactory() {
    try {
      // Create the SessionFactory from hibernate.cfg.xml
      Configuration cfg = new Configuration().configure();
      hibernateBatchSize = new Integer(cfg.getProperty("jdbc.batch_size")).intValue();
      cfg.addAnnotatedClass(com.neu.reviewerfinder.hibernate.conf.AuthorConf.class);
      cfg.addAnnotatedClass(com.neu.reviewerfinder.hibernate.conf.PaperConf.class);
      cfg.addAnnotatedClass(com.neu.reviewerfinder.hibernate.conf.CitationConf.class);
      cfg.addAnnotatedClass(com.neu.reviewerfinder.hibernate.conf.PublicationConf.class);
      cfg.addAnnotatedClass(com.neu.reviewerfinder.hibernate.conf.CommitteeConf.class);
      cfg.addAnnotatedClass(com.neu.reviewerfinder.hibernate.conf.AuthorInfoConf.class);
      cfg.addAnnotatedClass(com.neu.reviewerfinder.hibernate.conf.UserConf.class);
      return cfg.configure().buildSessionFactory();
    } catch (Throwable ex) {
      // Make sure you log the exception, as it might be swallowed
      System.err.println("Initial SessionFactory creation failed." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  // Getters
  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static int getBatchSize() {
    return hibernateBatchSize;
  }
}
