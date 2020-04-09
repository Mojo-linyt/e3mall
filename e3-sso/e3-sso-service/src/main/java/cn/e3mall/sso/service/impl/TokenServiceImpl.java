package cn.e3mall.sso.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @ClassName TokenServiceImpl
 * @Description TODO
 * 根据token从redis中获取用户信息
 * @Author Mojo
 * @Date 2019/9/19 22:22
 * @Version 1.0
 **/
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JedisClient jedisClient;
    @Value("${TOKEN_EXPIRE}")
    private Integer TOKEN_EXPIRE;

    @Override
    public E3Result getUserByToken(String token) {

        String jsonUser = jedisClient.get("SESSION:" + token);
        if (StringUtils.isBlank(jsonUser)){
            return E3Result.build(201, "用户登录信息已经过期");
        }
        jedisClient.expire("SESSION:" + token, TOKEN_EXPIRE);
        TbUser user = JsonUtils.jsonToPojo(jsonUser, TbUser.class);
        return E3Result.ok(user);
    }
}
