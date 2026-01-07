package cn.metast.tuoke.module.live.utils;
import org.springframework.cglib.beans.BeanMap;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
/**
 * 一些工具
 */
public class CommUtils {
    //数字格式化
    public static String numberFormat(long num) {
        if (num < 10000) {

        } else if (num < 1000000) {
            double d = num / 10000.0;
            BigDecimal bigD = new BigDecimal(d);
            double t = bigD.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return String.valueOf(t) + "万";

        } else if (num < 100000000) {
            double d = num / 10000.0;
            BigDecimal bigD = new BigDecimal(d);
            double t = bigD.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            return String.valueOf(t) + "万";
        } else if (num < 10000000000L) {
            double d = num / 10000.0;
            BigDecimal bigD = new BigDecimal(d);
            double t = bigD.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            return String.valueOf(t) + "亿";
        } else if (num < 10000000000L) {
            double d = num / 10000.0;
            BigDecimal bigD = new BigDecimal(d);
            double t = bigD.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            return String.valueOf(t) + "亿";
        }
        return String.valueOf(num);
    }
    /* 数字格式化 */
    public static String NumberFormat(Double num){
        DecimalFormat df = new DecimalFormat("#0.0");
        DecimalFormat df2 = new DecimalFormat("#0.00");
        String n = num.toString();
        if(num < 10000){

        }else if(num < 1000000){
            n = df2.format(num / 10000)+"万";
        }else if(num < 100000000){
            n = df.format(num / 10000)+"万";
        }else if(num < 10000000000L){
            n = df2.format(num / 100000000)+"亿";
        }else {
            n = df.format(num / 100000000)+"亿";
        }

        return n;
    }


    //对象转Map
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }
    //对象转Map 不要 null
    public static <T> Map<String, Object> beanToMapNotNull(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                Object o = beanMap.get(key);
                if(o != null) {
                    map.put(key + "", beanMap.get(key));
                }
            }
        }
        return map;
    }
}
