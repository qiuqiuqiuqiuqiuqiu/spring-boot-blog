package com.reljicd.repository;

import com.reljicd.model.WebInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Administrator on 2018-07-07.
 */
public interface WebInfoRepository  extends JpaRepository<WebInfo, Long> {

    Page<WebInfo> findAllByOrderByIdDesc(Pageable pageable);

    Optional<WebInfo> findById(Long id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update WEBINFO set description=?2 ,  OPTION_VALUE=?3 where ID=?1 ",nativeQuery = true)
    void updateById(Long id, String description, String optionValue);
}
