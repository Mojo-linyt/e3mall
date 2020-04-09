package cn.e3mall.cart.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;

import java.util.List;

/**
 * @InterfaceName CartService
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/27 19:57
 * @Version 1.0
 **/
public interface CartService {

    E3Result addCart(long userId, long itemId, int num);

    E3Result mergeCart(long userId, List<TbItem> itemList);

    List<TbItem> getCartList(long userId);

    E3Result updateCartNum(long userId, long itemId, int num);

    E3Result deleteCartItem(long userId, long itemId);

    E3Result clearCartItem(long userId);

}
