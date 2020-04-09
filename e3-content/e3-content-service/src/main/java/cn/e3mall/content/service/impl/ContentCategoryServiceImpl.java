package cn.e3mall.content.service.impl;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ContentCategoryServiceImpl
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/26 20:53
 * @Version 1.0
 **/
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCategoryList(Long parentId) {

        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> contentCategoryList = tbContentCategoryMapper.selectByExample(example);
        List<EasyUITreeNode> nodeList = new ArrayList<>();
        for (TbContentCategory tbContentCategory :
                contentCategoryList) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            nodeList.add(node);
        }
        return nodeList;

    }

    @Override
    public E3Result addContentCategory(Long parentId, String name) {

        //创建一个tb_content_category表的pojo对象
        TbContentCategory tbContentCategory = new TbContentCategory();
        //设置pojo属性
        tbContentCategory.setName(name);
        //默认排序是1
        tbContentCategory.setSortOrder(1);
        //1（正常），2（删除）
        tbContentCategory.setStatus(1);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        //新添加的节点一定是子节点
        tbContentCategory.setIsParent(false);
        tbContentCategory.setParentId(parentId);
        //插入到数据库
        tbContentCategoryMapper.insert(tbContentCategory);
        //判断父节点的isparent属性，如果不是true,改为true
        //根据parentid获取父节点
        TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()){
            parent.setIsParent(true);
            //更新到数据库
            tbContentCategoryMapper.updateByPrimaryKey(parent);
        }
        //返回结果，E3Result,包含pojo
        return E3Result.ok(tbContentCategory);

    }
}
