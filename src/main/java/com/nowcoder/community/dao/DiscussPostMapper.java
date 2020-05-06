package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    /**
     * 首页显示界面
     * @param userId ： 主要用于后期个人主页实现需要，可以使用动态SQL来实现是否需要将某用户所有信息展示
     * @param offset ： 用于分页
     * @param limit ： 每页由多少个数据。
     * @return
     */

    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);


    //查找帖子的个数，方便进行分页.
    //@param是用来取别名，如果只有一个参数，并且在<if>里使用，必须加别名。
    int selectDiscussPostRows(@Param("userId") int userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

    //更新帖子数量
    int updateCommentCount(int id, int commentCount);
}
