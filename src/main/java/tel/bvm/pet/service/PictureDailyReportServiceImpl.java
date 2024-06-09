package tel.bvm.pet.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tel.bvm.pet.model.*;
import tel.bvm.pet.receiver.DateTimeUtilityService;
import tel.bvm.pet.receiver.FileTypeMatchingService;
import tel.bvm.pet.receiver.FileUtilityService;
import tel.bvm.pet.repository.DailyReportRepository;
import tel.bvm.pet.repository.PictureDailyReportRepository;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class PictureDailyReportServiceImpl implements PictureDailyReportService {

    private final DailyReportRepository dailyReportRepository;

    private final FileTypeMatchingService fileTypeMatchingService;

    private final DateTimeUtilityService dateTimeUtilityService;
    private final PictureDailyReportRepository pictureDailyReportRepository;
    private final FileUtilityService fileUtilityService;

    @Value("${path.to.picture_daily_report.folder}")
    private String pictureDailyReportDir;

    public PictureDailyReportServiceImpl(PictureDailyReportRepository pictureDailyReportRepository, DailyReportRepository dailyReportRepository, FileTypeMatchingService fileTypeMatchingService, DateTimeUtilityService dateTimeUtilityService, FileUtilityService fileUtilityService) {
        this.dailyReportRepository = dailyReportRepository;
        this.fileTypeMatchingService = fileTypeMatchingService;
        this.dateTimeUtilityService = dateTimeUtilityService;
        this.pictureDailyReportRepository = pictureDailyReportRepository;
        this.fileUtilityService = fileUtilityService;
    }

    /**
     * Загружает изображение ежедневного отчета в систему.
     * <p>
     * Этот метод проверяет тип загружаемого файла изображения, находит соответствующий ежедневный отчет по его ID,
     * сохраняет информацию об изображении в базе данных и сохраняет само изображение в файловой системе.
     *
     * @param idDailyReport          ID ежедневного отчета, к которому прикрепляется изображение.
     * @param pictureDailyReportFile Мультипарт файл изображения ежедневного отчета.
     * @throws IOException              если возникает ошибка при сохранении файла.
     * @throws IllegalArgumentException если тип файла не является допустимым изображением.
     * @throws NoSuchElementException   если не найден ежедневный отчет, вид питомца или сам питомец по указанному ID.
     */
    @Override
    public void uploadPictureDailyReport(long idDailyReport, MultipartFile pictureDailyReportFile) throws IOException {
        if (!fileTypeMatchingService.isValidImage(pictureDailyReportFile)) {
            throw new IllegalArgumentException("Invalid file type. Please upload an image file.");
        }
        Optional<DailyReport> dailyReportOptional = Optional.ofNullable(dailyReportRepository.findByIdDailyReport(idDailyReport));
        Optional<Long> dailyReportIdOptional = dailyReportOptional.map(DailyReport::getIdDailyReport);
        if (!dailyReportIdOptional.isPresent()) {
            throw new NoSuchElementException("Daily Report not found with id: " + idDailyReport);
        }
        Optional<ViewPet.NameViewPet> viewPetOptional = Optional.ofNullable(dailyReportOptional.get().getPet().getViewPet().getNameViewPet());
        Optional<Pet> petOptional = Optional.ofNullable(dailyReportOptional.get().getPet());
        Optional<LocalDateTime> dateTimeDailyReportOptional = Optional.ofNullable(dailyReportOptional.get().getDateTime());

        if (!viewPetOptional.isPresent()) {
            throw new NoSuchElementException("Name view pet not found");
        }

        if (!petOptional.isPresent()) {
            throw new NoSuchElementException("Pet not found");
        }

        if (!dateTimeDailyReportOptional.isPresent()) {
            throw new NoSuchElementException("Date time daily report not found");
        }

        String viewPetFileName = viewPetOptional.map(Enum::toString).orElse("defaultView");
        String idPetFileName = petOptional.map(Object::toString).orElse("defaultPet");
        String idDailyReportFileName = dailyReportIdOptional.map(Object::toString).orElse("defaultIdDailyReport");
        String dateTimeDailyReportFileName = dateTimeDailyReportOptional.map(dateTimeUtilityService::dateTimeToString).orElse("defaultDateTimeDailyReport");

        Path filePath = Path.of(pictureDailyReportDir, viewPetFileName + "_" + idPetFileName + "_" + idDailyReportFileName + "_" + dateTimeDailyReportFileName + "." + fileUtilityService.getExtensions(Objects.requireNonNull(pictureDailyReportFile.getOriginalFilename())));

        fileUtilityService.saveFile(pictureDailyReportFile, filePath);

        PictureDailyReport pictureDailyReport = findPictureByDailyReportId(idDailyReport);
        pictureDailyReport.setDailyReport(dailyReportOptional.get());
        pictureDailyReport.setFilePath(filePath.toString());
        pictureDailyReport.setFileSize(pictureDailyReportFile.getSize());

        String contentType = pictureDailyReportFile.getContentType();

        if (contentType == null) {
            throw new IllegalStateException("Content type of file is null");
        }

        pictureDailyReport.setMediaType(contentType);
        pictureDailyReport.setDataForm(fileUtilityService.generateFileForDataBase(filePath, 100));
        pictureDailyReportRepository.save(pictureDailyReport);
    }

    /**
     * Находит изображение ежедневного отчета по идентификатору ежедневного отчета.
     *
     * @param idDailyReport ID ежедневного отчета.
     * @return Изображение ежедневного отчета или новый пустой объект, если изображение не найдено.
     */
    @Override
    public PictureDailyReport findPictureByDailyReportId(long idDailyReport) {
        return pictureDailyReportRepository.findPictureDailyReportByDailyReport_idDailyReport(idDailyReport)
                .orElse(new PictureDailyReport());
    }

    /**
     * Формирует и отправляет HTTP-ответ с изображением ежедневного отчета.
     * <p>
     * Этот метод извлекает данные изображения из базы данных и записывает их в HTTP-ответ,
     * позволяя клиенту просматривать изображение через веб-интерфейс.
     *
     * @param response      HttpServletResponse, через который отправляется изображение.
     * @param idDailyReport ID ежедневного отчета, изображение которого требуется отправить.
     * @return Обновленный объект HttpServletResponse с изображением.
     * @throws IOException если возникает ошибка при записи файла в ответ.
     */
    @Override
    public HttpServletResponse response(HttpServletResponse response, long idDailyReport) throws IOException {
        PictureDailyReport pictureDailyReport = findPictureByDailyReportId(idDailyReport);
        fileUtilityService.viewFileResponse(response, pictureDailyReport);
        return response;
    }
}
