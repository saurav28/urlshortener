package org.saurav.urlshortener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class URL {
	
@Column
@Id
private String URL_UUID;

@Column
private String URL_NAME;

public String getURL_UUID() {
	return URL_UUID;
}

public void setURL_UUID(String uRL_UUID) {
	URL_UUID = uRL_UUID;
}

public String getURL_NAME() {
	return URL_NAME;
}

public void setURL_NAME(String uRL_NAME) {
	URL_NAME = uRL_NAME;
}



}
