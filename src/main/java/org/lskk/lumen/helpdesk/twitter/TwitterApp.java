package org.lskk.lumen.helpdesk.twitter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Twitter Consumer App information, this does not contain any access token.
 * @see TwitterAuthz
 */
@Entity
public class TwitterApp implements Serializable {

	@Id
	private String id;
	private String apiKey;
	private String apiSecret;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Twitter consumer key for this tenant.
	 */
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String value) {
		this.apiKey = value;
	}

	/**
	 * Twitter consumer secret for this tenant.
	 */
	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String value) {
		this.apiSecret = value;
	}

} // TwitterSysConfig
