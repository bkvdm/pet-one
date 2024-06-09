package tel.bvm.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tel.bvm.pet.model.PictureDailyReport;

import java.util.Optional;

public interface PictureDailyReportRepository extends JpaRepository<PictureDailyReport, Long> {

    /**
     * Находит изображение ежедневного отчета по его уникальному идентификатору.
     *
     * @param id Идентификатор изображения ежедневного отчета.
     * @return {@code Optional} с изображением ежедневного отчета, если таковое найдено,
     * иначе возвращается пустой {@code Optional}.
     */
    Optional<PictureDailyReport> findById(Long id);

    /**
     * Находит изображение, связанное с ежедневным отчетом, по идентификатору этого отчета.
     * <p>
     * Метод ищет изображение ежедневного отчета, используя связь с таблицей ежедневных отчетов
     * по идентификатору ежедневного отчета. Позволяет получить изображение, связанное с конкретным
     * ежедневным отчетом.
     *
     * @param dailyReportId Идентификатор ежедневного отчета, для которого необходимо найти изображение.
     * @return {@code Optional} с найденным изображением ежедневного отчета, если таковое существует,
     * иначе возвращается пустой {@code Optional}.
     */
    Optional<PictureDailyReport> findPictureDailyReportByDailyReport_idDailyReport(long dailyReportId);
}
