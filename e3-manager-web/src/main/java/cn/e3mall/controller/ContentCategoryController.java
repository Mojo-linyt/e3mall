package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName ContentCategoryController
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/26 22:12
 * @Version 1.0
 **/
@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam(name = "id",defaultValue = "0") Long parentId){

        List<EasyUITreeNode> nodeList = contentCategoryService.getContentCategoryList(parentId);
        return nodeList;

    }

    @RequestMapping(name = "/content/category/create", method = RequestMethod.POST)
    @ResponseBody
    public E3Result createContentCategory(Long parentId, String name){
        E3Result result = contentCategoryService.addContentCategory(parentId, name);
        return result;
    }

}
