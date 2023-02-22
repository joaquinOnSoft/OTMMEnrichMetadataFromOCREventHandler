#!/bin/sh

echo "Installing OTMMEnrichMetadataFromOCREventHandler-coins-23.02.22 Customization in Installer Configuration"

echo "Adding OTMMEnrichMetadataFromOCREventHandler-coins-23.02.22.jar to $TEAMS_HOME"

cp "$PWD/OTMMEnrichMetadataFromOCREventHandler-coins-23.02.22.jar" "$TEAMS_HOME/plugins"
chmod 775 "$TEAMS_HOME/plugins/OTMMEnrichMetadataFromOCREventHandler-coins-23.02.22.jar"

if [ -d "$JBOSS_HOME" ]
then
	echo "Jboss Home : $JBOSS_HOME"
	
	cd "$TEAMS_HOME/install/ant"
	ant -f "$TEAMS_HOME/install/ant/build.xml" "build-customizations-module"
fi

if [ -d "$TOMEE_HOME" ] || [ ! -d "$JBOSS_HOME" ]
then
	echo "Tomee Home : $TOMEE_HOME"
	                                                                   
	cp "$PWD/OTMMEnrichMetadataFromOCREventHandler-coins-23.02.22.jar" "$TEAMS_HOME/ear/artesia.ear/lib"
fi