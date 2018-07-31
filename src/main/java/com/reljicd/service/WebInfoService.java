package com.reljicd.service;

import com.reljicd.model.WebInfo;
import org.springframework.data.domain.Page;

import java.util.Optional;

/**
 * Created by Administrator on 2018-07-07.
 */
public interface WebInfoService {
     Page<WebInfo> findAllByIdPageable(int page) ;

     Optional<WebInfo> findForId(Long id);

     WebInfo save(WebInfo webInfoOld);
}
