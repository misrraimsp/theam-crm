package misrraimsp.theam.crm.util.converter;

import misrraimsp.theam.crm.model.Image;
import misrraimsp.theam.crm.util.BadImageException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Component
public class MultipartFileToImageConverter implements Converter<MultipartFile,Image> {

    @Override
    public Image convert(MultipartFile multipartFile) throws BadImageException {
        String imageName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));// Normalize image name
        if(imageName.contains("..")) {// Check if the image's name contains invalid characters
            throw new BadImageException("filename contains invalid path sequence " + imageName);
        }
        byte[] data;
        try {
            data = multipartFile.getBytes();
        }
        catch (IOException e) {
            throw new BadImageException("Could not upload image " + imageName + "because of: " + e.getMessage());
        }
        Image image = new Image();
        image.setName(imageName);
        image.setMimeType(multipartFile.getContentType());
        image.setData(data);
        return image;
    }
}
