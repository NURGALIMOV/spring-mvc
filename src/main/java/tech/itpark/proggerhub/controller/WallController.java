package tech.itpark.proggerhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.itpark.proggerhub.dto.PostDto;
import tech.itpark.proggerhub.service.PostService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/walls/{id}")
@RequiredArgsConstructor
public class WallController {
    private final PostService service;

    @GetMapping("/posts")
    public List<PostDto> getAll(
            @RequestParam(value = "lastSeenId", required = false, defaultValue = "0") long lastSeenId,
            @RequestParam Optional<Integer> perPage) {
        return service.getAll(lastSeenId, perPage.orElse(PostService.MAX_PER_PAGE));
    }

    //read
    @GetMapping("/posts/{postId}")
    public PostDto getById(@PathVariable long postId) {
        return service.getById(postId);
    }

    //create
    @PostMapping(value = "/posts")
    public List<Long> create(@RequestBody List<PostDto> posts) {
        return service.create(posts);
    }

    //update
    @PutMapping("/posts/{postId}")
    public void update(@PathVariable long postId, @RequestBody PostDto post) {
        service.update(postId, post);
    }

    //update all
    @PutMapping("/posts")
    public void update(@RequestBody List<PostDto> posts) {
        service.update(posts);
    }

    //delete
    @DeleteMapping("/posts/{postId}")
    public void deleteById(@PathVariable long postId) {
        service.deleteById(postId);
    }

    //delete all
    @DeleteMapping("/posts")
    public void delete(@RequestBody List<PostDto> posts) {
        service.delete(posts);
    }

}
