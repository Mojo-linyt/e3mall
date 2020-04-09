package cn.e3mall.content.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ContentServiceImpl
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/28 0:24
 * @Version 1.0
 **/
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${contentList}")
    private String contentList;

    @Override
    public E3Result addContent(TbContent tbContent) {
        tbContent.setUpdated(new Date());
        tbContent.setCreated(new Date());
        tbContentMapper.insert(tbContent);
        //缓存同步，删除缓存中对应的数据
        jedisClient.hdel(contentList, tbContent.getCategoryId().toString());
        return E3Result.ok();
    }

    @Override
    public List<TbContent> getContentListByCid(Long cid) {

        //查询缓存
        try {
            //如果缓存中有则直接响应结果
            String json = jedisClient.hget(contentList, cid + "");
            if (StringUtils.isNotBlank(json)){
                List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        //如果没有则查询数据库
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
        //把结果添加到缓存中
        try {
            jedisClient.hset(contentList, cid + "", JsonUtils.objectToJson(list));
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
