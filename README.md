# 短链接服务

[项目地址 http://d.kazex.fun](http://d.kazex.fun)

长链接生成短链接，访问短链接 302 重定向至原始长链接

| 依赖           | 说明            |
|--------------|---------------|
| Spring Boot  | MVC 框架        |
| thymeleaf    | 模板引擎          |
| MyBatis-plus | ORM 框架        |
| Redis        | 缓存            |
| hutool       | Hash 算法、布隆过滤器 |

## 方法实现

- 短链接生成: 使用 MurmurHash 算法将原始长链接 hash 为 32 位散列值, 再将其转换为 Base62.
- 短链接缓存: 使用布隆过滤器保存短链接是否存在, 使用 Redis 缓存映射关系. 用户访问时会延长缓存时间.
- 短链接跳转: 使用 302 重定向至原始长链接，并记录短链接访问量.

## 技术要点

- MurmurHash: 长链转短链需要一个哈希算法，且不用考虑解码，只用关心运算速度和冲突概率，MurmurHash 是一种非加密型哈希算法，与 MD5、SHA 等常见哈希函数相比，性能与随机分布特征都要更佳。MurmurHash 有 32 bit、64 bit、128 bit 的实现，32 bit 已经足够表示近 43 亿个短链接。本项目使用 [hutool](https://github.com/dromara/hutool) 的实现。

- base62: MurmurHash 生成的哈希值最长有 10 位十进制数，为了进一步缩短短链接长度，可以将哈希值转为 62 进制，最长为 6 个字符。

- 布隆过滤器: 哈希函数不可避免会产生哈希冲突，随着短链接越来越多，冲突概率也会越大。每次生成短链接后，向布隆过滤器中查找是否已经存在此短链接，如果已经存在，则在长链接后添加一个自定义字符串，重新 hash，重复上一步，直到没有哈希冲突，把短链接加入布隆过滤器。这里使用 hutool 工具包中基于 JVM 的布隆过滤器来实现。

- Redis: 生成短链接后，通常在后续一段时间内此短链接的使用频率较高，则向 Redis 中添加带过期时间的缓存来减轻数据库压力。

- 302: 状态码：301 为永久重定向、302 为临时重定向，通常需要记录短链接访问次数或需要修改、删除短链接时，使用 302 临时重定向来处理，和服务器压力相比，数据的价值往往更大。
