package tel.bvm.pet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tel.bvm.pet.model.Client;

import java.util.Set;

public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Получает множество идентификаторов всех клиентов.
     *
     * @return Множество идентификаторов клиентов.
     */
    @Query("SELECT c.id FROM Client c")
    Set<Long> findAllClientIds();
}
