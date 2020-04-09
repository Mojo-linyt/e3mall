package cn.e3mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName PageController
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/8 23:37
 * @Version 1.0
 **/
@Controller
public class PageController {

    @RequestMapping(name = "/", method = RequestMethod.GET)
    public String showIndex(){
        return "index";
    }

    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }

}
