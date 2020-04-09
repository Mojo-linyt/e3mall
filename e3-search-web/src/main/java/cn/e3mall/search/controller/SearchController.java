package cn.e3mall.search.controller;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName SearchController
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/6 22:35
 * @Version 1.0
 **/
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Value("${search_result_rows}")
    private Integer search_result_rows;

    @RequestMapping("/search")
    public String search(Model model, String keyword, @RequestParam(defaultValue = "1") Integer page) throws SolrServerException, UnsupportedEncodingException {

        //keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
        SearchResult searchResult = searchService.search(keyword, page, search_result_rows);
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("recourdCount", searchResult.getRecordCount());
        model.addAttribute("itemList", searchResult.getItemList());
//        int a = 1/0;
        return "search";

    }

}
