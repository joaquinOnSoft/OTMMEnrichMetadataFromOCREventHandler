@echo off

Echo Installing OTMMEnrichMetadataFromOCREventHandler-coins-23.02.22 Customization in Installer Configuration

Echo Adding OTMMEnrichMetadataFromOCREventHandler-coins-23.02.22.jar to %TEAMS_HOME%

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

copy %CD%\\OTMMEnrichMetadataFromOCREventHandler-coins-23.02.22.jar %TEAMS_HOME%\\plugins

if exist %JBOSS_HOME% (
	Echo Jboss Home : %JBOSS_HOME%
	cd %TEAMS_HOME%\\install\\ant
	ant -f %TEAMS_HOME%\\install\\ant\\build.xml build-customizations-module
)

if exist %TOMEE_HOME% (
	Echo Tomee Home : %TOMEE_HOME%
	
	Echo Installing in Installer Configuration
	copy %CD%\\OTMMEnrichMetadataFromOCREventHandler-coins-23.02.22.jar %TEAMS_HOME%\\ear\\artesia.ear\\lib
)
