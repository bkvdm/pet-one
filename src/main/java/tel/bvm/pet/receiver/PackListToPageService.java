package tel.bvm.pet.receiver;

import java.util.List;

public interface PackListToPageService {
    <T> List<T> packListToPage(Integer pageNumber, Integer pageSize, List<T> fullList);
}
