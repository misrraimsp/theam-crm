package misrraimsp.theam.crm.service;

import lombok.RequiredArgsConstructor;
import misrraimsp.theam.crm.data.ImageRepository;
import misrraimsp.theam.crm.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;

@RequiredArgsConstructor
@Service
public class ImageServer {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ImageRepository imageRepository;

    public Image findById(String id) {
        return imageRepository.findById(id).orElseThrow(() ->
                new HttpClientErrorException(
                        HttpStatus.NOT_FOUND,
                        "Not Found",
                        String.format(
                                "{\"errorMessage\": \"Entity of class %s not found by id=%s\"}",
                                Image.class.getSimpleName(),
                                id
                        ).getBytes(),
                        Charset.defaultCharset()
                )
            );
    }

    public Image create(Image image) {
        Image persistedImage = imageRepository.save(image);
        LOGGER.info("Image (id={}) persisted", persistedImage.getId());
        return persistedImage;
    }

    public void delete(String imageId) {
        imageRepository.delete(this.findById(imageId));
        LOGGER.info("Image (id={}) deleted", imageId);
    }

}
