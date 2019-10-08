FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER 876441905@qq.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /msdp-codegen

WORKDIR /msdp-codegen

EXPOSE 5003

ADD ./msdp-visual/msdp-codegen/target/msdp-codegen.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar msdp-codegen.jar
