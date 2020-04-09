package cn.e3mall.search.mapper;

import cn.e3mall.common.pojo.SearchItem;

import java.util.List;

/**
 * @InterfaceName ItemMapper
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/4 20:23
 * @Version 1.0
 **/
public interface ItemMapper {

    List<SearchItem> getItemList();

    SearchItem getItemById(long itemId);

}
