package com.qkm;

import redis.clients.jedis.Jedis;

public class TestList {
    public static void testList() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushDB();
        System.out.println("===========添加一个list===========");
        jedis.lpush("lists", "ArrayList", "Vector", "Stack", "HashMap", "WeakHashMap", "LinkedHashMap");
        jedis.lpush("lists", "HashSet");
        jedis.lpush("lists", "TreeSet");
        jedis.lpush("lists", "TreeMap");
        System.out.println("lists的内容：" + jedis.lrange("lists", 0, -1));//-1代表倒数第一个元素，-2代表倒数第二个元素
        System.out.println("lists区间0-3的元素：" + jedis.lrange("lists", 0, 3));
        System.out.println("===============================");
        // 删除列表指定的值 ，第二个参数为删除的个数（有重复时），后add进去的值先被删，类似于出栈
        System.out.println("删除指定元素个数：" + jedis.lrem("lists", 2, "HashMap"));
        System.out.println("lists的内容：" + jedis.lrange("lists", 0, -1));
        System.out.println("删除下表0-3区间之外的元素：" + jedis.ltrim("lists", 0, 3));
        System.out.println("lists的内容：" + jedis.lrange("lists", 0, -1));
        System.out.println("lists列表出栈（左端）：" + jedis.lpop("lists"));
        System.out.println("lists的内容：" + jedis.lrange("lists", 0, -1));
        System.out.println("lists添加元素，从列表右端，与lpush相对应：" + jedis.rpush("lists", "EnumMap"));
        System.out.println("lists的内容：" + jedis.lrange("lists", 0, -1));
        System.out.println("lists列表出栈（右端）：" + jedis.rpop("lists"));
        System.out.println("lists的内容：" + jedis.lrange("lists", 0, -1));
        System.out.println("修改lists指定下标1的内容：" + jedis.lset("lists", 1, "LinkedArrayList"));
        System.out.println("lists的内容：" + jedis.lrange("lists", 0, -1));
        System.out.println("===============================");
        System.out.println("lists的长度：" + jedis.llen("lists"));
        System.out.println("获取lists下标为2的元素：" + jedis.lindex("lists", 2));
        System.out.println("===============================");
        jedis.lpush("sortedList", "3", "6", "2", "0", "7", "4");
        System.out.println("sortedList排序前：" + jedis.lrange("sortedList", 0, -1));
        System.out.println(jedis.sort("sortedList"));
        System.out.println("sortedList排序后：" + jedis.lrange("sortedList", 0, -1));
    }

    public static void main(String[] args) {
        testList();
    }
}
