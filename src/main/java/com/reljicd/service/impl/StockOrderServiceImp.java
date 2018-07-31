package com.reljicd.service.impl;

import com.reljicd.model.StockOrder;
import com.reljicd.repository.StockOrderRepository;
import com.reljicd.service.StockOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018-07-30.
 */
@Service
public class StockOrderServiceImp implements StockOrderService {
    private final StockOrderRepository stockOrderRepository;

    @Autowired
    public StockOrderServiceImp(StockOrderRepository stockOrderRepository) {
        this.stockOrderRepository = stockOrderRepository;
    }

    @Override
    public Page<StockOrder> findAllOrderedByDatePageable(int page) {
        return stockOrderRepository.findAllByOrderByOperateDateDesc(new PageRequest(subtractPageByOne(page), 20));
    }

    @Override
    public void save(StockOrder stockOrder) {
        stockOrderRepository.saveAndFlush(stockOrder);
    }

    public boolean isExistByStockCodeAndOperateDate(String stockCode, Date operateDate) {
        List<StockOrder> optionalStockOrder = stockOrderRepository.findByStockCodeAndOperateDate(stockCode, operateDate);
        return (optionalStockOrder != null && optionalStockOrder.size() > 0) ? true : false;
    }

    private int subtractPageByOne(int page) {
        return (page < 1) ? 0 : page - 1;
    }
}
