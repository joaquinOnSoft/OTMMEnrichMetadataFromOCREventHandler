/*
 * (C) Copyright 2020 Joaquín Garzón (http://opentext.com) and others.
 * 
 * Contributors:
 *   Joaquín Garzón - initial implementation
 */
package com.opentext.otmm.sc.evenlistener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.reflections.Reflections;

import com.artesia.entity.TeamsIdentifier;
import com.artesia.event.Event;
import com.opentext.otmm.sc.evenlistener.handler.AbstractOTMMEventHandler;
import com.opentext.otmm.sc.evenlistener.handler.OTMMEventHandler;

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

			// OTMMEventHandler handler = new PlateIndentificationOnMetadataUpdate();
			// handler.handle(event);

			Reflections reflections = new Reflections("com.opentext.otmm.sc.evenlistener.handler");
			Set<Class<? extends AbstractOTMMEventHandler>> classes = reflections
					.getSubTypesOf(AbstractOTMMEventHandler.class);
			for (Class<? extends AbstractOTMMEventHandler> aClass : classes) {
				log.info("Event handler added: " +  aClass.getName());

				try {
					Method handle = aClass.getDeclaredMethod("handle", Event.class);
					
					OTMMEventHandler handler = aClass.getDeclaredConstructor().newInstance();

					handle.invoke(handler, event);
										
					log.info("Event handler called: " +  aClass.getName());
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | SecurityException | NoSuchMethodException e) {
					log.error(e);
				}
			}
		}
	}

}
