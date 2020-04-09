package cn.e3mall.pagehelper;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @ClassName TestPageHelper
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/9 1:07
 * @Version 1.0
 **/
public class TestPageHelper {

//    @Autowired
//    private static TbItemMapper tbItemMapper;

    @Test
    public void testPageHelper(){

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        TbItemMapper tbItemMapper = applicationContext.getBean(TbItemMapper.class);
        PageHelper.startPage(1, 10);
        TbItemExample tbItemExample = new TbItemExample();
        List<TbItem> tbItemList = tbItemMapper.selectByExample(tbItemExample);
        PageInfo<TbItem> tbItemPageInfo = new PageInfo<>(tbItemList);
        System.out.println(tbItemPageInfo.getTotal());
        System.out.println(tbItemPageInfo.getPages());
        System.out.println(tbItemPageInfo.getSize());


    }

}
