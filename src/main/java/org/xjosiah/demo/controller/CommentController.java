package org.xjosiah.demo.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xjosiah.demo.entity.Comment;
import org.xjosiah.demo.services.CommentService;
import org.xjosiah.demo.utils.DIYResponseEntity;
import org.xjosiah.demo.utils.TokenUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 状态码说明：
 * 3000-获取文章成功：不需要权限
 * 3001-添加文章成功
 * 3002-鉴权失败
 * 3003-tokne丢失
 * <p>
 * 3010-删除成功
 * 3011-权限不够
 * <p>
 * 3999-未知原因
 */
@CrossOrigin
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/{begin}")
    public ResponseEntity<String> getComments(@NotNull @PathVariable String begin) {
        int limited = 5;
        List<Comment> comments = commentService.getCommentsByLimited(Integer.parseInt(begin), limited);
        String result = DIYResponseEntity.DIYResponse("3000", comments, "「获取成功」");
        return ResponseEntity.ok(result);
    }
    @PostMapping("/add")
    public ResponseEntity<String> addComment(@NotNull @Param("title") String title, @NotNull @Param("content") String content, @NotNull @Param("token") String token) {
        String resultForToken = getUsernameInToken(token);
        if (!resultForToken.startsWith("-ok-")) {
            return ResponseEntity.ok(resultForToken);
        }
        String author = resultForToken.substring(4);
        if (commentService.addComment(title, content, author)) {
            return ResponseEntity.ok(DIYResponseEntity.DIYResponse("3001", null, "「添加成功」添加文章成功"));
        }
        return ResponseEntity.ok(DIYResponseEntity.DIYResponse("3999", null, "「添加失败」请重新添加文章"));
    }

    @DeleteMapping("/{id}/{token}")
    public ResponseEntity<String> delComment(@NotNull @PathVariable String id, @NotNull @PathVariable String token) {
        String usernameInToken = getUsernameInToken(token);
        if (!usernameInToken.startsWith("-ok-")) {
            return ResponseEntity.ok(usernameInToken);
        }
        String username = usernameInToken.substring(4);
        String author = commentService.getCommentById(id).getAuthor();
        if (commentService.checkRole(username, author)) {
            return commentService.delComment(id) ? ResponseEntity.ok(DIYResponseEntity.DIYResponse("3010", null, "「删除成功」文章已经删除"))
                    : ResponseEntity.ok(DIYResponseEntity.DIYResponse("3999", null, "「删除失败」请重新删除"));
        }
        return ResponseEntity.ok(DIYResponseEntity.DIYResponse("3011", null, "「删除失败」用户权限不够"));
    }

    public String getUsernameInToken(String token) {
        if (token == null || token.equals("")) {
            return DIYResponseEntity.DIYResponse("3003", null, "「添加失败」无token，请先登录！");
        }
        DecodedJWT jwt = TokenUtils.verify(token);
        if (jwt == null) {
            return DIYResponseEntity.DIYResponse("3002", null, "「添加失败」鉴权失败，请重新登录！");
        }
        return "-ok-" + jwt.getClaim("username").asString();
    }
}
