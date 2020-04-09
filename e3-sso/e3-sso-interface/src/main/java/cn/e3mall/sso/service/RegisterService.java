package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;

/**
 * @InterfaceName RegisterService
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/18 22:45
 * @Version 1.0
 **/
public interface RegisterService {

    E3Result checkData(String param, int type);

    E3Result register(TbUser user);

}
