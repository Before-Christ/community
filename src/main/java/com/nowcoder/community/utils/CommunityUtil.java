package com.nowcoder.community.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtil {

    // 生成随机字符串，用于生成验证码等
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    //MD5加密：只能加密，不能解密
    //因此用salt:就是加一个随机字符串， 例如：hello + 3e4a8 = abc........
    public static String md5(String key){
        if (StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());

    }

}
