package com.owen.bithu.trade.api.zgtop;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mizlicai on 2019/6/12.
 * zgtop 常量
 * https://zg.top/api#info info source
 */
public class ZgtopConstant {

    public static String KEY_KEY = "key";

    public static String SECRET_KEY = "secret";

    public static String SIGN_KEY = "sign";

    public static String RESULT_KEY = "result";

    public static String RESULT_CODE_KEY = "resultCode";

    public static String HTTP_SUCCESS_CODE = "200";

    //api访问前缀
    public static final String URL_PREX = "https://www.zg.top/API/";

    //api_key
    public static final String API_KEY_VALUE = "";

    //secret_key
    public static final String SECRET_KEY_VALUE = "";

    //区块链资产对照表
    public static Map<Integer, String> assetMapping = new HashMap<Integer, String>();

    //错误代码对照表
    public static Map<Integer, String> errorMapping = new HashMap<Integer, String>();



    //初始化数据
    static {

        //1.初始化区块链资产对照表
        assetMapping.put(36, "ETH/USDT");
        assetMapping.put(42, "EOS/USDT");
        assetMapping.put(39, "THETA/USDT");
        assetMapping.put(37, "SNT/USDT");
        assetMapping.put(35, "BTC/USDT");
        assetMapping.put(43, "WTC/USDT");
        assetMapping.put(44, "EAI/USDT");
        assetMapping.put(47, "FTL/USDT");
        assetMapping.put(48, "OCN/USDT");
        assetMapping.put(49, "OMG/USDT");
        assetMapping.put(50, "ELF/USDT");
        assetMapping.put(51, "IOST/USDT");
        assetMapping.put(52, "CDT/USDT");
        assetMapping.put(53, "GAT/USDT");
        assetMapping.put(54, "CVT/USDT");
        assetMapping.put(56, "ZXC/USDT");
        assetMapping.put(62, "GRAM/USDT");
        assetMapping.put(63, "WICC/USDT");
        assetMapping.put(64, "BQT/USDT");
        assetMapping.put(65, "HUR/USDT");
        assetMapping.put(66, "FREC/USDT");
        assetMapping.put(68, "DACC/USDT");
        assetMapping.put(69, "STM/USDT");
        assetMapping.put(72, "ZRX/USDT");
        assetMapping.put(74, "SWFTC/USDT");
        assetMapping.put(76, "AE/USDT");
        assetMapping.put(77, "BAT/USDT");
        assetMapping.put(78, "CCMC/USDT");
        assetMapping.put(79, "PAY/USDT");
        assetMapping.put(82, "LRC/USDT");
        assetMapping.put(83, "REP/USDT");
        assetMapping.put(84, "TYT/USDT");
        assetMapping.put(86, "OTWP/USDT");
        assetMapping.put(87, "QO就，S/USDT");
        assetMapping.put(88, "WOO/USDT");
        assetMapping.put(89, "DOGE/USDT");
        assetMapping.put(90, "BTS/USDT");
        assetMapping.put(91, "LTC/USDT");
        assetMapping.put(92, "ETC/USDT");
        assetMapping.put(93, "ISC/USDT");
        assetMapping.put(101, "TUSD/BTC");
        assetMapping.put(103, "BDC/USDT");
        assetMapping.put(106, "XCN/USDT");
        assetMapping.put(108, "SPND/ETH");
        assetMapping.put(109, "AERGO/ETH");
        assetMapping.put(110, "TASK/ETH");
        assetMapping.put(112, "TASK/USDT");
        assetMapping.put(116, "COC/USDT");
        assetMapping.put(118, "TCH/USDT");
        assetMapping.put(120, "KBC/ETH");
        assetMapping.put(123, "Ankr/ETH");
        assetMapping.put(124, "CAS/USDT");
        assetMapping.put(125, "CAS/ETH");
        assetMapping.put(126, "PSP/USDT");
        assetMapping.put(127, "GOD/USDT");
        assetMapping.put(129, "HT/USDT");
        assetMapping.put(130, "BNB/USDT");
        assetMapping.put(131, "DUSD/USDT");
        assetMapping.put(132, "CIF/USDT");
        assetMapping.put(133, "ZGT/USDT");
        assetMapping.put(134, "BRDT/USDT");
        assetMapping.put(135, "PJD/USDT");
        assetMapping.put(136, "LBT/USDT");
        assetMapping.put(137, "YT/USDT");
        assetMapping.put(138, "B91/USDT");
        assetMapping.put(139, "GT/USDT");
        assetMapping.put(140, "PLO/USDT");

        //2.初始化错误代码对照表
        errorMapping.put(101, "必填参数不能为空");
        errorMapping.put(102, "API key不存在");
        errorMapping.put(103, "API已禁止使用");
        errorMapping.put(104, "权限已关闭");
        errorMapping.put(105, "权限不足");
        errorMapping.put(106, "签名不匹配");
        errorMapping.put(201, "对应资产不存在");
        errorMapping.put(202, "对应资产不能充值和提款");
        errorMapping.put(203, "资产还没分配到钱包地址");
        errorMapping.put(204, "取消挂单失败（部分成交或全部已成交）");
        errorMapping.put(205, "交易数量不能小于0.0001");
        errorMapping.put(206, "交易价格不能小于0.0001");
        errorMapping.put(207, "币种未开放交易");
        errorMapping.put(208, "交易对币种余额不足");
        errorMapping.put(209, "交易密码错误");
        errorMapping.put(210, "交易价格不在限价区间内");
        errorMapping.put(211, "币种余额不足");
        errorMapping.put(212, "最大交易总金额受限");
        errorMapping.put(213, "最小交易总金额受限");
        errorMapping.put(401, "非法参数");
        errorMapping.put(402, "系统异常");

    }

}
