package tel.bvm.pet.service;

import tel.bvm.pet.model.Shelter;
import tel.bvm.pet.model.ShelterDto;

public interface ShelterService {
    Shelter addShelter(ShelterDto shelterDto);

    String allShelterInfo(Integer numberOfShelters);
}
