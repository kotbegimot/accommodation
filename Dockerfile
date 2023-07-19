FROM maven:3.8.5-openjdk-17 
COPY ./src /home/app/src
COPY ./pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
EXPOSE 8070
ENTRYPOINT ["java","-jar","/home/app/target/accommodation-1.0.0.jar", "-web -webAllowOthers -tcp -tcpAllowOthers -browser"]