package tel.bvm.pet.service;

import tel.bvm.pet.model.DailyReportDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface DailyReportService {

    void addDailyReportTextFormat(long idPet, String well, String reaction, LocalDateTime localDateTime);

    List<String> idDailyReportByPetId(Integer pageNumber, Integer pageSize, long idPet);

    List<DailyReportDto> listUnverifiedDailyReports(Integer pageNumber, Integer pageSize, LocalDate date);

    DailyReportDto dailyReportDtoById(long idDailyReport);

    void checkCorrectDailyReport(Set<Long> idDailyReport);
}
