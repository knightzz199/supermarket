## 工程简介
基于springboot+mybatis+mysql+redis+rabbitmq+elasticsearch的商城系统

## 使用说明
1.克隆到本地
git clone https://github.com/knightzz199/supermarket.git

2.修改项目中所有的配置主机和密码
application.properties中mysql配置,redis配置,rabbitmq配置,config包中elasticsearch配置，gitee图床配置，还可以更改swagger的配置
修改完毕即可在本地启动项目

# 使用docker发布到服务器上启动
1.使用maven打包项目

2.编写Dockerfile文件如下

FROM java:8
COPY *.jar /app.jar
CMD ["--server.port=8080"]
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]

3.将打包的jar包和编写的Dockerfile文件用远程工具传到服务器上(我的是Cenos7.6 安装docker的步骤这里不细说了)

4.输入docker命令启动项目
构建镜像
docker build -t supermarket .
启动镜像
docker run -d -p 8080:8080 --name supermarket supermarket

## 接口文档查看和测试
该项目集成了swagger，项目通过docker发布到了服务器上，可以直接访问swagger界面查看所有借口并测试接口
http://81.68.91.48:8080/swagger-ui.html
我的阿里云服务器内存不够了，于是去白嫖了一个月的腾讯云服务器，新服务器没有注册域名，所有无法用nginx反向代理到简洁的域名，通过ip地址和端口号访问。
由于有拦截器，测试部分接口时记得先登录一下用户账号

## 设计方案
数据库的设计分为7个表：商家，用户，商品，订单，商品类型，用户收藏，用户地址。
用redis做缓存增加访问性能，用elasticsearch做搜索引擎，做商品的全局搜索，并高亮搜索关键词
题目要求的抢购功能由redis和rabbitmq实现，查询redis中商品库存，库存充足时进入消息队列，并将用户信息存入redis中，避免一个用户多次购买，此功能也可以用redis事务乐观锁来实现
图片上传的功能使用码云的图床实现，将用户上传的图片文件处理后形成一个url访问路径地址，再存入数据库中
登录设计使用session验证做拦截器，本来想实现QQ登录的功能，但申请被腾讯驳回了。
