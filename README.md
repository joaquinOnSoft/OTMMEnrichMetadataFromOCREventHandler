# OTMMEnrichMetadataFromOCREventHandler
**OpenText Media Management (OTMM)** Event listener which listen the event **Metadata Updated (80008)**.
This event is launched once the metadata of an asset are updated.  

> IMPORTANT: These add-on has been developed and tested on OTMM 22.4

## Sub-projects 
There are two sub-projects:
 * [Plates](doc/plates.md): This add-on search for Spanish plate number in the OCR text found in the image
 * [Coins](doc/coins.md): This add-on search for coin's year and value in the OCR text found in the image
 
## Deployment

### Event 

* Browse to your *Media Management Administration Dashboard*: &lt;OTMM_BASE&gt;/teams
* Click on `System » Event`

  ![System » Event](images/otmm-dashboard-system-events.png)
  
* Introduce `80008` in the *Search...* text box and press *ENTER*

> **80008** event means	`metadata was edited and saved`

  ![System » Event](images/otmm-dashboard-system-events-8008-external-use-allowed.png)

* Click on `External Use Allowed` if disabled

  ![System » Event](images/otmm-dashboard-system-events-update-event.png)
  
* Click on `Save` button

### Event manager  
Environment > Support Manager