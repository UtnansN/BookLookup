FROM gradle:6.7.0-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle assemble --no-daemon

FROM tomcat:9.0
COPY --from=build /home/gradle/src/build/libs/*.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]