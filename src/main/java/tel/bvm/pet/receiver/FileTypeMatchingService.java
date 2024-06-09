package tel.bvm.pet.receiver;

import org.springframework.web.multipart.MultipartFile;

public interface FileTypeMatchingService {
    boolean isValidImage(MultipartFile file);

    boolean isValidPdf(MultipartFile file);
}
