package tech.itpark.proggerhub.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.itpark.proggerhub.dto.MediaUploadDto;
import tech.itpark.proggerhub.service.MediaService;

@RequestMapping("/media")
@RestController
@RequiredArgsConstructor
public class MediaController {

    private final Log log = LogFactory.getLog(getClass());
    private final MediaService service;

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public MediaUploadDto upload(@RequestParam MultipartFile file) {
        log.debug(file.getOriginalFilename());
        return service.upload(file);
    }
}
