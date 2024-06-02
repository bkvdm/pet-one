package tel.bvm.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tel.bvm.pet.model.Form;

import java.util.Optional;

public interface FormRepository extends JpaRepository<Form, Long> {

    /**
     * Находит форму по идентификатору связанного с ней объекта ContentForm.
     * <p>
     * Получение формы происходит по связи с объектом ContentForm через его уникальный идентификатор.
     * Если форма с данным идентификатором содержимого существует, возвращается {@code Optional},
     * содержащий объект формы. В противном случае возвращается пустой {@code Optional}.
     *
     * @param id Идентификатор объекта ContentForm, связанного с формой.
     * @return {@code Optional} соответствующей формы, если таковая найдена, иначе пустой {@code Optional}.
     */
    Optional<Form> findFormByContentForm_Id(long id);
}
