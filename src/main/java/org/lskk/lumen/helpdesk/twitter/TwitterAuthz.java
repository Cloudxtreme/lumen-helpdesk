package org.lskk.lumen.helpdesk.twitter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.hibernate.annotations.Type;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * Authorization by a specific Twitter user granted to a specific Twitter Consumer App.
 * This can be stored as embedded data structure in file or database for tenant, person, shop, etc.
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, property="@type")
@JsonSubTypes(@JsonSubTypes.Type(name="TwitterAuthorization", value=TwitterAuthz.class))
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class TwitterAuthz implements Serializable {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne @JoinColumn
	private TwitterApp app;
	@Column(name = "app_id", insertable = false, updatable = false)
	private String appId;
	private Long userId;
	private String screenName;
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentOffsetDateTime")
	private OffsetDateTime creationTime;
	private String accessToken;
	private String accessTokenSecret;
	private String requestToken;
	private String requestTokenSecret;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TwitterApp getApp() {
		return app;
	}

	public void setApp(TwitterApp app) {
		this.app = app;
	}

	public String getAppId() {
		return appId;
	}

	/**
	 * Twitter User's ID, if known.
	 * @return
	 */
	@Nullable
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Twitter User's screen name, if known.
	 * @return
	 */
	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/**
	 * When this authorization was granted.
	 * @return
	 */
	public OffsetDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(OffsetDateTime creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * Returns the value of the '<em><b>Twitter Tenant Access Token</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Access token that has access to this tenant's Twitter account, referred by {@link #getTwitterTenantScreenName()}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Twitter Tenant Access Token</em>' attribute.
	 * @see #setAccessToken(String)
	 * @see SocmedPackage#getTwitterSysConfig_TwitterTenantAccessToken()
	 * @model
	 * @generated
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Sets the value of the '{@link TwitterAuthz#getAccessToken <em>Twitter Tenant Access Token</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Twitter Tenant Access Token</em>' attribute.
	 * @see #getAccessToken()
	 * @generated
	 */
	public void setAccessToken(String value) {
		this.accessToken = value;
	}

	/**
	 * Returns the value of the '<em><b>Twitter Tenant Access Token Secret</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Access token secret that has access to this tenant's Twitter account, referred by {@link #getTwitterTenantScreenName()}.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Twitter Tenant Access Token Secret</em>' attribute.
	 * @see #setAccessTokenSecret(String)
	 * @see SocmedPackage#getTwitterSysConfig_TwitterTenantAccessTokenSecret()
	 * @model
	 * @generated
	 */
	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	/**
	 * Sets the value of the '{@link TwitterAuthz#getAccessTokenSecret <em>Twitter Tenant Access Token Secret</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Twitter Tenant Access Token Secret</em>' attribute.
	 * @see #getAccessTokenSecret()
	 * @generated
	 */
	public void setAccessTokenSecret(String value) {
		this.accessTokenSecret = value;
	}

	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	public String getRequestTokenSecret() {
		return requestTokenSecret;
	}

	public void setRequestTokenSecret(String requestTokenSecret) {
		this.requestTokenSecret = requestTokenSecret;
	}
} // TwitterSysConfig
