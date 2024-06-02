package tel.bvm.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tel.bvm.pet.model.Shelter;

import java.util.Optional;
import java.util.Set;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {

    /**
     * Находит приют по его уникальному идентификатору.
     * <p>
     * Этот метод используется для получения объекта приюта по идентификатору.
     * Если приют с таким идентификатором существует, возвращается {@code Optional}, содержащий приют.
     * В ином случае возвращается пустой {@code Optional}.
     *
     * @param id Уникальный идентификатор приюта.
     * @return {@code Optional}, содержащий приют, если он найден, или пустой {@code Optional}, если приют не найден.
     */
    Optional<Shelter> findById(long id);

    /**
     * Извлекает идентификаторы всех приютов.
     * <p>
     * Этот метод используется для получения набора идентификаторов всех приютов.
     * Используется для операций, где требуется список идентификаторов.
     *
     * @return {@code Set} уникальных идентификаторов приютов.
     */
    @Query("SELECT s.id FROM Shelter s")
    Set<Long> findShelterIds();
}
