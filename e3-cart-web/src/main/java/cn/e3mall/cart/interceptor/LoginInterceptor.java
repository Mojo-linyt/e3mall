package cn.e3mall.cart.interceptor;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginInterceptor
 * @Description TODO
 * 登录处理拦截器
 * @Author Mojo
 * @Date 2019/9/27 18:28
 * @Version 1.0
 **/
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    /**
     * @Method preHandle
     * @Description TODO
     * 前处理，执行handler之前执行此方法
     * @Author Mojo
     * @Date 2019/9/27 18:33
     * @Param [httpServletRequest, httpServletResponse, handler]
     * @Return boolean
     **/
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        //从cookie中取token
        String token = CookieUtils.getCookieValue(httpServletRequest, "token");
        //如果没有token，则未登录，放行
        if (StringUtils.isBlank(token)){
            return true;
        }
        //有token，调用sso服务,根据token获取用户信息
        E3Result e3Result = tokenService.getUserByToken(token);
        //没有取到用户信息，则登录过期，放行
        if (e3Result.getStatus() != 200){
            return true;
        }
        //取到用户信息，是登录状态
        TbUser tbUser = (TbUser) e3Result.getData();
        //把用户信息放到request中，只需要在controller中判断request中是否包含用户信息，放行
        httpServletRequest.setAttribute("user", tbUser);
        return true;
    }

    /**
     * @Method postHandle
     * @Description TODO
     * 执行handler之后，返回modelandview之前
     * @Author Mojo
     * @Date 2019/9/27 18:34
     * @Param [httpServletRequest, httpServletResponse, handler, modelAndView]
     * @Return void
     **/
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * @Method afterCompletion
     * @Description TODO
     * 返回modelandview之后，处理异常
     * @Author Mojo
     * @Date 2019/9/27 18:35
     * @Param [httpServletRequest, httpServletResponse, handler, e]
     * @Return void
     **/
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {

    }
}
