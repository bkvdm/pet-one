package tel.bvm.pet.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tel.bvm.pet.model.ContentForm;
import tel.bvm.pet.model.Form;
import tel.bvm.pet.receiver.FileTypeMatchingService;
import tel.bvm.pet.receiver.FileUtilityService;
import tel.bvm.pet.repository.ContentFormRepository;
import tel.bvm.pet.repository.FormRepository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class FormServiceImpl implements FormService {

    private final FileUtilityService fileUtilityService;
    private final FileTypeMatchingService fileTypeMatchingService;
    private final ContentFormRepository contentFormRepository;
    private final FormRepository formRepository;

    @Value("${path.to.form_pdf.folder}")
    private String formDir;

    public FormServiceImpl(FileUtilityService fileUtilityService, FileTypeMatchingService fileTypeMatchingService, ContentFormRepository contentFormRepository, FormRepository formRepository) {
        this.fileUtilityService = fileUtilityService;
        this.fileTypeMatchingService = fileTypeMatchingService;
        this.contentFormRepository = contentFormRepository;
        this.formRepository = formRepository;
    }

    /**
     * Загружает форму (приложение в форме PDF документа) в систему и связывает ее по idContent.
     *
     * @param idContent Идентификатор контента, к которому будет прикреплена форма.
     * @param formFile  Файл формы, который нужно загрузить.
     * @throws IOException если произошла ошибка при сохранении файла.
     */
    @Override
    public void uploadForm(long idContent, MultipartFile formFile) throws IOException {
        if (!fileTypeMatchingService.isValidPdf(formFile)) {
            throw new IllegalArgumentException("Invalid file type. Please upload an PDF file.");
        }

        Optional<ContentForm> contentFormOptional = Optional.ofNullable(contentFormRepository.findById(idContent));

        if (!contentFormOptional.isPresent()) {
            throw new NoSuchElementException("Content form with id " + idContent + " already exists.");
        }

        Optional<ContentForm.NameContentForm> nameContentFormOptional = Optional.ofNullable(contentFormOptional.get().getNameContent());

        String formFileName = nameContentFormOptional.map(Enum::toString).orElse("defaultForm");

        Path filePath = Path.of(formDir, formFileName + "." + fileUtilityService.getExtensions(Objects.requireNonNull(formFile.getOriginalFilename())));

        fileUtilityService.saveFile(formFile, filePath);

        Form form = findFormByContentFormId(idContent);
        form.setContentForm(contentFormOptional.get());
        form.setFilePath(filePath.toString());
        form.setFileSize(formFile.getSize());

        String contentType = formFile.getContentType();

        if (contentType == null) {
            throw new IllegalStateException("Content type of file is null");
        }

        form.setMediaType(contentType);
        form.setDataForm(fileUtilityService.generateFileForDataBase(filePath, 1));
        formRepository.save(form);
    }

    /**
     * Находит форму по идентификатору связанного с ней контента.
     *
     * @param idContentForm Идентификатор контента, с которым связана форма.
     * @return Найденный объект формы, либо создает и возвращает новый, если форма не найдена.
     */
    @Override
    public Form findFormByContentFormId(long idContentForm) {
        return formRepository.findFormByContentForm_Id(idContentForm)
                .orElse(new Form());
    }
}
