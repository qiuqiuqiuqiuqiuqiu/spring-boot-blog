package com.reljicd.repository;

import com.reljicd.model.StockOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018-07-30.
 */
public interface StockOrderRepository extends JpaRepository<StockOrder, Long> {

    List<StockOrder> findByStockCodeAndOperateDate(@Param("stockCode") String stockCode,@Param("operateDate") Date operateDate);
    //Page<StockOrder> findByStockOrderOrderByOperateDateDesc(StockOrder stockOrder, Pageable pageable);

    Page<StockOrder> findAllByOrderByOperateDateDesc(Pageable pageable);

    //Optional<StockOrder> findById(Long id);
}
