package cn.e3mall.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * @ClassName TestFreemarker
 * @Description TODO
 * @Author Mojo
 * @Date 2019/9/14 20:58
 * @Version 1.0
 **/
public class TestFreemarker {

    @Test
    public void testFreemarker() throws IOException, TemplateException {

        //创建一个模板文件
        //创建一个configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置模板文件保存的目录
        configuration.setDirectoryForTemplateLoading(new File("D:\\Desktop\\Mojo\\Project\\idea-workspace\\e3parent\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        //设置模板文件的编码格式
        configuration.setDefaultEncoding("utf-8");
        //加载一个模板文件，创建一个模板对象
//        Template template = configuration.getTemplate("hello.ftl");
        Template template = configuration.getTemplate("student.ftl");
        //创建一个数据集，可以是pojo,map;推荐使用map
        Map map = new HashMap<>();
        map.put("hello", "hello freemarker!!!");
        Student student = new Student(1, "影見", 18);
        map.put("student", student);
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "小明1", 15));
        students.add(new Student(2, "小明2", 16));
        students.add(new Student(3, "小明3", 17));
        students.add(new Student(4, "小明4", 18));
        students.add(new Student(5, "小明5", 19));
        students.add(new Student(6, "小明6", 20));
        map.put("students", students);
        //添加日期类型
        map.put("date", new Date());
        //null值的处理
        map.put("null", null);
        //创建一个writer对象，指定输出文件的路径及文件名
        Writer writer = new FileWriter(new File("D:\\Desktop\\Mojo\\Project\\idea-workspace\\e3parent\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl\\out\\student.html"));
        //生成静态页面
        template.process(map, writer);
        //关闭流
        writer.close();

    }

}
