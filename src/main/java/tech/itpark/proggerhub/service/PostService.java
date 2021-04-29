package tech.itpark.proggerhub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.itpark.proggerhub.dto.PostDto;
import tech.itpark.proggerhub.mapping.PostMapper;
import tech.itpark.proggerhub.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    public static final int MAX_PER_PAGE = 10;
    private final PostRepository repository;
    private final PostMapper postMapper;


    public List<PostDto> getAll(long lastSeenId, int perPage) {
        return postMapper.fromModels(repository.getAll());
    }

    public PostDto getById(long id) {
        return postMapper.fromModel(repository.getById(id));
    }

    public long create(PostDto post) {
        return repository.create(postMapper.fromDto(post));
    }

    public List<Long> create(List<PostDto> posts) {
        return repository.create(posts.stream()
                .map(postMapper::fromDto)
                .collect(Collectors.toList()));
    }

    public void update(long postId, PostDto post) {
        if (post.getId() == 0) {
            post.setId(postId);
        } else if (postId != post.getId()) {
            throw new IllegalArgumentException("Incorrect data for update");
        }
        repository.update(postMapper.fromDto(post));
    }

    public void update(List<PostDto> posts) {
        if (posts.stream()
                .anyMatch(post -> post.getId() == 0)) {
            throw new IllegalArgumentException("Incorrect data for update");
        }
        repository.update(posts.stream()
                .map(postMapper::fromDto)
                .collect(Collectors.toList()));
    }

    public void deleteById(long postId) {
        repository.deleteById(postId);
    }

    public void delete(List<PostDto> posts) {
        if (posts.stream()
                .anyMatch(post -> post.getId() == 0)) {
            throw new IllegalArgumentException("Incorrect data for delete");
        }
        repository.delete(posts.stream()
                .map(postMapper::fromDto)
                .collect(Collectors.toList()));
    }

}
