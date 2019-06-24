//package com.owen.file.mizfile;
//
//import com.miz.asset.co.bo.PayBillBo;
//import com.miz.asset.common.exception.AssetRuntimeException;
//import com.miz.asset.emuns.AssetCodeType;
//import com.miz.rapid.util.net.exception.ConnectException;
//import com.miz.rapid.util.net.model.SftpConfig;
//import com.miz.rapid.util.net.sftp.SFTPUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.*;
//import java.util.concurrent.atomic.AtomicLong;
//
///**
// * 资金需求方打款对账文件 辅助类 Created by chars on 2017/6/6 10:14
// *
// * @since 1.0.0
// */
//public class PayBillReconFileHelper {
//
//    private static SftpConfig sftpConfig;
//
//    static {
//        // 连接
//        sftpConfig = new SftpConfig();
//        sftpConfig.setHost(RepayFtpProperties.host);
//        sftpConfig.setPort(RepayFtpProperties.port);
//        sftpConfig.setUsername(RepayFtpProperties.user);
//        sftpConfig.setPassword(RepayFtpProperties.pass);
//        System.out.println("sftpConfig:" + sftpConfig);
//    }
//
//    /**
//     * 计数器  map
//     * key 为商户id _日期
//     */
//    public static Map<String, AtomicLong> currentCountMap = new ConcurrentHashMap<>();
//
//    /**
//     * 文件输出流 os map
//     * key 为商户id _日期ss
//     */
//    public static Map<String, FileWriter> osMap = new ConcurrentHashMap<>();
//
//    /**
//     * 队列map
//     * key 为商户id _日期
//     */
//    private static Map<String, BlockingQueue<PayBillBo>> queueMap = new ConcurrentHashMap<>();
//
//    public static String getKey(String merchantId, String date) {
//        return "PAY_" + merchantId + "_" + date.replace("-","");
//    }
//
//    /**
//     * 获取当前计数
//     *
//     * @param key key
//     * @return 当前计数
//     */
//    public static AtomicLong getCurrentCount(final String key) {
//        return currentCountMap.get(key);
//    }
//
//    /**
//     * 获取输出流
//     *
//     * @param key key
//     * @return 输出流
//     */
//    public static FileWriter getOutputStream(final String key) {
//        return osMap.get(key);
//    }
//
//    /**
//     * 获取队列
//     *
//     * @param key key
//     * @return 队列
//     */
//    public static BlockingQueue<PayBillBo> getQueue(final String key) {
//        return queueMap.get(key);
//    }
//
//
//    /**
//     * 任务是否完成
//     *
//     * @param key key
//     * @return 完成返回 true
//     */
//    public static boolean isComplete(final String key) {
//        return currentCountMap.get(key) == null || currentCountMap.get(key).longValue() <= 0L;
//    }
//
//    /**
//     * 添加处理数据
//     *
//     * @param payBillBo payBillBo
//     */
//    public static boolean add(final PayBillBo payBillBo) {
//        if (payBillBo == null) {
//            return false;
//        }
//        String key = getKey(payBillBo.getMerchantId(), payBillBo.getBillDate());
//        if (StringUtils.isEmpty(key)) {
//            return false;
//        }
//        try {
//            final BlockingQueue<PayBillBo> queue = queueMap.get(key);
//            queue.put(payBillBo);
//        } catch (final Exception e) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * 添加处理数据
//     *
//     * @param payBillBos payBillBos
//     */
//    public static boolean addAll(final List<PayBillBo> payBillBos) {
//        if (CollectionUtils.isEmpty(payBillBos)) {
//            return false;
//        }
//        String key = getKey(payBillBos.get(0).getMerchantId(), payBillBos.get(0).getBillDate());
//        if (StringUtils.isEmpty(key)) {
//            return false;
//        }
//        for (PayBillBo payBillBo : payBillBos) {
//            try {
//                final BlockingQueue<PayBillBo> queue = queueMap.get(key);
//                queue.put(payBillBo);
//            } catch (final Exception e) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//
//    /**
//     * 任务启动
//     *
//     * @param total 数据总数
//     * @param key   key
//     */
//    public static void start(final int total, final String merchantId, String key) throws AssetRuntimeException {
//
//        // 前期的准备
//        if (!queueMap.containsKey(key)) {
//            // 记录 总数 map
//            currentCountMap.put(key, new AtomicLong(total));
//
//            // 初始化 队列map
//            queueMap.put(key, new ArrayBlockingQueue<>(1000));
//
//            // 初始化 输出流 map
//            String file = key + ".txt";
//            try {
//                final FileWriter writer = new FileWriter(new File(file));
//                log.info("生成对账文件:{}", file);
//                osMap.put(key, writer);
//                // 写入头文件
//                writer.write(total + "\n");
//                writer.write(PayFtpProperties.title + "\n");
//            } catch (final Exception e) {
//                log.error("生成对账文件:{}异常:", file, e);
//                throw new AssetRuntimeException(AssetCodeType.SYSTEM_ERROR);
//            }
//        }
//
//        // 计算实际使用的线程数目
//        final int ts = 1;
//        final ExecutorService executor = Executors.newFixedThreadPool(ts);
//        // 启动3个线程去执行写文件操作
//        //for (int i = 0; i < ts; i++) {
//        try {
//            executor.execute(new PayBillReconFileRunner(key));
//        } catch (final Exception e) {
//            log.error("启动对账文件线程异常:", e);
//            throw new AssetRuntimeException(AssetCodeType.SYSTEM_ERROR);
//        }
//        //}
//        //结束线程
//        new Thread(() -> {
//            while (true) {
//                try {
//                    TimeUnit.SECONDS.sleep(1L);
//                    if (isComplete(key)) {
//                        destroy(key);
//                        upload(merchantId, key);
//                        executor.shutdown();
//                        log.info("{}生成借款对账单文件完成", key);
//                        return;
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//    }
//
//    /**
//     * 销毁数据
//     *
//     * @param key 对账日期
//     */
//    private static void destroy(final String key) {
//        queueMap.remove(key);
//        currentCountMap.remove(key);
//
//        // 先关闭流
//        final FileWriter writer = osMap.get(key);
//        if (writer != null) {
//            try {
//                writer.flush();
//                writer.close();
//            } catch (final IOException ignored) {
//                log.error("文件关闭异常");
//            }
//        }
//        osMap.remove(key);
//    }
//
//    /**
//     * 上传
//     *
//     * @param key 对账日期
//     */
//    /**
//     * 上传
//     *
//     * @param key 对账日期
//     */
//    private static void upload(final String merchantId, final String key) {
//        final File file = new File(key + ".txt");
//        FileInputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(file);
//        } catch (final Exception e) {
//            log.error("文件读取异常");
//        }
//
////        }
//        // 上传
//        boolean result;
//        // 每个商户有对应的路径
//        String path = String.format(PayFtpProperties.path, merchantId);
//        try {
//            result = SFTPUtil.upload(sftpConfig, path, file.getName(), inputStream);
//        } catch (Exception e) {
//            result = false;
//            if (e instanceof ConnectException) {
//                log.error("连接异常,error:{}", e);
//            } else if (e instanceof IOException) {
//                log.error("io异常,error:{}", e);
//            } else {
//                log.error("未知异常,error:{}", e);
//            }
//        }
//
//        log.info("文件上传结果:{}", result);
//        if (result) {
//            if (!file.delete()) {
//                log.error("文件删除异常");
//            }
//        }
//    }
//
//    /**
//     * 判断商户下文件是否已存在
//     *
//     * @param merchantId 商户
//     * @param fileName 文件名
//     * @return
//     */
//    public static boolean isFileExists(String merchantId, String fileName) {
//        try {
//            // 每个商户有对应的路径
//            String path = String.format(PayFtpProperties.path, merchantId);
//            return SFTPUtil.fileIsExists(sftpConfig, path, fileName);
//        } catch (ConnectException e) {
//            return false;
//        }
//    }
//
//}
//
