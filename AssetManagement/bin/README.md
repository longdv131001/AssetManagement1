###To run locally:

> mvn spring-boot:run

App will be deployed on port 8080 by default


###To build artifact for release:

> mvn clean package -Puat

This will build with uat profile (application-uat.properties)

> mvn clean package -Pdev

This will build with dev profile (application-dev.properties)

###Copy built artifact:

Go to directory with pem file, then for dev profile war:

> scp -i .\rookies-java-2-as.pem :path-to-your-war\asset-management.war ubuntu@54.169.78.245:~/tomcat-dev/webapps


for uat profile war:

> scp -i .\rookies-java-2-as.pem :path-to-your-war\asset-management.war ubuntu@54.169.78.245:~/tomcat-uat/webapps