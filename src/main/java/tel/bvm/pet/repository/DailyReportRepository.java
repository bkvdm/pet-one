package tel.bvm.pet.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tel.bvm.pet.model.DailyReport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {

    /**
     * Находит ежедневный отчёт по уникальному идентификатору отчёта.
     *
     * @param idDailyReport Идентификатор ежедневного отчёта.
     * @return Ежедневный отчёт или {@code null}, если отчёт не найден.
     */
    DailyReport findByIdDailyReport(long idDailyReport);

    /**
     * Находит идентификаторы всех ежедневных отчетов, связанных с конкретным питомцем.
     *
     * @param idPet Идентификатор питомца.
     * @return Набор уникальных идентификаторов ежедневных отчетов.
     */
    @Query("SELECT dr.idDailyReport FROM DailyReport dr WHERE dr.pet.id = :idPet")
    Set<Long> findIdDailyReportByPetId(@Param("idPet") long idPet);

    /**
     * Возвращает список всех не проверенных ежедневных отчетов.
     *
     * @return Список ежедневных отчетов, которые ещё не проверены.
     */
    List<DailyReport> findByIsCheckFalse();

    /**
     * Находит все ежедневные отчеты, которые не проверены и созданы в определенную дату.
     *
     * @param date Дата, в которую были созданы непроверенные отчеты.
     * @return Список ежедневных отчетов соответствующих заданным критериям.
     */
    @Query("SELECT dr FROM DailyReport dr WHERE dr.isCheck = false AND CAST(dr.dateTime AS date) = :date")
    List<DailyReport> findByIsCheckFalseAndDateTime(@Param("date") LocalDate date);

    /**
     * Находит все ежедневные отчеты по идентификатору питомца и точной дате и времени.
     *
     * @param idPet    Идентификатор питомца.
     * @param dateTime Точная дата и время создания ежедневных отчетов.
     * @return Набор ежедневных отчетов соответствующих заданным критериям.
     */
    @Query("SELECT dr FROM DailyReport dr WHERE dr.pet.id = :idPet AND dr.dateTime = :dateTime")
    Set<DailyReport> findDailyReportsByPetIdAndDateTime(@Param("idPet") Long idPet, @Param("dateTime") LocalDateTime dateTime);

    /**
     * Обновляет статус проверки ежедневного отчета по его идентификатору.
     *
     * @param idDailyReport Идентификатор ежедневного отчета.
     * @param isCheck       Новое значение для поля проверки (isCheck).
     */
    @Modifying
    @Transactional
    @Query("UPDATE DailyReport dr SET dr.isCheck = :isCheck WHERE dr.idDailyReport = :idDailyReport")
    void updateIsCheckStatusById(Long idDailyReport, Boolean isCheck);

    /**
     * Находит количество ежедневных отчетов, созданных между началом и концом указанного дня.
     *
     * @param startOfDay Начало дня (включительно).
     * @param endOfDay   Конец дня (не включительно).
     * @return Количество отчетов, созданных в заданный временной промежуток.
     */
    @Query("SELECT COUNT(dr) FROM DailyReport dr WHERE dr.dateTime >= :startOfDay AND dr.dateTime < :endOfDay")
    long countByDateTimeBetween(@Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}
