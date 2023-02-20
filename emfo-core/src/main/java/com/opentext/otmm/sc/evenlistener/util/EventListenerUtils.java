/*
 * (C) Copyright 2019 Joaquín Garzón (http://opentext.com) and others.
 * 
 * Contributors:
 *   Joaquín Garzón - initial implementation
 */
package com.opentext.otmm.sc.evenlistener.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artesia.common.exception.BaseTeamsException;
import com.artesia.common.prefs.PrefData;
import com.artesia.common.prefs.PrefDataId;
import com.artesia.security.SecuritySession;
import com.artesia.security.session.services.LocalAuthenticationServices;
import com.artesia.system.services.SystemServices;
import com.artesia.user.TeamsUser;
import com.artesia.user.UserIdentifier;
import com.artesia.user.services.UserServices;

public final class EventListenerUtils {

	public static final String COMPONENT = "INACTIVE_USERS";
	public static final String KEY = "CONFIG";	
	/** user to get session */
	public static final String USER_LOGIN_ID = "USER_LOGIN_ID";		
	public static final String TEAMSDB_CONTEXT = "java:jboss/jdbc/teamsdb";
	
	
	private static final Log log = LogFactory.getLog(EventListenerUtils.class);

	/**
	 * Create and return a local session.
	 * 
	 * @return SecuritySession
	 */
	public static SecuritySession getLocalSession() {
		SecuritySession localSession = null;
		try {
			String loginId = getOTMMSettingValue(COMPONENT, KEY, USER_LOGIN_ID);
			log.info("Getting session for user : :"+loginId);
			localSession = LocalAuthenticationServices.getInstance().createLocalSession(loginId);
		} catch (BaseTeamsException e) {
			log.error("An exception occured while trying to get the login session", e);
			return null;
		}
		return localSession;
	}

	public static TeamsUser getUserDetails(String teamsUserId) throws BaseTeamsException {
		log.info("getUserDetails()>> for User" + teamsUserId);
		UserServices userServices = UserServices.getInstance();
		TeamsUser retrievedTeamsUser = userServices.retrieveTeamsUser(new UserIdentifier(teamsUserId),
				getLocalSession());

		if (retrievedTeamsUser != null) {
			return retrievedTeamsUser;
		}
		return null;
	}

	/**
	 * Create and return a local session.
	 * 
	 * @return SecuritySession
	 */
	public static SecuritySession getLocalSession(String aUser) {
		SecuritySession localSession = null;
		try {
			localSession = LocalAuthenticationServices.getInstance().createLocalSession(aUser);
		} catch (BaseTeamsException e) {
			log.error("An exception occured while trying to get the login session", e);
			return null;
		}
		return localSession;
	}

	/**
	 * Get system setting
	 * @param group
	 * @param component
	 * @param key
	 * @return
	 * @throws BaseTeamsException
	 */
	public static String getOTMMSettingValue(String component, String key, String property) throws BaseTeamsException {

		PrefDataId dataId = new PrefDataId(component, key, property);
		PrefData retrievedData = SystemServices.getInstance().retrieveSystemSettingsByPrefDataId(dataId, null);

		return retrievedData.getValue();

	}

	public static String getOTMMSettingValue(String component, String key, String property, String defaultValue) throws BaseTeamsException {

		String value = getOTMMSettingValue(component, key, property);
		return (value == null || value.trim().length() == 0)?defaultValue:value;
	}

}
