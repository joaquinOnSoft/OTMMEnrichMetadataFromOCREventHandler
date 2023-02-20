/*
 * (C) Copyright 2021 Joaquín Garzón (http://opentext.com) and others.
 * 
 * Contributors:
 *   Joaquín Garzón - initial implementation
 */
package com.opentext.otmm.sc.evenlistener.handler;

import com.artesia.asset.AssetIdentifier;
import com.artesia.asset.metadata.services.AssetMetadataServices;
import com.artesia.asset.services.AssetServices;
import com.artesia.common.exception.BaseTeamsException;
import com.artesia.entity.TeamsIdentifier;
import com.artesia.event.Event;
import com.artesia.metadata.MetadataCollection;
import com.artesia.metadata.MetadataField;
import com.artesia.metadata.MetadataValue;
import com.opentext.otmm.sc.evenlistener.OTMMField;
import com.opentext.otmm.sc.evenlistener.helper.SecurityHelper;
import com.opentext.otmm.sc.modules.plates.Plate;

/**
 * Fraud Media Analysis (FMA) No metadata: The picture doesn’t include any
 * metadata
 */
public class PlateIndentificationOnMetadataUpdate extends AbstractOTMMEventHandler implements OTMMField {
	@Override
	public boolean handle(Event event) {
		boolean handled = false;

		log.info("PlateIndentificationOnEndingImportJob.handle()");

		String strAssetId = event.getObjectId();

		log.info("assetId: " + strAssetId);

		if (strAssetId != null) {
			AssetIdentifier assetId = new AssetIdentifier(strAssetId);
			log.info("Initializing plate indentification on asset: " + assetId.getId());

			MetadataCollection mc = retrieveMetadataForAsset(assetId, new TeamsIdentifier[] {
				new TeamsIdentifier(ARTESIA_FIELD_MEDIAANALYSIS_OCR_TEXT),
				new TeamsIdentifier(ARTESIA_FIELD_MEDIAANALYSIS_BRAND_NAME),
				new TeamsIdentifier(ARTESIA_FIELD_MEDIAANALYSIS_BRAND_CONFIDENCE),
				new TeamsIdentifier(CUSTOM_FIELD_CAR_PLATE_NUMBER),
				new TeamsIdentifier(CUSTOM_FIELD_CAR_PLATE_COUNTRY),
				new TeamsIdentifier(CUSTOM_FIELD_CAR_BRAND)
			});

			
			if (mc != null) {
				
				String plate = null;
				String countryName = null;
				String brand = null;				
				
				MetadataValue metadataPlate = mc.getValueForField(new TeamsIdentifier(CUSTOM_FIELD_CAR_PLATE_NUMBER));
				MetadataValue metadataCoutry = mc.getValueForField(new TeamsIdentifier(CUSTOM_FIELD_CAR_PLATE_COUNTRY));
				MetadataValue metadataBrand = mc.getValueForField(new TeamsIdentifier(CUSTOM_FIELD_CAR_BRAND));				
				
				MetadataValue metadataOCRText = mc.getValueForField(new TeamsIdentifier(ARTESIA_FIELD_MEDIAANALYSIS_OCR_TEXT));
				
				MetadataValue metadataRMABrand[] = mc.getValuesForTabularField(new TeamsIdentifier(ARTESIA_FIELD_MEDIAANALYSIS_BRAND_NAME));
				MetadataValue metadataRMABrandConfidence[] = mc.getValuesForTabularField(new TeamsIdentifier(ARTESIA_FIELD_MEDIAANALYSIS_BRAND_CONFIDENCE));
				if(metadataRMABrand != null && metadataRMABrand.length > 0) {
					int brandConfidence = metadataRMABrandConfidence[0].getIntValue();
					if (brandConfidence >= 75) {
						brand = metadataRMABrand[0].getStringValue();
						log.info("Brand: " + brand);
					}
					else {
						log.info("Brand IGNORED. Confidence level minor than 75%");
					}
				}				
				
				if (metadataOCRText != null) {
					log.info("Recovering car plate related fields.");
					
					if(metadataPlate != null && metadataCoutry != null && metadataBrand != null) {
						String plateValue = metadataPlate.getStringValue();
						String countryValue = metadataCoutry.getStringValue();
						String brandValue = metadataBrand.getStringValue();
						
						if ((plateValue == null || plateValue.compareTo("") == 0) &&
								(countryValue == null || countryValue.compareTo("") == 0) &&
								(brandValue == null || brandValue.compareTo("") == 0) ) {
							
							log.info("Plate metadata fields are all 'null' or 'empty'");
							
							String ocrText = metadataOCRText.getStringValue();
		
							log.info("OCR text: " + ocrText);
							if (ocrText != null) {
								if (Plate.containsPlate(ocrText)){
									plate = Plate.findFirstPlate(ocrText);
									log.info("\tOCR text contains plate: " + plate);
								}

								countryName = Plate.findFirstCountryCode(ocrText);
								if (countryName != null) {
									log.info("\tOCR text contains country: " + countryName);
								}
								
								saveCarDetails(assetId, plate, countryName, brand);
							}
						} else {
							log.info("Plate metadata fields previously filled!!!");
						}
						
					} else {
						log.info("Plate metadata fields NOT associated with this asset!!!");
					}
					
				} else {
					log.info("OCR text NOT FOUND!!!");
				}					

				handled = true;

			} else {
				log.info("Assets 'Text on Image' metadata NOT FOUND for asset: " + assetId.getId());
			}
		} else {
			log.info("Asset Id was NULL!!!");
		}

		return handled;
	}

	private void saveCarDetails(AssetIdentifier assetId, String plate, String countryName, String brand) {
		MetadataField metaPlate = new MetadataField(new TeamsIdentifier(CUSTOM_FIELD_CAR_PLATE_NUMBER));
		metaPlate.setValue(plate);
		MetadataField metaCountry= new MetadataField(new TeamsIdentifier(CUSTOM_FIELD_CAR_PLATE_COUNTRY));
		metaCountry.setValue(countryName);
		MetadataField metaBrand = new MetadataField(new TeamsIdentifier(CUSTOM_FIELD_CAR_BRAND));
		metaBrand.setValue(brand);
		
		try {
			AssetServices.getInstance().lockAsset(assetId, SecurityHelper.getAdminSession());

			
			AssetMetadataServices.getInstance().saveMetadataForAssets(
					new AssetIdentifier[] {assetId},				
					new MetadataField[] { metaPlate, metaCountry, metaBrand }, 
					SecurityHelper.getAdminSession());
			
			AssetServices.getInstance().unlockAsset(assetId, SecurityHelper.getAdminSession());			
		} catch (BaseTeamsException e) {
			log.error("Saving car details.", e);
		}
		
	}
}
