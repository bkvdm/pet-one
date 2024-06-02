package tel.bvm.pet.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import tel.bvm.pet.model.PicturePet;

import java.io.IOException;

public interface PicturePetService {
    void uploadPicturePet(long petId, MultipartFile picturePetFile) throws IOException;

    PicturePet findPictureByPetId(long petId);

    HttpServletResponse response(HttpServletResponse response, long id) throws IOException;
}
