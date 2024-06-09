package tel.bvm.pet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tel.bvm.pet.model.FragmentNameText;
import tel.bvm.pet.model.Pet;
import tel.bvm.pet.model.PetDto;
import tel.bvm.pet.service.PetService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import java.util.Set;

@RestController
@Controller
@RequestMapping("pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    private static final Logger logger = LoggerFactory.getLogger(PetController.class);

    /**
     * Возвращает страницу со списком питомцев доступных для усыновления (всех видов).
     *
     * @param pageNumber Номер страницы, которую требуется получить.
     * @param pageSize   Количество элементов на странице, по умолчанию равно 10.
     * @return Список питомцев (все виды) с описанием свойств и картинка.
     */
    @Operation(summary = "Получить страницу со всеми питомцами (всех видов), доступными для усыновления",
            description = "Возвращает список питомцев, содержащий ссылку на уменьшенное изображение питомца, " +
                    "которое хранится в таблице базы данных.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Страница со списком питомцев"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос, проверьте параметры"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")})
    @GetMapping(value = "/page-list-all-pet", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PetDto>> listPets(
            @Parameter(description = "Номер страницы", required = true, example = "1") @RequestParam Integer pageNumber,
            @Parameter(description = "Количество питомцев на странице, значение по умолчанию - 10") @RequestParam(value = "limit", required = false, defaultValue = "10") Integer pageSize) {
        List<PetDto> pets = petService.getAllFreePets(pageNumber, pageSize);
        return ResponseEntity.ok().body(pets);
    }

    /**
     * Возвращает страницу со списком питомцев (CAT), доступных для усыновления.
     *
     * @param pageNumber Номер страницы, которую требуется получить.
     * @param pageSize   Количество элементов на странице, по умолчанию равно 10.
     * @return Список питомцев (CAT) с описанием свойств и картинка.
     */
    @Operation(summary = "Получить страницу со всеми питомцами (CAT), доступными для усыновления",
            description = "Возвращает список питомцев, содержащий ссылку на уменьшенное изображение питомца, " +
                    "которое хранится в таблице базы данных.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Страница со списком питомцев"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос, проверьте параметры"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")})
    @GetMapping(value = "/page-list-cat-pet", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PetDto>> listPetsCats(
            @RequestParam Integer pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer pageSize) {
        List<PetDto> pets = petService.getAllFreePetsCats(pageNumber, pageSize);
        return ResponseEntity.ok().body(pets);
    }

    /**
     * Возвращает страницу со списком питомцев (DOG), доступных для усыновления.
     *
     * @param pageNumber Номер страницы, которую требуется получить.
     * @param pageSize   Количество элементов на странице, по умолчанию равно 10.
     * @return Список питомцев (CAT) с описанием свойств и картинка.
     */
    @Operation(summary = "Получить страницу со всеми питомцами (DOG), доступными для усыновления",
            description = "Возвращает список питомцев, содержащий ссылку на уменьшенное изображение питомца, " +
                    "которое хранится в таблице базы данных.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Страница со списком питомцев"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос, проверьте параметры"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")})
    @GetMapping(value = "/page-list-dog-pet", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PetDto>> listPetsDogs(
            @RequestParam Integer pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer pageSize) {
        List<PetDto> pets = petService.getAllFreePetsDogs(pageNumber, pageSize);
        return ResponseEntity.ok().body(pets);
    }

    /**
     * Добавляет питомца в приют.
     * Принимает DTO питомца и сохраняет его в базе данных.
     *
     * @param petDto DTO питомца для сохранения.
     * @return Объект добавленного питомца.
     */
    @Operation(summary = "Добавление нового питомца", description = "Добавление питомца и его сохранение в базе данных", responses = {
            @ApiResponse(responseCode = "201", description = "Питомец успешно добавлено"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные питомца")
    })
    @PostMapping("/add-pet")
    public ResponseEntity<Pet> addPet(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "DTO питомца для добавления",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PetDto.class))
            ) @RequestBody PetDto petDto
    ) {
        petService.addPet(petDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Получает карту статусов проверки ежедневных отчетов питомцев для определенной даты.
     * Ключи карты соответствуют различным категориям проверки, в том числе:
     * - отчёты, которые не были завершены (нет фото, не описаны реакция питомца или его самочувствие),
     * - идентификаторы ежедневных отчётов, предназначенные для проверки волонтерами,
     * - идентификаторы питомцев, отчёты для которых отсутствуют на заданную дату,
     * - идентификаторы питомцев, отчёты для которых поданы после установленного крайнего срока (21:00).
     * <p>
     * Этот эндпоинт позволяет фильтровать и классифицировать ежедневные отчёты по указанной дате,
     * обеспечивая волонтеров необходимой информацией для дальнейшей проверки.
     * <p>
     * Если на данную дату отчеты отсутствуют, клиент получит ответ со статусом 404 "Not Found".
     * При наличии отчетов клиент получит ответ со статусом 200 "OK" и карту результатов проверки.
     *
     * @param date Дата для проверки в формате yyyy-MM-dd.
     * @return ResponseEntity, содержащее карту результатов проверки или статус 404, если отчеты отсутствуют за указанную дату.
     */
    @Operation(summary = "Получение карты статусов проверки ежедневных отчетов после первичного анализа",
            description = "Получение результатов первичного анализа ежедневных отчетов, классифицированных по определенным категориям, для указанной даты.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Карта результатов проверки успешно получена"),
                    @ApiResponse(responseCode = "404", description = "Отчеты не найдены на заданную дату")
            })
    @GetMapping("/check-daily-reports")
    public ResponseEntity<Map<FragmentNameText, Set<Long>>> checkDailyReports(
            @Parameter(name = "date", description = "Дата, на которую нужно получить статус проверки ежедневных отчетов", required = true, example = "2024-05-30")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            LocalDateTime endOfDay = date.atTime(23, 59, 59, 999999999);

            Map<FragmentNameText, Set<Long>> checkStatus = petService.getIdPetForVolunteerCheckAndResultInitialCheckDailyReports(endOfDay);
            return ResponseEntity.ok(checkStatus);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Эндпоинт для предварительной проверки ежедневных отчетов.
     * Метод обрабатывает запросы на представление данных отчетов для указанной даты
     * и формирует сообщения для дальнейшей отправки.
     *
     * @param date Дата, на основе которой необходимо подготовить отчеты в формате yyyy-MM-dd.
     * @return Статус выполнения операции.
     */
    @Operation(summary = "Выполнение предварительной проверки ежедневных отчетов",
            description = "Метод для выполнения предварительной проверки и подготовки сообщений о состоянии ежедневных отчетов.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Сообщения отчетов успешно подготовлены"),
                    @ApiResponse(responseCode = "404", description = "Отчеты не найдены для указанной даты", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Ошибка в формате даты", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @GetMapping("/daily-reports/pre-check")
    public ResponseEntity<String> dailyReportPreCheck(
            @Parameter(name = "date", description = "Дата для предварительной проверки отчетов", required = true, example = "1945-05-09")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            LocalDateTime endOfDay = date.atTime(23, 59, 59, 999999999);
            boolean reportsExist = petService.checkIfReportsExistForDate(endOfDay);
            if (!reportsExist) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no daily reports available for the requested date: " + date.toString());
            }
            petService.dailyReportPreCheckMessages(endOfDay);
            return ResponseEntity.ok("Сообщения отчетов на дату " + date + " были успешно подготовлены.");
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("Invalid date format: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An internal server error occurred: " + e.getMessage());
        }
    }

    /**
     * Эндпоинт для получения идентификаторов питомцев на основе даты решений (по срокам нахождения питомцев
     * на испытательном сроке у клиента (30, 44, 60 и более дней).
     * Преобразует LocalDate в LocalDateTime для поиска и возвращает сведения, если результат пустой.
     *
     * @param date Дата, для которой пользователь хочет получить идентификаторы питомцев.
     * @return ResponseEntity содержащий или набор идентификаторов, или сообщение об отсутствии результатов.
     */
    @Operation(summary = "Получение идентификаторов питомцев по дате, для которых нужно принять решение (усыновить, продлить испытательный срок или вернуть питомца в приют)",
            description = "Метод для получения идентификаторов питомцев, основанный на датах принятия решений. " +
                    "Дата преобразуется из LocalDate в LocalDateTime.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Идентификаторы питомцев успешно найдены"),
                    @ApiResponse(responseCode = "404", description = "Питомцы не найдены для указанной даты", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Некорректный формат даты", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
            })
    @GetMapping("/decisions")
    public ResponseEntity<?> getPetDecisionsByDate(
            @Parameter(name = "date", description = "Дата для получения общего списка из id питомцев по которым нужно принять решение", required = true, example = "1945-05-09")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            LocalDateTime startOfDay = date.atStartOfDay();
            Set<Long> petIdsSet = petService.petOnDateDecision(startOfDay);
            List<Long> petIds = new ArrayList<>(petIdsSet);

            if (petIds.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("На запрошенную дату отчёты отсутствуют: " + date.toString());
            }

            return ResponseEntity.ok(petIds);
        } catch (DateTimeParseException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Неправильный формат даты: " + date.toString());
        } catch (Exception e) {
            logger.error("Логгирование ошибки при обработке запроса для даты: {}", date, e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Произошла внутренняя ошибка на сервере.");
        }
    }

    /**
     * Эндпоинт для усыновленния питомцев на определенную дату.
     * Принимает дату и набор идентификаторов питомцев в теле запроса.
     *
     * @param date           Дата, для которой нужно оформить усыновление питомцев.
     * @param obtainedIdPets Набор идентификаторов питомцев, для которых выполняется подсчет.
     * @return ResponseEntity с количеством успешно усыновленных питомцев или сообщением об ошибке.
     */
    @PostMapping("/pets/successful-adoptions")
    @Operation(summary = "Оформление факта усыновления питомцев",
            description = "Этот метод принимает дату и набор идентификаторов питомцев для оформления усыновления. " +
                    "На основе этой информации подсчитывается количество успешно усыновленных питомцев.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Количество успешно усыновленных питомцев"),
                    @ApiResponse(responseCode = "400", description = "Проблемы с входными данными", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
            })
    public ResponseEntity<?> countSuccessfullyAdoptedPets(
            @RequestParam @Parameter(description = "Набор идентификаторов питомцев обособленно в каждой ячейке")
            Set<Long> obtainedIdPets,
            @Parameter(name = "date", description = "Дата для операции", required = true, example = "1945-05-09")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
            String result = petService.countSuccessfullyAdoptionPet(obtainedIdPets, endOfDay);
            return ResponseEntity.ok(result);
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неправильный формат даты: " + date.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла внутренняя ошибка на сервере.");
        }
    }

    /**
     * Эндпоинт для возвращения питомцев в приют или продления испытательного периода.
     * Принимает идентификаторы питомцев и дату операции.
     *
     * @param ids Идентификаторы питомцев для операции.
     * @param date Дата, для которой пользователь хочет принять решение.
     * @return ResponseEntity с сообщением о результате операции.
     */
    @Operation(summary = "Возврат питомцев в приют или продление испытательного периода",
            description = "Этот метод позволяет определить питомцев, которых нужно вернуть в приют или для которых необходимо продлить испытательный срок, " +
                    "основываясь на предоставленных идентификаторах и дате.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Операция успешно выполнена"),
                    @ApiResponse(responseCode = "400", description = "Проблемы с входными данными", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
            })
    @PostMapping("/pets/decisions")
    public ResponseEntity<?> handlePetDecision(
            @RequestParam @Parameter(description = "Набор идентификаторов питомцев обособленно в каждой ячейке")
            Set<Long> ids,
            @Parameter(name = "date", description = "Дата события", required = true, example = "1945-05-09")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            LocalDateTime operationDateTime = date.atStartOfDay();
            String result = petService.returningPetToShelterOrExtendingProbationPeriod(ids, operationDateTime);
            return ResponseEntity.ok(result);
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Неправильный формат даты: " + date.toString());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Ошибка при обработке запроса на дату: {}", date, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла внутренняя ошибка на сервере.");
        }
    }

    /**
     * Принимает запрос на возврат питомцев в приют от клиента.
     * <p>Этот метод обрабатывает набор идентификаторов питомцев, которые клиент хочет вернуть в приют.
     * Питомцы, отвечающие критериям возврата, удаляются из списка владения клиентов.</p>
     *
     * @param idPetForTransfer идентификаторы питомцев, которые должны быть возвращены в приют.
     * @return ResponseEntity со строкой, содержащей результат операции возврата.
     * @throws ResponseStatusException с кодом {@code HttpStatus.NOT_FOUND}, если нет питомцев, отвечающих критериям возврата.
     * @throws ResponseStatusException с кодом {@code HttpStatus.BAD_REQUEST}, если предоставлены недопустимые идентификаторы питомцев.
     * @throws ResponseStatusException с кодом {@code HttpStatus.INTERNAL_SERVER_ERROR}, в случае внутренней ошибки сервера.
     */
    @PostMapping("/transfer-pet-from-client")
    @Operation(summary = "Возврат питомцев в приют от клиента",
            description = "Принимает набор идентификаторов питомцев для возврата и обрабатывает запрос на их возврат в приют. " +
                    "Возвращает информацию об успешно возвращенных и о питомцах, которые не могут быть возвращены.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Питомцы успешно возвращены в приют"),
                    @ApiResponse(responseCode = "404", description = "Не найдены питомцы, соответствующие критериям возврата", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Недопустимые идентификаторы питомцев", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
            })
    public ResponseEntity<String> transferPetToShelter(
            @RequestParam @Parameter(description = "Набор идентификаторов питомцев обособленно в каждой ячейке")
            Set<Long> idPetForTransfer) {
        try {
            String result = petService.transferPetToShelterByClient(idPetForTransfer);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Не найдены питомцы для возврата", e);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недопустимые идентификаторы питомцев", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера", e);
        }
    }

    /**
     * Эндпоинт для передачи питомца клиенту на испытательный период.
     *
     * @param idPet    Идентификатор питомца, который будет передан.
     * @param idClient Идентификатор клиента, которому питомец будет передан.
     * @param localDate Дата, когда испытательный период начнется. Формат: 'yyyy-MM-dd', например, '1945-05-09'.
     * @return ResponseEntity со статусом выполнения операции.
     */
    @PostMapping("/transfer-pet-to-client")
    @Operation(summary = "Передача питомца клиенту на испытательный период",
            description = "Принимает идентификаторы питомца и клиента, а также дату начала испытательного периода (в формате 'yyyy-MM-dd', например, '1945-05-09'). Выполняет передачу питомца клиенту на заданный период. Время для 'dateTake' конвертируется в начало дня.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Питомец успешно передан клиенту на испытательный период"),
                    @ApiResponse(responseCode = "400", description = "Недопустимые параметры запроса или питомец/клиент не найдены", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = @Content)
            })
    public ResponseEntity<?> transferPetToClient(@RequestParam long idPet, @RequestParam long idClient,
                                                 @RequestParam @Parameter(description = "Дата передачи питомца клиенту на испытательный срок в формате 1945-05-09")
                                                 LocalDate localDate) {
        try {
            LocalDateTime dateTake = localDate.atStartOfDay();
            petService.transferPetToClientForProbationaryPeriod(idPet, idClient, dateTake);
            return ResponseEntity.ok("Pet has been successfully transferred to the client for probationary period.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred during the transfer operation: " + e.getMessage());
        }
    }
}
