package tech.itpark.proggerhub.service;

import org.apache.tika.Tika;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.itpark.proggerhub.dto.MediaUploadDto;
import tech.itpark.proggerhub.exception.MediaUploadException;
import tech.itpark.proggerhub.exception.UnsupportedMediaTypeException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class MediaService implements InitializingBean {

    private final Path path = Path.of("C:/tmp");
    private final Tika tika = new Tika();
    private final Map<String, String> supportedMediaTypeExtensions = Map.of(
            MediaType.IMAGE_PNG_VALUE, ".png",
            MediaType.IMAGE_JPEG_VALUE, ".jpg"
    );

    @Override
    public void afterPropertiesSet() throws Exception {
        Files.createDirectories(path);
    }

    public MediaUploadDto upload(MultipartFile file) {
        try {
            final var type = tika.detect(file.getInputStream());
            final var extension = Optional.ofNullable(
                    supportedMediaTypeExtensions.get(type)
            )
                    .orElseThrow(UnsupportedMediaTypeException::new);

            final var name = UUID.randomUUID() + extension;

            final var formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            final var today = formatter.format(OffsetDateTime.now());
            final var webPath = today + "/" + name;
            final var resolved = path.resolve(today);
            Files.createDirectories(resolved);
            file.transferTo(resolved.resolve(name));
            return new MediaUploadDto(webPath);
        } catch (IOException e) {
            throw new MediaUploadException(e);
        }
    }

}
