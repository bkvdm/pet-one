package tel.bvm.pet.service;

import org.springframework.web.multipart.MultipartFile;
import tel.bvm.pet.model.Form;

import java.io.IOException;

public interface FormService {
    void uploadForm(long idContent, MultipartFile formFile) throws IOException;

    Form findFormByContentFormId(long idContentForm);
}
