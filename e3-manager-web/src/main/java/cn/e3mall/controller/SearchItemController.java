package cn.e3mall.controller;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName SearchItemController
 * @Description TODO
 * 导入商品数据到索引库
 * @Author Mojo
 * @Date 2019/9/5 1:33
 * @Version 1.0
 **/
@Controller
public class SearchItemController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/item/import")
    @ResponseBody
    public E3Result importAllItem(){
        E3Result e3Result = searchItemService.importAllItem();
        return e3Result;
    }

}
