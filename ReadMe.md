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
7.典型应用场景
- 前置（Pre)
    - 限流
    - 鉴权
    - 参数校验调整
- 后置（Post）
    - 统计
    - 日志
    
8.Zuul的高可用
- 多个Zuul节点注册到Eureka Server
- Nginx和Zuul"混搭"


# Zuul 
1.Pre和Post过滤器
- 继承ZuulFilter类
- 重写四个方法

```java
@Component
public class XXXFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        return null;
    }
}
```
- 具体配置见FilterConstants常量类

2.限流
> 时机：请求被转发之前调用
> 令牌桶限流
```java
public class RateLimitFilter extends ZuulFilter {
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);
    }
    
```
- import com.google.common.util.concurrent.RateLimiter;

3.鉴权（权限校验）
- 在前置过滤器中实现相关逻辑
- 分布式Session Vs OAuth2

4.跨域
- 跨域问题
- 在被调用的类或者方法上增加@CrossOrigin注解
    - 作用域在类和方法上
- 在Zuul里增加CorsFilter过滤器
    - 作用域在网关上
    
    
- @CrossOrigin (allowCredentials = "true")//单个方法（接口）跨域,(allowCredentials = "true")，允许cookie跨域
- Zuul中跨域配置
```java
@Component
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("*")); //原始域，如：http://www.a.com
        config.setAllowedHeaders(Arrays.asList("*"));//允许头
        config.setAllowedMethods(Arrays.asList("*"));//允许的方法，get,post ...
        config.setMaxAge(300l);//缓存时间
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
```

# Hystrix
1.Spring Cloud Hystrix
- 防止雪崩利器
- 基于Netflix对应的Hystrix
- 功能
    - 服务器降级
    - 依赖隔离
    - 服务熔断
    - 监控（Hystrix Dashboard)
    
2.服务降级
- 优先核心服务，非核心服务不可用或弱可用
- 通过HystrixCommand注解具体指定
- fallbackMethod(回退函数)中具体实现降级逻辑

> 触发降级
> - 依赖
> - 注解 @EnableCircuitBreaker和@HystrixCommand
> - fallbackMethod具体实现

> 超时设置
```java
  @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    }) 
    //超时时间设置
```

3.依赖隔离
- 线程池隔离
- Hystrix自动实现了依赖隔离

4.服务熔断
```java
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), //设置熔断
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//断路器最小请求数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),//断路器休眠时间窗，结束后会将断路器设置为half open；如果下一次请求成功，会主逻辑，断路器closed；如果下一次失败，断路器进入open
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")//断路器打开的错误率条件
    })
```
- Circuit Breaker：断路器

5.使用配置项——配置Hystrix
```properties
#hystrix全局配置超时时间
# hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=1000
#hystrix为某个方法配置超时时间（getProductInfoList)
# hystrix.command.getProductInfoList.execution.isolation.thread.timeoutInMilliseconds=3000
```
- 注意hystrix.command.default

6.feign-hystrix的使用
- 配置：feign.hystrix.enabled=true
- 服务调用接口：@FeignClient(name="product",fallback = ProductClient.ProductClientFallback.class)


# 服务追踪
1.链路监控
1.spring cloud sleuth
- 添加依赖
> <!--<dependency>-->
            <!--<groupId>org.springframework.cloud</groupId>-->
            <!--<artifactId>spring-cloud-starter-sleuth</artifactId>-->
        <!--</dependency>-->

- 修改配置文件
> logging.level.org.springframework.cloud.netflix.feigin=debug
2.zipkip
- docker部署zipkip
> docker run -d -p 9411:9411 openzipkin/zipkin
- 添加依赖
>      <!--<dependency>-->
              <!--<groupId>org.springframework.cloud</groupId>-->
              <!--<artifactId>spring-cloud-sleuth-zipkin</artifactId>-->
          <!--</dependency>-->

- 修改配置文件
> spring.zipkin.base-url=http://localhost:9411

3.集成步骤
- 引入依赖
> 包含sleuth和zipkin
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkip</artifactId>
        </dependency>
        
- 启动Zipkin Server
- 配置参数

4.分布式追踪系统
- 核心步骤
    - 数据采集
    - 数据存储
    - 查询显示
    
5.OpenTracing
- 优势
    - 来自CNCF
    - ZIKIP、TRACER、JAEGER、GRPC等
6.Annotation
- 事件类型
    - cs(Client Send)：客户端发起请求的时间
    - cr(Client Received)：客户端收到处理完成请求的时间
    - ss(Server Send)：服务端处理完成逻辑的时间
    - sr(Server Received)：服务端收到调用端请求的时间
- 客户端调用的时间=cr-cs
- 服务端处理的时间=sr-ss

7.ZipKin
- Zipkin 设计源于Google Dapper文章
- twitter开源
- 核心组件
    - UI
    - API
    - Collector
    - Storage
- 几个关键概念
    - traceId：全局跟踪id，跟踪的入口点
    - spanId：下一层的请求跟踪id
    - parentId：上一次请求id，将前后请求串联起来