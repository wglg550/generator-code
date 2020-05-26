# Docker image for springboot file run
# VERSION 0.0.1
# Author: eangulee
# 基础镜像使用java
#FROM java:8
# 作者
#MAINTAINER wangliang <wg_lg520@21cn.com>
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
#VOLUME /tmp
# 将jar包添加到容器中并更名为app.jar
#ADD generator-code-1.0-SNAPSHOT.jar app.jar
#EXPOSE 8111
# 运行jar包
#RUN bash -c 'touch /app.jar'
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

FROM java:8
VOLUME /tmp
# 下面jar包的名称为springboot项目打包完成的jar包名称

#ADD generator-code-1.0-SNAPSHOT.jar app.jar
#EXPOSE 8111
#ENTRYPOINT ["java","-jar","/app.jar"]

COPY ./generator-code-1.0-SNAPSHOT.jar /app/app.jar
COPY ./application.yml /app/application.yml
EXPOSE 8111
ENTRYPOINT ["java" ,"-Djava.security.egd=file:/dev/./urandom --spring.config.location=classpath:file:/app/application-yml","-jar","/app/app.jar"]
