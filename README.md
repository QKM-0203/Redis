<<<<<<< HEAD
## Redis入门
**Redis 是一个开源（BSD许可）的，内存中的数据结构存储系统，它可以用作数据库、缓存和消息中间件。
它支持多种类型的数据结构，如 字符串（strings）， 散列（hashes）， 列表（lists）， 集合（sets），
有序集合（sorted sets） 与范围查询， bitmaps， hyperloglogs 和 地理空间（geospatial） 索引半径查询。
Redis 内置了 复制（replication），LUA脚本（Lua scripting）， LRU驱动事件（LRU eviction），
事务（transactions） 和不同级别的 磁盘持久化（persistence）， 并通过 Redis哨兵（Sentinel）和
自动 分区（Cluster）提供高可用性（high availability）。**
## Redis的作用
**内存数据存储,持久化,效率高用于高速缓.**
## 连接本机redis
```java
redis-cli -p 6379 
```
## 关闭连接
```java
shutdown
```
**redis有16个数据库,默认使第0个**
## 切换数据库
```java
select 数字
```
## 数据库大小
```java
dbsize
```
## 清空数据
```java
flushdb
```
## 清空所有数据库的数据
```java
flushall
```
## key是否存在
```java
exists key
```
## 查询所有的key
```java
key *
```
## 移除key
```java
move key 数据库数字
```
##查询key的类型
```java
type key
```
## 设置过期时间
```java
expire key 时间
```
## 查询剩余时间
```java
ttl key
```
## redis是单线程
**读取数据的效率是cpu>内存>磁盘,在cpu中存取会出现多线程.虽然多线程会增加效率.但是cpu的调度会影响效率,
而redis相当于在内存中存取,没有上下文切换,效率就是最快的.**
## redis的五大基本数据类型
### String
**追加字符串**
```java
append  key "string"(如何key不存在,就相当于set key)
```
**字符串长度**
```java
strlen key
```
**自增1**
```java
incr key
```
**自减1**
```java
decr key
```
**自增自减指定步长**
```java
incrby key 数字
decrby key 数字
```
**截取[闭区间],替换[闭区间]**
```java
getrange key 0 3 (0,1,2,3)   (0,-1)就是全部的值
setrange key 0 xx (就会把从0开始的两个字符换成xx,后面的不变)
```
**设置一个过期时间的key**
```java
setex key 时间 value
```
**如果没有key就创建,否则创建失败**
```java
setnx key value
```
**批量创建key和获取key**
```java
mset k1 v1 k2 v2
mget k1 k2
```
**创建对象**
```java
方式1: set user:1 {name:qkm,age:18}   key就是user:1 value就是{name:qkm,age:18} 
方式2: mset user:1:name qkm user:1:age 18   key就是user:1:name ,user:1:age value 就是qkm,18
```
**组合使用**
```java
getset  先get在set 如果get不到就不输出结果同时set,第二次在get就会获取到
```
### List(都以l开头)
**list可以实现栈,队列,阻塞队列**
**从左边开始放值**
```java
lpush list one(以空格分开可以放多个值)
lpush list two
//查询list中所有的值    
lrange list 0 -1  // two one
```
**从右边开始放值**
```java
rpush list four
rpush list five
lrange list 0 -1 // teo one four five
```
**从左边/右边弹出值**
```java
lpop list 
rpop list
```
**获取指定下标的值**
```java
lindex list 0
```
**获取list长度**
```java
llen list
```
**移除指定的值**
```java
lrem list 数字 one   移除几个one
```
**截取list**
```java
ltrim list 1 2 只截取list的1和2
```
**将最后一个元素pop出去在push到另一个列表中**
```java
rpoplpush list mylist list是原先的列表,mylist是另外的一个列表
```
**替换列表中的某个值**
```java
lset list 0 itm  替换列表中第0个位置为itm,但前提必须是列表存在且有第0个元素,否则会报错
```
**插入值**
```java
linsert  list  before/after value newvalue    在某个值的前还是后插入一个值
```
### Set(都是以S开头)
**增加值**
```java
sadd set one
```
**获取所有的set值**
```java
smembers set
```
**获取set的元素个数**
```java
scard set
```
**获取是否有当前的元素**
```java
sismember set  one
```
**移除指定的值**
```java
srem set one
```
**移除随机的set中的值**
```java
spop set
```
**获取set中随机的多少个值**
```java
srandmembers set 数字
```
**移动指定的值到指定的集合**
```java
smove set myset one 将set中的one移动到myset 
```
**集合的并集**
```java
sunion set1 set2
```
**集合的差集**
```java
sdiff set1 set2
```
**集合的交集**
```java
sinter set1 set2
```
### Hash(哈希)
**key-map的形式**
**给hash中添加一个key-value**
```java
hset hash f1 v1
```
**设置多个键值对**
```java
hmset hash f2 v2 f3 v3
```
**获取hash中的key的value**
```java
hget hash f1   获取多个同样是加m
```
**获取hash中全部的key-value**
```java
hgetall hash
```
**删除hash中指定的key-value**
```java
hdel hash f1
```
**hash的长度**
```java
hlen hash
```
**判断hash是否存在指定的键**
```java
hexists hash f1
```
**获取hash中指定的键**
```java
hkeys hash
```
**获取hash中所有的值**
```java
hvals hash
```
**如果hash不存在指定的key-value就创建**
 ```java
hsetnx hash key value
```
**自增某个key对应的value**
```java
hincrby hash key 数字
```
### Zset(有序集合,就是在之前的set添加一个score属性,score可以重复但是属性不能重复)
**添加数据**
```java
zadd zset 1 qkm
```
**获取指定区间的数据**
```java
zrangebyscore zset -inf +inf withsorce(附score) 也可以是排序默认升序,也可以是显示全部的元素
```
**获取指定的索引区间下的元素**
```java
zrange zset 0 1 withsorce(附score)   这个有序集合默认添加进去的元素默认按照score排序递增如果写0 -1也就是升序排序输出,获取的是下标0和1号的对应元素
zrevrange是降序,降序查找指定区间时数字就得从大到小了,但是根据索引查找就不需要
```
**移除元素**
```java
zrem zset qkm
```
**获取集合个数**
```java
zcard zset
```
**某个区间有多少个值**
```java
zcount zset -1 2   就是看-1到2这个区间中你的集合符合条件的有多少个值
```
## 三种特殊的数据结构
### geospatial
**可以用于查找附近的的人,两地的距离**
**增添信息 key -纬度 -经度 名称**
```java
geoadd china:city  116.40 39.90 beijing 121.47 31.23 shanghai
        106.50 29.53 chongqing  114.05 22.52 shenzhen 120.16 30.24 hangzhou 
        108.96 34.26 xian
```
**获取当前城市的维度经度**
```java
geopos chins:city xian chongqing   查询多个城市
```
**查询两地的距离**
```java
geodist china:city xian beijing km   //910.0565
```
**查看附近的人(必须获取附近的人的定位)**
```java
//georadius 就是以一个指定的经纬度然后方圆多少km的地方
georadius china:city 110 30 500 km withcoord(带上经度纬度) count 3 查询指定的数量
```
**查找指定城市周围多少km的城市**
```java
georadiusbymember china:city beijing 500 km
```
**geohash 将经纬度转换成字符串**
```java
geohash china:city xian   //wqj6zky6bn0
```
**geo的底层实现原理是由Zset实现的,所以他的命令也适用于geo,zrange china:city 就可以查看里面有多少个元素**
### Hyperloglog
**用作基数统计的算法,基数就是一个集合中不重复的数据**
**优点:占用的内存是固定的,2的64次方的数据只需要12kb的内存**
**增加元素**
```java
pfadd mykey a b c d d
pfadd mykey1 a v f e d d
```
**统计mykey中的基数数量(没有重复的数量)**
```java
pfcount mykey   // 4 
```
**和并两个pf**
```java
pfmerge mykey3 mykey1 mykey  //a b c d v f e(同样和并之后没有重复的数量)
```
**当允许有容错出现时可以使用,可以统计哪个用户访问网站,重复也没关系因为技术就不算,set就比较耗内存**
### Bitmaps
**位存储 只有0 1 表示**
**使用位存储打卡记录**
```java
0没卡,1打卡
setbit sign 1 0
setbit sign 2 1
setbit sign 3 1
setbit sign 4 0
```
**看哪天是否打卡**
```java
getbit sign  4 //1
```
**统计打卡的天数**
```java
bitcount sign  //2
```
## redis事务
**redis事务没有隔离级别的概念,redis事务分为三步**
**1.开启事务(multi)**
**2.命令入队(命令入队的时候不执行,只有发起执行命令之后才会执行所有的命令)**
**3.执行命令(exec)**
**放弃事务 discard 则前面的命令都不执行**
**事务的一个命令具有原子性,多个命令不具有原子性**
**有运行时的异常的命令,出现异常的不执行,其余正确的执行**
**如果是语法问题(编译时异常)则全部语句都不能执行**
**正常执行**
```java
127.0.0.1:6379[1]> multi
OK
127.0.0.1:6379[1]> set name qkm
QUEUED
127.0.0.1:6379[1]> sadd set1 2 3 4 5
QUEUED
127.0.0.1:6379[1]> hset user name qkm age 18
QUEUED
127.0.0.1:6379[1]> zadd zset 100 score 200 score
QUEUED
127.0.0.1:6379[1]> exec
1) OK
2) (integer) 4
3) (integer) 2
4) (integer) 1
```

**语法问题**
```java
        127.0.0.1:6379[1]> multi 
        OK
        127.0.0.1:6379[1]> sadd set "sc"
        QUEUED
        127.0.0.1:6379[1]> sincr set "sc"
        (error) ERR unknown command `sincr`, with args beginning with: `set`, `sc`,
        127.0.0.1:6379[1]> exec
        (error) EXECABORT Transaction discarded because of previous errors.
        127.0.0.1:6379[1]> smembers set
        1) "2"
        2) "1"
        3) "4"
        4) "3"
        5) "qkm"


```
**sincr set "sc" 语法有问题  (error) EXECABORT Transaction discarded because of previous errors.之前的语句
都没有执行,发现sc没有加到集合中去**

**运行时异常**
```java
127.0.0.1:6379[1]> set name "qkm"
QUEUED
127.0.0.1:6379[1]> incr name
QUEUED
127.0.0.1:6379[1]> get name
QUEUED
127.0.0.1:6379[1]> exec
1) OK
2) (error) ERR value is not an integer or out of range
3) "qkm"

```
**字符串不能加1 ,出现运行时异常,异常的语句没有执行,别的都正常执行了**
### redis实现乐观锁
**使用watch监视就相当于乐观锁**
```java
 watch money
```
**当你执行事务的时候,一个事务输入完命令之后没有执行,另一个事务开启了并且修改刚才同一条数据,同时执行了,那么当你在
次执行前面的命令时就会执行失败,但是如果你还想修改当前的值,那就先解锁,在相当于重新获取新值才能更新**
**在事务中不能执行watch key**
```java
 get money
"50"
127.0.0.1:6379[1]> watch money
OK
127.0.0.1:6379[1]> set money 100
OK
127.0.0.1:6379[1]> multi 
OK
127.0.0.1:6379[1]> set money 50
QUEUED
127.0.0.1:6379[1]> exec
(nil)
```
**先给money 加锁,然后又修改了money的值,则在事务中就会执行失败,因为和之前监视的值不一样(面向的是多个人操作同一条数据的情况)**
**这个不会出现ABA的情况,ABA:就是监视的时候是100,然后我改成50在改成100,虽然改值了但是还是之前的值,想让系统以为没改值
,然后修改成功,但是并没有,所以说watch底层应该不是用的cas(compare and swap)自己猜的**
## Jedis
**Jedis Client是Redis官网推荐的一个面向java客户端，库文件实现了对各类API进行封装调用。**


