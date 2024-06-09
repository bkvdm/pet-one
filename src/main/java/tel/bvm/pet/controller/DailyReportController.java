package tel.bvm.pet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tel.bvm.pet.model.DailyReportDto;
import tel.bvm.pet.service.DailyReportService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Parameter;
import tel.bvm.pet.service.PetService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@Controller
@RequestMapping("daily-report")
public class DailyReportController {

    private final DailyReportService dailyReportService;

    public DailyReportController(DailyReportService dailyReportService, PetService petService) {
        this.dailyReportService = dailyReportService;
    }

    /**
     * Добавляет ежедневный отчет о питомце в формате JSON DailyReportDto.
     * Ожидает получение данных отчета и даты в формате LocalDate. Если дата совпадает с текущей,
     * используется актуальное время, в противном случае - конец указанной даты.
     *
     * @param dailyReportDto Данные отчета включая ID питомца, самочувствие, реакцию, и дату отчета.
     * @return Сообщение о успешном добавлении или детали ошибки.
     */
    @Operation(summary = "Добавление ежедневного отчета питомца",
            description = "Метод для добавления ежедневного отчета питомца, содержащий информацию о его самочувствии и реакции.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Отчет успешно добавлен"),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Питомец не найден", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @PostMapping("/add-report-json")
    public ResponseEntity<?> addDailyReportTextFormat(@RequestBody DailyReportDto dailyReportDto) {
        try {
            LocalDate dateProvided = dailyReportDto.getDateTime().toLocalDate();
            LocalDateTime dateTimeToUse = dateProvided.equals(LocalDate.now())
                    ? LocalDateTime.now()
                    : dateProvided.atTime(23, 59, 59, 999999999);
            dailyReportService.addDailyReportTextFormat(dailyReportDto.getPetId(), dailyReportDto.getWell(), dailyReportDto.getReaction(), dateTimeToUse);
            return ResponseEntity.ok("Report added successfully.");
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format: " + e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Pet not found with ID " + dailyReportDto.getPetId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    /**
     * Добавляет ежедневный отчет о питомце в текстовом формате.
     * Ожидает получение данных отчета и даты в формате LocalDate.
     *
     * @param idPet    ID питомца, для которого добавляется отчет. Это поле является обязательным.
     * @param well     Информация о самочувствии питомца.
     * @param reaction Информация о реакции питомца.
     * @param date     Дата отчета.
     * @return Сообщение о успешном добавлении или детали ошибки.
     */
    @Operation(summary = "Добавление ежедневного отчета питомца",
            description = "Метод для добавления ежедневного отчета питомца, содержащий информацию о его самочувствии и реакции.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Отчет успешно добавлен"),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Питомец не найден", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @PostMapping("/add-report-text")
    public ResponseEntity<?> addDailyReportTextFormat(
            @Parameter(description = "Идентификатор питомца, для которого добавляется отчет. Это обязательное поле для заполнения.", required = true)
            @RequestParam(value = "idPet", required = true) long idPet,

            @Parameter(description = "Информация о самочувствии питомца.", required = false)
            @RequestParam(value = "well", required = false, defaultValue = "Нет данных") String well,

            @Parameter(description = "Информация о реакции питомца.", required = false)
            @RequestParam(value = "reaction", required = false, defaultValue = "Нет данных") String reaction,

            @Parameter(description = "Дата отчета в формате 'yyyy-MM-dd'. Если дата не указана, будет использоваться текущая дата.", required = false, example = "1945-05-09")
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            LocalDateTime dateTimeToUse = (date != null) ? date.atTime(23, 59, 59, 999999999) : LocalDateTime.now();
            dailyReportService.addDailyReportTextFormat(idPet, well, reaction, dateTimeToUse);
            return ResponseEntity.ok("Report added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
        }
    }

    /**
     * Возвращает список URL-адресов страницы изображений дневных отчетов для указанного питомца.
     *
     * @param pageNumber Номер страницы списка URL-адресов изображений.
     * @param pageSize   Количество URL-адресов на странице.
     * @param idPet      Идентификатор питомца, для которого требуется получить список URL-адресов изображений.
     * @return Список URL-адресов изображений страницы дневных отчетов питомца.
     */
    @Operation(summary = "Получить список URL-адресов изображений отчетов питомца постранично",
            description = "Возвращает страницу со списком URL-адресов изображений, связанных с дневными отчетами питомца.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Страница с URL-адресами изображений успешно отправлена"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос, питомец с данным идентификатором не найден или проблема с параметрами пагинации"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера при попытке получить страницу с URL-адресами изображений")
            })
    @GetMapping(value = "/{idPet}/daily-reports/by-id-pet")
    public List<String> getDailyReportPictureUrls(
            @Parameter(description = "Номер страницы списка URL-адресов изображений") @RequestParam Integer pageNumber,
            @Parameter(description = "Количество URL-адресов на странице") @RequestParam Integer pageSize,
            @Parameter(description = "Идентификатор питомца") @PathVariable long idPet) {
        return dailyReportService.idDailyReportByPetId(pageNumber, pageSize, idPet);
    }

    /**
     * Получает страницу списка непроверенных ежедневных отчетов DTOs.
     * Отчеты могут быть отфильтрованы по указанной дате. Поддерживает пагинацию.
     *
     * @param pageNumber Номер страницы для пагинации, начинается с 1.
     * @param pageSize   Размер страницы для пагинации, должен быть больше 0.
     * @param date       Дата в формате ISO (YYYY-MM-DD) для фильтрации непроверенных отчетов. Параметр необязательный.
     * @return ResponseEntity со списком DTOs непроверенных ежедневных отчетов или со статусом 404,
     * если список пуст или 400 при некорректных значениях пагинации.
     */
    @Operation(summary = "Получение страницы списка непроверенных ежедневных отчетов DTOs",
            description = "Метод для получения отфильтрованной страницы отчетов DTOs содержащий ссылку на фото к ежедневному отчёту, непроверенных ежедневных отчетов, " +
                    "с поддержкой пагинации и возможностью фильтрации по дате.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Страница непроверенных ежедневных отчетов получена"),
                    @ApiResponse(responseCode = "400", description = "Неправильный запрос, проверьте параметры пагинации"),
                    @ApiResponse(responseCode = "404", description = "Непроверенные ежедневные отчеты не найдены")
            })
    @GetMapping("/unverified")
    public ResponseEntity<?> listUnverifiedDailyReports(
            @Parameter(name = "pageNumber", description = "Номер страницы для пагинации", required = true, example = "1")
            @RequestParam(value = "pageNumber") Integer pageNumber,

            @Parameter(name = "pageSize", description = "Размер страницы для пагинации", required = true, example = "10")
            @RequestParam(value = "pageSize") Integer pageSize,

            @Parameter(name = "date", description = "Дата в формате ISO (YYYY-MM-DD) для фильтрации непроверенных ежедневных отчетов.", required = false, example = "1945-05-09")
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<DailyReportDto> unverifiedReportsDto = dailyReportService.listUnverifiedDailyReports(pageNumber, pageSize, date);
            return ResponseEntity.ok(unverifiedReportsDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Получает DTO ежедневного отчёта по идентификатору.
     *
     * @param idDailyReport Идентификатор ежедневного отчёта.
     * @return ResponseEntity с DTO ежедневного отчёта.
     * @throws NoSuchElementException если ежедневный отчет не найден.
     */
    @Operation(summary = "Получение DTO ежедневного отчета по идентификатору, содержащий ссылку на фото ежедневного отчёта",
            description = "Метод для получения DTO ежедневного отчёта по его идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "DTO ежедневного отчёта найден"),
                    @ApiResponse(responseCode = "404", description = "Ежедневный отчёт не найден")
            })
    @GetMapping("/{idDailyReport}")
    public ResponseEntity<DailyReportDto> getDailyReportDtoById(
            @Parameter(name = "idDailyReport", description = "Идентификатор ежедневного отчёта", required = true, example = "1")
            @PathVariable long idDailyReport) {
        try {
            DailyReportDto dailyReportDto = dailyReportService.dailyReportDtoById(idDailyReport);
            return ResponseEntity.ok(dailyReportDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Отмечает ежедневные отчеты как проверенные по переданным идентификаторам.
     * Эндпоинт используется для массового обновления статуса проверки отчетов (isCheck) на "true".
     *
     * @param idDailyReportSet Set идентификаторов ежедневных отчетов, которые необходимо обновить.
     * @return ResponseEntity<Void> со статусом 200 OK, если обновление прошло успешно.
     */
    @Operation(summary = "Обновление статуса проверки ежедневных отчетов",
            description = "Массовое обновление статуса 'isCheck' ежедневных отчетов на 'true' по идентификаторам.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Статусы отчетов успешно обновлены"),
                    @ApiResponse(responseCode = "400", description = "Некорректный запрос или данные")
            })
    @PostMapping("/check")
    public ResponseEntity<Void> updateDailyReportStatus(@RequestBody Set<Long> idDailyReportSet) {
        dailyReportService.checkCorrectDailyReport(idDailyReportSet);
        return ResponseEntity.ok().build();
    }
}
