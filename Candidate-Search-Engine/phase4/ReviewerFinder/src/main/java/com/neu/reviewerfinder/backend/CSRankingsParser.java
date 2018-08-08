package com.neu.reviewerfinder.backend;

import com.neu.reviewerfinder.hibernate.conf.AuthorInfoConf;
import com.neu.reviewerfinder.hibernate.dao.AuthorInfoDaoImpl;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSRankingsParser {
  private AuthorInfoDaoImpl authorInfoDaoImpl;
  private List<AuthorInfoConf> listAuthors;

  public CSRankingsParser(String path) {
    String csvFile = path;
    String line = "";
    String cvsSplitBy = ",";
    listAuthors = new ArrayList<>();
    int skipHeaders = 1;
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

      while ((line = br.readLine()) != null) {
        // use comma as separator
        if (skipHeaders < 1) {
          String[] content = line.split(cvsSplitBy);
          AuthorInfoConf author = new AuthorInfoConf();
          author.setName(content[0].trim());
          author.setAffiliation(content[1].trim());
          author.setCountry(content[2].trim());
          if (content[3].equals("#N/A"))
            author.setDept(null);
          else
            author.setDept(content[3].trim());
          if (content[4].equals("#N/A"))
            author.setYear(0);
          else {
            author.setYear(Integer.parseInt(content[4].trim()));
          }
          listAuthors.add(author);
        }
        skipHeaders--;
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      authorInfoDaoImpl = new AuthorInfoDaoImpl();
      authorInfoDaoImpl.storeAuthors(listAuthors);
    }
  }
}
