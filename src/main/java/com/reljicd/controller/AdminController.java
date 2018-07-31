package com.reljicd.controller;

import com.reljicd.model.Role;
import com.reljicd.model.User;
import com.reljicd.model.WebInfo;
import com.reljicd.service.UserService;
import com.reljicd.service.WebInfoService;
import com.reljicd.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Created by Administrator on 2018-07-02.
 */
@Controller
public class AdminController {
    private final UserService userService;
    private final WebInfoService webInfoService;

    @Autowired
    public AdminController(UserService userService, WebInfoService webInfoService) {
        this.userService = userService;
        this.webInfoService = webInfoService;
    }

    @GetMapping("/admin")
    public String home() {
        return "/admin/index";
    }

    @GetMapping("/admin/userList")
    public String userList(@RequestParam(defaultValue = "0") int page,
                           Model model) {
        Page<User> posts = userService.findAllOrderedByIdPageable(page);
        Pager pager = new Pager(posts);

        model.addAttribute("pager", pager);
        return "/admin/userList";
    }

    @GetMapping("/admin/user/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        Optional<User> optionalPost = userService.findForId(id);
        if (optionalPost.isPresent()) {
            User user = optionalPost.get();
            model.addAttribute("user", user);
            Collection<Role> roles= user.getRoles();
            if(null!=roles){
                List<String> rolesList=new ArrayList<>();
                for (Role role:roles){
                    rolesList.add(role.getRole());
                }
                model.addAttribute("rolesList", rolesList);
            }
        } else {
            return "/error";
        }
        return "/admin/userEdit";
    }

    @RequestMapping(value = "/admin/user/userEditPost", method = RequestMethod.POST)
    public String userEditPost(User user, @RequestParam("rolesList") String[] rolesArr) {
        {
            User userOld = userService.findForId(user.getId()).get();
            userOld.setPassword(user.getPassword());
            List<Role> roleList = new ArrayList<Role>();
            for (int i = 0; i < rolesArr.length; i++) {
                roleList.add(userService.findByRole(rolesArr[i]));
            }
            userOld.setRoles(roleList);
            userService.save(userOld);
            return "redirect:/admin/userList/";
        }
    }

    @GetMapping("/admin/setting")
    public String setting(@RequestParam(defaultValue = "0") int page,
                          Model model) {
        Page<WebInfo> posts = webInfoService.findAllByIdPageable(page);
        Pager pager = new Pager(posts);
        model.addAttribute("pager", pager);
        return "/admin/setting";
    }


    @RequestMapping(value = "/admin/options/edit/{id}", method = RequestMethod.GET)
    public String settingWebInfo(@PathVariable Long id,
                                 Principal principal,
                                 Model model) {
        Optional<WebInfo> optionalWebInfo = webInfoService.findForId(id);
        if (optionalWebInfo.isPresent()) {
            WebInfo webInfo = optionalWebInfo.get();
            model.addAttribute("webInfo", webInfo);
        }
        return "/admin/settingEdit";
    }

    @RequestMapping(value = "/admin/options/optionEditPost", method = RequestMethod.POST)
    public String optionEditPost(WebInfo webInfo) {
        {
            WebInfo webInfoOld = webInfoService.findForId(webInfo.getId()).get();
            webInfoOld.setOptionKey(webInfo.getOptionKey());
            webInfoOld.setOptionValue(webInfo.getOptionValue());
            webInfoOld.setDescription(webInfo.getDescription());
            webInfoService.save(webInfoOld);
            return "redirect:/admin/setting";
        }
    }

}
