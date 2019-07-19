package com.nowcoder.controller;

import com.nowcoder.Service.NewsService;
import com.nowcoder.Service.UserService;
import com.nowcoder.model.HostHolder;
import com.nowcoder.model.News;

import com.nowcoder.model.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Controller
public class HomeController {
    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    /**
     * 得到news  userId=0时查询所有，否则查询某个用户的
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    private List<ViewObject> getNews(int userId,int offset,int limit){
        List<News> newsList =  newsService.getLatesNews(userId,offset,limit);
        List<ViewObject> vos = new ArrayList<>();
        for(News news:newsList){
            ViewObject vo = new ViewObject();
            vo.set("news",news);
            vo.set("user",userService.getUser(news.getUserId()));
            vos.add(vo);
        }
        return vos;
    }


    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        model.addAttribute("vos",getNews(0,0,10));
        return "home";//返回界面
    }




    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId,
                            @RequestParam(value = "pop",defaultValue = "0")int pop) {
        model.addAttribute("vos",getNews(userId,0,10));
        model.addAttribute("pop",pop);
        return "home";//返回界面
    }




}
