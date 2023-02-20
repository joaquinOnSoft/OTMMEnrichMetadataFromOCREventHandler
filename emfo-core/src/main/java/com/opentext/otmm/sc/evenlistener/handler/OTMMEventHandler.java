/*
 * (C) Copyright 2020 Joaquín Garzón (http://opentext.com) and others.
 * 
 * Contributors:
 *   Joaquín Garzón - initial implementation
 */
package com.opentext.otmm.sc.evenlistener.handler;

import com.artesia.event.Event;

public interface OTMMEventHandler {

	public boolean handle(Event event);
}
