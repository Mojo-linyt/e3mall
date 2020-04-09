package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @InterfaceName ItemCatService
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/12 22:13
 * @Version 1.0
 **/
public interface ItemCatService {

    List<EasyUITreeNode> getItemCatList(Long parentId);

}
