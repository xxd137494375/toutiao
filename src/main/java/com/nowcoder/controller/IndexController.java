package com.nowcoder.controller;

import com.nowcoder.Service.ToutiaoService;
import com.nowcoder.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Author ：xingxiangdong
 * @Date :2019/7/713:22
 */
@Controller
public class IndexController {

    @Autowired
    private ToutiaoService toutiaoService;



//    @RequestMapping({"/","/login","/index"})
//    @ResponseBody
//    public String index(HttpSession session){
//        return "Hello bupt"+session.getAttribute("msg") + toutiaoService.say();
//    }

    @RequestMapping("/profile/{groupId}/{userId}")
    @ResponseBody
    //http://localhost:8080/profile/1/2?type=3&value=4
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId")int  userId,
                          @RequestParam(value = "type",defaultValue = "1")int type,
                          @RequestParam(value = "value",defaultValue = "bupt")String value){
        return String.format("{%s},{%d},{%d},{%s    }",groupId,userId,type,value);
    }
    @RequestMapping(value = {"/vm"})
    public String news(Model model){
        model.addAttribute("name","xxd<br>");
        List<String> colors = Arrays.asList(new String[]{"red<br>","green<br>","blue<br>"});

        Map<String,String> map = new HashMap<>();
        for(int i=0;i<4;++i){
            map.put(String.valueOf(i),String.valueOf(i*i+"<br>"));
        }
        model.addAttribute("colors",colors);
        model.addAttribute("map",map);
        model.addAttribute("user",new User("xingxiangdong"));
        return "new";
    }


    @RequestMapping(value = "/request")
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session){
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerName = request.getHeaderNames();
        while (headerName.hasMoreElements()){
            String name = headerName.nextElement();
            sb.append(name+": "+request.getHeader(name)+"<br>");
        }
        for(Cookie cookie : request.getCookies()){
            sb.append("Cookie:");
            sb.append(cookie.getName());
            sb.append(": ");
            sb.append(cookie.getValue());
            sb.append("<br>");
        }
        sb.append("getMethod:    "+request.getMethod()+"<br>");
        sb.append("getPathInfo:    "+request.getPathInfo()+"<br>");
        sb.append("getQueryString:    "+request.getQueryString()+"<br>");
        sb.append("getRequestURI:    "+request.getRequestURI()+"<br>");

        return sb.toString();
    }

    @RequestMapping("/response")
    @ResponseBody
    public String response(@CookieValue(value = "buptid",defaultValue = "a")String buptid,
                           @RequestParam(value = "key",defaultValue = "key")String key,
                           @RequestParam(value = "value",defaultValue = "value")String value,
                           HttpServletResponse response){

        response.addCookie(new Cookie(key,value));//添加cookie
//        response.addHeader(key,value);
//        response.addHeader("name","xxd");
        return "BUPTID from Cookie:"+buptid;
    }

    @RequestMapping("redirect/{code}")
    public RedirectView redirect(@PathVariable("code")int code,
                                 HttpSession session){
        RedirectView red = new RedirectView("/",true);
        if(code==301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        session.setAttribute("msg","Jump from redirect");
        return red;
    }

    @RequestMapping("redirect2/{code}")
    public String redirect1(@PathVariable("code")int code){
//        RedirectView red = new RedirectView("/",true);
//        if(code==301){
//            red.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
//        }
//        return red;
        return "redirect:/";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key",required = false)String key) throws IllegalAccessException {
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalAccessException("Key 错误");
    }


    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e) {
        return "ERROR:" + e.getMessage();
    }
}
