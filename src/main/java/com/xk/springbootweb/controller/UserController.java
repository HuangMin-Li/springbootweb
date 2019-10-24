package com.xk.springbootweb.controller;
import com.xk.springbootweb.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @RequestMapping("/index")
    public String show1(Model m){
        m.addAttribute("msg","hello thymeleaf");
        return "index";
    }
    @RequestMapping("/user")
    public String show2(Model m){

        User user=new User();
        user.setId(5);
        user.setName("成龙");
        user.setFriend(new User(3,"李小龙"));
        m.addAttribute("user",user);
        return "user";
    }
    @RequestMapping("/date")
    public String showDate(Model m){
        m.addAttribute("today",new Date());
        return "date";
    }
    @RequestMapping("/list")
    public String showList(Model m){
       /* User user1=new User();
        user1.setId(2);
        user1.setName("张三");
        User user2=new User();
        user2.setId(3);
        user2.setName("李四");
        users.add(user1);
        users.add(user2);*/
        List<User> users=new ArrayList<>();
        users.add(new User(2,"张三"));
        users.add(new User(3,"李四"));
        m.addAttribute("users",users);
        return "list";
    }

    /*@RequestMapping("/test")
    public ModelAndView i18nTest(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("i18nTest.html");
        return mv;
    }*/
    @RequestMapping("/test")
    public String test(){
        return "i18nTest";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
