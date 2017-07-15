package com.book.online.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
public class HomePageController {

    /**
     * web首页
     * @return
     */
    @RequestMapping(value={"/web/home/index.html","/"}, method= RequestMethod.GET)
    public String index(HttpServletRequest req, ModelMap model) {
        return "mobile/demo/main";
    }

}
