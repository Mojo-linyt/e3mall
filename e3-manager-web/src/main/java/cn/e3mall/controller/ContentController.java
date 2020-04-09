package cn.e3mall.controller;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName ContentController
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/28 0:28
 * @Version 1.0
 **/
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/save")
    @ResponseBody
    public E3Result addContent(TbContent tbContent){
        E3Result e3Result = contentService.addContent(tbContent);
        return e3Result;
    }

}
