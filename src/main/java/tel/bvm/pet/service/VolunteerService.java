package tel.bvm.pet.service;

import tel.bvm.pet.model.Volunteer;

public interface VolunteerService {
    Volunteer callingVolunteer();

    Volunteer addVolunteer(Volunteer volunteer);

    void deleteVolunteer(long id);
}
