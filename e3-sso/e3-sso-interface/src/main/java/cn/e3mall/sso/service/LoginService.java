package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;

/**
 * @InterfaceName LoginService
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/19 18:39
 * @Version 1.0
 **/
public interface LoginService {

    E3Result userLogin(String username, String password);

}
