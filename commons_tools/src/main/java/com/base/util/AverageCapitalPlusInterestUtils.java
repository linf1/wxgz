package com.base.util;
/**
 * Description:等额本息工具类
 * Copyright: Copyright (corporation)2015
 * Company: Corporation
 * @author: 凯文加内特
 * @version: 1.0
 * Created at: 2015年11月30日 下午3:45:46
 * Modification History:
 * Modified by :
 */

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 等额本息还款，也称定期付息，即借款人每月按相等的金额偿还贷款本息，其中每月贷款利息按月初剩余贷款本金计算并逐月结清。把按揭贷款的本金总额与利息总额相加，
 * 然后平均分摊到还款期限的每个月中。作为还款人，每个月还给银行固定金额，但每月还款额中的本金比重逐月递增、利息比重逐月递减。
 */

public class AverageCapitalPlusInterestUtils {

    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还本金和利息
     *
     * 公式：每月偿还本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
     *
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param totalmonth
     *            还款总月数
     * @return 每月偿还本金和利息,四舍五入
     */
    public static double getPerMonthPrincipalInterest(double invest, double yearRate, double totalmonth) {
        double monthRate = yearRate;
        double a = invest*monthRate * Math.pow(1 + monthRate, totalmonth);
        double b = Math.pow(1 + monthRate, totalmonth) - 1;
        double c = a/b;
        BigDecimal monthIncome = new BigDecimal(c);
        double contractAmount = monthIncome.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();//四舍五入
        return contractAmount;
    }

    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还利息
     *
     * 公式：每月偿还利息=贷款本金×月利率×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param totalmonth
     *            还款总月数
     * @return 每月偿还利息
     */
    public static Map<Integer, Double> getPerMonthInterest(double invest, double yearRate, int totalmonth) {
        Map<Integer, Double> map = new HashMap<Integer, Double>();
        double monthRate = yearRate/12;
        Double monthInterest;
        for (int i = 1; i < totalmonth + 1; i++) {
            BigDecimal multiply = new BigDecimal(invest).multiply(new BigDecimal(monthRate));
            BigDecimal sub  = new BigDecimal(Math.pow(1 + monthRate, totalmonth)).subtract(new BigDecimal(Math.pow(1 + monthRate, i-1)));
            BigDecimal monthInterest1 = multiply.multiply(sub).divide(new BigDecimal(Math.pow(1 + monthRate, totalmonth) - 1), 6, BigDecimal.ROUND_HALF_UP);
            monthInterest = monthInterest1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            map.put(i, monthInterest);
        }
        return map;
    }

    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还本金
     *
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param totalmonth
     *            还款总月数
     * @return 每月偿还本金
     */
    public static Map<Integer, Double> getPerMonthPrincipal(double invest, double yearRate, int totalmonth) {
        double monthRate = yearRate / 12;
        BigDecimal monthIncome = new BigDecimal(invest)
                .multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalmonth)))
                .divide(new BigDecimal(Math.pow(1 + monthRate, totalmonth) - 1), 2, BigDecimal.ROUND_HALF_UP);
        Map<Integer, Double> mapInterest = getPerMonthInterest(invest, yearRate, totalmonth);
        Map<Integer, Double> mapPrincipal = new HashMap<Integer, Double>();

        for (Map.Entry<Integer, Double> entry : mapInterest.entrySet()) {
            BigDecimal monthInterest = monthIncome.subtract(new BigDecimal(entry.getValue()));
            Double monthInterest1 = monthInterest.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            mapPrincipal.put(entry.getKey(), monthInterest1);
        }
        return mapPrincipal;
    }

    /**
     * 等额本息计算获取还款方式为等额本息的总利息
     *
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param totalmonth
     *            还款总月数
     * @return 总利息
     */
    public static double getInterestCount(double invest, double yearRate, int totalmonth) {
        BigDecimal count = new BigDecimal(0);
        Map<Integer, Double> mapInterest = getPerMonthInterest(invest, yearRate, totalmonth);

        for (Map.Entry<Integer, Double> entry : mapInterest.entrySet()) {
            count = count.add(new BigDecimal(entry.getValue()));
        }
        return count.doubleValue();
    }

    /**
     * 应还本金总和
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param totalmonth
     *            还款总月数
     * @return 应还本金总和
     */
    public static double getPrincipalInterestCount(double invest, double yearRate, int totalmonth) {
        double monthRate = yearRate / 12;
        BigDecimal perMonthInterest = new BigDecimal(invest)
                .multiply(new BigDecimal(monthRate * Math.pow(1 + monthRate, totalmonth)))
                .divide(new BigDecimal(Math.pow(1 + monthRate, totalmonth) - 1), 2, BigDecimal.ROUND_DOWN);
        BigDecimal count = perMonthInterest.multiply(new BigDecimal(totalmonth));
        count = count.setScale(2, BigDecimal.ROUND_DOWN);
        return count.doubleValue();
    }

//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        double invest = 14102.02; // 本金
//        int month = 30;
//        double yearRate = 1.1/3000*7*30; // 年利率
//        double perMonthPrincipalInterest = getPerMonthPrincipalInterest(invest, yearRate, month);
//        System.out.println("等额本息---每月还款本息：" + perMonthPrincipalInterest);
//        Map<Integer, BigDecimal> mapInterest = getPerMonthInterest(invest, yearRate, month);
//        System.out.println("等额本息---每月还款利息：" + mapInterest);
//        Map<Integer, BigDecimal> mapPrincipal = getPerMonthPrincipal(invest, yearRate, month);
//        System.out.println("等额本息---每月还款本金：" + mapPrincipal);
//        double count = getInterestCount(invest, yearRate, month);
//        System.out.println("等额本息---总利息：" + count);
//        double principalInterestCount = getPrincipalInterestCount(invest, yearRate, month);
//        System.out.println("等额本息---应还本息总和：" + principalInterestCount);
//    }
}