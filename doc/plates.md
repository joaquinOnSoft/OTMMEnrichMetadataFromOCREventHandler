OpenText Media Management (OTMM) Event listener which listen the event **Metadata Updated (80008)**.
This event is launched once the metadata of an asset are updated.  

This add-on search for Spanish plate number in the OCR text found in the image

> IMPORTANT: These add-on has been developed and tested on OTMM 21.3

If a plate number is found, It's stored in these custom fields: 

 * **CUSTOM.FIELD.CAR.PLATE.NUMBER**
 * **CUSTOM.FIELD.CAR.PLATE.COUNTRY** 
 * **CUSTOM.FIELD.CAR.BRAND**

> NOTE: These custom field must be created from TEAMS administration panel.

> **IMPOTANT**: This code was done for a quick POC. The use of "Metadata Updated (80008)" 
> event is not the best choice for a production environment.
> In a production environment better options consist of overwrite the Import job.
> See the **Chapter 5 Jobs** of the document **OpenText? Media Management Integration Guide**
> available on  the **OpenText Knowledge Center**.
