package com.neu.reviewerfinder.hibernate.conf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author DJ This is the Hibernate Mapping Class for Citations
 */
@Entity
@Table(name = "Citation")
public class CitationConf {
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hilo_sequence_generator")
  @GenericGenerator(name = "hilo_sequence_generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {@Parameter(name = "sequence_name", value = "hilo_sequence"),
          @Parameter(name = "initial_value", value = "1"),
          @Parameter(name = "increment_size", value = "1000000"),
          @Parameter(name = "optimizer", value = "hilo")})
  @Id
  @Column(name = "citation_id")
  private Long id;

  @Column(name = "paper_cite_key", length = 200)
  private String paper_cite_key;

  @Column(name = "paper_cited_key", length = 200)
  private String paper_cited_key;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPaper_cite_key() {
    return paper_cite_key;
  }

  public void setPaper_cite_key(String paper_cite_key) {
    this.paper_cite_key = paper_cite_key;
  }

  public String getPaper_cited_key() {
    return paper_cited_key;
  }

  public void setPaper_cited_key(String paper_cited_key) {
    this.paper_cited_key = paper_cited_key;
  }

}
