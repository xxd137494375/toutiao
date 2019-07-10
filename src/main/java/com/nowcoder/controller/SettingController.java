package com.nowcoder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author ：xingxiangdong
 * @Date :2019/7/810:37
 */
@Controller
public class SettingController {
    @RequestMapping(value = "/setting")
    @ResponseBody
    public String setting(){
        return "setting OK";
    }
}
