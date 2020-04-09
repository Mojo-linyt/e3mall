package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUIDataGirdResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

/**
 * @InterfaceName ItemService
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/8 0:02
 * @Version 1.0
 **/
public interface ItemService {

    TbItem getItemById(Long id);

    EasyUIDataGirdResult getItemList(int page,int rows);

    E3Result addItem(TbItem tbItem,String desc);

    TbItemDesc getItemDescById(long itemId);

}
