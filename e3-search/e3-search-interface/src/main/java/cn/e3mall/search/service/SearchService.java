package cn.e3mall.search.service;

import cn.e3mall.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrServerException;

/**
 * @InterfaceName SearchService
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/6 20:43
 * @Version 1.0
 **/
public interface SearchService {

    SearchResult search(String keyword, int page, int rows) throws SolrServerException;

}
