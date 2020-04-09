package cn.e3mall.cart.controller;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CartController
 * @Description TODO
 * 购物车服务
 * @Author Mojo
 * @Date 2019/9/24 15:17
 * @Version 1.0
 **/
@Controller
public class CartController {

    @Autowired
    private ItemService itemService;

    @Value("${COOKIE_CART_EXPIRE}")
    private Integer COOKIE_CART_EXPIRE;

    @Autowired
    private CartService cartService;

    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num, HttpServletRequest request, HttpServletResponse response){

        //判断用户是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        //如果登录了，则把购物车加入redis
        if (user != null) {
            //调用服务保存到redis
            cartService.addCart(user.getId(), itemId, num);
            //返回逻辑视图
            return "cartSuccess";
        }
        //未登录，则把购物车加入cookie
        //查询购物车列表，因为后续也需要获取购物车列表，所以将方法提出外面
        List<TbItem> cartList = getCartListFromCookie(request);
        //遍历购物车列表，判断商品是否存在
        boolean flag = false;
        for (TbItem tbItem :
                cartList) {
            //存在
            if (tbItem.getId().equals(itemId)){
                flag = true;
                //商品数量相加
                tbItem.setNum(tbItem.getNum() + num);
                //跳出循环
                break;
            }
        }
        //不存在
        if (!flag){
            //从数据库中查询商品
            TbItem item = itemService.getItemById(itemId);
            item.setNum(num);
            String image = item.getImage();
            if (StringUtils.isNotBlank(image)){
                String[] split = image.split(",");
                item.setImage(split[0]);
            }
            cartList.add(item);
        }
        //写入cookie
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        return "cartSuccess";

    }

    public List<TbItem> getCartListFromCookie(HttpServletRequest request){

        //从cookie中查询购物车列表
        String json = CookieUtils.getCookieValue(request, "cart", true);
        if (StringUtils.isBlank(json)){
            return new ArrayList<>();
        }
        List<TbItem> itemList = JsonUtils.jsonToList(json, TbItem.class);
        return itemList;

    }

    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request, HttpServletResponse response){

        //无论是否登录都得从cookie中获取购物车列表
        List<TbItem> cartList = getCartListFromCookie(request);
        //判断是否登录
        TbUser user = (TbUser) request.getAttribute("user");
        //如果是登录状态，调用服务将cookie和redis的购物车合并
        if (user != null) {
            cartService.mergeCart(user.getId(), cartList);
            //把cookie中的购物车删掉
            CookieUtils.deleteCookie(request, response, "cart");
            //调用服务从redis中获取购物车列表
            cartList = cartService.getCartList(user.getId());
        }
        //未登录状态
        //将购物车列表传递给页面
        request.setAttribute("cartList", cartList);
        return "cart";

    }

    /**
     * @Method updateCartItemNum
     * @Description TODO
     * 更新购物车数量
     * @Author Mojo
     * @Date 2019/9/28 17:55
     * @Param [itemId, num, request, response]
     * @Return cn.e3mall.common.utils.E3Result
     **/
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    public E3Result updateCartItemNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request, HttpServletResponse response){

        //判断登录状态
        TbUser user = (TbUser) request.getAttribute("user");
        //登录状态
        if (user != null) {
            cartService.updateCartNum(user.getId(), itemId, num);
            return E3Result.ok();
        }
        //未登录状态
        List<TbItem> cartList = getCartListFromCookie(request);
        for (TbItem item :
                cartList) {
            if (item.getId().equals(itemId)){
                item.setNum(num);
                break;
            }
        }
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        return E3Result.ok();

    }

    /**
     * @Method deleteCartItem
     * @Description TODO
     * 删除购物车中的商品
     * @Author Mojo
     * @Date 2019/9/28 17:55
     * @Param [itemId, request, response]
     * @Return java.lang.String
     **/
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response){

        //判断登录状态
        TbUser user = (TbUser) request.getAttribute("user");
        //登录状态
        if (user != null) {
            cartService.deleteCartItem(user.getId(), itemId);
            return "redirect:/cart/cart.html";
        }
        //未登录状态
        List<TbItem> cartList = getCartListFromCookie(request);
        for (TbItem item :
                cartList) {
            if (item.getId().equals(itemId)) {
                cartList.remove(item);
                break;
            }
        }
        CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
        return "redirect:/cart/cart.html";

    }

}
