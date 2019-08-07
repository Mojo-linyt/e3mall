package cn.e3mall.service.impl;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ItemServiceImpl
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/8 0:07
 * @Version 1.0
 **/
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    
    @Override
    public TbItem getItemById(long id) {
//        根据主键查询
//        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andIdEqualTo(id);
        List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);
        if (null != tbItems && 0 < tbItems.size())
            return tbItems.get(0);
        return null;
    }
}
