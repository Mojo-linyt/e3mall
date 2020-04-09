package cn.e3mall.search.service.impl;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName SearchServiceImpl
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/6 21:00
 * @Version 1.0
 **/
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String keyword, int page, int rows) throws SolrServerException {

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(keyword);
        if (0 >= page)
            page = 1;
        solrQuery.setStart((page-1)*rows);
        solrQuery.setRows(rows);
        solrQuery.set("df", "item_title");
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</em>");
        SearchResult searchResult = searchDao.search(solrQuery);
        long recordCount = searchResult.getRecordCount();
        long totalPages = recordCount / rows;
        if (0 < (recordCount % rows))
            totalPages++;
        searchResult.setTotalPages((int) totalPages);
        return searchResult;
    }
}
