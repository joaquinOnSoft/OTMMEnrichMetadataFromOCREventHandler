#!/bin/sh

EMFO_JAR=OTMMEnrichMetadataFromOCREventHandler-coins-23.02.28.jar

echo "Reverting 'Enrich metadata from OCR event handler (coins)' from Installer Configuration"

echo "Deleting $EMFO_JAR from $TEAMS_HOME"

rm -rf "$TEAMS_HOME/plugins/$EMFO_JAR"

if [ -d "$JBOSS_HOME" ]
then
	echo "Jboss Home : $JBOSS_HOME"
	
	cd "$TEAMS_HOME/install/ant"
	ant -f "$TEAMS_HOME/install/ant/build.xml" "build-customizations-module"
fi

if [ -d "$TOMEE_HOME" ] || [ ! -d "$JBOSS_HOME" ]
then
	echo "Tomee Home : $TOMEE_HOME"
	
	rm "$TEAMS_HOME/ear/artesia.ear/lib/$EMFO_JAR"
fi

