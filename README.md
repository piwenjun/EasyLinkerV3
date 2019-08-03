# EasyLinkerV3

## 1.项目介绍

​        说起V3就比较尴尬了，从2018年10月放鸽子到现在。主要因为本人为了生活在奔波吃饭，一次次的计划因为谋生而放弃。想起去年找工作那会，进了垃圾公司，被垃圾黑公司耗光了精力。那段时间过的真的是艰难。纵然是当时身心疲惫，但是一直没有忘记自己刚开始的想法：做一个通用的物联网设备控制台。

​         时间过的确实很快，一转眼就到2019年下半年了，今年工作还好，相对比较轻松，才有精力重新捡起来落满灰尘的开发板，继续V3的开发。

​         经过一年的社会洗礼，到现在从技术还是从处事能力，都有了明显提高，所以新版本的技术架构是对新接触物联网的WEB开发者是很有帮助的。

​        关于为何使用Groovy，主要还是为了省事，我没深入学过Groovy的高级特性，目前完全是拿来当现代Java用，所以大家不用感觉这个新语言很难，Java学Groovy两天就就够了，不外乎就是熟悉一下常见的套路。看一下马上就可以上手。

​        在此感谢【物联网开发交流群】的大佬李勇，群主赵雷给予的技术支持和指导，和EMQ，Activemq等无数优秀开源项目。还有小伙伴毓小六的长情♂陪伴。希望各位大佬越来越牛。

> 当你身处于艰难的日子的时候，想想自己这些年的追求和理想，可能艰难对你就是一种磨练了。
>
> ​                                                                                                                 -----------------------------沃厦基捌硕の

## 2.技术栈

​        SpringBoot全家桶，还有前端的VUE，数据库是MongoDb，都是大家很熟悉的技术，没有搞花里胡哨。本项目初衷就是为了提出一个通用的物联网平台的设计方案。越简单越好。

## 3.项目结构

项目名成就是含义，想必作为程序员基本都能做到见名知意：

![1564475295448](C:\Users\admin\Github\easyboot\doc_pic\1564475295448.png)

上图看起来很明了，这里就不废话了。不过为了方便大家部署和记忆，我整理了一下微服务的部署网络环境，微服务好比插件的形式，大家用到哪个微服务就启动哪个，通过网关进行请求，所以部署是非常自由的。

| 微服务节点         | IP：端口       | 备注           |
| ------------------ | -------------- | -------------- |
| EurekaServer       | localhost:8000 | 微服务注册中心 |
| SpringCloudGateway | localhost:8001 | 统一网关       |
| CoAP Service       | localhost:8002 | COAP入口       |
| MQTT Service       | localhost:8003 | Mqtt服务       |
| HTTP Service       | localhost:8004 | HTTP服务       |
| FILE Service       | localhost:8005 | 文件服务       |
| TCP Service        | localhost:8006 | TCP服务        |
| UDP Service        | localhost:8007 | UDP服务        |
| Terminal Service   | localhost:8008 | 终端模拟器服务 |

上面是扩展的微服务的端口，大家柯自行调整，但是V3我们还是继续用老传统：2500端口：

| 微服务节点 | IP：端口       | 备注             |
| ---------- | -------------- | ---------------- |
| V3         | localhost:2500 | 这就是V3核心服务 |

### 4.开始编码

如何从github拉取代码这里也不废话，只讲几个大家不太注意的细节问题。

当代码拉下来以后，导入IDEA，等所有的依赖和包都导入完成以后，猴急猴急的打开V3项目，熟练的运行main，你会发现什么？

![1564476080507](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\1564476080507.png)

是不是main运行不了？运行起来才怪。主要原因：IDEA对Groovy的SpringMVC支持的不太好，默认是Java项目的模式，我们新建了Groovy项目以后，需要手动配置源码路径，具体操作步骤：

![1564476523832](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\1564476523832.png)

右键项目的【groovy】目录，Mark As -->Sources Root，手动指定源码路径【classpath】，这样就可以了。