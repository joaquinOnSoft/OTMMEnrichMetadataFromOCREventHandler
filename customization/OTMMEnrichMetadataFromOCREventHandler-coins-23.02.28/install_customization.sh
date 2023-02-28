#!/bin/sh

EMFO_JAR=OTMMEnrichMetadataFromOCREventHandler-coins-23.02.28.jar

echo "Installing Coin metadata extractor custom event listener"

echo "Adding $EMFO_JAR to $TEAMS_HOME"

cp "$PWD/$EMFO_JAR" "$TEAMS_HOME/plugins"
chmod 775 "$TEAMS_HOME/plugins/$EMFO_JAR"

if [ -d "$JBOSS_HOME" ]
then
	echo "Jboss Home : $JBOSS_HOME"
	
	cd "$TEAMS_HOME/install/ant"
	ant -f "$TEAMS_HOME/install/ant/build.xml" "build-customizations-module"

	# NOT TESTED with JBOSS
	echo "Adding web.xml with custom event listener"
	mv "$TEAMS_HOME/ear/artesia/otmmuxr/WEB-INF/web.xml" "$TEAMS_HOME/ear/artesia/otmmux/WEB-INF/web.xml.back"	
	cp "$PWD/web.xml" "$TEAMS_HOME/ear/artesia/otmmux/WEB-INF/web.xml"
	
	#echo "Copying reflections-0.10.2.jar"
	#cp "$PWD/reflections-0.10.2.jar" "$TEAMS_HOME/ear/artesia/otmmux/WEB-INF/reflections-0.10.2.jar"
fi

if [ -d "$TOMEE_HOME" ] || [ ! -d "$JBOSS_HOME" ]
then
	echo "Tomee Home : $TOMEE_HOME"
	
	cp "$PWD/$EMFO_JAR" "$TEAMS_HOME/ear/artesia.ear/lib"

	echo "Adding web.xml with custom event listener"
	mv "$TEAMS_HOME/ear/artesia.ear/otmmux.war/WEB-INF/web.xml" "$TEAMS_HOME/ear/artesia.ear/otmmux.war/WEB-INF/web.xml.back"
	cp "$PWD/web.xml" "$TEAMS_HOME/ear/artesia.ear/otmmux.war/WEB-INF/web.xml"
	
	# echo "Copying reflections-0.10.2.jar"	
	# cp "$PWD/reflections-0.10.2.jar" "$TEAMS_HOME/ear/artesia.ear/otmmux.war/WEB-INF/reflections-0.10.2.jar"
fi

