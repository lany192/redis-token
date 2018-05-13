package com.lany.authorization.manager.impl;

import com.lany.authorization.manager.TokenManager;
import com.lany.authorization.model.TokenModel;
import com.lany.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 通过Redis存储和验证token的实现类
 *
 * @see TokenManager
 */
@Component
public class RedisTokenManager implements TokenManager {
    private RedisTemplate<String, TokenModel> redis;

    @Autowired
    public void setRedis(RedisTemplate redisTemplate) {
        this.redis = redisTemplate;
        //泛型设置成Long后必须更改对应的序列化方案
        redis.setKeySerializer(new JdkSerializationRedisSerializer());
    }

    @Override
    public TokenModel createToken(long userId) {
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        TokenModel model = new TokenModel(userId, token);
        //存储到redis并设置过期时间
        redis.boundValueOps(token).set(model, Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return model;
    }

    @Override
    public TokenModel getTokenInfoByToken(String token) {
        if (token != null && token.length() > 0 && redis.hasKey(token)) {
            TokenModel model = redis.boundValueOps(token).get();
            //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
            redis.boundValueOps(token).expire(Constants.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
            return model;
        }
        return null;
    }

    @Override
    public void deleteToken(String token) {
        redis.delete(token);
    }
}
