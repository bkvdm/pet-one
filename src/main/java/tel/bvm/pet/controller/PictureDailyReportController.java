package tel.bvm.pet.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tel.bvm.pet.service.PictureDailyReportService;

import java.io.IOException;
import java.util.NoSuchElementException;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

@RestController
@Controller
@RequestMapping("picture-daily-report")
public class PictureDailyReportController {

    private final PictureDailyReportService pictureDailyReportService;

    public PictureDailyReportController(PictureDailyReportService pictureDailyReportService) {
        this.pictureDailyReportService = pictureDailyReportService;
    }

    /**
     * Загружает ежедневную фотографию отчёта на сервер. Допускается загрузка только файлов в формате изображений.
     * Выполняет проверку файла на соответствие требованиям форматов изображений.
     *
     * @param idDailyReport          Идентификатор ежедневного отчёта, к которому принадлежит загружаемая фотография.
     * @param pictureDailyReportFile Файл ежедневного отчёта в формате multipart.
     * @return ResponseEntity с сообщением об успешной загрузке или с описанием возникшей ошибки.
     * @throws IOException Если произойдёт ошибка при чтении файла.
     */
    @Operation(summary = "Загрузка ежедневной фотографии отчёта",
            description = "Метод загружает фотографию к ежедневному отчёту на сервер",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Файл успешно загружен"),
                    @ApiResponse(responseCode = "400", description = "Неправильный запрос или формат файла"),
                    @ApiResponse(responseCode = "404", description = "Ежедневный отчёт не найден"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера при загрузке файла")
            })
    @PostMapping(value = "/{idDailyReport}/upload-picture-daily-report", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPictureDailyReport(
            @Parameter(name = "idDailyReport", description = "Идентификатор ежедневного отчёта", required = true, example = "1")
            @PathVariable long idDailyReport,
            @Parameter(name = "file", description = "Файл ежедневной фотографии отчёта в формате multipart", required = true)
            @RequestParam("file") MultipartFile pictureDailyReportFile) {
        try {
            pictureDailyReportService.uploadPictureDailyReport(idDailyReport, pictureDailyReportFile);
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Отправляет изображение, связанное с дневным отчетом, в ответ на запрос по уникальному идентификатору отчета.
     *
     * @param idDailyReport Идентификатор дневного отчета, изображение для которого следует получить.
     * @param response      Объект HttpServletResponse, используемый для отправки изображения.
     * @throws IOException Если возникает проблема с записью изображения в ответ.
     */
    @Operation(summary = "Получить изображение из дневного отчета по идентификатору",
            description = "Отправляет изображение из дневного отчета, хранящееся в системе.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Изображение из дневного отчета успешно отправлено"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос, отчет с данным идентификатором не найден"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера при попытке отправить изображение")
            })
    @GetMapping(value = "/{idDailyReport}/picture-report", produces = MediaType.IMAGE_PNG_VALUE)
    public void getDailyReportPicture(@Parameter(description = "Идентификатор дневного отчета") @PathVariable long idDailyReport,
                                      HttpServletResponse response) throws IOException {
        pictureDailyReportService.response(response, idDailyReport);
    }
}
