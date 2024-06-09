package tel.bvm.pet.service;

import tel.bvm.pet.model.FragmentNameText;
import tel.bvm.pet.model.Pet;
import tel.bvm.pet.model.PetDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface PetService {

    List<PetDto> getAllFreePets(Integer pageNumber, Integer pageSize);

    List<PetDto> getAllFreePetsCats(Integer pageNumber, Integer pageSize);

    List<PetDto> getAllFreePetsDogs(Integer pageNumber, Integer pageSize);

    Optional<Pet> findPetById(Long petId);

    Pet addPet(PetDto petDto);

    long petsForBaseStats(String status);

    long petsForBaseStatsByIdShelter(String status, long idShelter);

    Map<FragmentNameText, Set<Long>> getIdPetForVolunteerCheckAndResultInitialCheckDailyReports(LocalDateTime localDateTime);

    void dailyReportPreCheckMessages(LocalDateTime localDateTime);

    Boolean checkIfReportsExistForDate(LocalDateTime localDateTime);

    Set<Long> petOnDateDecision(LocalDateTime localDateTime);

    String countSuccessfullyAdoptionPet(Set<Long> obtainedIdPets, LocalDateTime localDateTime);

    String returningPetToShelterOrExtendingProbationPeriod(Set<Long> obtainedIdPets, LocalDateTime localDateTime);

    String transferPetToShelterByClient(Set<Long> idPetForTransfer);

    void transferPetToClientForProbationaryPeriod(long idPet, long idClient, LocalDateTime dateTake);
}
