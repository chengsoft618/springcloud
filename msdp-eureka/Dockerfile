FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER 876441905@qq.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /msdp-eureka

WORKDIR /msdp-eureka

EXPOSE 8761

ADD ./msdp-eureka/target/msdp-eureka.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar msdp-eureka.jar
