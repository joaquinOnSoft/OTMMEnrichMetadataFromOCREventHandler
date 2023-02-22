#!/bin/sh

echo "Reverting OTMMEnrichMetadataFromOCREventHandler-coins-23.02.22 Customization from Installer Configuration"

echo "Deleting OTMMEnrichMetadataFromOCREventHandler-coins-23.02.22.jar to $TEAMS_HOME"

rm "$TEAMS_HOME/plugins/OTMMEnrichMetadataFromOCREventHandler-coins-23.02.22.jar"

if [ -d "$JBOSS_HOME" ]
then
	echo "Jboss Home : $JBOSS_HOME"
	
	cd "$TEAMS_HOME/install/ant"
	ant -f "$TEAMS_HOME/install/ant/build.xml" "build-customizations-module"
fi

if [ -d "$TOMEE_HOME" ] || [ ! -d "$JBOSS_HOME" ]
then
	echo "Tomee Home : $TOMEE_HOME"
	
	rm "$TEAMS_HOME/ear/artesia/lib/OTMMEnrichMetadataFromOCREventHandler-coins-23.02.22.jar"
fi
