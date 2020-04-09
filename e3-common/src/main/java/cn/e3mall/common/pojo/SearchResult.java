package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName SearchResult
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/6 19:57
 * @Version 1.0
 **/
public class SearchResult implements Serializable {

    private long recordCount;
    private int totalPages;
    private List<SearchItem> itemList;

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }
}
