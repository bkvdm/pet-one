package tel.bvm.pet.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tel.bvm.pet.model.Pet;
import tel.bvm.pet.model.PicturePet;
import tel.bvm.pet.model.ViewPet;
import tel.bvm.pet.receiver.FileTypeMatchingService;
import tel.bvm.pet.receiver.FileUtilityService;
import tel.bvm.pet.repository.PetRepository;
import tel.bvm.pet.repository.PicturePetRepository;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

@Service
@Transactional
public class PicturePetServiceImpl implements PicturePetService {

    private final PicturePetRepository picturePetRepository;

    private final PetRepository petRepository;

    private final FileTypeMatchingService fileTypeMatchingService;

    private final FileUtilityService fileUtilityService;

    @Value("${path.to.picture_pet.folder}")
    private String picturePetDir;

    public PicturePetServiceImpl(PetRepository petRepository, PicturePetRepository picturePetRepository, FileTypeMatchingService fileTypeMatchingService, FileUtilityService fileUtilityService) {
        this.petRepository = petRepository;
        this.picturePetRepository = picturePetRepository;
        this.fileTypeMatchingService = fileTypeMatchingService;
        this.fileUtilityService = fileUtilityService;
    }

    /**
     * Загружает фотографию питомца в систему.
     * <p>
     * Проверяет наличие питомца в базе данных, корректность типа файла изображения, сохраняет файл,
     * записывает информацию о файле в базу данных и связывает его с соответствующим питомцем.
     *
     * @param petId          ID питомца, которому принадлежит фотография.
     * @param picturePetFile Мультипарт файл с фотографией питомца.
     * @throws IOException              если происходит ошибка сохранения файла.
     * @throws NoSuchElementException   если питомец по данному ID не найден.
     * @throws IllegalArgumentException если тип файла не поддерживается.
     * @throws IllegalStateException    если тип содержимого файла не определен.
     */
    @Override
    public void uploadPicturePet(long petId, MultipartFile picturePetFile) throws IOException {

        Optional<Pet> pet = Optional.ofNullable(petRepository.findPetById(petId));
        if (!pet.isPresent()) {
            throw new NoSuchElementException("Pet not found with id " + petId);
        }
        Optional<ViewPet.NameViewPet> viewPetOpt = Optional.ofNullable(pet.get().getViewPet().getNameViewPet());
        Optional<Long> petIdOpt = pet.map(Pet::getId);


        if (!fileTypeMatchingService.isValidImage(picturePetFile)) {
            throw new IllegalArgumentException("Invalid file type. Please upload an image file.");
        }

        String viewFileName = viewPetOpt
                .map(Enum::toString)
                .orElse("defaultView");

        String idFileName = petIdOpt.map(Object::toString).orElse("defaultId");

        Path filePath = Path.of(picturePetDir,
                viewFileName + "_" + idFileName + "." + fileUtilityService.getExtensions(Objects.requireNonNull(picturePetFile.getOriginalFilename())));

        fileUtilityService.saveFile(picturePetFile, filePath);

        PicturePet picturePet = findPictureByPetId(petId);
        picturePet.setPet(pet.get());
        picturePet.setFilePath(filePath.toString());
        picturePet.setFileSize(picturePetFile.getSize());

        String contentType = picturePetFile.getContentType();

        if (contentType == null) {
            throw new IllegalStateException("Content type of file is null");
        }

        picturePet.setMediaType(contentType);
        picturePet.setDataForm(fileUtilityService.generateFileForDataBase(filePath, 200));
        picturePetRepository.save(picturePet);
    }

    /**
     * Находит фотографию питомца по идентификатору питомца.
     *
     * @param petId ID питомца.
     * @return Объект изображения питомца или новый пустой объект, если изображение не найдено.
     */
    @Override
    public PicturePet findPictureByPetId(long petId) {
        return picturePetRepository.findByPetId(petId).orElse(new PicturePet());
    }

    /**
     * Формирует HTTP-ответ для отображения фотографии питомца клиенту.
     * <p>
     * Производит поиск фотографии по ID питомца, добавляет фотографию в HTTP-ответ
     * и отправляет его клиенту, позволяя просмотреть изображение питомца.
     *
     * @param response текущий HTTP-ответ.
     * @param id       ID питомца, фотографию которого требуется отправить.
     * @return Обновленный HttpServletResponse с фотографией питомца.
     * @throws IOException если происходит ошибка записи файла в HTTP-ответ.
     */
    @Override
    public HttpServletResponse response(HttpServletResponse response, long id) throws IOException {
        PicturePet picturePet = findPictureByPetId(id);
        fileUtilityService.viewFileResponse(response, picturePet);
        return response;
    }
}
