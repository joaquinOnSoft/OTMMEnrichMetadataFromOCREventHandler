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
import com.opentext.otmm.sc.modules.coins.Coin;

public class CoinIndentificationOnMetadataUpdate extends AbstractOTMMEventHandler implements OTMMField {

	@Override
	public boolean handle(Event event) {
		boolean handled = false;

		log.info("CoinIndentificationOnMetadataUpdate.handle()");

		String strAssetId = event.getObjectId();

		log.info("assetId: " + strAssetId);

		if (strAssetId != null) {
			AssetIdentifier assetId = new AssetIdentifier(strAssetId);
			log.info("Initializing COIN indentification on asset: " + assetId.getId());

			MetadataCollection mc = retrieveMetadataForAsset(assetId,
					new TeamsIdentifier[] { 
							new TeamsIdentifier(ARTESIA_FIELD_MEDIAANALYSIS_OCR_TEXT),
							new TeamsIdentifier(CUSTOM_FNMT_FIELD_ANO),
							new TeamsIdentifier(CUSTOM_FNMT_FIELD_VALOR) 
						});
			
			if (mc != null) {				
				String year = null;
				String value = null;
			
				MetadataValue metadataYear = mc.getValueForField(new TeamsIdentifier(CUSTOM_FNMT_FIELD_ANO));
				MetadataValue metadataValue = mc.getValueForField(new TeamsIdentifier(CUSTOM_FNMT_FIELD_VALOR));					
				MetadataValue metadataOCRText = mc.getValueForField(new TeamsIdentifier(ARTESIA_FIELD_MEDIAANALYSIS_OCR_TEXT));
										
				if (metadataOCRText != null) {
					log.info("Recovering year and value from the coin picture (after OCR)");
					
					if(metadataYear != null && metadataValue != null) {
						String yearValue = metadataYear.getStringValue();
						String coinValue = metadataValue.getStringValue();
						
						if ((yearValue == null || yearValue.compareTo("") == 0) &&
								(coinValue == null || coinValue.compareTo("") == 0) ) {
							
							log.info("Coin metadata fields are all 'null' or 'empty'");
							
							String ocrText = metadataOCRText.getStringValue();
		
							log.info("OCR text: " + ocrText);
							if (ocrText != null) {
								if (Coin.hasYear(ocrText)){
									year = Coin.findFirstYear(ocrText);
									log.info("\tOCR text contains YEAR: " + year);
								}

								if (Coin.hasValue(ocrText)){
									value = Coin.findFirstValue(ocrText);
									log.info("\tOCR text contains DENOMINATION/VALUE: " + value);
								}								
								
								saveCoinDetails(assetId, year, value);
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
		}

		return handled;
	}
	
	private void saveCoinDetails(AssetIdentifier assetId, String year, String value) {
		MetadataField metaYear = new MetadataField(new TeamsIdentifier(CUSTOM_FNMT_FIELD_ANO));
		metaYear.setValue(year);
		MetadataField metaValue = new MetadataField(new TeamsIdentifier(CUSTOM_FNMT_FIELD_VALOR));
		metaValue.setValue(value);

		
		try {
			AssetServices.getInstance().lockAsset(assetId, SecurityHelper.getAdminSession());
			
			AssetMetadataServices.getInstance().saveMetadataForAssets(
					new AssetIdentifier[] {assetId},				
					new MetadataField[] { metaYear, metaValue}, 
					SecurityHelper.getAdminSession());
			
			AssetServices.getInstance().unlockAsset(assetId, SecurityHelper.getAdminSession());			
		} catch (BaseTeamsException e) {
			log.error("Saving car details.", e);
		}		
	}	
}
