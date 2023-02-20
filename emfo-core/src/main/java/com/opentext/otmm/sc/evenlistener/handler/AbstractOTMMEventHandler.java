/*
 * (C) Copyright 2020 Joaquín Garzón (http://opentext.com) and others.
 * 
 * Contributors:
 *   Joaquín Garzón - initial implementation
 */
package com.opentext.otmm.sc.evenlistener.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artesia.asset.AssetIdentifier;
import com.artesia.asset.metadata.services.AssetMetadataServices;
import com.artesia.common.exception.BaseTeamsException;
import com.artesia.entity.TeamsIdentifier;
import com.artesia.metadata.MetadataCollection;
import com.artesia.security.SecuritySession;
import com.opentext.otmm.sc.evenlistener.helper.SecurityHelper;

public abstract class AbstractOTMMEventHandler implements OTMMEventHandler{

	protected static final Log log = LogFactory.getLog(AbstractOTMMEventHandler.class);

	public AbstractOTMMEventHandler() {
		super();
	}

	protected MetadataCollection retrieveMetadataForAsset(AssetIdentifier assetId, String identifier) {
		return retrieveMetadataForAsset(assetId, new TeamsIdentifier(identifier));
	}

	protected MetadataCollection retrieveMetadataForAsset(AssetIdentifier assetId, TeamsIdentifier identifier) {
		return retrieveMetadataForAsset(assetId, new TeamsIdentifier[] { identifier });
	}

	/**
	 * Retrieve metadata fields for the asset
	 * 
	 * @param assetId - Asset unique identifier
	 * @return
	 */
	protected MetadataCollection retrieveMetadataForAsset(AssetIdentifier assetId, TeamsIdentifier[] identifiers) {
		log.debug("Asset Id: " + assetId.getId());
	
		SecuritySession session = SecurityHelper.getAdminSession();
	
		MetadataCollection assetMetadataCol = null;
		try {
			assetMetadataCol = AssetMetadataServices.getInstance().retrieveMetadataForAsset(assetId, identifiers,
					session);
		} catch (BaseTeamsException e) {
			log.error("Error retrieving metadata", e);
		}
	
		return assetMetadataCol;
	}
}