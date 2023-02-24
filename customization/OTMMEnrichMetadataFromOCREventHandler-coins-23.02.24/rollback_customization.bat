@echo off

Echo Reverting OTMMEnrichMetadataFromOCREventHandler-coins-23.02.24 Customization from Installer Configuration

Echo Deleting OTMMEnrichMetadataFromOCREventHandler-coins-23.02.24.jar from %TEAMS_HOME%

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

del /Q %TEAMS_HOME%\\plugins\\OTMMEnrichMetadataFromOCREventHandler-coins-23.02.24.jar

if exist %JBOSS_HOME% (
	Echo Jboss Home : %JBOSS_HOME%
	cd %TEAMS_HOME%\\install\\ant
	ant -f %TEAMS_HOME%\\install\\ant\\build.xml build-customizations-module	
)

if exist %TOMEE_HOME% (
	Echo Tomee Home : %TOMEE_HOME%
	del /Q %TEAMS_HOME%\\ear\\artesia.ear\\lib\\OTMMEnrichMetadataFromOCREventHandler-coins-23.02.24.jar
)
