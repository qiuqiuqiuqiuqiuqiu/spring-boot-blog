package com.reljicd.service;

import com.reljicd.model.StockOrder;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * Created by Administrator on 2018-07-30.
 */
public interface StockOrderService {
    Page<StockOrder> findAllOrderedByDatePageable(int page);

    void save(StockOrder stockOrder);

    boolean isExistByStockCodeAndOperateDate(String stockCode, Date operateDate);
}
