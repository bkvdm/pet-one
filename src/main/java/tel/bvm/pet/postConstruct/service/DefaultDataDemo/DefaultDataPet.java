package tel.bvm.pet.postConstruct.service.DefaultDataDemo;

import org.springframework.stereotype.Component;
import tel.bvm.pet.model.Pet;
import tel.bvm.pet.model.Shelter;
import tel.bvm.pet.model.ViewPet;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DefaultDataPet {

    private final DefaultDataShelter defaultDataShelter = new DefaultDataShelter(this);
    private final DefaultDataViewPet defaultDataViewPet = new DefaultDataViewPet(this);

    Pet petCatOne = new Pet(defaultDataShelter.shelterInfo("Приют Друзей"), null, defaultDataViewPet.viewPetInfo(ViewPet.NameViewPet.CAT), "Василиса", true, LocalDateTime.of(2023, 3, 23, 0, 0), null, null);
    Pet petCatTwo = new Pet(defaultDataShelter.shelterInfo("Приют Друзей"), null, defaultDataViewPet.viewPetInfo(ViewPet.NameViewPet.CAT), "Мурзик", true, LocalDateTime.of(2023, 5, 4, 0, 0), null, null);
    Pet petCatThree = new Pet(defaultDataShelter.shelterInfo("Приют Друзей"), null, defaultDataViewPet.viewPetInfo(ViewPet.NameViewPet.CAT), "Снежок", true, LocalDateTime.of(2023, 5, 15, 0, 0), null, null);
    Pet petCatFour = new Pet(defaultDataShelter.shelterInfo("Убежище лап"), null, defaultDataViewPet.viewPetInfo(ViewPet.NameViewPet.CAT), "Черныш", true, LocalDateTime.of(2023, 6, 18, 0, 0), null, null);
    Pet petCatFive = new Pet(defaultDataShelter.shelterInfo("Убежище лап"), null, defaultDataViewPet.viewPetInfo(ViewPet.NameViewPet.CAT), "Барсик", true, LocalDateTime.of(2023, 7, 20, 0, 0), null, null);
    Pet petCatSix = new Pet(defaultDataShelter.shelterInfo("ЗооДом"), null, defaultDataViewPet.viewPetInfo(ViewPet.NameViewPet.CAT), "Луна", true, LocalDateTime.of(2023, 8, 1, 0, 0), null, null);

    Pet petDogOne = new Pet(defaultDataShelter.shelterInfo("Питомец"), null, defaultDataViewPet.viewPetInfo(ViewPet.NameViewPet.DOG), "Макс", true, LocalDateTime.of(2023, 3, 3, 0, 0), null, null);
    Pet petDogTwo = new Pet(defaultDataShelter.shelterInfo("Питомец"), null, defaultDataViewPet.viewPetInfo(ViewPet.NameViewPet.DOG), "Белла", true, LocalDateTime.of(2023, 4, 14, 0, 0), null, null);
    Pet petDogThree = new Pet(defaultDataShelter.shelterInfo("Питомец"), null, defaultDataViewPet.viewPetInfo(ViewPet.NameViewPet.DOG), "Шарик", true, LocalDateTime.of(2023, 5, 25, 0, 0), null, null);
    Pet petDogFour = new Pet(defaultDataShelter.shelterInfo("Убежище лап"), null, defaultDataViewPet.viewPetInfo(ViewPet.NameViewPet.DOG), "Лорд", true, LocalDateTime.of(2023, 6, 8, 0, 0), null, null);
    Pet petDogFive = new Pet(defaultDataShelter.shelterInfo("ЗооДом"), null, defaultDataViewPet.viewPetInfo(ViewPet.NameViewPet.DOG), "Джесси", true, LocalDateTime.of(2023, 7, 17, 0, 0), null, null);
    Pet petDogSix = new Pet(defaultDataShelter.shelterInfo("ЗооДом"), null, defaultDataViewPet.viewPetInfo(ViewPet.NameViewPet.DOG), "Тайсон", true, LocalDateTime.of(2023, 8, 27, 0, 0), null, null);

    public List<Pet> petList = new ArrayList<>(Arrays.asList
            (petCatOne, petCatTwo, petCatThree, petCatFour, petCatFive, petCatSix,
                    petDogOne, petDogTwo, petDogThree, petDogFour, petDogFive, petDogSix));

    public List<Pet> petList(Shelter shelter) {
        List<Pet> petShelter = new ArrayList<>();

        for (Pet pet : petList) {
            if (pet.getShelter().equals(shelter)) {
                petList.add(pet);
            }
        }
        defaultDataShelter.shelterRegistry();
        return petShelter;
    }

    public List<Pet> petListViewPet(ViewPet viewPet) {
        List<Pet> petViewPet = new ArrayList<>();

        for (Pet pet : petList) {
            if (pet.getViewPet().equals(viewPet)) {
                petList.add(pet);
            }
        }
        defaultDataViewPet.viewPetRegistry();
        return petViewPet;
    }
}
