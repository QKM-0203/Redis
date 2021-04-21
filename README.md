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
* 开启事务(multi)
* 命令入队(命令入队的时候不执行,只有发起执行命令之后才会执行所有的命令)
* 执行命令(exec)

**放弃事务 discard 则前面的命令都不执行**

**事务的一个命令具有原子性,多个命令不具有原子性**

**有运行时的异常的命令,出现异常的不执行,其余正确的执行**

**如果是语法问题(编译时异常)则全部语句都不能执行**
### 例子
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
## redis.conf
* 对大小写不敏感
* 包含别的文件
```java
# include /path/to/local.conf
# include /path/to/other.conf
```
**网络**
* 绑定的IP   bind 127.0.0.1 ::1
* 保护模式   protected-mode yes
* 端口设置   port 6379

**通用配置**
* daemonize yes 采用的是单进程多线程的模式。当redis.conf中选项daemonize设置成yes时，代表开启守护进程模式。
  在该模式下，redis会在后台运行，并将进程pid号写入至redis.conf选项pidfile设置的文件中，此时redis将一直运行，除非手动kill该进程。
* pidfile /var/run/redis/redis-server.pid(以后台方式运行就会将进程号写进该文件)
* loglevel notice 日志级别
```java# Specify the server verbosity level.
# This can be one of:
# debug (a lot of information, useful for development/testing)
# verbose (many rarely useful info, but not a mess like the debug level)
# notice (moderately verbose, what you want in production probably)
# warning (only very important / critical messages are logged)
```
*  logfile /var/log/redis/redis-server.log  日志生成的文件的位置

**快照**

持久化,因为redis是内存数据库,断电即失,所以就会有持久化,那莫一下就是规定持久化的要求
* save 900 1   如果900s中至少有1个key被修改,那么就持久化
* save 300 10  如果300s中至少有10个key被修改,那么就持久化
* save 60 10000  如果60s中至少有10000个key被修改,那么就持久化

**后面我们会根据要求来修改持久化的要求**
* stop-writes-on-bgsave-error yes 如果持久化失败,还会进行下面的操作吗
* rdbcompression yes  是否压缩rdb文件
* rdbchecksum yes 保存rdb文件时,进行错误校验
* dir /var/lib/redis  存放rdb文件的目录
  **安全**

**设置密码**
* 默认是没有密码的   config set requirepass  密码  设置密码
* 设置密码之后是需要登录的   auth 密码   登录
* config get requirepass  查看密码
  **限制**

*  maxclients 10000  最大客户端数量 10000
*  maxmemory <bytes> 默认内存大小
*  maxmemory-policy noeviction  内存达到上限之后的处理策略

    * volatile-lru：从已设置过期时间的数据集（server.db[i].expires）中挑选最近最少使用的数据进行淘汰
    * volatile-ttl：从已设置过期时间的数据集（server.db[i].expires）中挑选将要过期的数据进行淘汰
    * volatile-random：从已设置过期时间的数据集（server.db[i].expires）中选择任意数据进行淘汰
    * allkeys-lru：当内存不足以容纳新写入数据时，在键空间中，移除最近最少使用的key（最常用）
    * allkeys-random：从数据集（server.db[i].dict）中选择任意数据进行淘汰
    * no-eviction：禁止驱逐数据，也就是说当内存不足以容纳新写入数据时，新写入操作会报错
    * volatile-lfu：从已设置过期时间的数据集 (server.db[i].expires) 中挑选最不经常使用的数据进行淘汰
    * allkeys-lfu：当内存不足以容纳新写入数据时，在键空间中，移除最不经常使用的 key

**AOF模式**

* aof 默认不开启,默认使用rdb持久化
* appendfilename "appendonly.aof"    aof的文件后缀名是.aof  rdb文件是.rdb
* 三种aof文件持久化的形式
    * appendfsync always   每次更新同步(sync)就写入文件中,消耗性能,安全
    * appendfsync everysec    每秒执行1次 sync,可能会丢失1s的数据   (默认),较安全
    * appendfsync no       不执行 sync ,这个时候由操作系统自己同步数据,速度最快,不安全
## 持久化
**Redis是一个高效的内存数据库，所有的数据都存放在内存中。我们知道，内存中的信息会随着进程的退出或机器的宕机而消失 。
为此，redis提供了两种持久化机制：RDB和AOF。这两种持久化方式的原理实际上就是把内存中所有数据的快照保存到磁盘文件上，
以避免数据丢失,默认情况下使用rdb来持久化.**
### RDB
**原理:**
`RDB的主要原理就是在某个时间点把内存中所有数据保存到磁盘文件中，这个过程既可以通过人工输入命令执行，也可以让服务器周期性执行。
对于“把内存中的数据转存到磁盘中”这一过程，其实现无非就是通过定义好某种存储协议，然后按照该协议写入或读出。服务器周期执行持久化的设置是在redis.conf中设置`

**手动持久化的命令**
 * save   会堵塞服务器进程,直到rdb文件创建完为止
 * bgsave    不会堵塞父进程,他是fork出一个子进程,用来达到持久化,当写完数据库状态后，新 RDB 文件就会原子地替换旧的 RDB 文件。父进程还可以处理客户端请求,在redis中一般都是使用bgsave来持久化

**rdb文件的载入**
   
`RDB文件中存放的是二进制数据,后缀名是.rdb，RDB 文件的载入是在服务器启动时自动执行的，所以没有用于载入的命令，期间阻塞主进程。只要没有开启 AOF 持久化功能，
在启动时检测到有 RDB 文件，就会自动载入。 当服务器有开启 AOF 持久化功能时，服务器将会优先使用 AOF 文件来还原数据库状态。原因是 AOF 文件的更新频率通常比 RDB 文件的更新频率高。`

**思考问题**
 * **问题1: 如果小李在公司服务器中执行了flushAll命令,问怎么办?**
   
   `答: 需要找到aof文件之后,删除flushAll命令 之后重启redis,执行save命令即可.`
 * **问题2: 如果在执行 BGSAVE 期间，客户端发送 SAVE、BGSAVE 或 BGREWRITEAOF 命令给服务端，服务端会如何处理呢？**
  
   `答案：在执行 BGSAVE 期间，上述三个命令都不会被执行。
   详细原因：前两个会被直接拒绝，原因是为了避免父子进程同时执行两个 rdbSave 调用，防止产生竞争条件。
   而 BGREWRITEAOF 命令则是会被延迟到 BGSAVE 命令执行之后再执行。
   但如果是 BGREWRITEAOF 命令正在执行，此时客户端发送 BGSAVE 命令则会被拒绝。
   因为 BGREWRITEAOF 和 BGSAVE 都是由子进程执行的，所以在操作方面没有冲突的地方，
   不能同时执行的原因是性能上的考虑——并发出两个子进程，并且这两个子进程都会同时执行大量 io（磁盘写入）操作`
   
**AOF**

**原理:**
`我们从上面的介绍知道，RDB 持久化通过保存数据库状态来持久化。而 AOF 与之不同，它是通过保存对数据库的写命令来记录数据库状态。`

也就是说当你执行了 set key 123,Redis 就会将这条写命令保存到 AOF 文件中。在服务器下次启动时，就可以通过载入和执行 AOF 文件中保存的命令，来还原服务器关闭前的数据库状态了。
总体流程和 RDB 持久化一样会创建一个文件,后缀名是.aof、在服务器下次启动时就载入这个文件来还原数据.

**AOF持久化的实现**

`AOF 持久化功能的实现可以分为 3 个步骤：命令追加、文件写入、文件同步`
 * 命令追加: 将命令追加到AOF缓冲区的末尾。
 * 文件写入: 将AOF缓冲区的内存写到.aof文件中。
 * 文件同步: 将文件保存到磁盘上。

`在《Redis设计与实现》中提到，Redis 服务器进程就是一个事件循环，这个循环中的文件事件（socket 的可读可写事件）负责接收客户端的命令请求，以及向客户端发送命令结果。
当服务器是处理处理事件的时侯,可能会发生写的操作,使得一些内容会被追加到 AOF 缓冲区末尾。所以，在服务器每次结束一个事件循环之前 ，都会调用 flushAppendOnlyFile 方法。`

==flushAppendOnlyFile方法:==`执行以下两个工作：`

* WRITE：根据条件，将缓冲区内容写入到 AOF 文件。 
  
* SAVE：根据条件，调用 fsync 或 fdatasync 函数，将 AOF 文件保存到磁盘中。

**既然 AOF 持久化是通过保存写命令到文件的，那随着时间的推移，这个 AOF 文件记录的内容就越来越多，文件体积也就越来越大，对其进行数据还原的时间也就越来越久。**

**AOF重写**

`创建一个新的.aof文件,新文件里面不会有冗余的数据,所以新文件比旧文件小很多`

==所谓冗余就是:当你使用同一个命令对某条数据改了好几次,新文件只存储最后一条数据,所以说不会有冗余的数据存在 ==

`注意:`**因为要有大量的IO操作,所以redis是使用子进程来实现这个功能的,否则会导致主进程堵塞.**

==又有一个新的问题产生了:==子进程在重写的过程中父进程产生了新的修改命令,这就会导致数据库状态和重写后的.aof文件不一致`

**解决:**
`为了解决这个问题，Redis 设置了一个 AOF 重写缓冲区。在子进程执行 AOF 重写期间，主进程需要执行以下三个步骤：`
* 执行客户端的请求命令 
* 将执行后的写命令追加到 AOF 缓冲区
* 将执行后的写命令追加到 AOF 重写缓冲区

`当子进程当子进程结束重写后，会向主进程发送一个信号，主进程接收到之后会调用信号处理函数执行以下步骤：`

* 将 AOF 重写缓冲区内容写入新的 AOF 文件中。此时新文件所保存的数据库状态就和当前数据库状态一致了
* 对新文件进行改名，原子地覆盖现有 AOF 文件，完成新旧文件的替换.

当函数执行完成后,主进程就继续处理客户端命令,所以整个AOF重写的过程中,只有在执行信号处理的时候再回堵塞主线程,其他时候都不会堵塞
## redis 发布订阅
**订阅消息**
```java
subscribe name(name 就是名字)



        127.0.0.1:6379> subscribe qkm
        Reading messages... (press Ctrl-C to quit)
        1) "subscribe"
        2) "qkm"
        3) (integer) 1
        1) "message"
        2) "qkm"
        3) "one"

```
**发布消息**
```java
publish name message      //只要生产者发布消息,将他的所有订阅者形成一个链表,然后给他们发生消息

        127.0.0.1:6379> publish  qkm "one"
        (integer) 1

```
**redis可以实现一些简单的场景,比如实时聊天室,公众号,关注**
**复杂的一些场景**
