FROM centos:latest
VOLUME /tmp

#ENV
ENV	HOME /root
ENV	LANG en_US.UTF-8
ENV	LC_ALL en_US.UTF-8
ENV JAVA_VERSION 8u91
ENV BUILD_VERSION b14

#Upgrading system
RUN yum install -y curl; yum -y install wget; yum upgrade -y; yum update -y;  yum clean all

# Downloading Java
RUN wget --no-cookies --no-check-certificate --header "Cookie: oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/$JAVA_VERSION-$BUILD_VERSION/jdk-$JAVA_VERSION-linux-x64.rpm" -O /home/jdk-8-linux-x64.rpm
RUN rpm -ivh /home/jdk-8-linux-x64.rpm
ENV JAVA_HOME /usr/java/latest
RUN yum clean all

#ADD
ADD albatross-user-microservice-1.0.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 3001
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]