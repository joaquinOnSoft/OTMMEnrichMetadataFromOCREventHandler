@echo off

EMFO_JAR=OTMMEnrichMetadataFromOCREventHandler-coins-23.02.28.jar

echo Reverting Coin metadata extractor custom event listener from Installer Configuration

echo "Deleting $EMFO_JAR from %TEAMS_HOME%"

if not defined JBOSS_HOME (
	set JBOSS_HOME=${jboss.home}
)

if not defined TOMEE_HOME (
	set TOMEE_HOME=${tomee.home}
)

set JBOSS_HOME=%JBOSS_HOME: =%
set TOMEE_HOME=%TOMEE_HOME: =%

if "%JBOSS_HOME%"=="" (
	set JBOSS_HOME=${jboss.home}
)
if "%TOMEE_HOME%"=="" (
	set TOMEE_HOME=${tomee.home}
)

del /Q %TEAMS_HOME%\\plugins\\$EMFO_JAR

if exist %JBOSS_HOME% (
	echo Jboss Home : %JBOSS_HOME%
	cd %TEAMS_HOME%\\install\\ant
	ant -f %TEAMS_HOME%\\install\\ant\\build.xml build-customizations-module	
)

if exist %TOMEE_HOME% (
	echo Tomee Home : %TOMEE_HOME%
	del /Q %TEAMS_HOME%\\ear\\artesia.ear\\lib\\$EMFO_JAR

	echo "Reverting web.xml without custom event listener"	
	cp "$TEAMS_HOME/ear/artesia.ear/otmmux.war/WEB-INF/web.xml" "$TEAMS_HOME/ear/artesia.ear/otmmux.war/WEB-INF/web.xml.rollback"
	mv "$TEAMS_HOME/ear/artesia.ear/otmmux.war/WEB-INF/web.xml.back" "$TEAMS_HOME/ear/artesia.ear/otmmux.war/WEB-INF/web.xml"
	# echo "Deleting reflections-0.10.2.jar"
	# rm "$TEAMS_HOME/ear/artesia.ear/otmmux.war/WEB-INF/reflections-0.10.2.jar"				
)

