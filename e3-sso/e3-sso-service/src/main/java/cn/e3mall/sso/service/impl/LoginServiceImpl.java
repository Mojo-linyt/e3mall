package cn.e3mall.sso.service.impl;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName LoginServiceImpl
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/19 18:45
 * @Version 1.0
 **/
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${TOKEN_EXPIRE}")
    private Integer TOKEN_EXPIRE;

    @Override
    public E3Result userLogin(String username, String password) {

        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> userList = tbUserMapper.selectByExample(example);
        if (userList == null || userList.size() == 0){
            return E3Result.build(400, "用户名或密码错误");
        }
        TbUser user = userList.get(0);
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
            return E3Result.build(400, "用户名或密码错误");
        }
        String token = UUID.randomUUID().toString();
        user.setPassword(null);
        jedisClient.set("SESSION:" + token, JsonUtils.objectToJson(user));
        jedisClient.expire("SESSION:" + token, TOKEN_EXPIRE);
        return E3Result.ok(token);
    }

}
