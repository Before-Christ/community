package com.nowcoder.community.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {

    private int id;
    private int userId;//帖子发布者
    private int entityType;//评论的类型，是帖子还是帖子的评论。
    private int entityId;
    private int targetId;//针对某人的评论
    private String content;
    private int status;
    private Date createTime;
}
