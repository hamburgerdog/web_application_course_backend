package org.xjosiah.demo.services.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xjosiah.demo.dao.CommentMapper;
import org.xjosiah.demo.dao.UserMapper;
import org.xjosiah.demo.entity.Comment;
import org.xjosiah.demo.entity.User;
import org.xjosiah.demo.services.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<Comment> getCommentsByLimited(int begin, int limited) {
        return commentMapper.selCommentsByLimited(begin, limited);
    }

    @Override
    public Boolean addComment(String title, String content, String author) {
        String uuid = IdUtil.simpleUUID();
        DateTime date = DateUtil.date();
        return commentMapper.insertComment(uuid, title, content, date, author);
    }

    @Override
    public Boolean delComment(String commentId) {
        return commentMapper.delCommentById(commentId);
    }

    @Override
    public Boolean checkRole(String username, String author) {
        if (username.equals(author)) return true;
        User user = userMapper.findUserByName(username);
        return user.getRole().equals("admin");
    }

    @Override
    public Comment getCommentById(String id) {
        return commentMapper.selCommentById(id);
    }
}
