package cn.e3mall.controller;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName PictureController
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/19 23:03
 * @Version 1.0
 **/
@Controller
public class PictureController {

    @Value("${image-server-url}")
    private String image_server_url;

    @RequestMapping(value = "/pic/upload",produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){

        try {

            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/client.conf");
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            url = image_server_url + url;
            Map map = new HashMap<>();
            map.put("error", 0);

            map.put("url", url);
            return JsonUtils.objectToJson(map);

        } catch (Exception e) {
            e.printStackTrace();
            Map map = new HashMap<>();
            map.put("error", 1);
            map.put("message", "图片上传失败");
            return JsonUtils.objectToJson(map);
        }

    }

}
