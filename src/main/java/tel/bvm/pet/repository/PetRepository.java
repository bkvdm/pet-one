package tel.bvm.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tel.bvm.pet.model.Pet;
import tel.bvm.pet.model.ViewPet;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;

public interface PetRepository extends JpaRepository<Pet, Long> {

    /**
     * Находит питомца по уникальному идентификатору.
     *
     * @param id Идентификатор питомца.
     * @return Найденный питомец или {@code null}, если питомец не найден.
     */
    Pet findPetById(long id);

    /**
     * Возвращает список всех питомцев с заданным статусом занятости.
     *
     * @param busyFree Статус занятости питомца.
     * @return Список питомцев.
     */
    List<Pet> findAllByBusyFree(boolean busyFree);

    /**
     * Находит питомцев по статусу занятости и видимому имени.
     *
     * @param busyFree    Статус занятости питомца.
     * @param nameViewPet Видимое имя питомца.
     * @return Список питомцев.
     */
    List<Pet> findByBusyFreeAndViewPetNameViewPet(boolean busyFree, ViewPet.NameViewPet nameViewPet);

    /**
     * Вычисляет общее количество питомцев в системе.
     *
     * @return Количество питомцев.
     */
    long count();

    /**
     * Количество питомцев на испытательном сроке у клиента для последующего усыновления
     */
    long countByBusyFreeTrueAndDateTakeNotNull();

    /**
     * Количество питомцев доступных для усыновления клиентом
     */
    long countByBusyFreeTrueAndDateTakeNull();

    /**
     * Количество питомцев на карантине и недоступных для усыновления
     */
    long countByBusyFreeFalseAndDateTakeNull();

    /**
     * Количество усыновлённых питомцев
     */
    long countByBusyFreeFalseAndDateTakeNotNull();

    /**
     * Количество питомцев на испытательном сроке у клиента, взятые из приюта с идентификатором idShelter
     */
    long countByShelterIdAndBusyFreeTrueAndDateTakeNotNull(Long idShelter);

    /**
     * Количество питомцев свободные для усыновления, в приюте с идентификатором idShelter
     */
    long countByShelterIdAndBusyFreeTrueAndDateTakeNull(Long idShelter);

    /**
     * Количество питомцев на карантине, недоступные для усыновления в приюте с идентификатором idShelter
     */
    long countByShelterIdAndBusyFreeFalseAndDateTakeNull(Long idShelter);

    /**
     * Количество усыновлённых питомцев из приюта с идентификатором idShelter
     */
    long countByShelterIdAndBusyFreeFalseAndDateTakeNotNull(Long idShelter);

    /**
     * Находит идентификаторы питомцев, которые не заняты и находятся на испытательном сроке у клиента.
     *
     * @return Набор идентификаторов питомцев.
     */
    @Query("SELECT p.id FROM Pet p WHERE p.busyFree = true AND p.dateTake IS NOT NULL")
    Set<Long> findPetIdsWhereBusyFreeAndDateTakeNotNull();

    /**
     * Обновляет статус занятости питомца по идентификатору.
     *
     * @param status Новый статус занятости.
     * @param idPet  Идентификатор питомца.
     * @return Количество обновлённых записей.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Pet p SET p.busyFree = :status WHERE p.id = :id")
    int updateBusyFreeStatusById(@Param("status") boolean status, @Param("id") long idPet);

    /**
     * Находит идентификатор клиента, который взял питомца на испытательный срок или усыновил питомца.
     *
     * @param idPet Идентификатор питомца.
     * @return Набор идентификаторов клиентов.
     */
    @Query("SELECT p.client.id FROM Pet p WHERE p.id = :idPet")
    Set<Long> findClientIdByPetId(@Param("idPet") Long idPet);
}
