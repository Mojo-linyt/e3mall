package cn.e3mall.content.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbContent;

import java.util.List;

/**
 * @InterfaceName ContentService
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/28 0:23
 * @Version 1.0
 **/
public interface ContentService {

    E3Result addContent(TbContent tbContent);

    List<TbContent> getContentListByCid(Long cid);

}
