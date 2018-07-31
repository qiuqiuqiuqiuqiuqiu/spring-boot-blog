package com.reljicd.service.impl;

import com.reljicd.model.Post;
import com.reljicd.model.StockOrder;
import com.reljicd.model.User;
import com.reljicd.repository.PostRepository;
import com.reljicd.service.PostService;
import com.reljicd.service.StockOrderService;
import com.reljicd.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;

    @Autowired
    private StockOrderService stockOrderService;

    @Autowired
    public PostServiceImp(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Optional<Post> findForId(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Optional<Post> findByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    @Override
    public Post save(Post post) {
        //这里进行正则匹配，入库到单表
       List<StockOrder> stockOrders= StringUtils.getStockOrderInfoByBody(post.getBody());
        for (int i=0;i<stockOrders.size();i++){
            if(!stockOrderService.isExistByStockCodeAndOperateDate(stockOrders.get(i).getStockCode(),stockOrders.get(i).getOperateDate())){
                stockOrderService.save(stockOrders.get(i));
            }
        }
        return postRepository.saveAndFlush(post);
    }

    @Override
    public Page<Post> findByUserOrderedByDatePageable(User user, int page) {
        return postRepository.findByUserOrderByCreateDateDesc(user, new PageRequest(subtractPageByOne(page), 5));
    }

    @Override
    public Page<Post> findAllOrderedByDatePageable(int page) {
        return postRepository.findAllByOrderByCreateDateDesc(new PageRequest(subtractPageByOne(page), 5));
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    private int subtractPageByOne(int page) {
        return (page < 1) ? 0 : page - 1;
    }
}
