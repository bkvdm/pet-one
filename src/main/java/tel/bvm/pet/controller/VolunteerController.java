package tel.bvm.pet.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tel.bvm.pet.model.Volunteer;
import tel.bvm.pet.service.VolunteerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@Controller
@RequestMapping("volunteer")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    /**
     * Вызывает следующего волонтёра из списка.
     * Регистрирует актуальное время вызова волонтёра и возвращает информацию о нём.
     *
     * @return ResponseEntity с объектом волонтёра и кодом статуса HTTP 200.
     */
    @Operation(summary = "Вызов следующего волонтёра", description = "Метод для вызова следующего волонтёра и регистрации времени его активации", responses = {
            @ApiResponse(responseCode = "200", description = "Волонтёр успешно вызван"),
            @ApiResponse(responseCode = "404", description = "Список волонтёров пуст"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")})
    @GetMapping("/call-next")
    public ResponseEntity<Volunteer> callNextVolunteer() {
        Volunteer nextVolunteer = volunteerService.callingVolunteer();
        return ResponseEntity.ok(nextVolunteer);
    }

    /**
     * Добавляет нового волонтёра в систему.
     * Принимает объект волонтёра и сохраняет его в базе данных.
     *
     * @param volunteer Объект волонтёра для сохранения.
     * @return Объект добавленного волонтёра.
     */
    @Operation(summary = "Добавление нового волонтёра", description = "Добавление волонтёра и его сохранение в базе данных", responses = {
            @ApiResponse(responseCode = "201", description = "Волонтёр успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные волонтёра")
    })
    @PostMapping("/add-volunteer")
    public Volunteer addVolunteer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Объект волонтёра для добавления",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Volunteer.class))
            ) @RequestBody Volunteer volunteer
    ) {
        return volunteerService.addVolunteer(volunteer);
    }

    /**
     * Удаляет волонтёра из системы по идентификатору.
     * Удаляет волонтёра из базы данных, если он существует.
     *
     * @param id Идентификатор волонтёра для удаления.
     * @return ResponseEntity без содержимого с кодом статуса HTTP 200, если операция прошла успешно.
     */
    @Operation(summary = "Удаление волонтёра", description = "Удаляет волонтёра из системы по его идентификатору", responses = {
            @ApiResponse(responseCode = "200", description = "Волонтёр успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Волонтёр с таким идентификатором не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера при удалении волонтёра")
    })
    @DeleteMapping("/{id}/volunteer-delete")
    public ResponseEntity<Void> deleteVolunteer(
            @Parameter(name = "id", description = "Идентификатор волонтёра", required = true, example = "1")
            @PathVariable long id
    ) {
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.ok().build();
    }
}
