package com.lany.authorization.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 */
@Data
@AllArgsConstructor
public class TokenModel implements Serializable {

    //用户id
    private long userId;

    //随机生成的uuid
    private String token;
}
