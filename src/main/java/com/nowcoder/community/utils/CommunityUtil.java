package com.nowcoder.community.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
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

    //用来生成json对象，code就是我们自己写的状态，可以是404，403等
    public static String getJsonString(int code, String msg, Map<String, Object> map){

        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if (map != null){
            for (String key : map.keySet()) {
                json.put(key, map.get(key));
            }
        }
        return json.toJSONString();

    }
    public static String getJsonString(int code, String msg){
        return getJsonString(code, msg, null);
    }
    public static String getJsonString(int code){
        return getJsonString(code, null, null);
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("age", 25);
        System.out.println(getJsonString(0, "ok",map));
    }

}

