package cn.e3mall.controller;

import cn.e3mall.common.pojo.EasyUIDataGirdResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName ItemController
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/8 17:24
 * @Version 1.0
 **/
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    @ResponseBody
    private TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGirdResult getItemList(Integer page,Integer rows){

        EasyUIDataGirdResult result = itemService.getItemList(page, rows);
        return result;

    }

    /**
     * @Class ItemController
     * @Method
     * @Description TODO
     * 商品添加
     * @Author Mojo
     * @Date 2019/8/23 20:08
     **/
    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public E3Result addItem(TbItem tbItem,String desc){

        E3Result result = itemService.addItem(tbItem, desc);
        return result;

    }

}

