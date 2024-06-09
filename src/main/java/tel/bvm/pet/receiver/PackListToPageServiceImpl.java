package tel.bvm.pet.receiver;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackListToPageServiceImpl implements PackListToPageService {

    /**
     * Разбивает полный список элементов на страницы и возвращает страницу с указанным номером.
     * <p>
     * Этот метод принимает полный список элементов и параметры разбиения на страницы,
     * такие как номер страницы и размер страницы, чтобы вернуть только ту часть списка,
     * которая соответствует указанной странице.
     *
     * @param <T>        Тип элементов в списке
     * @param pageNumber Номер требуемой страницы, начиная с 1
     * @param pageSize   Размер каждой страницы, должен быть больше 0
     * @param fullList   Полный список всех элементов
     * @return Список элементов на указанной странице
     * @throws IllegalArgumentException если размер страницы меньше или равен 0,
     *                                  если номер страницы меньше или равен 0,
     *                                  или если номер страницы слишком велик для данного списка
     */
    @Override
    public <T> List<T> packListToPage(Integer pageNumber, Integer pageSize, List<T> fullList) {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("Page size must be greater than 0");
        } else if (pageNumber <= 0) {
            throw new IllegalArgumentException("Page number must be greater than 0");
        }

        int fromIndex = (pageNumber - 1) * pageSize;
        if (fromIndex > fullList.size()) {
            throw new IllegalArgumentException("Page number is too large");
        }
        int toIndex = Math.min(fromIndex + pageSize, fullList.size());
        return fullList.subList(fromIndex, toIndex);
    }
}
