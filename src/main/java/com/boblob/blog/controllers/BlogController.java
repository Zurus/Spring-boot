package com.boblob.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by ADivaev on 03.11.2020.
 */

@Controller
public class BlogController {

    @GetMapping("/blog")
    public String blogMain(Model model) {
        return "blog-main";
    }
}
