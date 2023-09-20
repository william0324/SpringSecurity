# SpringSecurity
##### v0.0.1

数据库认证加授权 前端采用Thymelef hutool工具获取二维码

##### v1.0.1

JWT+Spring Security+redis+mysql

- 配置jwt工具类，主要包含三个方法：

  1. createToken 创建令牌
  2. verifyToken 验证令牌
  3. DecodedJWT 解析令牌，获取令牌中自定义的用户信息

- 认证成功处理器

  - 根据用户信息生成jwt
  - 响应前端一个Token字符串
  - 将生成的jwt放入到redis中，设置过期时间和jwt的过期时间

- jwt过滤器，用于检查token，redis中是否有token等

  - 过滤条件如下:

  ```mermaid
  graph TD
  如果是登录请求uri,直接放行 --> 请求头中的Authorization,为空,那么不允许用户访问 --> 去掉bearer后,如果为空,不允许访问 --> 校验jwt -->判断redis中是否存在jwt
  ```

  - 过滤成功最后执行如下操作:
    - 从jwt中获取用户信息和权限信息
    - 反序列化成对象user
    - ==UsernamePasswordAuthenticationToken==
    - 把token放到安全上下文中

- 修改 web安全配置类WebSecurityConfig

  - `.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class)`

- ### **实现用户退出的问题**

  问题：因为JWT无状态，如果要实现退出功能无法实现。

  解决办法:

  使用redis

  步骤：

  ①　登陆成功之后把生成JWT存到redis中

  | key            | value                               |
  | -------------- | ----------------------------------- |
  | logintoken:jwt | UsernamePasswordAuthenticationToken |

  ②　用户退出时，从redis中删除该token

  ③　用户每次访问时，先校验jwt是否合法，如果合法再从redis里面取出logintoken:jwt判断这个jwt还存不存在，如果不存在就说是用户已经退出来，就返回未登陆。

