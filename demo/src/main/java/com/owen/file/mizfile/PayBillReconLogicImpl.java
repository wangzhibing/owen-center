//package com.owen.file.mizfile;
//
//import com.miz.asset.co.bo.BorrowBo;
//import com.miz.asset.co.bo.MerchantBo;
//import com.miz.asset.co.bo.PayBillBo;
//import com.miz.asset.common.constants.AssetConstants;
//import com.miz.asset.gateway.api.enums.ResultStatus;
//import com.miz.asset.gateway.api.request.console.QueryPayBillRo;
//import com.miz.asset.gateway.api.vo.PayBillVo;
//import com.miz.asset.service.atom.PayBillService;
//import com.miz.asset.service.logic.BaseLogicSupport;
//import com.miz.asset.service.logic.common.MerchantLogic;
//import com.miz.asset.service.logic.recon.PayBillReconLogic;
//import com.miz.asset.service.logic.recon.help.PayBillReconFileHelper;
//import org.dozer.Mapper;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import javax.annotation.Resource;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * 资金需求方打款对账单
// * Created by chars on  2017/6/1 15:06
// *
// * @since 1.0.0
// */
//@Service
//public class PayBillReconLogicImpl extends BaseLogicSupport implements PayBillReconLogic {
//
//    @Resource
//    private PayBillService payBillService;
//
//    @Resource
//    private MerchantLogic merchantLogic;
//
//    @Resource
//    private Mapper mapper;
//
//    @Override
//    public boolean generate(BorrowBo borrow, ResultStatus payStatus, Long amount) {
//        if (borrow == null) {
//            return false;
//        }
//        logger.info("处理放款结果:生成放款对账单:{}", borrow);
//
//        if (payBillService.findByBorrowNo(borrow.getBorrowNo()) != null) {
//            return false;
//        }
//
//        PayBillBo payBillBo = new PayBillBo();
//        payBillBo.setMerchantId(borrow.getMerchantId());
//        payBillBo.setOuterOrderNo(borrow.getOuterOrderNo());
//        payBillBo.setBorrowNo(borrow.getBorrowNo());
//        payBillBo.setBorrowTime(borrow.getCreateDate());
//        payBillBo.setApplyAmount(borrow.getBorrowAmount());
//        //实际打款金额
//        payBillBo.setPayAmount(amount);
//        payBillBo.setPayStatus(payStatus);
//        payBillBo.setVersion(AssetConstants.DEFAULT_VERSION);
//
//        if (borrow.getBorrowAmount().longValue() == amount.longValue()) {
//            payBillBo.setBillStatus(ResultStatus.S);
//        } else {
//            // todo 发送邮件
//            payBillBo.setBillStatus(ResultStatus.F);
//        }
//        payBillBo.setBillDate(LocalDate.now().toString());
//
//        return payBillService.insert(payBillBo) > 0;
//    }
//
//    @Override
//    public void file(String date) {
//
//        //不同商户需要生成 不同对账文件
//        List<MerchantBo> list = merchantLogic.queryAllMerchant();
//        if (CollectionUtils.isEmpty(list)){
//            logger.warn("当前没有需要生成借款对账单文件的商户");
//            return;
//        }
//        logger.info("需要生成借款对账单文件的商户列表:{}", list);
//        for (MerchantBo merchantBo : list) {
//            //todo 给爱又米放款对账单，时间怎么算的
//            final int total = payBillService.countByMerchantIdAndBillDate(merchantBo.getMerchantId(), date);
//            // 判断sftp服务器是否已经存在该文件
//            String key = PayBillReconFileHelper.getKey(merchantBo.getMerchantId(), date);
//            if (PayBillReconFileHelper.isFileExists(merchantBo.getMerchantId(), key+".txt")){
//                logger.warn("{}文件已存在!不再重复生成", key+".txt");
//                continue;
//            }
//            PayBillReconFileHelper.start(total, merchantBo.getMerchantId(), key);
//            if (total == 0) {
//                logger.info("[{}]商户在[{}]没有对账记录", merchantBo.getMerchantName(), date);
//            }
//            new Thread(() -> {
//                for (int i = 1; i <= total / AssetConstants.DATA_MIGRATION_LIMIT + 1; i++) {
//                    List<PayBillBo> payBillBos = payBillService.listByByMerchantIdAndBillDate(merchantBo.getMerchantId(), date, i, AssetConstants.DATA_MIGRATION_LIMIT);
//                    PayBillReconFileHelper.addAll(payBillBos);
//                }
//            }).start();
//        }
//    }
//
//    @Override
//    public List<PayBillVo> queryListByCondition(QueryPayBillRo queryRo) {
//        return payBillService.queryListByCondition(queryRo).stream().map(payBillBo -> mapper.map(payBillBo, PayBillVo.class)).collect(Collectors.toList());
//    }
//
//    @Override
//    public int countListByCondition(QueryPayBillRo queryRo) {
//        return payBillService.countListByCondition(queryRo);
//    }
//}
