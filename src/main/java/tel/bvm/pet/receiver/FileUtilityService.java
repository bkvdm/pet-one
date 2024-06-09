package tel.bvm.pet.receiver;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import tel.bvm.pet.model.CommonFileTemplate;

import java.io.IOException;
import java.nio.file.Path;

public interface FileUtilityService {
    HttpServletResponse viewFileResponse(HttpServletResponse response, CommonFileTemplate commonFileTemplate) throws IOException;

    byte[] generateFileForDataBase(Path filePath, int width) throws IOException;

    String getExtensions(String fileName);

    byte[] generateFilePdfForDataBase(Path filePath) throws IOException;

    void saveFile(MultipartFile formFile, Path filePath) throws IOException;
}
