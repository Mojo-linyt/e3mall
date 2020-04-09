package cn.e3mall.portal.controller;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName IndexController
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/24 19:47
 * @Version 1.0
 **/
@Controller
public class IndexController {

    @Value("${content_lunbo_id}")
    private Long content_lunbo_id;

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model){
        //查询首页内容列表
        List<TbContent> ad1list = contentService.getContentListByCid(content_lunbo_id);
        model.addAttribute("ad1List", ad1list);
        return "index";
    }

}
