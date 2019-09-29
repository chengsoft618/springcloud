FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER 876441905@qq.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /msdp-upms

WORKDIR /msdp-upms

EXPOSE 4000

ADD ./msdp-upms/msdp-upms-biz/target/msdp-upms-biz.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar msdp-upms-biz.jar
