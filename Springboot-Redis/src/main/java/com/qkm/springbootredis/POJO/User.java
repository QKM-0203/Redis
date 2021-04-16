package com.qkm.springbootredis.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
/**
 * 当使用默认的序列化方式(JDk)就得实现接口Serializable
 */
public class User implements Serializable{
    private String name;
    private int age;
}
