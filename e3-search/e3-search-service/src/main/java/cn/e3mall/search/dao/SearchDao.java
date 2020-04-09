package cn.e3mall.search.dao;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SearchDao
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/6 20:07
 * @Version 1.0
 **/
@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;

    public SearchResult search(SolrQuery solrQuery) throws SolrServerException {

        QueryResponse queryResponse = solrServer.query(solrQuery);
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        SearchResult searchResult = new SearchResult();
        searchResult.setRecordCount(solrDocumentList.getNumFound());
        List<SearchItem> itemList = new ArrayList<>();
        SearchItem searchItem;
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        List<String> strings;
        String title;
        for (SolrDocument solrDocument : solrDocumentList){
            searchItem = new SearchItem();
            searchItem.setId((String) solrDocument.get("id"));
            strings = highlighting.get(solrDocument.get("id")).get("item_title");
            if (null != strings && 0 < strings.size()){
                title = strings.get(0);
            }else {
                title = (String) solrDocument.get("item_title");
            }
            searchItem.setTitle(title);
            searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
            searchItem.setPrice((Long) solrDocument.get("item_price"));
            searchItem.setImage((String) solrDocument.get("item_image"));
            searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
            itemList.add(searchItem);
        }
        searchResult.setItemList(itemList);
        return searchResult;

    }

}
