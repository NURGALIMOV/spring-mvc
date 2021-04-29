package tech.itpark.proggerhub.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.inurgalimov.jdbc.JdbcTemplate;
import tech.itpark.proggerhub.model.Post;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final DataSource ds;

    public List<Post> getAll() {
        return JdbcTemplate.query(
                ds,
                // language=PostgreSQL
                "SELECT id, content, attachment, EXTRACT(EPOCH FROM created) AS created FROM posts",
                rs -> new Post(
                        rs.getLong("id"),
                        null,
                        rs.getString("content"),
                        rs.getString("attachment"),
                        rs.getLong("created")
                )
        );
    }

    public Post getById(long id) {
        return JdbcTemplate.querySingle(
                ds,
                "SELECT id, content, attachment, EXTRACT(EPOCH FROM created) AS created FROM posts WHERE id = ?",
                rs -> Post.builder()
                        .id(rs.getLong("id"))
                        .attachment(rs.getString("attachment"))
                        .content(rs.getString("content"))
                        .created(rs.getLong("created"))
                        .build(),
                id
        );
    }

    public long create(Post post) {
        return JdbcTemplate.querySingle(
                ds,
                "INSERT INTO posts (content, attachment) VALUES (?, ?) RETURNING id",
                rs -> rs.getLong("id"),
                post.getContent(),
                post.getAttachment()
        );
    }

    public List<Long> create(List<Post> posts) {
        StringBuilder sql = new StringBuilder("INSERT INTO posts (content, attachment) VALUES ");
        posts.forEach(post -> sql.append("(?, ?), "));
        sql.append("RETURNING id");
        Object[] objects = posts.stream()
                .flatMap(post -> Stream.of(post.getContent(), post.getAttachment()))
                .toArray();
        return JdbcTemplate.query(
                ds,
                sql.toString().replace(", RETURNING", " RETURNING"),
                rs -> rs.getLong("id"),
                objects
        );
    }

    public void update(Post post) {
        JdbcTemplate.querySingle(
                ds,
                "UPDATE posts SET content = ?, attachment = ? WHERE id = ?",
                rs -> Optional.ofNullable(rs),
                post.getContent(),
                post.getAttachment(),
                post.getId()
        );
    }

    public void update(List<Post> posts) {
        StringBuilder sql = new StringBuilder("UPDATE posts SET content = temp.content, attachment = temp.attachment FROM (VALUES ");
        posts.forEach(post -> sql.append("(?, ?, ?),"));
        sql.append(") AS temp(content, attachment, id) WHERE temp.id = posts.id");
        Object[] objects = posts.stream()
                .flatMap(post -> Stream.of(post.getContent(), post.getAttachment(), post.getId()))
                .toArray();
        JdbcTemplate.querySingle(ds, sql.toString().replace(",)", ")"), rs -> null, objects);
    }

    public void deleteById(long postId) {
        JdbcTemplate.querySingle(ds, "DELETE FROM posts WHERE id = ?", rs -> null, postId);
    }

    public void delete(List<Post> posts) {
        StringBuilder sql = new StringBuilder("DELETE FROM posts USING (VALUES ");
        posts.forEach(post -> sql.append("(?),"));
        sql.append(") AS temp(id) WHERE posts.id = temp.id");
        Object[] objects = posts.stream().map(Post::getId).toArray();
        JdbcTemplate.querySingle(ds, sql.toString().replace(",)", ")"), rs -> null, objects);
    }

}
