package tel.bvm.pet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tel.bvm.pet.service.PicturePetService;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.io.IOException;

@RestController
@Controller
@RequestMapping("picture-pet")
public class PicturePetController {

    private final PicturePetService picturePetService;

    public PicturePetController(PicturePetService picturePetService) {
        this.picturePetService = picturePetService;
    }

    /**
     * Загружает картинку питомца на сервер (в тестовом режиме на этапе разработки проекта).
     * Допустима, только загрузка изображений форматов image.
     * Проводит проверку файла на соответствие формату изображений.
     *
     * @param id   Идентификатор питомца, которому принадлежит загружаемая картинка.
     * @param file Файл картинки для загрузки в формате multipart.
     * @return ResponseEntity с кодом статуса HTTP 200, если загрузка прошла успешно.
     * @throws IOException если произошла ошибка при чтении файла.
     */
    @Operation(summary = "Загрузка картинки питомца (в тестовом режиме на этапе разработки проекта)", description = "Метод для загрузки картинки питомца на сервер", responses = {
            @ApiResponse(responseCode = "200", description = "Успешная загрузка картинки питомца"),
            @ApiResponse(responseCode = "400", description = "Неправильный запрос"),
            @ApiResponse(responseCode = "404", description = "Питомец не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")})
    @PostMapping(value = "/{id}/upload-pet-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadPicturePet(
            @Parameter(name = "id", description = "Идентификатор питомца", required = true, example = "1") @PathVariable Long id,
            @Parameter(name = "file", description = "Файл картинки в формате multipart", required = true) @RequestParam MultipartFile file) throws IOException {
        picturePetService.uploadPicturePet(id, file);
        return ResponseEntity.ok().build();
    }

    /**
     * Отправляет изображение выбранного питомца в ответ на запрос по номеру идентификатору питомца.
     *
     * @param id       Идентификатор питомца, изображение которого следует получить.
     * @param response Объект HttpServletResponse, используемый для отправки изображения.
     * @throws RuntimeException Если возникает проблема с записью изображения в ответ.
     */
    @Operation(summary = "Получить изображение питомца по идентификатору",
            description = "Отправляет изображение питомца в формате PNG, которое хранится в базе данных.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Изображение питомца успешно отправлено"),
                    @ApiResponse(responseCode = "400", description = "Неверный запрос, питомец с данным идентификатором не найден"),
                    @ApiResponse(responseCode = "500", description = "Ошибка сервера при попытке отправить изображение")
            })
    @GetMapping(value = "{id}/picture-pet", produces = MediaType.IMAGE_PNG_VALUE)
    public void picturePet(@Parameter(description = "Идентификатор питомца") @PathVariable long id,
                           HttpServletResponse response) {
        try {
            picturePetService.response(response, id);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write image to response", e);
        }
    }
}
