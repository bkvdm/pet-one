package tel.bvm.pet.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tel.bvm.pet.model.FragmentNameText;
import tel.bvm.pet.model.Shelter;
import tel.bvm.pet.model.ShelterDto;
import tel.bvm.pet.repository.ShelterRepository;

import java.util.*;

@Service
@Transactional
public class ShelterServiceImpl implements ShelterService {

    private final ShelterRepository shelterRepository;
    private final PetService petService;

    public ShelterServiceImpl(ShelterRepository shelterRepository, PetService petService) {
        this.shelterRepository = shelterRepository;
        this.petService = petService;
    }

    /**
     * Добавляет приют в систему на основе предоставленного DTO.
     *
     * @param shelterDto DTO приюта, содержащее всю необходимую информацию для создания нового приюта.
     * @return Объект Shelter, представляющий добавленный приют.
     */
    @Override
    public Shelter addShelter(ShelterDto shelterDto) {
        return shelterRepository.save(convertShelterDtoToShelter(shelterDto));
    }

    /**
     * Конвертирует DTO приюта в сущность приюта.
     *
     * @param shelterDto DTO приюта для конвертаций.
     * @return Сущность приюта.
     */
    private Shelter convertShelterDtoToShelter(ShelterDto shelterDto) {
        return new Shelter(
                shelterDto.getNameShelter(),
                shelterDto.getOperationMode(),
                shelterDto.getContact(),
                shelterDto.getAddress(),
                shelterDto.getDrillingDirector(),
                shelterDto.getSecurityContact(),
                null);
    }

    /**
     * Возвращает информацию обо всех приютах в системе, ограниченную указанным количеством.
     * <p>
     * Собирает информацию о приютах, включая общую информацию и детали о каждом приюте.
     * Может выбросить исключение, если список приютов пуст.
     *
     * @param numberOfShelters Ограничение на количество приютов для отображения.
     * @return Строковое представление информации обо всех приютах.
     * @throws NoSuchElementException если список приютов пуст.
     */
    @Override
    public String allShelterInfo(Integer numberOfShelters) {
        Set<Long> idShelterSet = shelterRepository.findShelterIds();

        if (idShelterSet.isEmpty()) {
            throw new NoSuchElementException("List of shelters is empty");
        }

        StringBuilder infoAllShelters = new StringBuilder();
        infoAllShelters.append(summaryInformationShelters());

        Iterator<Long> iterator = idShelterSet.iterator();

        for (int i = 0; i < numberOfShelters && iterator.hasNext(); i++) {
            Long idShelter = iterator.next();
            infoAllShelters.append(idShelterInfo(idShelter));
        }

        return infoAllShelters.toString();
    }

    /**
     * Собирает и возвращает обобщенную информацию о всех приютах.
     *
     * @return Строку с обобщенной информацией о всех приютах.
     */
    public String summaryInformationShelters() {

        StringBuilder stringPack = new StringBuilder();

        stringPack.append(FragmentNameText.GENERAL_INFORMATION.getDisplayName()).
                append(petService.petsForBaseStats("totalNumberPets")).
                append(FragmentNameText.GENERAL_INFORMATION_ONE.getDisplayName()).
                append(FragmentNameText.GENERAL_INFORMATION_TWO.getDisplayName()).
                append(petService.petsForBaseStats("adopted")).
                append(FragmentNameText.GENERAL_INFORMATION_THREE.getDisplayName()).
                append(FragmentNameText.GENERAL_INFORMATION_FOUR.getDisplayName()).
                append(petService.petsForBaseStats("onProbation")).
                append(FragmentNameText.GENERAL_INFORMATION_FIVE.getDisplayName()).
                append(FragmentNameText.GENERAL_INFORMATION_SIX.getDisplayName()).
                append(petService.petsForBaseStats("quarantinedNotAdoption")).
                append(FragmentNameText.GENERAL_INFORMATION_SEVEN.getDisplayName()).
                append(petService.petsForBaseStats("forAdoption")).
                append(FragmentNameText.GENERAL_INFORMATION_EIGHT.getDisplayName()).
                append(FragmentNameText.GENERAL_INFORMATION_NINE.getDisplayName()).
                append(FragmentNameText.TEXT_SEPARATOR_SIMPLE.getDisplayName());

        return stringPack.toString();
    }

    /**
     * Возвращает детальную информацию о конкретном приюте по идентификатору.
     * <p>
     * Может выбросить исключение, если приют с указанным идентификатором не найден.
     *
     * @param idShelter Идентификатор приюта.
     * @return Строку с детальной информацией о приюте.
     * @throws NoSuchElementException если приют с заданным идентификатором не найден.
     */
    public String idShelterInfo(long idShelter) {
        Optional<Shelter> shelterOptional = shelterRepository.findById(idShelter);

        if (!shelterOptional.isPresent()) {
            throw new NoSuchElementException("Shelter not found with id " + idShelter);
        }

        Shelter shelter = shelterOptional.get();

        StringBuilder stringPack = new StringBuilder();

        stringPack.append(FragmentNameText.HEADING_TEXT.getDisplayName()).
                append(FragmentNameText.SHELTER_TEXT_TITLE_PART.getDisplayName()).
                append(shelter.getId()).append(") ").append(shelter.getNameShelter()).
                append(FragmentNameText.SHELTER_TEXT_TITLE_PART_ONE.getDisplayName()).
                append(FragmentNameText.SHELTER_TEXT_TITLE_PART_TWO.getDisplayName()).
                append(FragmentNameText.PETS_ON_PROBATION_TEXT.getDisplayName()).
                append(petService.petsForBaseStatsByIdShelter("onProbationIdShelter", idShelter)).
                append(FragmentNameText.PETS_ON_PROBATION_TEXT_ONE.getDisplayName()).
                append(FragmentNameText.PETS_FOR_ADOPTION_TEXT.getDisplayName()).
                append(petService.petsForBaseStatsByIdShelter("forAdoptionIdShelter", idShelter)).
                append(FragmentNameText.PETS_FOR_ADOPTION_TEXT_ONE.getDisplayName()).
                append(FragmentNameText.PETS_QUARANTINED_NOT_ADOPTION_TEXT.getDisplayName()).
                append(petService.petsForBaseStatsByIdShelter("quarantinedNotAdoptionIdShelter", idShelter)).
                append(FragmentNameText.PETS_QUARANTINED_NOT_ADOPTION_TEXT_ONE.getDisplayName()).
                append(FragmentNameText.ADOPTED_PETS_TEXT.getDisplayName()).
                append(petService.petsForBaseStatsByIdShelter("adoptedIdShelter", idShelter)).
                append(FragmentNameText.ADOPTED_PETS_TEXT_ONE.getDisplayName()).
                append(FragmentNameText.RESUME_TEXT.getDisplayName()).
                append(FragmentNameText.HEADING_SHELTER_CONTACT.getDisplayName()).
                append(FragmentNameText.SHELTER_CONTACT_OPERATION_MODE.getDisplayName()).
                append(shelter.getOperationMode()).
                append(FragmentNameText.SHELTER_CONTACT_PHONE_MAIL.getDisplayName()).
                append(shelter.getContact()).
                append(FragmentNameText.SHELTER_CONTACT_ADDRESS.getDisplayName()).
                append(shelter.getAddress()).
                append(FragmentNameText.SHELTER_CONTACT_DRILLING_DIRECTOR.getDisplayName()).
                append(shelter.getDrillingDirector()).
                append(FragmentNameText.SHELTER_CONTACT_SECURITY_CONTACT.getDisplayName()).
                append(shelter.getSecurity_contact()).
                append(FragmentNameText.TEXT_SEPARATOR_SCISSORS.getDisplayName());
        return stringPack.toString();
    }
}
