package com.boblob.blog.controllers;

import com.boblob.blog.models.Post;
import com.boblob.blog.models.Role;
import com.boblob.blog.models.User;
import com.boblob.blog.repo.PostRepository;
import com.boblob.blog.repo.UserRepo;
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
    private UserRepo userRepo;

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

        if (file != null) {
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
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
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

//
//    @GetMapping("/blog")
//    public String blogMain(Model model) {
//        Iterable<Post> posts = postRepository.findAll();
//        model.addAttribute("posts", posts);
//        return "blog-main";
//    }
//
//    @GetMapping("/blog/{id}")
//    public String blogDetails(@PathVariable(value="id") long postId, Model model) {
//        if (!postRepository.existsById(postId)) {
//             return "redirect:/blog";
//        }
//
//        Optional<Post> post =  postRepository.findById(postId);
//        List<Post> res = new ArrayList<>();
//        post.ifPresent(res::add);
//        model.addAttribute("post", res);
//        return "blog-details";
//    }
//
//    @GetMapping("/blog/{id}/edit")
//    public String blogEdit(@PathVariable(value="id") long postId, Model model) {
//        if (!postRepository.existsById(postId)) {
//             return "redirect:/blog";
//        }
//
//        Optional<Post> post =  postRepository.findById(postId);
//        List<Post> res = new ArrayList<>();
//        post.ifPresent(res::add);
//        model.addAttribute("post", res);
//        return "blog-edit";
//    }
//
//    @PostMapping("/blog/add")
//    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text,  Model model) {
//        Post post = new Post(title, anons, full_text);
//        postRepository.save(post);
//        return "redirect:/blog";
//    }
//
//    @PostMapping("/blog/{id}/edit")
//    public String blogPostUpdate(@PathVariable(value ="id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text,  Model model) {
//        Post post = postRepository.findById(id).orElseThrow(null);
//        post.setTitle(title);
//        post.setAnons(anons);
//        post.setFull_text(full_text);
//        postRepository.save(post);
//        return "redirect:/blog";
//    }
//    @PostMapping("/blog/{id}/remove")
//    public String blogPostDelete(@PathVariable(value ="id") long id,  Model model) {
//        Post post = postRepository.findById(id).orElseThrow(null);
//        postRepository.delete(post);
//        return "redirect:/blog";
//    }
}
