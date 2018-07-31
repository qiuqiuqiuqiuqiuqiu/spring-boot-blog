package com.reljicd.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018-07-30.
 */
@Entity
@Table(name = "stock_order")
public class StockOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stock_order_id")
    private Long id;

    @Column(name = "stock_code")
    private String stockCode;

    @Column(name = "stock_count")
    private String stockCount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "operate_date", nullable = false, updatable = false)
    //@CreationTimestamp
    private Date operateDate;

    @Column(name = "operate_type")
    private String operateType;

    @Column(name = "operate_price")
    private String stockPrice;

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockCount() {
        return stockCount;
    }

    public void setStockCount(String stockCount) {
        this.stockCount = stockCount;
    }

    public void setStockPrice(String stockPrice) {
        this.stockPrice = stockPrice;
    }

    public String getStockPrice() {
        return stockPrice;
    }
}
