package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;

/**
 * @InterfaceName TokenService
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/19 22:20
 * @Version 1.0
 **/
public interface TokenService {

    E3Result getUserByToken(String token);

}
