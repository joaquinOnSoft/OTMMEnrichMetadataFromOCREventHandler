/*
 * (C) Copyright 2020 Joaquín Garzón (http://opentext.com) and others.
 * 
 * Contributors:
 *   Joaquín Garzón - initial implementation
 */
package com.opentext.otmm.sc.evenlistener;

public interface OTMMEvent {
	/**
	 * <strong>EVENT_ID</strong>: 5006	
	 * <strong>DESCR</strong>: Analysis data from Azure is deleted	
	 * <strong>PUBLICATION_KEY</strong>: TEAMS.MEDIA_ANALYSIS
	 * <strong>MESSAGE</strong>: All the analysis data corresponding to media analysis job is deleted
	 * <strong>NOTE</strong>: Event stored at table: mm.EVENT_CTXTS
	 * <strong>IMPORTANT</strong> 
	 * The data from Azure is deleted regardless of whether the 
	 * extraction of media analytics succeeded or not. 
	 */
	public static final String ANALYSIS_DATA_FROM_AZURE_IS_DELETED = "5006";
	
	/**
	 * <strong>EVENT_ID</strong>: 2229145	
	 * <strong>DESCR</strong>: Asset Import	
	 * <strong>PUBLICATION_KEY</strong>: TEAMS.IMPORT
	 * <strong>MESSAGE</strong>: An asset was created
	 * <strong>NOTE</strong>: Event stored at table: mm.EVENT_CTXTS
	 */
	public static final String ASSET_CREATED = "2229145";

	/**
	 * <strong>EVENT_ID</strong>: 2229148	
	 * <strong>DESCR</strong>: Asset Import	
	 * <strong>PUBLICATION_KEY</strong>: TEAMS.IMPORT
	 * <strong>MESSAGE</strong>: Ending import job
	 * <strong>NOTE</strong>: Event stored at table: mm.EVENT_CTXTS
	 */
	public static final String IMPORT_JOB_ENDED = "2229148";
	
	/**
	 * <strong>EVENT_ID</strong>: 80008	
	 * <strong>DESCR</strong>: Metadata Updated	
	 * <strong>PUBLICATION_KEY</strong>: TEAMS.IMPORT
	 * <strong>MESSAGE</strong>: metadata was edited and saved
	 * <strong>NOTE</strong>: Event stored at table: mm.EVENT_CTXTS
	 */
	public static final String METADATA_UPDATED = "80008";
}
