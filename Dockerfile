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
#VOLUME /tmp
ENV TZ Asia/Shanghai
ENV JAVA_OPTS=$JAVA_OPTS
# 下面jar包的名称为springboot项目打包完成的jar包名称

#ADD generator-code-1.0-SNAPSHOT.jar app.jar
#EXPOSE 8111
#ENTRYPOINT ["java","-jar","/app.jar"]

#docker-maven-plugin打包时需要一下参数 start
#COPY ./generator-code-1.0-SNAPSHOT.jar /app/app.jar
#COPY ./application.yml /app/application.yml
#docker-maven-plugin打包时需要一下参数 end
WORKDIR /app
ARG JAR_FILE
ADD ${JAR_FILE} /app/app.jar
#ARG PROP_FILE
#ADD ${PROP_FILE} /app/application.yml
#EXPOSE 8111
#亲测可行,指定配置文件
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom --spring.config.location=classpath:file:/app/application-yml","-jar","/app/app.jar"]
#亲测可行,手动xmx,指定配置文件
#ENTRYPOINT ["java","-Xmx512m","-Xms512m","-XX:MaxPermSize=128m","-XX:PermSize=128m","-Djava.security.egd=file:/dev/./urandom --spring.config.location=classpath:file:/app/application-yml","-jar","/app/app.jar"]
#亲测可行,动态xmx,能指定配置文件
#ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar -Dspring.config.location=classpath:file:/app/application-yml"]
#亲测可行,动态xmx
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]

#亲测可行,动态xmx,不能指定配置文件
#ENTRYPOINT java $JAVA_OPTS -jar /app/app.jar
#CMD java -XX:InitialRAMPercentage=50.0 -XX:MaxRAMPercentage=50.0 -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m $JAVA_OPTS -jar /opt/app.jar
