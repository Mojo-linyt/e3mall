package cn.e3mall.order.interceptor;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginInterceptor
 * @Description TODO
 * @Author Mojo
 * @Date 2019/10/9 15:57
 * @Version 1.0
 **/
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${SSO_URL}")
    private String SSO_URL;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CartService cartService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        //从cookie中取token
        String token = CookieUtils.getCookieValue(httpServletRequest, "token");
        //如果token不存在，未登录状态，跳转到登录页面，登陆后，跳转到当前请求的url
        if (StringUtils.isBlank(token)) {
            httpServletResponse.sendRedirect(SSO_URL + "/page/login?redirect=" + httpServletRequest.getRequestURL());
            return false;
        }
        //如果token存在，调用sso系统的服务，根据token获取用户信息
        E3Result e3Result = tokenService.getUserByToken(token);
        //如果取不到，登录过期，重新登录
        if (e3Result.getStatus() != 200) {
            httpServletResponse.sendRedirect(SSO_URL + "/page/login?redirect=" + httpServletRequest.getRequestURL());
            return false;
        }
        //如果取到，将用户信息写入request域中
        TbUser user = (TbUser) e3Result.getData();
        httpServletRequest.setAttribute("user", user);
        //判断cookie中是否有购物车数据，如果有就合并到服务端
        String cartList = CookieUtils.getCookieValue(httpServletRequest, "cart", true);
        if (StringUtils.isNotBlank(cartList)) {
            cartService.mergeCart(user.getId(), JsonUtils.jsonToList(cartList, TbItem.class));
        }
        //放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
