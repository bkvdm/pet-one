package tel.bvm.pet.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tel.bvm.pet.model.*;
import tel.bvm.pet.receiver.PackListToPageService;
import tel.bvm.pet.repository.DailyReportRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class DailyReportServiceImpl implements DailyReportService {

    private final DailyReportRepository dailyReportRepository;
    private final PackListToPageService packListToPageService;
    private final PetService petService;

    public DailyReportServiceImpl(DailyReportRepository dailyReportRepository, PackListToPageService packListToPageService, PetService petService) {
        this.dailyReportRepository = dailyReportRepository;
        this.packListToPageService = packListToPageService;
        this.petService = petService;
    }

    /**
     * Добавляет текстовый ежедневный отчет о состоянии питомца в систему.
     *
     * @param idPet         Идентификатор питомца, для которого добавляется отчет.
     * @param well          Информация о самочувствии питомца.
     * @param reaction      Информация о реакции питомца.
     * @param localDateTime Дата и время создания отчета, если не предоставлено, используется текущее время.
     */
    @Override
    public void addDailyReportTextFormat(long idPet, String well, String reaction, LocalDateTime localDateTime) {
        if (localDateTime == null) {
            localDateTime = LocalDateTime.now();
        }

        DailyReportDto dailyReportDto = new DailyReportDto(idPet, localDateTime, well, reaction, false, 0, null);
        dailyReportRepository.save(convertDailyReportDtoToDailyReport(dailyReportDto));
    }

    private DailyReport convertDailyReportDtoToDailyReport(DailyReportDto dailyReportDto) {
        Optional<Pet> petOptional = petService.findPetById(dailyReportDto.getPetId());
        if (!petOptional.isPresent()) {
            throw new NoSuchElementException("Pet not found with id daily report: " + dailyReportDto.getPetId());
        }

        return new DailyReport(
                petOptional.get(),
                dailyReportDto.getDateTime(),
                dailyReportDto.getWell(),
                dailyReportDto.getReaction(),
                false,
                null);
    }

    /**
     * Предоставляет список идентификаторов ежедневных отчетов, связанных с определенным питомцем.
     *
     * @param pageNumber Номер страницы данных.
     * @param pageSize   Размер страницы данных.
     * @param idPet      Идентификатор питомца.
     * @return Список строк с URL-адресами изображений ежедневных отчетов.
     */
    @Override
    public List<String> idDailyReportByPetId(Integer pageNumber, Integer pageSize, long idPet) {
        Set<Long> idDailyReportByPetIds = dailyReportRepository.findIdDailyReportByPetId(idPet);

        if (idDailyReportByPetIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> sortedIdDailyReportByPetIds = idDailyReportByPetIds.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        List<String> pictureDailyReportEncodeByIdPet = sortedIdDailyReportByPetIds.stream()
                .map(id -> "http://localhost:8080/picture-pet/picture-daily-report/" + id + "/picture-report")
                .collect(Collectors.toList());

        return packListToPageService.packListToPage(pageNumber, pageSize, pictureDailyReportEncodeByIdPet);
    }

    /**
     * Возвращает список непроверенных ежедневных отчетов за определенную дату или вообще всех непроверенных, если дата не указана.
     *
     * @param pageNumber Номер страницы данных.
     * @param pageSize   Размер страницы данных.
     * @param date       Дата, для которой требуется получить отчеты. Если null, возвращаются все непроверенные отчеты.
     * @return Список DTO непроверенных ежедневных отчетов.
     */
    @Override
    public List<DailyReportDto> listUnverifiedDailyReports(Integer pageNumber, Integer pageSize, LocalDate date) {

        List<DailyReport> dailyReportsUnverified;

        if (date == null) {
            dailyReportsUnverified = dailyReportRepository.findByIsCheckFalse();
        } else {
            dailyReportsUnverified = dailyReportRepository.findByIsCheckFalseAndDateTime(date);
        }
        if (dailyReportsUnverified.isEmpty()) {
            throw new NoSuchElementException("List of unverified daily reports is empty");
        }
        return packListToPageService.packListToPage(pageNumber, pageSize, convertDailyReportsToDailyReportsDtoList(dailyReportsUnverified));
    }

    /**
     * Получает DTO ежедневного отчета по идентификатору отчета.
     *
     * @param idDailyReport Идентификатор ежедневного отчета.
     * @return DTO ежедневного отчета.
     */
    @Override
    public DailyReportDto dailyReportDtoById(long idDailyReport) {

        Optional<DailyReport> dailyReportOptional = Optional.ofNullable(dailyReportRepository.findByIdDailyReport(idDailyReport));

        if (!dailyReportOptional.isPresent()) {
            throw new NoSuchElementException("Daily report not found with id: " + idDailyReport);
        }

        DailyReport dailyReport = dailyReportOptional.get();

        return convertDailyReportToDailyReportDto(dailyReport);
    }

    public DailyReportDto convertDailyReportToDailyReportDto(DailyReport dailyReport) {

        return new DailyReportDto(dailyReport.getPet().getId(),
                dailyReport.getDateTime(),
                dailyReport.getWell(),
                dailyReport.getReaction(),
                dailyReport.getCheck(),
                dailyReport.getPictureDailyReport().getId(),
                "http://localhost:8080/picture-pet/picture-daily-report/" + dailyReport.getIdDailyReport() + "/picture-report");
    }

    public List<DailyReportDto> convertDailyReportsToDailyReportsDtoList(List<DailyReport> dailyReports) {
        return dailyReports.stream().map(dailyReport -> convertDailyReportToDailyReportDto(dailyReport)).collect(Collectors.toList());
    }

    /**
     * Помечает ежедневные отчеты как проверенные в соответствии с переданным набором идентификаторов.
     *
     * @param idDailyReport Набор идентификаторов ежедневных отчетов, которые необходимо проверить.
     */
    @Override
    public void checkCorrectDailyReport(Set<Long> idDailyReport) {

        for (Long id : idDailyReport) {
            dailyReportRepository.updateIsCheckStatusById(id, true);
        }
//TODO здесь нужно отправить сообщение клиенту по его ChartId о том, что ежедневный отчёт рассмотрен и принят волонтёром
    }
}
