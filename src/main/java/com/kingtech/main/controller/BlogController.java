package com.kingtech.main.controller;

import com.kingtech.main.data.model.Blog;
import com.kingtech.main.data.model.CreateBlogRequestModel;
import com.kingtech.main.data.model.CustomResponseModel;
import com.kingtech.main.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/blog/"})
public class BlogController {

    @Autowired
    BlogRepository blogRepository;

    @GetMapping
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }


    @GetMapping(path = {"{id}"})
    public ResponseEntity<Blog> findById(@PathVariable int id) {
        return blogRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("search")
    public List<Blog> search(@RequestBody Map<String, String> body) {
        String searchTerm = body.get("text");
        return blogRepository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
    }

    @PostMapping
    public Blog createBlog(@RequestBody CreateBlogRequestModel blogRequestModel) {
        Blog blog = new Blog(blogRequestModel.getTitle(), blogRequestModel.getContent());
        return blogRepository.save(blog);
    }

    @PutMapping("{id}")
    public CustomResponseModel<Blog> update(@PathVariable int id, @RequestBody CreateBlogRequestModel requestModel) {
        return blogRepository.findById(id)
                .map(blog -> {
                    blog.setTitle(requestModel.getTitle());
                    blog.setContent(requestModel.getContent());
                    Blog updatedBlog = blogRepository.save(blog);
                    return new CustomResponseModel<>("Blog updated successfully", updatedBlog);
                }).orElse(new CustomResponseModel<>("Blog does not exist", null));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return blogRepository.findById(id)
                .map(record -> {
                    blogRepository.deleteById(id);
                    return ResponseEntity.ok().body(new CustomResponseModel<>("Blog deleted successfully", record));
                }).orElse(ResponseEntity.notFound().build());
    }
}
