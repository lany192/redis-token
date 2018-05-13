package com.lany.authorization.manager;

import com.lany.authorization.model.TokenModel;

/**
 * 对Token进行操作的接口
 */
public interface TokenManager {

    /**
     * 创建一个token关联上指定用户
     *
     * @param userId 指定用户的id
     * @return 生成的token
     */
    TokenModel createToken(long userId);

    /**
     * 从字符串中解析token
     *
     * @param token 加密后的字符串
     * @return
     */
    TokenModel getTokenInfoByToken(String token);

    /**
     * 清除token
     */
    void deleteToken(String token);

}
