package tel.bvm.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tel.bvm.pet.model.Volunteer;

import java.util.List;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    /**
     * Получает список всех волонтёров, отсортированных по дате последнего назначения в порядке возрастания.
     * <p>
     * Этот метод идеально подходит для идентификации волонтёров, которые не были назначены на задачи
     * в течение самого долгого времени, позволяя управляющим равномерно распределять задачи среди волонтёров.
     * Отсортированный список используется для оптимизации процесса назначения задач,
     * обеспечивая распределение нагрузки на волонтёров.
     *
     * @return Список волонтёров, отсортированный по дате последнего назначения от самой ранней к самой поздней.
     */
    @Query("SELECT v FROM Volunteer v ORDER BY v.lastAssigned ASC")
    List<Volunteer> findVolunteersOrdered();
}
