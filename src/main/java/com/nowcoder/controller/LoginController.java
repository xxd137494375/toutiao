package com.nowcoder.controller;

import com.nowcoder.Service.NewsService;
import com.nowcoder.Service.UserService;
import com.nowcoder.aspect.LogAspect;
import com.nowcoder.model.News;
import com.nowcoder.model.ViewObject;
import com.nowcoder.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Controller
public class LoginController {
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

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


    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String reg(Model model, @RequestParam("username")String username,
                      @RequestParam("password")String password,
                      @RequestParam(value = "rember",defaultValue = "0")int rember,
                      HttpServletResponse response) {
        try{
            Map<String, Object> map = userService.register(username, password);
//            if(map.isEmpty()){
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if(rember>0){
                    cookie.setMaxAge(3600*24*5);//设置cookie有效时间
                }
                response.addCookie(cookie);
                return ToutiaoUtil.getJSONString(0,"注册成功");
            }else{
               return ToutiaoUtil.getJSONString(1,map);
            }
        }catch (Exception e){
            logger.error("注册异常"+e.getMessage());
            return ToutiaoUtil.getJSONString(1,"注册异常");
        }

    }



    @RequestMapping(path = {"/login/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(Model model, @RequestParam("username")String username,
                      @RequestParam("password")String password,
                      @RequestParam(value = "rember",defaultValue = "0")int rememberme,
                        HttpServletResponse response) {
        try{
            Map<String, Object> map = userService.login(username, password);
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
                response.addCookie(cookie);
                cookie.setPath("/");
                if(rememberme>0){
                    cookie.setMaxAge(3600*24*5);
                }
                return ToutiaoUtil.getJSONString(0,"成功");
            }else{
                return ToutiaoUtil.getJSONString(1,map);
            }
        }catch (Exception e){
            logger.error("注册异常"+e.getMessage());
            return ToutiaoUtil.getJSONString(1,"注册异常");
        }
    }


    @RequestMapping(path = {"/logout/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket")String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }



}
