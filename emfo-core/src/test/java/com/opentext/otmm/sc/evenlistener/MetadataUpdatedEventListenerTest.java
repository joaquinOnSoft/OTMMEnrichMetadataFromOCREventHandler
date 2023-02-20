/*
 * (C) Copyright 2023 Joaquín Garzón (http://opentext.com) and others.
 * 
 * Contributors:
 *   Joaquín Garzón - initial implementation
 */
package com.opentext.otmm.sc.evenlistener;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.artesia.entity.TeamsIdentifier;
import com.artesia.event.Event;
import com.artesia.security.SecuritySession;
import com.artesia.user.UserIdentifier;
import com.opentext.otmm.sc.evenlistener.helper.SecurityHelper;

public class MetadataUpdatedEventListenerTest {

	@Test
	public void onEvent() {
		 		
		Event ev = mock(Event.class);
		when(ev.getEventId()).thenReturn(new TeamsIdentifier("80008"));
		when(ev.getUserId()).thenReturn(new UserIdentifier("tsuper"));
		when(ev.getObjectId()).thenReturn("01dfae6e5d4d90d9892622325959afbe");
		when(ev.getObjectName()).thenReturn("Event");
		when(ev.getObjectType()).thenReturn("com.artesia.event.Event");
		when(ev.getDescription()).thenReturn("Metadata Updated");
		when(ev.getXmlContent()).thenReturn("");
				
		try (MockedStatic<SecurityHelper> securityHelper = Mockito.mockStatic(SecurityHelper.class)) {
			securityHelper.when(SecurityHelper::getAdminSession).thenReturn(new SecuritySession());
	    }		
					
		MetadataUpdatedEventListener eventListener = new MetadataUpdatedEventListener();
		eventListener.onEvent(ev);
	}
}
