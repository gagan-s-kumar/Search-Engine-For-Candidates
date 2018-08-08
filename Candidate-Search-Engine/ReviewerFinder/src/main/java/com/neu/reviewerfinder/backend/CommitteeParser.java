package com.neu.reviewerfinder.backend;

import com.neu.reviewerfinder.hibernate.conf.CommitteeConf;
import com.neu.reviewerfinder.hibernate.dao.CommitteeDaoImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * This is the CommitteeData Parsing Class. The committee Info from the committees files is
 * extracted using these files.
 */
public class CommitteeParser {

  // Declaration
  private List<CommitteeConf> committeeList = new ArrayList<>();
  private CommitteeDaoImpl committeeDaoImpl;

  /**
   * This is the CommitteeParser Constructor It iterates through files of all years and Generates
   * the Committee Table with fields- (Conference Name,Name,Year,G-Flag,P-Flag)
   */
  public CommitteeParser(String path) {
    File folder = new File(path);
    File[] listOfFiles = folder.listFiles();
    committeeDaoImpl = new CommitteeDaoImpl();
    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        System.out.println("File " + listOfFiles[i].getName());
        String fileName = listOfFiles[i].getName();
        Integer delimLoc = fileName.indexOf("-");
        String conference = fileName.substring(0, delimLoc - 4);
        Integer year = Integer.parseInt(fileName.substring(delimLoc - 4, delimLoc));

        // Open the file
        FileInputStream fstream;
        try {
          fstream = new FileInputStream(listOfFiles[i]);
          BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
          String strLine;

          // Read File Line By Line
          while ((strLine = br.readLine()) != null) {
            Boolean gFlag = false;
            Boolean pFlag = false;
            Integer nameLoc = 0;
            if (strLine.startsWith("G:")) {
              gFlag = true;
              nameLoc = 2;
            } else if (strLine.startsWith("P:")) {
              pFlag = true;
              nameLoc = 2;
            } else if (strLine.startsWith("E:")) {
              nameLoc = 2;
            }
            String name = strLine.substring(nameLoc).trim();
            name = StringEscapeUtils.unescapeHtml4(name);
            CommitteeConf committeeConf = new CommitteeConf();
            committeeConf.setConference(conference);
            committeeConf.setName(name);
            committeeConf.setYear(year);
            committeeConf.setG_flag(gFlag);
            committeeConf.setP_flag(pFlag);
            committeeList.add(committeeConf);
            if (committeeList.size() > 1000) {
              committeeDaoImpl.storeCommittees(committeeList);
              committeeList = new ArrayList<>();
            }
          }
          if (committeeList.size() > 0) {
            committeeDaoImpl.storeCommittees(committeeList);
          }
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

  }

  // Getter
  public List<CommitteeConf> getCommitteeList() {
    return committeeList;
  }
}
