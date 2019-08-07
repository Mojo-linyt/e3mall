package cn.e3mall.controller;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName ItemController
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/8 0:28
 * @Version 1.0
 **/
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{id}")
    @ResponseBody
    public TbItem getItemById(@PathVariable long id){
        TbItem tbItem = itemService.getItemById(id);
        return tbItem;
    }

}
