package cn.e3mall.fastdfs;

import cn.e3mall.common.utils.FastDFSClient;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

/**
 * @ClassName TestFastDFS
 * @Description TODO
 * @Author Mojo
 * @Date 2019/8/19 20:57
 * @Version 1.0
 **/
public class TestFastDFS {

    @Test
    public void testUpload() throws Exception{

        ClientGlobal.init("D:/Mojo/Project/idea-workspace/e3parent/e3-manager-web/src/main/resources/config/client.conf");
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageClient storageClient = new StorageClient(trackerServer, null);
        String[] jpgs = storageClient.upload_file("D:/Mojo/Study/学习资料/java基础（pck）/80-93/e3商城_day01/黑马32期/01.教案-3.0/01.参考资料/广告图片/f5fe1218bf3098984ec744bf993ee2fd.jpg", "jpg", null);
        for (String jpg:
             jpgs) {
            System.out.println(jpg);
        }

    }

    @Test
    public void testFastDFSClient() throws Exception{

        FastDFSClient fastDFSClient = new FastDFSClient("D:/Mojo/Project/idea-workspace/e3parent/e3-manager-web/src/main/resources/config/client.conf");
        String s = fastDFSClient.uploadFile("D:/Mojo/Study/学习资料/java基础（pck）/80-93/e3商城_day01/黑马32期/01.教案-3.0/01.参考资料/广告图片/1946ceef1ea90c932e1f7c8bb631a3fa.jpg");
        System.out.println(s);

    }

}
