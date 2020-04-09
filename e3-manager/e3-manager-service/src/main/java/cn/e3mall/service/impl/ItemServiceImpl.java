package cn.e3mall.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.EasyUIDataGirdResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
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
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;
    @Autowired
    private JedisClient jedisClient;
    @Value("${REDIS_ITEM_PRE}")
    private String REDIS_ITEM_PRE;
    @Value("${REDIS_CACHE_EXPIRE}")
    private Integer REDIS_CACHE_EXPIRE;
    
    @Override
    public TbItem getItemById(Long id) {

        //查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_PRE + ":" + id + ":" + "BASE");
            if (StringUtils.isNotBlank(json)){
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return tbItem;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        //缓存没有，则查询数据库
//        根据主键查询
//        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andIdEqualTo(id);
        List<TbItem> tbItems = tbItemMapper.selectByExample(tbItemExample);
        if (null != tbItems && 0 < tbItems.size()) {
            //将结果添加到缓存
            try {
                jedisClient.set(REDIS_ITEM_PRE + ":" + id + ":" + "BASE", JsonUtils.objectToJson(tbItems.get(0)));
                jedisClient.expire(REDIS_ITEM_PRE + ":" + id + ":" + "BASE", REDIS_CACHE_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tbItems.get(0);
        }
        return null;

    }

    @Override
    public EasyUIDataGirdResult getItemList(int page, int rows) {

        EasyUIDataGirdResult easyUIDataGirdResult = new EasyUIDataGirdResult();
        PageHelper.startPage(page, rows);
        TbItemExample tbItemExample = new TbItemExample();
        List<TbItem> tbItemList = tbItemMapper.selectByExample(tbItemExample);
        easyUIDataGirdResult.setRows(tbItemList);
        PageInfo<TbItem> tbItemPageInfo = new PageInfo<>(tbItemList);
        long total = tbItemPageInfo.getTotal();
        easyUIDataGirdResult.setTotal(total);
        return easyUIDataGirdResult;

    }

    @Override
    public E3Result addItem(TbItem tbItem, String desc) {

        //生成商品id
        long itemId = IDUtils.genItemId();
        //补全item的属性
        tbItem.setId(itemId);
        //1:正常，2:下架,3:删除
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        //向商品表插入数据
        tbItemMapper.insert(tbItem);
        //创建一个商品描述表对应的pojo对象
        TbItemDesc tbItemDesc = new TbItemDesc();
        //补全属性
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        //向商品描述表插入数据
        tbItemDescMapper.insert(tbItemDesc);
        //发送商品添加消息
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(itemId + "");
                return textMessage;
            }
        });
        //返回成功
        return E3Result.ok();
    }

    @Override
    public TbItemDesc getItemDescById(long itemId) {

        //查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":" + "DESC");
            if (StringUtils.isNotBlank(json)){
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return itemDesc;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        //缓存没有，则查询数据库
        TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        //将结果添加到缓存
        try {
            jedisClient.set(REDIS_ITEM_PRE + ":" + itemId + ":" + "DESC", JsonUtils.objectToJson(itemDesc));
            jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":" + "DESC", REDIS_CACHE_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc;

    }
}
