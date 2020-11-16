package com.boblob.blog.controllers;

import com.boblob.blog.models.Post;
import com.boblob.blog.models.Role;
import com.boblob.blog.models.User;
import com.boblob.blog.repo.PostRepository;
import com.boblob.blog.repo.UserRepo;
import com.boblob.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 */
@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public String greeting() {
        return "greeting";
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "main";
    }

    @PostMapping("/add")
    public String blogPostAdd(
            @AuthenticationPrincipal User user,
            @RequestParam String title, @RequestParam String anons, @RequestParam String full_text,
            @RequestParam("file") MultipartFile file, Model model) throws IOException {
        Post post = new Post(title, anons, full_text, user);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadFolder = new File(uploadPath);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));

            post.setFileName(resultFileName);
        }

        postRepository.save(post);
        return "redirect:/list";
    }

    @GetMapping("/add")
    public String blogAdd(Model model) {
        return "add";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        if (!userService.addUser(user)) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        return "redirect:/login";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String anons,  Model model) {
        Iterable<Post> posts = null;
        if (anons != null && !anons.isEmpty()) {
            posts = postRepository.findByAnons(anons);
        } else {
            posts = postRepository.findAll();
        }
        model.addAttribute("posts", posts);
        return "main";
    }

    @GetMapping("/activate/{code}")
    public String activateCode(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User seccessfully activated");
        } else {
            model.addAttribute("message", "Активация не прошла!");
        }

        return "login";
    }

}
