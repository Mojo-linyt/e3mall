package cn.e3mall.item.listener;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HtmlGenListener
 * @Description TODO
 * 监听商品添加消息，生成对应的静态页面
 * @Author Mojo
 * @Date 2019/9/17 1:09
 * @Version 1.0
 **/
public class HtmlGenListener implements MessageListener {

    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${HTML_GEN_PATH}")
    private String HTML_GEN_PATH;



    @Override
    public void onMessage(Message message) {

        try {

            //从消息中取商品id,通过id获取商品基本信息和商品描述
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            Long itemId = Long.valueOf(text);
            Thread.sleep(1000);
            TbItem tbItem = itemService.getItemById(itemId);
            Item item = new Item(tbItem);
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            //创建数据集封装商品数据
            Map map = new HashMap<>();
            map.put("item", item);
            map.put("itemDesc", itemDesc);
            //生成静态页面
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate("item.ftl");
            Writer out = new FileWriter(new File(HTML_GEN_PATH + itemId +".html"));
            template.process(map, out);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
