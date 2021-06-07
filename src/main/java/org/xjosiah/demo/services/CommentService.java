package org.xjosiah.demo.services;

import org.xjosiah.demo.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByLimited(int begin, int limited);

    Boolean addComment(String title, String content, String author);

    Boolean delComment(String commentId);

    Boolean checkRole(String username, String author);

    Comment getCommentById(String id);
}
