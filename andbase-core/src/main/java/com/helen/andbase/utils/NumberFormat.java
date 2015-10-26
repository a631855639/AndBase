package com.helen.andbase.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Helen on 2015/10/16.
 *
 */
public class NumberFormat {

    /**
     * 金额格式化
     * @param s 金额
     * @param len 小数位数
     * @return 格式后的金额
     */
    public static String format(String s, int len) {
        if (s == null || s.length() < 1) {
            return "";
        }
        java.text.NumberFormat format = null;
        double num = Double.parseDouble(s);
        if (len == 0) {
            format = new DecimalFormat("###,###");

        } else {
            StringBuilder buff = new StringBuilder();
            buff.append("###,###.");
            for (int i = 0; i < len; i++) {
                buff.append("0");
            }
            format = new DecimalFormat(buff.toString());
        }
        return format.format(num);
    }

    /**
     *
     * 2014-8-30 下午2:29:01
     *  四舍五入保留两位
     */
    public static String format(double d){
        DecimalFormat df=new DecimalFormat("##,##0.00");
        BigDecimal b = new BigDecimal(d);
        d=b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return df.format(d);
    }

    /**
     *
     * 2014-8-30 下午2:29:01
     *  四舍五入保留两位
     */
    public static String format(String s){
        try{
            double d = Double.valueOf(s);
            s = format(d);
        }catch(Exception e){
            e.printStackTrace();
        }
        return s;
    }
}
