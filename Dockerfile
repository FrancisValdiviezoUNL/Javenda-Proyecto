FROM icr.io/appcafe/open-liberty:full-java21-openj9-ubi-minimal

# Argumento con valor por defecto
ARG ENV_DRIVER_JDBC_DIR=./target/lib/*.jar

#Funcionando con docker-compose
COPY --chown=1001:0 ${ENV_DRIVER_JDBC_DIR} /config/

COPY src/main/liberty/config/server.xml /config/server.xml

COPY target/jbrew-web.war /config/apps/
