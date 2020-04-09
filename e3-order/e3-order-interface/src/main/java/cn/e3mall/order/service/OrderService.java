package cn.e3mall.order.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.order.pojo.OrderInfo;

/**
 * @InterfaceName OrderService
 * @Description TODO
 * @Author Mojo
 * @Date 2019/10/9 17:56
 * @Version 1.0
 **/
public interface OrderService {

    E3Result createOrder(OrderInfo orderInfo);

}
