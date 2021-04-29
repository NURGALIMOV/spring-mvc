package tech.itpark.proggerhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
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