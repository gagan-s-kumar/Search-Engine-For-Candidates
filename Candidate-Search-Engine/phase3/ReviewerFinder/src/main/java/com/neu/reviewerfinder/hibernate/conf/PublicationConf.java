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
 * @author DJ
 * This is the Hibernate Mapping Class for
 * Publications
 */
@Entity
@Table(name="Publication")
public class PublicationConf {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hilo_sequence_generator")
    @GenericGenerator(
            name = "hilo_sequence_generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "hilo_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1000000"),
                    @Parameter(name = "optimizer", value = "hilo")
            })
	@Column(name="publication_id")
    private Long id;
	
	@Column(name="publication_key",length=100)
	private String publication_key;
	
	@Column(name="publisher",length = 100000)
	private String publisher;
	
	@Column(name="detail",length = 100000)
	private String detail;
	
	@Column(name="volume")
	private String volume;
	
	@Column(name="isJournal")
	private Boolean isJournal;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getPublication_key() {
		return publication_key;
	}

	public void setPublication_key(String publication_key) {
		this.publication_key = publication_key;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public Boolean getIsJournal() {
		return isJournal;
	}

	public void setIsJournal(Boolean isJournal) {
		this.isJournal = isJournal;
	}
	
}
