package com.reljicd.util;

import com.reljicd.model.StockOrder;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018-07-30.
 */
public class StringUtils {
    public static List<StockOrder> getStockOrderInfoByBody(String body) {
        List<StockOrder> ret = new ArrayList<>();
        String[] lines = body.split("<br />");
        for (String line : lines) {
            String[] before = line.split("]:");
            if (before.length >= 2) {
                Date operateDate = getOperateDate(before[0]);
                if (null != operateDate) {
                    StockOrder stockOrder = getStockOrderByStr(before[1]);
                    if (null != stockOrder) {
                        stockOrder.setOperateDate(operateDate);
                        ret.add(stockOrder);
                    }
                } else {
                    continue;
                }
            }
        }
        return ret;
    }

    private static StockOrder getStockOrderByStr(String forward) {
        if (forward.indexOf("]") > 0) {
            forward = forward.substring(forward.indexOf("]") + 1, forward.length());
        }
        String[] allInfo = forward.split(",");
        if (allInfo.length >= 4) {
            StockOrder stockOrder = new StockOrder();
            stockOrder.setOperateType(("0".equals(allInfo[0])) ? "买入" : "卖出");
            stockOrder.setStockCode(allInfo[1]);
            stockOrder.setStockPrice(allInfo[2].replace("预警价格", ""));
            stockOrder.setStockCount(allInfo[3]);
            return stockOrder;
        }
        return null;
    }

    private static Date getOperateDate(String str) {
        if (str.indexOf("]") > 0) {
            str = str.substring(1, str.indexOf("]"));
        }
        Calendar date = Calendar.getInstance();
        str = String.valueOf(date.get(Calendar.YEAR)) + "-" + str;
        //字符串转化为date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strToDate = formatter.parse(str, pos);
        return strToDate;
    }

    public static void main(String[] args) {
        String body = "[07-18 09:34:05][APP][A14     ]: [收到下单指令]0, 603283, 预警价格27.354, 200<br />[07-18 09:34:05][APP][A14     ]: [买入下单]603283, 27.63<br />[07-18 09:38:19][APP][A14     ]: [收到下单指令]0, 603937, 预警价格17.711, 300<br />[07-18 09:38:19][APP][A14     ]: [买入下单]603937, 17.89<br />[07-18 09:39:27][APP][A14     ]: [收到下单指令]0, 603676, 预警价格17.652, 300<br />[07-18 09:39:27][APP][A14     ]: [买入下单]603676, 17.83<br />[07-18 09:49:28][APP][A14     ]: [收到下单指令]0, 2885, 预警价格26.156, 200<br />[07-18 09:49:28][APP][A14     ]: [买入下单]2885, 26.42<br />[07-18 09:50:32][APP][A14     ]: [收到下单指令]0, 2873, 预警价格26.255, 200<br />[07-18 09:50:32][APP][A14     ]: [买入下单]2873, 26.52<br />[07-18 09:52:28][APP][A14     ]: [收到下单指令]0, 603612, 预警价格18.572, 300<br />[07-18 09:52:28][APP][A14     ]: [买入下单]603612, 18.76<br />";
        List<StockOrder> list = StringUtils.getStockOrderInfoByBody(body);
        System.out.println(list.size());
    }
}
