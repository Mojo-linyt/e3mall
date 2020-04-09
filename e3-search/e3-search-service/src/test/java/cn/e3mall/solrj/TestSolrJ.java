package cn.e3mall.solrj;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TestSolrJ
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/4 20:46
 * @Version 1.0
 **/
public class TestSolrJ {

    @Test
    public void addDocument() throws IOException, SolrServerException {

        //创建一个solrserver对象
        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8081/solr/jd_core");
        //创建一个文档对象solrinputdocument
        SolrInputDocument document = new SolrInputDocument();
        //向文档对象中添加域
        document.addField("id", "doc02");
        document.addField("item_title", "测试商品02");
        document.addField("item_price", 237);
        //把文档写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();

    }

    @Test
    public void deleteDocument() throws IOException, SolrServerException {

        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8081/solr/jd_core");
        solrServer.deleteByQuery("id:doc02");
        solrServer.commit();

    }

    @Test
    public void quertIndex() throws SolrServerException {

        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8081/solr/jd_core");
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("q", "*:*");
        QueryResponse queryResponse = solrServer.query(solrQuery);
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println(solrDocumentList.getNumFound());
        for (SolrDocument solrDocument : solrDocumentList){
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("item_category_name"));
        }

    }

    @Test
    public void queryIndexComplex() throws SolrServerException {

        SolrServer solrServer = new HttpSolrServer("http://192.168.25.128:8081/solr/jd_core");
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("手机");
        solrQuery.setStart(0);
        solrQuery.setRows(20);
        solrQuery.set("df", "item_title");
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em>");
        solrQuery.setHighlightSimplePost("</em>");
        QueryResponse queryResponse = solrServer.query(solrQuery);
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println(solrDocumentList.getNumFound());
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        List<String> strings;
        String title;
        for (SolrDocument solrDocument : solrDocumentList){
            System.out.println(solrDocument.get("id"));
            strings = highlighting.get(solrDocument.get("id")).get("item_title");
            if (null != strings && 0 < strings.size()){
                title = strings.get(0);
            }else {
                title = (String) solrDocument.get("item_title");
            }
            System.out.println(title);
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("item_category_name"));
        }

    }

}
