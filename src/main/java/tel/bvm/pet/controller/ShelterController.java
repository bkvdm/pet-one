package tel.bvm.pet.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import tel.bvm.pet.model.Shelter;
import tel.bvm.pet.model.ShelterDto;
import tel.bvm.pet.service.ShelterService;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@Controller
@RequestMapping("shelter")
public class ShelterController {

    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    /**
     * Добавляет новый приют в систему.
     *
     * @param shelterDto Запрос с данными о приюте, который необходимо добавить.
     * @return ResponseEntity с созданным приютом и статусом HTTP.
     * @throws RuntimeException Если не удалось добавить приют.
     */
    @Operation(summary = "Добавление нового приюта",
            description = "Создаёт новую запись приюта в системе и возвращает её с присвоенным идентификатором.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Приют успешно создан"),
                    @ApiResponse(responseCode = "400", description = "Неверные данные приюта"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера при сохранении приюта")
            })
    @PostMapping("/add-shelter")
    public ResponseEntity<Shelter> addShelter(@RequestBody ShelterDto shelterDto) {

        try {
            Shelter savedShelter = shelterService.addShelter(shelterDto);
            return new ResponseEntity<>(savedShelter, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add shelter", e);
        }
    }

    /**
     * Возвращает информацию о заданном количестве приютов.
     *
     * @param numberOfShelters Количество приютов для отображения.
     * @return Строку с информацией о приютах.
     */
    @Operation(summary = "Получить информацию о заданном количестве приютов",
            description = "Возвращает информацию о приютах, включая ID и название каждого приюта.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Информация о приютах получена",
                            content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                                    schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос, проверьте параметр 'count'"),
                    @ApiResponse(responseCode = "404", description = "Приюты не найдены"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера")
            })
    @GetMapping(value = "/info", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getAllShelterInfo(
            @Parameter(description = "Количество приютов для отображения", example = "5")
            @RequestParam(defaultValue = "10", required = false) Integer numberOfShelters) {
        String shelterInfo = shelterService.allShelterInfo(numberOfShelters);
        return ResponseEntity.ok(shelterInfo);
    }
}
