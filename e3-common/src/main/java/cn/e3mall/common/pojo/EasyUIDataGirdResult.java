package cn.e3mall.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName EasyUIDataGirdResult
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/9 20:00
 * @Version 1.0
 **/
public class EasyUIDataGirdResult implements Serializable {

    private long total;
    private List rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
