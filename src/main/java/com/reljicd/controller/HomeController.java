package com.reljicd.controller;

import com.reljicd.model.Post;
import com.reljicd.model.StockOrder;
import com.reljicd.service.PostService;
import com.reljicd.service.StockOrderService;
import com.reljicd.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final PostService postService;
    private final StockOrderService stockOrderService;

    @Autowired
    public HomeController(PostService postService, StockOrderService stockOrderService) {
        this.postService = postService;
        this.stockOrderService = stockOrderService;
    }

    @GetMapping(value = {"/home", "/"})
    public String home(@RequestParam(defaultValue = "0") int page,
                       Model model) {

        Page<Post> posts = postService.findAllOrderedByDatePageable(page);
        Pager pager = new Pager(posts);

        model.addAttribute("pager", pager);

        return "/home";
    }

    @GetMapping(value = {"/stockList"})
    public String stockList(@RequestParam(defaultValue = "0") int page,
                            Model model) {

        Page<StockOrder> posts = stockOrderService.findAllOrderedByDatePageable(page);
        Pager pager = new Pager(posts);

        model.addAttribute("pager", pager);

        return "/stockList";
    }
}
