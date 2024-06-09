package tel.bvm.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tel.bvm.pet.model.Pet;
import tel.bvm.pet.model.ViewPet;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
     */
    @Modifying
    @Transactional
    @Query("UPDATE Pet p SET p.busyFree = :status WHERE p.id = :id")
    void updateBusyFreeStatusById(@Param("status") boolean status, @Param("id") long idPet);

    /**
     * Находит идентификатор клиента, который взял питомца на испытательный срок или усыновил питомца.
     *
     * @param idPet Идентификатор питомца.
     * @return Optional содержащий идентификатор клиента, если он существует.
     */
    @Query("SELECT p.client.id FROM Pet p WHERE p.id = :idPet")
    Optional<Long> findClientIdByPetId(@Param("idPet") Long idPet);

    /**
     * Дата принятия решения об усыновлении питомца, продлении испытательного срока
     * или возврата питомца в приют
     * (для ситуаций 30 и 44 дня, нахождения питомца у клиента на испытательном сроке)
     *
     * @param startDateTime Начало интервала даты и времени
     * @param endDateTime   Конец интервала даты и времени
     */
    @Query("SELECT p FROM Pet p WHERE p.busyFree = true AND p.dateTake BETWEEN :startDateTime AND :endDateTime")
    Set<Pet> findAllByBusyFreeAndDateTakeBetween(@Param("startDateTime") LocalDateTime startDateTime,
                                                 @Param("endDateTime") LocalDateTime endDateTime);

    /**
     * @param dateDecision Дата принятия решения об усыновлении питомца или воврата питомца в приют
     *                     (для ситуаций от 60 и более дней, нахождения питомца у клиента на испытательном сроке)
     */
    @Query("SELECT p FROM Pet p WHERE p.busyFree = true AND p.dateTake <= :dateDecision")
    Set<Pet> findAllByBusyFreeAndDateTakeLessThanOrEqualTo(@Param("dateDecision") LocalDateTime dateDecision);
//    Set<Pet> findByBusyFreeTrueAndDateTakeLessThanEqual(LocalDateTime dateDecision);

    /**
     * Возвращает набор идентификаторов питомцев, соответствующих статусу занятости.
     * <p>
     *
     * @param busyFree параметр занятости питомца, где {@code true} означает, что питомец свободен, и {@code false} — что занят.
     * @return набор ({@code Set<Long>}) идентификаторов питомцев, соответствующих указанному состоянию занятости.
     */
    @Query("SELECT p.id FROM Pet p WHERE p.busyFree = :busyFree")
    Set<Long> findIdsByBusyFree(@Param("busyFree") Boolean busyFree);

    /**
     * Обновляет статус busyFree на false и очищает значение dateTake для питомца с указанным ID.
     * Используется в момент принятия решения о возврате питомца (статус: питомец на карантине).
     *
     * @param idPet идентификатор питомца, для которого требуется обновление.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Pet p SET p.busyFree = false, p.dateTake = NULL WHERE p.id = :idPet")
    void updatePetStatusAndClearDateTake(long idPet);

    /**
     * Находит идентификаторы питомцев, у которых поле busyFree установлено в true
     * и при этом значение поля dateTake не равно null.
     *
     * @return Множество идентификаторов (id) таких питомцев.
     */
    @Query("SELECT p.id FROM Pet p WHERE p.busyFree = true AND p.dateTake IS NOT NULL")
    Set<Long> findIdsOfPetsReadyForAdoption();

    /**
     * Возврат питомца в приют от клиента. Это завершающий этап, после начала процедуры возрата питомца.
     * Эта операция фиксирует физически воврат питомца в приют от клиента, у которого питомец не прошёл испытательный срок.
     * Перед этой операцией питомец меняет свойста свободен на false и дата null.
     * Обновляет связь питомца с клиентом, устанавливая id_client в null для питомца с заданным id.
     *
     * @param idPet Идентификатор питомца, для которого необходимо обновить связь.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Pet p SET p.client = null WHERE p.id = ?1")
    void disconnectPetFromClient(Long idPet);

    /**
     * Находит идентификаторы питомцев, которые не заняты (busy_free = false),
     * дата взятия (date_take) которых равна null, и у которых есть клиент (id_client != null).
     * Это те питомцы, по которым процедура возврата инициирована, но клиент ещё не вернул их в приют.
     *
     * @return Множество идентификаторов таких питомцев.
     */
    @Query("SELECT p.id FROM Pet p WHERE p.busyFree = false AND p.dateTake IS NULL AND p.client IS NOT NULL")
    Set<Long> findAvailablePetIds();

    /**
     * Находит идентификаторы всех доступных питомцев.
     * Доступный питомец определяется по следующим критериям:
     * - Питомец не занят (поле {@code busyFree} равно {@code true}).
     * - Питомец не взят на пробный период (поле {@code dateTake} равно {@code null}).
     * - У питомца нет владельца (поле {@code client} равно {@code null}).
     *
     * @return Множество ({@link Set}) идентификаторов ({@link Long}) доступных питомцев. Может быть пустым, если таких питомцев нет.
     */
    @Query("SELECT p.id FROM Pet p WHERE p.busyFree = true AND p.dateTake IS NULL AND p.client IS NULL")
    Set<Long> findAvailablePetsId();

    /**
     * Обновляет запись питомца в базе данных, устанавливая нового клиента и дату взятия питомца из приюта на испытательный срок.
     *
     * @param idPet    Идентификатор питомца, запись которого требуется обновить. Должен быть ненулевым и существовать в базе данных.
     * @param idClient Идентификатор клиента, на которого должен быть переоформлен питомец.
     * @param dateTake Дата, когда питомец был взят клиентом. Предоставляет точное время операции, которое регистрируется в базе данных.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Pet p SET p.client.id = :idClient, p.dateTake = :dateTake WHERE p.id = :idPet")
    void updatePetClientAndDateTake(@Param("idPet") long idPet, @Param("idClient") long idClient, @Param("dateTake") LocalDateTime dateTake);
}
