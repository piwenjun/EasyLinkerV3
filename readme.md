# EasyBoot：基于Groovy实现的 Springboot 脚手架
# 1.软件简介

> ​       在之前的工作中，面对新的JavaEE 项目（现在JavaEE已经不存在了，在这里统代指JavaWeb项目），我经常重复性的做一些工作：新建项目->加入依赖->分模块->搭建基本框架······。这一系列动作几乎在每一个项目中都会或多或少的存在，几次下来感觉简直就是重复机械劳动，毫无意义，因此准备把常用的东西封装一个脚手架，以方便后续使用。
>
> ​        本项目全部使用Groovy来实现。为何使用Groovy？这又是另一个故事。我在上家公司的时候，老大推荐我学习Groovy和Kotlin，我刚接触Groovy以后就喜欢上了，简直就是“现代版的Java”，简洁的语法和与Java的无缝集成，刚学完就爱不释手，我觉得Groovy就是2019版的Java。在这里推荐各位朋友有兴趣可以学习一下这个语言，网上资料很多，这里就不做赘述。
>
> ​        希望有缘分能看到本项目的朋友，能从中学习到Groovy和Web项目的基本操作路线，希望本项目能给各位初学者，老手，还有兴趣爱好者提供一个良好的实践案例。
>
> ​        另外，因为本人能力有限，写出来的代码难免有BUG，希望有经验的朋友发现问题和BUG以后及时联系本人【751957846@qq.com】改正，以免产生误导。
>
> ###         感谢全世界的开源界大神无私贡献，才有了我们现在的这些技术架构，致敬每一个开源作者！

# 2.技术栈

> ​        本项目采用的是Spring Boot为核心的技术栈，各项技术和依赖环境如下表罗列：

| 框架名       | 功能               | 版本号 |
| ------------ | :----------------- | ------ |
| Springboot   | 核心框架           | 2.1.5  |
| Shiro        | 安全领域           | 1.4.0  |
| Jwt          | 认证Token提供者    | 0.7.0  |
| Hutool       | Java工具集合       | 4.1.12 |
| FastJson     | Json解析器         | 1.2.56 |
| Kaptcha      | 图片验证码生成框架 | 2.3.2  |
| Redis        | 缓存提供者         | 4.0+   |
| Mysql        | 核心数据库         | 5.7+   |
| 其他后续补上 | 。。。             | 。。。 |



# 3.主要功能

> ​        本框架尽可能的减少功能，尽可能的提供多的、通用的底层框架搭建，因为功能太多了不适合具体的业务开发，所以我设计了一套核心框架，和一套编码、开发规范，方便使用者灵活的变通自己的业务。
>
> ​        主要功能：用户的注册、登录授权、权限验证，没了（是不是很简单，但是确实可以快速实现一个项目，后面我会给出一个Demo）。

# 4.项目结构

> ​      项目结构见下表所示，注意，因为IDEA的maven默认不识别groovy的项目根路径，当导入项目以后，会提示找不到项目包，此时需要手动加入,操作步骤如下：
>
> #####         右键项目[src/main/groovy]目录，make directory as ->source root 即可

```text
├─main
│  ├─groovy
│  │  └─com
│  │      └─easylinker
│  │          └─easyboot
│  │              └─manager
│  │                  ├─common
│  │                  │  ├─config
│  │                  │  │  ├─captcha
│  │                  │  │  ├─jwt
│  │                  │  │  ├─mvc
│  │                  │  │  ├─redis
│  │                  │  │  ├─shiro
│  │                  │  │  └─transaction
│  │                  │  ├─controller
│  │                  │  ├─dao
│  │                  │  ├─entry
│  │                  │  │  ├─controller
│  │                  │  │  └─form
│  │                  │  ├─exception
│  │                  │  ├─model
│  │                  │  ├─service
│  │                  │  └─web
│  │                  ├─modules
│  │                  │  ├─device
│  │                  │  │  ├─controller
│  │                  │  │  ├─dao
│  │                  │  │  ├─form
│  │                  │  │  ├─model
│  │                  │  │  └─service
│  │                  │  ├─plugin
│  │                  │  │  ├─controller
│  │                  │  │  └─model
│  │                  │  └─user
│  │                  │      ├─controller
│  │                  │      ├─dao
│  │                  │      ├─form
│  │                  │      ├─model
│  │                  │      └─service
│  │                  └─utils
│  └─resources
│      ├─static
│      └─templates
└─test
    └─groovy
        └─com
            └─easylinker
                └─webterminal
                    └─manager
```



# 5.状态码对照表

> ​        养成良好的编程习惯，不仅有助于个人梳理业务，而且有利于维护同事关系，我给出了一个对照表，如有需要可以自行扩展。

| 状态码 | 含义           | 备注                                                   |
| ------ | -------------- | ------------------------------------------------------ |
| 0      | 所有的失败操作 | 业务层失败                                             |
| 1      | 所有的成功操作 | 业务层成功                                             |
| 2      | 请求缺少Token  | 除了放行的，所有的业务请求都必须在HTTP 头里面加入token |
| 3      | Token过期      | token有过期时间，一般为1天                             |
| 4      | 权限不足       | 比如USER角色访问了ADMIN的资源                          |
| 5      | 。。。         | 。。。                                                 |



# 6.交换格式

> ​        对于请求格式没有特殊的要求，返回格式有统一形式，下面举个例子讲解。
>
> ​        这是注册成功以后的返回值：

```json
{
    "msg": "注册成功!",
    "code": 200,
    "data": []
}
```

> msg: 返回提示消息
>
> code: 状态码，和上面的表一一对应
>
> data:  返回数据,是Object类型，可以是数组，也可以是一个JSONObject
>
> 对应的Java实现类如下：

```java
class R extends JSONObject{
    private boolean status
    private Integer code
    private String msg
    private Object data
}
```

> 如何返回一个提示？看下面的Demo：

```java
    @GetMapping("/hello")
    String hello() {
        R.okWithData("world")
    }
```

> ##### 上面的是本系统内部的一些默认实现，当然你熟悉结构以后，完全可以重新定义自己的标准。

## 7.开发指南

##### 1.导入代码

> git clone https://github.com/wwhai/EasyBoot.git



##### 2.添加模块

> ​        自己的业务实现放在：com.easylinker.easyboot.manager.modules 包下，下面举一个简单的例子，我要写一个Rest接口，怎么操作呢？再modules下新建模块包，然后加入RestController：

```java
package com.easylinker.easyboot.manager.modules.user.controller

import com.easylinker.easyboot.manager.common.controller.AbstractController
import com.easylinker.easyboot.manager.common.web.R
import org.apache.shiro.authz.annotation.RequiresAuthentication
import org.apache.shiro.authz.annotation.RequiresRoles
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import static org.apache.shiro.authz.annotation.Logical.OR

@RestController
@RequiresAuthentication
class TestController   {

    @GetMapping("/hello")
    String hello() {
        R.okWithData("world")

    }
}

```



> 返回结果：

```json
{
    "msg": "SUCCESS",
    "code": 1,
    "data": "world"
}
```



##### 3.数据层

> 如果用到了数据库，再model包下新建数据模型即可，JPA会自动建表：

```java
package com.easylinker.easyboot.manager.modules.user.model

import com.easylinker.easyboot.manager.common.model.AbstractModel
import com.easylinker.easyboot.manager.modules.device.model.Device
import lombok.Data

import javax.persistence.*


@Entity
@Data
class AppUser extends AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id
    private String principle
    private String password
    private String email
    private String name
    private Integer state
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "appUser")
    private List<Role> roles
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "appUser")
    private List<Device> devices
}
```



## 8.常用API

> 待定【预计7月初发布】



