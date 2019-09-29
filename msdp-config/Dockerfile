FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER 876441905@qq.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /msdp-config

WORKDIR /msdp-config

EXPOSE 8888

ADD ./msdp-config/target/msdp-config.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar msdp-config.jar
