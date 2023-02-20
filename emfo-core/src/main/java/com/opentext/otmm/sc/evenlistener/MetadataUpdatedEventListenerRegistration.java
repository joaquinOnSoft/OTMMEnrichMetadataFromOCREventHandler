/*
 * (C) Copyright 2020 Joaquín Garzón (http://opentext.com) and others.
 * 
 * Contributors:
 *   Joaquín Garzón - initial implementation
 */
package com.opentext.otmm.sc.evenlistener;

import javax.servlet.ServletContextEvent;

import com.artesia.common.exception.BaseTeamsException;
import com.artesia.event.services.EventServices;
import com.artesia.security.SecuritySession;

public class MetadataUpdatedEventListenerRegistration extends AbstractEventListenerRegistration {

	public MetadataUpdatedEventListenerRegistration() {
		super();
		clientId = "Metadata-Updated";
	}
		
	@Override
	public void contextInitialized(ServletContextEvent event) {
		clientId = "Metadata-Updated";
		
		log.info(">>> " + getClassName() + " >> contextInitialized() Start >>>");

		try {		
			SecuritySession session = com.opentext.otmm.sc.evenlistener.util.EventListenerUtils.getLocalSession(USER_ALIAS_TSUPER);

			MetadataUpdatedEventListener metatadaUpdatedEventListener = new MetadataUpdatedEventListener(OTMMEvent.METADATA_UPDATED);
			EventServices.getInstance().addEventListener(clientId, metatadaUpdatedEventListener, session);			
			
		} catch (BaseTeamsException e) {
			log.error("<<< ERROR in class " + getClassName() + " >> contextInitialized() <<< " + e.getMessage());	
		}		
		
		log.info("<<< " + getClassName() + " >> contextInitialized() End <<<");				
	}
}
