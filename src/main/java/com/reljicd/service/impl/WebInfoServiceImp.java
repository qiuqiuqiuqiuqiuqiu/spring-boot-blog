package com.reljicd.service.impl;

import com.reljicd.model.WebInfo;
import com.reljicd.repository.WebInfoRepository;
import com.reljicd.service.WebInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Administrator on 2018-07-07.
 */
@Service
public class WebInfoServiceImp implements WebInfoService{
    private final WebInfoRepository webInfoRepository;

    @Autowired
    public WebInfoServiceImp(WebInfoRepository webInfoRepository) {
        this.webInfoRepository = webInfoRepository;
    }


    @Override
    public Page<WebInfo> findAllByIdPageable(int page) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        return webInfoRepository.findAllByOrderByIdDesc(new PageRequest(subtractPageByOne(page), 5, sort));
    }

    @Override
    public Optional<WebInfo> findForId(Long id) {
        return webInfoRepository.findById(id);
    }

    @Override
    public WebInfo save(WebInfo webInfo) {
        if (webInfo.getId() != null) {
            webInfoRepository.updateById(webInfo.getId(), webInfo.getDescription(),  webInfo.getOptionValue());
            return webInfo;
        }
        return webInfoRepository.saveAndFlush(webInfo);
    }

    private int subtractPageByOne(int page) {
        return (page < 1) ? 0 : page - 1;
    }
}
