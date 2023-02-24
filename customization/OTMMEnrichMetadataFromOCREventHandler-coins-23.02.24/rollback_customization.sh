#!/bin/sh

echo "Reverting OTMMEnrichMetadataFromOCREventHandler-coins-23.02.24 Customization from Installer Configuration"

echo "Reverting web.xml without custom event listener"

mv /opt/OTMM/ear/artesia/otmmux/WEB-INF/web.xml /opt/OTMM/ear/artesia/otmmux/WEB-INF/web.xml.rollback
mv /opt/OTMM/ear/artesia/otmmux/WEB-INF/web.xml.back /opt/OTMM/ear/artesia/otmmux/WEB-INF/web.xml 

echo "Deleting OTMMEnrichMetadataFromOCREventHandler-coins-23.02.24.jar to $TEAMS_HOME"

rm "$TEAMS_HOME/plugins/OTMMEnrichMetadataFromOCREventHandler-coins-23.02.24.jar"

if [ -d "$JBOSS_HOME" ]
then
	echo "Jboss Home : $JBOSS_HOME"
	
	cd "$TEAMS_HOME/install/ant"
	ant -f "$TEAMS_HOME/install/ant/build.xml" "build-customizations-module"
fi

if [ -d "$TOMEE_HOME" ] || [ ! -d "$JBOSS_HOME" ]
then
	echo "Tomee Home : $TOMEE_HOME"
	
	rm "$TEAMS_HOME/ear/artesia.ear/lib/OTMMEnrichMetadataFromOCREventHandler-coins-23.02.24.jar"
fi
