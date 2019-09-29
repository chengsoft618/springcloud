FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER 876441905@qq.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /msdp-gateway

WORKDIR /msdp-gateway

EXPOSE 9999

ADD ./msdp-gateway/target/msdp-gateway.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar msdp-gateway.jar
