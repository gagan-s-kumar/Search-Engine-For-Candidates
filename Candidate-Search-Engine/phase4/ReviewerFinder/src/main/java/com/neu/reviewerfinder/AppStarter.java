package com.neu.reviewerfinder;

import org.hibernate.Session;
import com.neu.reviewerfinder.hibernate.HibernateSetup;
import com.neu.reviewerfinder.ui.Login;

import javafx.application.Application;

public class AppStarter {

  public static void main(String[] args) {
    try {
      Session session = HibernateSetup.getSessionFactory().getCurrentSession();
      Application.launch(Login.class, args);
      // session.flush();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
