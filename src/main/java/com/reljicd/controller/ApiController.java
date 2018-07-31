package com.reljicd.controller;

import com.reljicd.model.Post;
import com.reljicd.model.RetMessage;
import com.reljicd.service.PostService;
import com.reljicd.service.UserService;
import com.reljicd.util.HttpUtils;
import org.h2.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

/**
 * Created by Administrator on 2018-07-17.
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    private final PostService postService;
    private final UserService userService;

    @Autowired
    public ApiController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }
    @GetMapping(value = {"/","","/index"})
    public String home(  HttpServletRequest httpServletRequest) {
        return "这里是API首页,当前用户："+HttpUtils.getUserName(httpServletRequest);
    }
    @RequestMapping(value = "/newPost", method = RequestMethod.POST)
    public RetMessage createNewPost(  Post post,
                                    HttpServletRequest httpServletRequest) {
        RetMessage retMessage = new RetMessage();
        try {
            if (StringUtils.isNullOrEmpty(post.getTitle()) || StringUtils.isNullOrEmpty(post.getBody())) {
                retMessage.setResult(false);
                retMessage.setMsg("标题，内容都不能为空");
            } else {
                //Post post = new Post();
                Optional<Post> optional= postService.findByTitle(post.getTitle());
                if(optional.isPresent()){
                    post.setId(optional.get().getId());
                }
                post.setCreateDate(new Date());
                post.setBody(post.getBody());
                post.setTitle(post.getTitle());
                post.setUser(userService.findByUsername(HttpUtils.getUserName(httpServletRequest)).get());
                postService.save(post);
                retMessage.setResult(true);
            }
        } catch (Exception ex) {
            retMessage.setResult(false);
            retMessage.setMsg(ex.getMessage());
        }
        return retMessage;
    }
}
