# Mark OTMM customization for deletion in a containerized environment

In some circumstances, you may need to delete a customization deployed in a containerized OTMM.
Just follow these steps:

 * Access your environment
 * Open **pgAdmin** in a browser. i.e. *http://localhost:5050/browser/*
 * Browse to `Servers  » otmm-base  » Database  » otmm_db  » schema  » public  » Tables`   

   ![pgAdmin](../images/pgaadmin-otmm-support-manager.png)
 
 * Select the table called `otmm_support_registry`   
   
   ![pgAdmin](../images/pgadmin-edit-otmm-table-support-registry.png)
   
 * Right click on `otmm_support_registry` and select `View/Edit data  » All rows`
 
   ![pgAdmin](../images/pgadmin-otmm-support-manager-view-edit-data-otmm-support-registry.png)
    
  * Edit the value of the column `marked_for_deletion`. Set the value to `true`
 
    ![pgAdmin](../images/pgadmin-otmm-support-manager-edit-data-otmm-support-registry.png)
    
Now let's apply our changes in our containerized environment.     

 * Open `K9s` application
 * Select your OTMM pod called `otmm-0`
 * Make `CTRL + d` to delete your pod

  ![pgAdmin](../images/k9-delete-pod-otm-0.png) 
      
 * Select `OK` button and press `ENTER`
 * Wait until the pod is *terminated* and *running*. It'll take a few minutes.
 
Now you can access to your OTMM environment. The customization has been deleted.
 