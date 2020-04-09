package cn.e3mall.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HtmlGenController
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/15 21:36
 * @Version 1.0
 **/
@Controller
public class HtmlGenController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("/genhtml")
    @ResponseBody
    public String genHtml() throws IOException, TemplateException {

        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("hello.ftl");
        Map map = new HashMap<>();
        map.put("hello", 123456);
        Writer writer = new FileWriter(new File("D:\\Desktop\\Mojo\\Project\\idea-workspace\\e3parent\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl\\out\\hello.html"));
        template.process(map,writer);
        writer.close();
        return "success";

    }

}
