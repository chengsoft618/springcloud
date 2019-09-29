FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER 876441905@qq.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /msdp-auth

WORKDIR /msdp-auth

EXPOSE 3000

ADD ./msdp-auth/target/msdp-auth.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar msdp-auth.jar
