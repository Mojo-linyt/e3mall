package cn.e3mall.search.activemq;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @ClassName ItemAddMessageListener
 * @Description TODO
 * 监听商品添加消息，收到消息后，将商品信息同步到索引库
 * @Author Mojo
 * @Date 2019/9/13 16:24
 * @Version 1.0
 **/
public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {

        TextMessage textMessage = (TextMessage) message;
        try {

            String text = textMessage.getText();
            Long itemId = Long.valueOf(text);
            Thread.sleep(1000);
            SearchItem searchItem = itemMapper.getItemById(itemId);
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            solrServer.add(document);
            solrServer.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
