package com.neu.reviewerfinder.backend;

import com.neu.reviewerfinder.hibernate.HibernateSetup;
import com.neu.reviewerfinder.hibernate.conf.UserConf;
import com.neu.reviewerfinder.hibernate.dao.UserDaoImpl;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DJ This is the User Parser Class All the data from the users.txt file is extracted and
 *         stored into the database using this user parser class.
 * 
 */

public class UserParser {

  private List<UserConf> userList = new ArrayList<>();;
  private UserDaoImpl userDaoImpl;

  /**
   * This is the UserParser Constructor -> It initializes the user parser and submits the users list
   * file for parsing.
   * 
   * @param users file location
   */
  public UserParser(String filePath) {
    FileInputStream fstream;
    try {
      fstream = new FileInputStream(filePath);
      BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
      String strLine;
      HibernateSetup.getSessionFactory().openSession();

      userDaoImpl = new UserDaoImpl();

      // Read File Line By Line
      // Ignore header
      br.readLine();
      while ((strLine = br.readLine()) != null) {
        String[] values = strLine.split("\t");
        if (values.length != 2)
          continue;

        UserConf userConf = new UserConf();
        userConf.setUsername(values[0]);
        userConf.setPassword(values[1]);
        userList.add(userConf);
      }
      userDaoImpl.storeUsers(userList);
      HibernateSetup.getSessionFactory().getCurrentSession().flush();
      HibernateSetup.getSessionFactory().getCurrentSession().clear();
      HibernateSetup.getSessionFactory().getCurrentSession().getTransaction().commit();
      br.close();
      fstream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Getter
  public List<UserConf> getUserList() {
    return userList;
  }

  /**
   * This is the Main Method for calling the user parser Accepts runtime arguments
   */
  public static void main(String[] args) {
    new UserParser(args[0]);
    HibernateSetup.getSessionFactory().close();
    System.exit(0);
  }

}
