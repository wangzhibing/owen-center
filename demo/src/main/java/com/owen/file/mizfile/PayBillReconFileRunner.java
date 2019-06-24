//package com.owen.file.mizfile;
//
//import com.miz.asset.co.bo.PayBillBo;
//import com.miz.mekansm.common.utils.DateTimeUtil;
//
//import java.io.FileWriter;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicLong;
//
///**
// * 资金需求方放款对账文件 服务 Created by chars on 2017/6/5 17:00
// *
// * @since 1.0.0
// */
//
//public class PayBillReconFileRunner implements Runnable {
//
//    private final AtomicLong atomic;
//
//    private final String key;
//
//    private final FileWriter writer;
//
//    private final BlockingQueue<PayBillBo> queue;
//
//    public PayBillReconFileRunner(final String key) throws Exception {
//        this.key = key;
//        this.queue = PayBillReconFileHelper.getQueue(key);
//        this.writer = PayBillReconFileHelper.getOutputStream(key);
//        this.atomic = PayBillReconFileHelper.getCurrentCount(key);
//    }
//
//    @Override
//    public void run() {
//        while (true) {
//
//            try {
//                PayBillBo payBillBo = null;
//                try {
//                    payBillBo = queue.poll(1L, TimeUnit.SECONDS);
//                } catch (final Exception ignore) {
//                }
//
//                if (PayBillReconFileHelper.isComplete(key)) {
//                    return;
//                }
//
//                if (payBillBo == null) {
//                    continue;
//                }
//
//                try {
//                    //借款单号|借款金额|借款时间|放款状态|外部借款单号
//                    String tail = atomic.decrementAndGet() == 0 ? "" : "\n";
//                    writer.write(payBillBo.getBorrowNo() + "|" + payBillBo.getApplyAmount() + "|" + DateTimeUtil.getDate(payBillBo.getBorrowTime(), "yyyy-MM-dd HH:mm:ss") + "|" + payBillBo.getPayStatus() + "|" + payBillBo.getOuterOrderNo() + tail);
//                } catch (final Exception e) {
//                    e.printStackTrace();
//                    // .
//                }
//            } catch (final Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}