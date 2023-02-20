/*
 * (C) Copyright 2019 Joaquín Garzón (http://opentext.com) and others.
 * 
 * Contributors:
 *   Joaquín Garzón - initial implementation
 */
package com.opentext.otmm.sc.evenlistener.helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artesia.common.exception.BaseTeamsException;
import com.artesia.security.SecuritySession;
import com.artesia.security.session.services.LocalAuthenticationServices;

public class SecurityHelper {

	private static Log log = LogFactory.getLog(SecurityHelper.class);

	public static SecuritySession getAdminSession() {
		SecuritySession session = null;
		try {
			session = LocalAuthenticationServices.getInstance().createLocalSession("tsuper");
		} catch (BaseTeamsException e) {
			log.error(e.getMessage(), e);
		}
		return session;
	}

	public static SecuritySession getUserSession(String userName) {
		SecuritySession session = null;
		try {
			System.out.println("Trying to create a session for " + userName);
			session = LocalAuthenticationServices.getInstance().createLocalSession(userName);
		} catch (BaseTeamsException e) {
			log.error(e.getMessage(), e);
		}
		return session;
	}
}
