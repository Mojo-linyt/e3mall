package cn.e3mall.item.pojo;

import cn.e3mall.pojo.TbItem;

/**
 * @ClassName Item
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/14 3:17
 * @Version 1.0
 **/
public class Item extends TbItem {

    public Item(TbItem tbItem) {
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
    }

    public String[] getImages(){

        String image = this.getImage();
        if (null != image && !"".equals(image)){

            String[] strings = image.split(",");
            return strings;

        }
        return null;

    }

}
