FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER 876441905@qq.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /msdp-activiti

WORKDIR /msdp-activiti

EXPOSE 5005

ADD ./msdp-visual/msdp-activiti/target/msdp-activiti.jar ./

CMD java -Djava.security.egd=file:/dev/./urandom -jar msdp-activiti.jar
