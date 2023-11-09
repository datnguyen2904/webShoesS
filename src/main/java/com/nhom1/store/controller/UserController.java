package com.nhom1.store.controller;

import com.nhom1.store.domain.User;
import com.nhom1.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/users/user-list")
    public String getAllUser(Model model) {
        List<User> users = userService.getAllUser().stream().filter(user -> !Objects.equals(user.getUsername(), "admin")).collect(Collectors.toList());
        model.addAttribute("users", users);
        return "userList";
    }

    @RequestMapping("/users/delete")
    public String deleteArticle(@RequestParam("id") Long id) {
        User user = userService.findById(id);
        if(Objects.equals(user.getDeleted(), Boolean.FALSE)){
            user.setDeleted(Boolean.TRUE);
        }
        else if(Objects.equals(user.getDeleted(), Boolean.TRUE)){
            user.setDeleted(Boolean.FALSE);
        }else {
            user.setDeleted(Boolean.TRUE);
        }
        userService.save(user);
        return "redirect:user-list";
    }
}
