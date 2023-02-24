# Event listener registration

I am not aware of any method to customize the contents of the **web.xml** file with the installation/helm charts.  The only thing I could suggest to try and customize it would be to build a package to deploy in support manager that would alter it as you wish.  I would be cautious/discourage about making a copy of the file and putting a copy in the support manager package because if the file changes with an upgrade then your copy would not have the required changes and could break the upgrade. I think your script in the support-manager package would have to do a search and replace on the file to add the appropriate customizations.

Looks like inside the **otmm-0** pod the path to this file is: `/opt/OTMM/ear/artesia/otmmux/WEB-INF/web.xml`
