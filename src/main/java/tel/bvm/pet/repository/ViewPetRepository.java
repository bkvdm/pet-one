package tel.bvm.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tel.bvm.pet.model.ViewPet;

import java.util.Optional;

public interface ViewPetRepository extends JpaRepository<ViewPet, Long> {

    /**
     * Находит представление питомца по его имени в представлении.
     * <p>
     * Этот метод используется для поиска представления питомца основываясь на перечисляемом типе имени,
     * что позволяет получить конкретное представление, ассоциированное с данным именем.
     * Если соответствующее представление найдено, возвращается {@code Optional}, включающий объект представления.
     * Если представление не найдено, возвращается пустой {@code Optional}.
     *
     * @param nameViewPet Имя представления питомца, определённое в перечисляемом типе {@link ViewPet.NameViewPet}.
     * @return {@code Optional} представления питомца, если оно существует, или пустой {@code Optional}, если представление не найдено.
     */
    Optional<ViewPet> findByNameViewPet(ViewPet.NameViewPet nameViewPet);
}
