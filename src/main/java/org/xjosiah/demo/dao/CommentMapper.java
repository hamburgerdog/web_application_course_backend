package org.xjosiah.demo.dao;

import cn.hutool.core.date.DateTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.xjosiah.demo.entity.Comment;

import java.util.List;

@Component
@Mapper
public interface CommentMapper {
    List<Comment> selCommentsByLimited(@Param("begin") int begin, @Param("limited") int limited);

    boolean insertComment(@Param("uuid") String uuid, @Param("title") String title, @Param("content") String content, @Param("date") DateTime date, @Param("author") String author);

    Boolean delCommentById(@Param("commentId") String commentId);

    Comment selCommentById(@Param("commentId") String commentId);
}
