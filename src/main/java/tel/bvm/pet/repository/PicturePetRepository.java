package tel.bvm.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tel.bvm.pet.model.PicturePet;

import java.util.List;
import java.util.Optional;

public interface PicturePetRepository extends JpaRepository<PicturePet, Long> {

    /**
     * Находит изображение питомца по идентификатору питомца.
     * <p>
     * Метод возвращает изображение связанное непосредственно с питомцем, идентификатор которого
     * был предоставлен. Возвращаемый объект обернут в {@code Optional} для безопасной обработки
     * потенциально отсутствующего значения.
     *
     * @param id Идентификатор питомца, для которого требуется найти изображение.
     * @return {@code Optional} с изображением питомца, если оно доступно,
     * иначе пустой {@code Optional}.
     */
    Optional<PicturePet> findByPetId(long id);

    /**
     * Находит все изображения питомцев с заданным статусом занятости.
     * <p>
     * Метод позволяет получить список изображений всех питомцев, имеющих указанный статус занятости.
     * Это может быть использовано, например, для отображения всех питомцев, которые в настоящий
     * момент доступны для усыновления или заняты.
     *
     * @param busyFree Статус занятости питомца, используемый для фильтрации результатов.
     * @return Список изображений питомцев с заданным статусом занятости.
     */
    List<PicturePet> findPicturePetByPet_BusyFree(boolean busyFree);
}
