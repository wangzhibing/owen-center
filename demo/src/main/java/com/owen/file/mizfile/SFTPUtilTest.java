//package com.owen.file.mizfile;
//
//import com.miz.rapid.util.net.exception.ConnectException;
//import com.miz.rapid.util.net.model.SftpConfig;
//import com.miz.rapid.util.net.sftp.SFTPUtil;
//import org.junit.Test;
//
//import java.io.IOException;
//
//public class SFTPUtilTest {
//
//    @Test
//    public void upload() throws IOException, ConnectException {
//        uploadTest("121.43.148.191", "asset_inner", "asset", "/borrower");
////        uploadTest("121.40.194.189", "asset_inner", "asset", "/repay/test");
////        uploadTest("120.26.72.75", "asset_inner", "asset", "/pay/test");
//    }
//
//    private void uploadTest(String host, String userName, String psd, String dir) throws IOException, ConnectException {
//        SftpConfig config  = new SftpConfig();
//        config.setHost(host);
//        config.setPort(2201);
//        config.setUsername(userName);
//        config.setPassword(psd);
//        SFTPUtil.upload(config, dir, "REPAY_MIZ_TEST_20171018.txt", "/Users/yirenjie/Downloads/REPAY_MIZ_TEST_20170927.txt");
//    }
//
//    @Test
//    public void download() throws IOException, ConnectException {
////        downloadTest("121.43.148.191", "asset_inner", "asset", "/repay/test");
////        downloadTest("121.40.194.189", "asset_inner", "asset", "/repay/test", "repay.txt");
//        downloadTest("121.40.194.189", "asset_inner", "asset", "/pay/test", "pay_text.txt");
////        downloadTest("120.26.72.75", "asset_inner", "asset", "/repay/test");
//    }
//
//    private void downloadTest(String host, String userName, String psd, String dir, String file) throws IOException, ConnectException {
//        SftpConfig config  = new SftpConfig();
//        config.setHost(host);
//        config.setPort(2201);
//        config.setUsername(userName);
//        config.setPassword(psd);
//        SFTPUtil.download(config, dir, file, "/Users/yirenjie/IdeaProjects/asset/project/web/");
//    }
//
//}
