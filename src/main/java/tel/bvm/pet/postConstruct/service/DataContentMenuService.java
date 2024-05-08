package tel.bvm.pet.postConstruct.service;

import java.util.Map;
import java.util.Set;

public interface DataContentMenuService {

    Set<Object> contentMenuSet();

    Map<Long, Set<Long>> contentMenuIds();
}
