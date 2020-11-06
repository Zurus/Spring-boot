package com.boblob.blog.repo;

import com.boblob.blog.models.Post;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ADivaev on 05.11.2020.
 */
public interface PostRepository extends CrudRepository<Post, Long> {
}
