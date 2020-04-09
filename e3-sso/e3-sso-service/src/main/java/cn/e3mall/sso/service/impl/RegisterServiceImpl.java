package cn.e3mall.sso.service.impl;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @ClassName RegisterServiceImpl
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/18 22:54
 * @Version 1.0
 **/
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public E3Result checkData(String param, int type) {

        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //1：用户名 2：手机号 3：邮箱
        if (type == 1) {
            criteria.andUsernameEqualTo(param);
        } else if (type == 2){
            criteria.andPhoneEqualTo(param);
        } else if (type == 3){
            criteria.andEmailEqualTo(param);
        } else {
            return E3Result.build(400, "数据类型错误！！！");
        }
        List<TbUser> userList = tbUserMapper.selectByExample(example);
        if (userList != null && userList.size() > 0){
            return E3Result.ok(false);
        }
        return E3Result.ok(true);

    }

    @Override
    public E3Result register(TbUser user) {

        //数据有效性校验
        if ( StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getPhone())){
            return E3Result.build(400, "用户数据不完整，注册失败！");
        }
        E3Result e3Result = checkData(user.getUsername(), 1);
        if ( !(boolean)e3Result.getData()){
            return e3Result.build(400, "用户名已被占用");
        }
        e3Result = checkData(user.getPhone(), 2);
        if ( !(boolean)e3Result.getData()){
            return E3Result.build(400, "手机号已被占用");
        }
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //对密码进行md5加密
        String digestAsHex = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(digestAsHex);
        tbUserMapper.insert(user);
        return E3Result.ok();

    }

}
