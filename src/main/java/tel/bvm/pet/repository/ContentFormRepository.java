package tel.bvm.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tel.bvm.pet.model.ContentForm;

public interface ContentFormRepository extends JpaRepository<ContentForm, Long> {

    /**
     * Находит форму контента по заданному идентификатору.
     * <p>
     * Производит поиск формы контента в базе данных по указанному уникальному
     * идентификатору. Если форма контента с таким идентификатором существует,
     * возвращает найденный объект. В противном случае возвращает {@code null}.
     *
     * @param id Идентификатор формы контента, которую нужно найти.
     * @return Найденный объект формы контента или {@code null}, если форма контента
     * с указанным идентификатором не была найдена.
     */
    ContentForm findById(long id);
}
