package com.nowcoder.community.utils;

public interface CommunityConstant {

    //激活成功
    int ACTIVATION_SUCCESS = 0;

    int ACTIVATION_REPEAT = 1;

    int ACTIVATION_FAILURE = 2;

    //默认状态的登陆凭证的超时时间
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    //记住我登陆凭证超时时间, 100天
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 100;
}