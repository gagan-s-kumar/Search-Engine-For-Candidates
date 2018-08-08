package com.neu.reviewerfinder.backend;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import com.neu.reviewerfinder.hibernate.HibernateSetup;
import com.neu.reviewerfinder.hibernate.conf.AuthorConf;
import com.neu.reviewerfinder.hibernate.conf.CitationConf;
import com.neu.reviewerfinder.hibernate.conf.PublicationConf;
import com.neu.reviewerfinder.hibernate.conf.PaperConf;
import com.neu.reviewerfinder.hibernate.dao.AuthorDaoImpl;
import com.neu.reviewerfinder.hibernate.dao.CitationDaoImpl;
import com.neu.reviewerfinder.hibernate.dao.PublicationDaoImpl;
import com.neu.reviewerfinder.hibernate.dao.PaperDaoImpl;
/**
 * @author DJ
 * This is the Main Parser Class
 * All the data from the Dblp.xml file 
 * are extracted and stored into the database
 * using this parser class.
 * 
 * NOTE:
 * The parser is only called if the tables do not exist in database.
 * Therefore, the runtime argument specifies if the parsing
 * has to be performed. 
 */
public class Parser {
	
	//Declaration of Parsing Indexes
	private int curElement = -1;
	private int ancestor = -1;
	private Paper paper;
	private Conference conf;
	private Article article;
	int line = 0;
	
	//Declaration of Hibernate DataAccessObjects
	private AuthorDaoImpl authorDaoImpl;
	private PaperDaoImpl paperDaoImpl;
	private PublicationDaoImpl publicationDaoImpl;
	private CitationDaoImpl citationDaoImpl;
	
	//Declaration of batch of Authors,Papers,Publications & Citations
	private List<AuthorConf> authorList = new ArrayList<>();
	private List<PaperConf> paperList = new ArrayList<>();
	private List<PublicationConf> publicationList = new ArrayList<>();
	private List<CitationConf> citationList = new ArrayList<>();
	
	//Flags & Accumulators
	int errors = 0;
	StringBuffer author;
	
	/**
	 * This is the SAX Parser ConfigHandler class
	 * It is called on the parse method.
	 * It sets the indexes of the current data
	 * to be extracted for the respective fields
	 * 
	 */
	private class ConfigHandler extends DefaultHandler {
		
		/**
		 * This is the startElement method
		 * It sets the index of the tag 
		 * to be extracted for respective tag name
		 * @param namespace,local name,raw name,attributes
		 * @return
		 */
		public void startElement(String namespaceURI, String localName,
				String rawName, Attributes atts) throws SAXException {
			if (rawName.equals("inproceedings")) {
				ancestor = Element.INPROCEEDING;
				curElement = Paper.INPROCEEDING;
				paper = new Paper();
				paper.key = atts.getValue("key");
			} else if (rawName.equals("article")) {
				ancestor = Element.ARTICLE;
				curElement = Article.ARTICLE;
				article = new Article();
				article.key = atts.getValue("key");
			} else if (rawName.equals("proceedings")) {
				ancestor = Element.PROCEEDING;
				curElement = Conference.PROCEEDING;
				conf = new Conference();
				conf.key = atts.getValue("key");
			} else if ((rawName.equals("author") && ancestor == Element.INPROCEEDING) || (rawName.equals("author") && ancestor == Element.ARTICLE) || (rawName.equals("editor") && ancestor == Element.PROCEEDING)) {
				author = new StringBuffer();
			}
			if (ancestor == Element.INPROCEEDING) {
				curElement = Paper.getElement(rawName);
			} else if (ancestor == Element.PROCEEDING) {
				curElement = Conference.getElement(rawName);
			} else if (ancestor == Element.ARTICLE) {
				curElement = Article.getElement(rawName);
			} else if (ancestor == -1) {
				ancestor = Element.OTHER;
				curElement = Element.OTHER;
			} else {
				curElement = Element.OTHER;
			}

			line++;
		}
		
		/**
		 * This is the characters method
		 * It sets the info to parser objects for the tag 
		 * to be extracted with respective tag name
		 * @param char[], start index,length
		 * @return
		 */
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			if (ancestor == Element.INPROCEEDING) {
				String str = new String(ch, start, length).trim();
				if (curElement == Paper.AUTHOR) {
					author.append(str);
				} else if (curElement == Paper.CITE) {
					paper.citations.add(str);
				} else if (curElement == Paper.CONFERENCE) {
					paper.conference = str;
				} else if (curElement == Paper.TITLE) {
					paper.title += str;
				} else if (curElement == Paper.YEAR) {
					paper.year = Integer.parseInt(str);
				}else if (curElement == Paper.CONFERENCEURL){
					paper.conferenceKey = str;
				}
			} else if (ancestor == Element.PROCEEDING) {
				String str = new String(ch, start, length).trim();
				if (curElement == Conference.PUBLISHER) {
					conf.name = str;
				} else if (curElement == Conference.AUTHOR) {
					author.append(str);
				} else if (curElement == Conference.DETAIL) {
					conf.detail = str;
				} else if (curElement == Conference.VOLUME) {
					conf.volume = str;
				} else if (curElement == Conference.URL) {
					conf.url = str;
				}
			} else if (ancestor == Element.ARTICLE) {
				String str = new String(ch, start, length).trim();
				if (curElement == Article.AUTHOR) {
					author.append(str);
				} else if (curElement == Article.PUBLISHER) {
					article.publisher = str;
				} else if (curElement == Article.JOURNAL) {
					article.journal = str;
				} else if (curElement == Article.TITLE) {
					article.title += str;
				} else if (curElement == Article.YEAR) {
					article.year = Integer.parseInt(str);
				}else if (curElement == Article.VOLUME){
					article.volume = str;
				}
			}
			
		}

		/**
		 * This is the endElement method
		 * It adds the info to batch object for the parser 
		 * objects populated above.
		 * If the batch_size exceeds, objects are flushed to database
		 * @param char[], start index,length
		 * @return
		 */
		public void endElement(String namespaceURI, String localName,
				String rawName) throws SAXException {
			if (rawName.equals("author") && ancestor == Element.INPROCEEDING) {
				paper.authors.add(author.toString().trim());
			}
			if (rawName.equals("author") && ancestor == Element.ARTICLE) {
				article.authors.add(author.toString().trim());
			}
			if (rawName.equals("editor") && ancestor == Element.PROCEEDING) {
				conf.authors.add(author.toString().trim());
			}
			if (Element.getElement(rawName) == Element.INPROCEEDING) {
				ancestor = -1;
				try {
					if (paper.title.equals("") || paper.conference.equals("")
							|| paper.year == 0) {
						System.out.println("Error in parsing " + paper);
						errors++;
						return;
					}
					
					PaperConf paperConf = new PaperConf();
					paperConf.setPublisher(paper.conference);
					paperConf.setPaper_key(paper.key);
					paperConf.setTitle(paper.title);
					paperConf.setYear(paper.year);
					PublicationConf publicationConf = new PublicationConf();
					publicationConf.setPublication_key(paper.conferenceKey);
					publicationConf.setDetail(paper.title);
					publicationConf.setPublisher(paper.conference);
					publicationConf.setVolume("");
					publicationConf.setIsJournal(false);
					publicationList.add(publicationConf);
						paperList.add(paperConf);
						for (String author: paper.authors) {
							AuthorConf authorConf = new AuthorConf();
							authorConf.setName(author);
							authorConf.setPaper_key(paper.key);
							authorList.add(authorConf);
						}
						for (String cited: paper.citations) {
							if (!cited.equals("...")) {								
								CitationConf citationConf = new CitationConf();
								citationConf.setPaper_cite_key(paper.key);
								citationConf.setPaper_cited_key(cited);
								citationList.add(citationConf);
							}
						}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("line:" + line);
					System.exit(0);
				}
			} else if (Element.getElement(rawName) == Element.PROCEEDING) {
				ancestor = -1;
				try {
					if (conf.name.equals("")){
						conf.name = conf.detail;
					}	
					if (conf.key.equals("") || conf.name.equals("")
							|| conf.detail.equals("")) {
						System.out.println("Line:" + line);
						System.exit(0);
					}
					PublicationConf conferenceConf = new PublicationConf();
					conferenceConf.setPublication_key(conf.url);
					conferenceConf.setDetail(conf.detail);
					conferenceConf.setPublisher(conf.name);
					conferenceConf.setVolume(conf.volume);
					conferenceConf.setIsJournal(false);
					for (String author: conf.authors) {
						AuthorConf authorConf = new AuthorConf();
						authorConf.setName(author);
						authorConf.setPaper_key(conf.key);
						authorList.add(authorConf);
					}
					publicationList.add(conferenceConf);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("line:" + line);
					System.exit(0);
				}
			} else if (Element.getElement(rawName) == Element.ARTICLE) {
				ancestor = -1;
				try {
					if (article.title.equals("") || article.journal.equals("")
							|| article.year == 0) {
						System.out.println("Error in parsing " + article);
						errors++;
						return;
					}
					PaperConf paperConf = new PaperConf();
					paperConf.setPublisher(article.journal);
					paperConf.setPaper_key(article.key);
					paperConf.setTitle(article.title);
					paperConf.setYear(article.year);
					PublicationConf publicationConf = new PublicationConf();
					publicationConf.setPublisher(article.journal);
					publicationConf.setPublication_key(article.key);
					publicationConf.setVolume(article.volume);
					publicationConf.setDetail(article.title);
					publicationConf.setIsJournal(true);
					publicationList.add(publicationConf);
						paperList.add(paperConf);
						for (String author: article.authors) {
							AuthorConf authorConf = new AuthorConf();
							authorConf.setName(author);
							authorConf.setPaper_key(article.key);
							authorList.add(authorConf);
						}
					
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("line:" + line);
					System.exit(0);
				}
			}

			if (line % 100000 == 0) {
				try {
						authorDaoImpl.storeAuthors(authorList);
						publicationDaoImpl.storePublications(publicationList);
						paperDaoImpl.storePapers(paperList);
						citationDaoImpl.storeCitations(citationList);
						HibernateSetup.getSessionFactory().getCurrentSession().flush();
						HibernateSetup.getSessionFactory().getCurrentSession().clear();
						HibernateSetup.getSessionFactory().getCurrentSession().getTransaction().commit();
						authorList = new ArrayList<>();
						publicationList = new ArrayList<>();
						citationList = new ArrayList<>();
						paperList = new ArrayList<>();	
					} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}

		private void Message(String mode, SAXParseException exception) {
			System.out.println(mode + " Line: " + exception.getLineNumber()
					+ " URI: " + exception.getSystemId() + "\n" + " Message: "
					+ exception.getMessage());
		}

		public void warning(SAXParseException exception) throws SAXException {
			Message("**Parsing Warning**\n", exception);
			throw new SAXException("Warning encountered");
		}

		public void error(SAXParseException exception) throws SAXException {
			Message("**Parsing Error**\n", exception);
			throw new SAXException("Error encountered");
		}

		public void fatalError(SAXParseException exception) throws SAXException {
			Message("**Parsing Fatal Error**\n", exception);
			throw new SAXException("Fatal Error encountered");
		}
	}
	
	/**
	 * This is the Parser Constructor
	 * -> 	It initializes the parser and submits 
	 *    	the dblp file for parsing.
	 * -> 	It also initializes the committees parser 
	 * 		and submits the committees data location
	 * @param dblp location,committees location,parsing flag
	 */
	public Parser(String dblpUri,String committeeUri,Boolean parseFlag) throws SQLException {
		try {
			if(parseFlag){
				System.out.println("Parsing...");
				HibernateSetup.getSessionFactory().openSession();
				authorDaoImpl = new AuthorDaoImpl();
				paperDaoImpl = new PaperDaoImpl();
				citationDaoImpl = new CitationDaoImpl();
				publicationDaoImpl = new PublicationDaoImpl();
				SAXParserFactory parserFactory = SAXParserFactory.newInstance();
				SAXParser parser = parserFactory.newSAXParser();
				ConfigHandler handler = new ConfigHandler();
				parser.getXMLReader().setFeature(
						"http://xml.org/sax/features/validation", true);
				
					parser.parse(new File(dblpUri), handler);
				
				try {
					authorDaoImpl.storeAuthors(authorList);
					publicationDaoImpl.storePublications(publicationList);
					paperDaoImpl.storePapers(paperList);
					citationDaoImpl.storeCitations(citationList);
					HibernateSetup.getSessionFactory().getCurrentSession().flush();
					HibernateSetup.getSessionFactory().getCurrentSession().clear();
					HibernateSetup.getSessionFactory().getCurrentSession().getTransaction().commit();
					System.out.println("Processed " + line);
				} catch (Exception e) {
					e.printStackTrace();
				}
				new CommitteeParser(committeeUri);
				HibernateSetup.getSessionFactory().getCurrentSession().flush();
				HibernateSetup.getSessionFactory().getCurrentSession().clear();
				HibernateSetup.getSessionFactory().getCurrentSession().getTransaction().commit();
				System.out.println("num of errors: " + errors);
			}
		} catch (IOException e) {
				System.out.println("Error reading URI: " + e.getMessage());
			} catch (SAXException e) {
				System.out.println("Error in parsing: " + e.getMessage());
			} catch (ParserConfigurationException e) {
				System.out.println("Error in XML parser configuration: "
						+ e.getMessage());
			}
	}
	
	//Getters
	public List<AuthorConf> getAuthorList() {
		return authorList;
	}
	public List<PaperConf> getPaperList() {
		return paperList;
	}
	public List<CitationConf> getCitationList() {
		return citationList;
	}
	
	/**
	 * This is the Main Method for calling the parser
	 * Accepts runtime arguments
	 * Sets a few parameters for parsing configuration
	 */
	public static void main(String[] args) throws SQLException,
			ClassNotFoundException {
		if(args.length < 3){
			System.out.println("Please enter all arguments");
			System.exit(0);
		}
		
		final String ENTITY_LIMIT = "jdk.xml.entityExpansionLimit";
		System.setProperty(ENTITY_LIMIT, "0");	
		
		Long start = System.currentTimeMillis();
		new Parser(args[0],args[1],Boolean.valueOf((args[2])));
		HibernateSetup.getSessionFactory().close();
		Long end = System.currentTimeMillis();
		System.out.println("Used: " + (end - start) / 1000 + " seconds");
		System.clearProperty(ENTITY_LIMIT);        
	}
}
