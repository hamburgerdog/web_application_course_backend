package org.xjosiah.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xjosiah.demo.entity.User;
import org.xjosiah.demo.services.UserService;
import org.xjosiah.demo.utils.DIYResponseEntity;
import org.xjosiah.demo.utils.TokenUtils;

import javax.validation.constraints.NotNull;

@Controller
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 注册功能
     * 注册有关的状态码说明：
     * 【2000】-注册成功
     * 【2001】-两次密码不一致
     * 【2002】-注册的用户已存在
     * 【2999】-其他原因注册失败
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam @NotNull String username, @RequestParam @NotNull String password, @RequestParam @NotNull String reword, @RequestParam String picUrl) {
        if (!password.equals(reword)) {
            return ResponseEntity.ok(DIYResponseEntity.DIYResponse("2001", null, "「注册失败」请检查并重新校验密码"));
        }
        if (userService.checkWhetherHasUser(username)) {
            return ResponseEntity.ok(DIYResponseEntity.DIYResponse("2002", null, "「注册失败」请用户已存在，请直接登录"));
        }
        if (!userService.register(new User(username, password, picUrl, "user"))) {
            return ResponseEntity.ok(DIYResponseEntity.DIYResponse("2999", null, "「注册失败」请重新注册"));
        }
        return ResponseEntity.ok(DIYResponseEntity.DIYResponse("2000", new User(username, password, picUrl, "user"), "「注册成功」"));
    }

    /**
     * 登录功能
     * 登录相关的错误码：
     * 【1000】-登录成功
     * 【1001】-其他原因登录失败
     * 【1002】-用户不存在
     * 【1003】-登录密码错误
     * 【1010】-获取头像成功
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        User result = userService.login(username, password);
        if (result == null) {
            User user = userService.getUserByName(username);
            if (user == null) {
                return ResponseEntity.ok(DIYResponseEntity.DIYResponse("1002", null, "「登录失败」用户未注册"));
            }
            if (!user.getPassword().equals(password)) {
                return ResponseEntity.ok(DIYResponseEntity.DIYResponse("1003", null, "「登录失败」登录密码错误"));
            }
            return ResponseEntity.ok(DIYResponseEntity.DIYResponse("1001", null, "「登录失败」请重新登录"));
        }
        String token = TokenUtils.token(username, password);
        return ResponseEntity.ok(DIYResponseEntity.DIYResponse("1000", result, token));
    }

    @GetMapping("/pic/{username}")
    public ResponseEntity<String> getUserPic(@PathVariable String username) {
        String picUrl = userService.getUserPicByName(username);
        return ResponseEntity.ok(DIYResponseEntity.DIYResponse("1010", picUrl, "「操作完成」获取头像成功"));
    }
}
