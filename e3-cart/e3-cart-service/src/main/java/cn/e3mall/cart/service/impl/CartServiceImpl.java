package cn.e3mall.cart.service.impl;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import com.sun.scenario.effect.impl.prism.PrImage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CartServiceImpl
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/27 19:59
 * @Version 1.0
 **/
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_CART_PRE}")
    private String REDIS_CART_PRE;

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public E3Result addCart(long userId, long itemId, int num) {

        //向redis中添加购物车
        //数据类型是hash，key：用户id，field：商品id，value：商品信息
        //判断商品是否存在
        Boolean hexists = jedisClient.hexists(REDIS_CART_PRE + ":" + userId, itemId + "");
        //如果存在，则数量相加
        if (hexists){
            //从redis中取出商品信息
            String jsonItem = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
            TbItem item = JsonUtils.jsonToPojo(jsonItem, TbItem.class);
            item.setNum(item.getNum() + num);
            //把商品信息放回redis
            jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
            return E3Result.ok();
        }
        //不存在，则根据商品id获取商品信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        //设置购物车数据量
        item.setNum(num);
        //取一张图片
        String image = item.getImage();
        if (StringUtils.isNotBlank(image)) {
            item.setImage(image.split(",")[0]);
        }
        //添加到购物车列表
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
        //返回成功
        return E3Result.ok();
    }

    @Override
    public E3Result mergeCart(long userId, List<TbItem> itemList) {

        //遍历cookie的购物车列表将商品添加到redis中
        for (TbItem item :
                itemList) {
            addCart(userId, item.getId(), item.getNum());
        }
        return E3Result.ok();

    }

    @Override
    public List<TbItem> getCartList(long userId) {

        //根据用户id查询购物车列表
        List<String> jsonList = jedisClient.hvals(REDIS_CART_PRE + ":" + userId);
        List<TbItem> itemList = new ArrayList<>();
        for (String json :
                jsonList) {
            TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
            itemList.add(item);
        }
        return itemList;

    }

    @Override
    public E3Result updateCartNum(long userId, long itemId, int num) {

        //从redis中取商品信息
        String json = jedisClient.hget(REDIS_CART_PRE + ":" + userId, itemId + "");
        //更新商品数量
        TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
        item.setNum(num);
        //写回redis
        jedisClient.hset(REDIS_CART_PRE + ":" + userId, itemId + "", JsonUtils.objectToJson(item));
        return E3Result.ok();

    }

    @Override
    public E3Result deleteCartItem(long userId, long itemId) {

        jedisClient.hdel(REDIS_CART_PRE + ":" + userId, itemId + "");
        return E3Result.ok();

    }

    @Override
    public E3Result clearCartItem(long userId) {

        //删除购物车
        jedisClient.del(REDIS_CART_PRE + ":" + userId);
        return E3Result.ok();
    }
}
