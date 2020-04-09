package cn.e3mall.content.service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;

import java.util.List;

/**
 * @InterfaceName ContentCategoryService
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/26 20:51
 * @Version 1.0
 **/
public interface ContentCategoryService {

    List<EasyUITreeNode> getContentCategoryList(Long parentId);

    E3Result addContentCategory(Long parentId, String name);

}
