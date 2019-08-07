package cn.e3mall.service;

import cn.e3mall.pojo.TbItem;

/**
 * @InterfaceName ItemService
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/8 0:02
 * @Version 1.0
 **/
public interface ItemService {
    TbItem getItemById(long id);
}
