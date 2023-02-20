/*
 * (C) Copyright 2020 Joaqu�n Garz�n (http://opentext.com) and others.
 * 
 * Contributors:
 *   Joaqu�n Garz�n - initial implementation
 */
package com.opentext.otmm.sc.evenlistener.handler;

import com.artesia.event.Event;

public interface OTMMEventHandler {

	public boolean handle(Event event);
}
