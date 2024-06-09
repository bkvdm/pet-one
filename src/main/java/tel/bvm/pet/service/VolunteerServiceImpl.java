package tel.bvm.pet.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tel.bvm.pet.model.Volunteer;
import tel.bvm.pet.repository.VolunteerRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class VolunteerServiceImpl implements VolunteerService {

    private final VolunteerRepository volunteerRepository;

    public VolunteerServiceImpl(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Вызов волонтера для назначения его на задачу.
     * <p>
     * Метод выбирает следующего в очереди волонтера и обновляет его время последнего назначения.
     *
     * @return Волонтер, который следующий по очереди на назначение.
     */
    @Override
    public Volunteer callingVolunteer() {
        List<Volunteer> volunteers = volunteerRepository.findVolunteersOrdered();

        Volunteer next = volunteers.get(0);
        next.setLastAssigned(LocalDateTime.now());
        volunteerRepository.save(next);

        return next;
    }

    /**
     * Добавляет информацию о новом волонтере в базу данных.
     *
     * @param volunteer Объект волонтера для добавления.
     * @return Добавленный объект волонтера.
     */
    @Override
    public Volunteer addVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    /**
     * Удаляет волонтера из базы данных по идентификатору.
     *
     * @param id Идентификатор волонтера для удаления.
     */
    @Override
    public void deleteVolunteer(long id) {
        volunteerRepository.deleteById(id);
    }
}
