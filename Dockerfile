# 基础镜像
FROM openjdk:17-jdk

# 指定工作目录
WORKDIR /app

# 将 jar 包添加到工作目录，比如 target/yuoj-backend-user-service-0.0.1-SNAPSHOT.jar
ADD target/dwz-0.0.1-SNAPSHOT.jar .

# 暴露端口
EXPOSE 8070

# 启动命令
ENTRYPOINT ["java","-jar","/dwz-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]
