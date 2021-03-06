# Spring Cloud
> 微服务  ——> SpringCloud ——>  SpringBoot

> Spring Framework ——> Spring Boot ——> Spring Cloud

1.技术储备
- 对Spring Boot的基础知识熟练掌握
- 对Linux和Docker的基本用法熟练掌握

2.重点
- Spring Cloud构建微服务
- 微服务改造探讨

3.组件
- Eureka
    - Eureka Server
    - Eureka Client
    - Eureka高可用
    - 服务发现机制
    
- Config
    - Config Server
    - Config Client
    - Spring Cloud Bus(结合RabbitMQ)自动刷新
    
- Ribbon
    - RestTemplate
    - Feign
    - Ribbon(分析源码，了解底层)
- Zuul
    - 动态路由
    - 校验
- Hystrix
    - Hystrix Dashboard
    - 熔断机制
    
4.容器编排和服务追踪
- Docker + Rancher -> 容器编排
- Spring Cloud Sleuth + Zipkin -> 服务追踪

> Spring Cloud是一个开发工具集，包含了多个子项目

# 微服务和其他常见框架
> 微服务是一种架构风格
- 一系列微小的服务共同组成
- 跑在自己的进程里
- 每个服务为独立的业务开发
- 独立部署
- 分布式的管理

> 单一应用架构 -> 垂直应用架构 -> 分布式服务架构 -> 流动计算架构

> 微服务架构的基础框架/组件
- 服务注册发现
- 服务网关（Service Gateway）
- 后端通用服务（也称中间服务Middle Tier Service）
- 前端服务（也称边缘服务Edge Service）


# Eureka
1.Spring Cloud Eureka
- 基于Netflix Eureka做了二次封装
- 两个组件组成：
    - Eureka Server 注册中心
    - Eureka Client 服务注册
    
2.注册中心
- Eureka Server 
- @EnableEurekaServer
- eureka.client.service-url.defaultZone=http://localhost:8761/eureka
- eureka.client.register-with-eureka=false

3.服务注册
- Eureka Client
- @EnableDiscoveryClient

4.Eureka的高可用
- Eureka Server互相注册        eureka  <->  eureka
                                  |
                               client
    - eureka1: 
                - server.port=8761
                - eureka.client.service-url.defaultZone=http://localhost:8762/eureka                              
    - eureka2: 
               - server.port=8762
               - eureka.client.service-url.defaultZone=http://localhost:8761/eureka
- Client注册到多个注册中心
    - eureka.client.service-url.defaultZone=http://localhost:8761/eureka/,http://localhost:8762/eureka/
    
5.总结
- @EnableEurekaServer和@EnableEurekaClient
- 心跳检查、健康检查、负载均衡等功能
- Eureka的高可用，生产上建议至少两台以上
- 分布式系统中，服务注册中心是最重要的基础部分
[1.@EnableDiscoveryClient注解是基于spring-cloud-commons依赖，并且在classpath中实现；
 2.@EnableEurekaClient注解是基于spring-cloud-netflix依赖，只能为eureka作用；]
 
# 服务拆分的方法论

1.如何拆"数据"
- 每个微服务都有单独的数据存储
- 依据服务特点选择不同结构的数据库类型
- 难点在确定边界
- 针对边界设计API
- 依据边界权衡数据冗余

# 应用间通信
1.Http vs RPC

- Dubbo
    - RPC
- Spring Cloud       
    - 服务间两种restful调用方式
        - RestTemplate
        - Feign

2.RestTemplate
- 第一种方式（直接使用restTemplate，url写死）
```java
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("http://localhost:8080/msg", String.class);
        log.info("response {}", response);
```
    
- 第二种方式（利用loadBalancerClient通过应用名获取url，然后再使用restTemplate)
```java
        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort()) + "/msg";
        String response = restTemplate.getForObject(url, String.class);
        log.info("response {}", response);
```
    

- 第三种方式（利用@LoadBlanced，可在restTemplate里使用应用名字）
```java
        String response = restTemplate.getForObject("http://PRODUCT/msg", String.class);
        log.info("response {}", response);
        return response;
```
      
3.负载均衡器 Ribbon
- 客户端负载均衡器：Ribbon
    - RestTemplate
    - Feign
    - Zuul
    
- Ribbon 
    - 核心
        - 服务发现
        - 服务选择规则
        - 服务监听
    - 组件
        - ServerList
        - IRule
        - ServerListFilter
 4.Feign  
 - 使用
     - 添加依赖
        ```markdown
           <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-openfeign</artifactId>
            </dependency>
        ```
     - 启动主类上添加注解 @EnableFeignClients
     - 申明调用方法 @FeignClient
        ```java
        @FeignClient(name = "product")
        public interface ProductClient {
            @GetMapping("/msg")
            String productMsg();
        }
    
       ```  
       - 其中product是应用名 eureka
       - /msg是应用中url方法调用路径 
- 特点
    - 声明式REST客户端（伪RPC)
    - 采用了基于接口的注解
    
5.同步 VS 异步
- 消息中间件
    - RabbitMQ
    - Kafka
    - ActiveMQ
6.微服务和容器：天生一对
- 从系统环境开始，自底至上打包应用
- 轻量级，对资源的有效隔离和管理
- 可复用，版本化
> -Microservice
> - Docker
> - Devops

# 统一配置中心

1.为什么统一配置中心
- 不方便维护
- 配置内容安全与权限
- 更新配置项目需重启

2.统一配置中心(config server)
>                            product                                    
> 远端git -> config-server /
                           \ order
                | 
              本地git
- 配置依赖
- 启动类加注解
    - @EnableDiscoveryClient
    - @EnableConfigServer
- 配置（主要git配置)

3.config client
- 添加依赖
- 注意eureka的配置
- 有mysql等配置，需将启动类文件(application.yml)命名改为引导文件bootstrap.yml
- 客户端配置如下：
    ```yaml
    spring.cloud.config.discovery.enabled=true
    spring.cloud.config.discovery.service-id=CONFIG
    spring.cloud.config.profile=dev
    eureka.client.service-url.defaultZone=http://localhost:8762/eureka
    ```
    
4.spring cloud bus自动刷新配置
- 载体：消息队列
>                               消息队列
>                          |               |
>         /bus-refresh                   product                                    
> 远端git -> config-server /
                           \ order
                | 
              本地git
              
- 依赖
```markdown
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bus-amqp</artifactId>
        </dependency>
          <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-config-monitor</artifactId>
            </dependency>
```
- natapp
> 建立内网映射
> github webhooks自动post提交

# 异步和消息
1.异步的常见形态
- 通知
- 请求/异步响应
- 消息

2.MQ应用场景
- 异步处理
- 流量削峰
- 日志处理  如：Kafka
- 应用解耦

3.Spring Cloud Stream 
> Spring Cloud Stream 是一个为微服务构建消息驱动能力的框架，应用程序通过input与output和Stream中的Binder交互
- 依赖
>  <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
          </dependency>
      
- 接口
```java
public interface StreamClient {

    @Input("myMessage")
    SubscribableChannel input();


    @Output("myMessage")
    MessageChannel output();
}
```
- 接收方 
    - @EnableBinding(StreamClient.class)
    - @StreamListener("myMessage")    
    
- Stream分组
```yaml
spring.cloud.stream.bindings.myMessage.group=order
```

# 服务网关和Zuul
1.服务网关的要素
- 稳定性，高可用
- 性能、并发性
- 安全性
- 扩展性

2.常用的网关方案
- Nginx + Lua
- Kong
- Tyk
- Spring Cloud Zuul

3.Zuul的特点
- 路由+过滤器 = Zuul
- 核心是一系列的过滤器

4.Zuul的四种过滤器API
- 前置（Pre）
- 后置（Post）
- 路由（Route）
- 错误（Error)

5.Zuul自定义路由
> 配置文件
```properties
#Zuul自定义路由 product -> myProduct
#zuul.routes.myProduct.path=/myProduct/**
#zuul.routes.myProduct.service-id=product
#简洁写法
#zuul.routes.product=/myProduct/**
#cookie不过滤掉，配置为空
#zuul.routes.sensitiveHeaders=
#排除某些路由
#zuul.ignored-patterns=/**/product/listForOrder
```

6.动态配置路由
- git上配置配置文件，推送到项目
- 动态导入ZuulConfig
    ```java
@Component
public class ZuulConfig {

    @ConfigurationProperties("zuul")
    @RefreshScope
    public ZuulProperties zuulProperties() {
        return new ZuulProperties();
    }
}

```

    