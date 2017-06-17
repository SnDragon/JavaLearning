# 说明文档
## 环境介绍
	Spring+SpringMVC+Mybatis+MySQL+Shiro+Maven
## 运行过程
### 1.用eclipse导入
应该可以看到如下目录结构：

![](http://i.imgur.com/f76eI6u.png)

### 2.执行SQL脚本
SQL脚本位于webapp->WEB-INF->sql下，该脚本在MySQL5.7上测试通过。
主要有五张表：

![](http://i.imgur.com/HupJjhX.png)

由用户、角色和权限之间的关系不难得出这五张表。

### 3.修改项目配置
要修改的主要是jdbc的连接配置，将jdbc.properties对应的属性修改成你本机的属性即可：

![](http://i.imgur.com/QoGWzSP.png)

### 4.确保Maven已导入相关依赖

---
## 项目演示

为方便演示，sql脚本中已有相应数据：

有两个用户：admin和user

admin的role为admin,user的role为user

role["admin"]具有user:view,user:create,user:update,user:delete四种权限

role["user"]只拥有user:view的权限


Shiro的核心配置如下：

![](http://i.imgur.com/ahXALOK.png)

接下来进行测试，启动项目，在浏览器中输入：

### http://localhost:8080/cg/index
由于/index配置了authc过滤器，因此用户在未认证之前会跳到loginUrl，也就是[http://localhost:8080/cg/login](http://localhost:8080/cg/login)进行认证：

![](http://i.imgur.com/2FYS7za.png)

输入正确的用户名跟密码(user/123456)后登录成功，再输入[http://localhost:8080/cg/index](http://localhost:8080/cg/index)，正常进入页面：


![](http://i.imgur.com/wm6yDCC.png)

此时用户role为user,拥有的权限为user:view,输入：[http://localhost:8080/cg/index2](http://localhost:8080/cg/index2 "http://localhost:8080/cg/index2"),[http://localhost:8080/cg/user](http://localhost:8080/cg/user "http://localhost:8080/cg/user"),
都可以正常访问，而[http://localhost:8080/cg/admin](http://localhost:8080/cg/admin "http://localhost:8080/cg/admin")则不能访问，由于用户已经登录，所以会跳到unauthorizedUrl即http://localhost:8080/cg/unauthorized，如图：


![](http://i.imgur.com/xp6TkIx.png)

注销：[http://localhost:8080/cg/logout](http://localhost:8080/cg/logout "http://localhost:8080/cg/logout")
之后会跳到登录页面，换成admin/123456登录再验证如上url。