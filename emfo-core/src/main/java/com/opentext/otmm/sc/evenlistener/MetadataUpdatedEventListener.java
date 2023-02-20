/*
 * (C) Copyright 2020 Joaquín Garzón (http://opentext.com) and others.
 * 
 * Contributors:
 *   Joaquín Garzón - initial implementation
 */
package com.opentext.otmm.sc.evenlistener;

import com.artesia.entity.TeamsIdentifier;
import com.artesia.event.Event;
//import com.opentext.otmm.sc.evenlistener.handler.PlateIndentificationOnMetadataUpdate;
//import com.opentext.otmm.sc.evenlistener.handler.OTMMEventHandler;

public class MetadataUpdatedEventListener extends AbstractEventLister {

	public MetadataUpdatedEventListener() {
		super();
	}

	public MetadataUpdatedEventListener(String events) {
		super(events);		
	}


	@Override
	public void onEvent(Event event) {
		displayEventObject(event);
		
		if (event.getEventId().equals(new TeamsIdentifier(OTMMEvent.METADATA_UPDATED))) {
			log.info("Ids match for 'Metadata Updated (80008)' event. Event Id: " + event.getEventId());
			
			//OTMMEventHandler handler = new PlateIndentificationOnMetadataUpdate();
			//handler.handle(event);
		}
	}

}
