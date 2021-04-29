package tech.itpark.proggerhub.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private long id;
    private Author author;
    private String content;
    private String attachment;
    private long created;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Author {
        private long id;
        private String avatar;
        private String name;
    }

}