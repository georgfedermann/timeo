#!/usr/bin/env bash

mvn clean package
cp target/timeo-stakeholderservice.war /Users/georg/Software/tomcat/tomcat8/timeo-stakeholderservice/webapps/

