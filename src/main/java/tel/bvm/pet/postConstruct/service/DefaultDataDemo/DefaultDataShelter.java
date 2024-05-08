package tel.bvm.pet.postConstruct.service.DefaultDataDemo;

import org.springframework.stereotype.Component;
import tel.bvm.pet.model.Shelter;

import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultDataShelter {

    private final DefaultDataPet defaultDataPet;

    public DefaultDataShelter(DefaultDataPet defaultDataPet) {
        this.defaultDataPet = defaultDataPet;
    }

    public Shelter createShelter(String nameShelter) {
        if (nameShelter.equals("Приют Друзей")) {
            Shelter shelter = new Shelter("Приют Друзей", "Пн-Вс с 8:00 до 20:00", "+7 (495) 123-45-67, info@javapriutdruzey.ru", "Москва, ул. Зоологическая, д. 15", "[Ссылка на карту](https://yandex.ru/maps/?pt=37.618423,55.756994&z=15&l=map)", "+7 (495) 765-43-21", null);
            shelter.setId(1);
            return shelter;
        } else if (nameShelter.equals("Питомец")) {
            Shelter shelter = new Shelter("Питомец", "Ежедневно с 9:00 до 19:00", "+7 (812) 123-45-67, welcome@pitomecjava.ru", "Санкт-Петербург, Невский пр., д. 112", "[Ссылка на карту](https://www.google.com/maps/place/59.9342802,30.3350986)", "+7 (812) 987-65-43", null);
            shelter.setId(2);
            return shelter;
        } else if (nameShelter.equals("Убежище лап")) {
            Shelter shelter = new Shelter("Убежище лап", "Круглосуточно", "+7 (383) 123-45-67, safe@javalap.ru", "Новосибирск, ул. Красная, д. 27", "[Ссылка на карту](https://yandex.ru/maps/?um=constructor%3A6f47cbc8f451cedb2f5a3059f809cb11902ee476ab5467f4a9dcef5a2f05c738&source=constructorLink)", "+7 (383) 765-43-21", null);
            shelter.setId(3);
            return shelter;
        } else if (nameShelter.equals("ЗооДом")) {
            Shelter shelter = new Shelter("ЗооДом", "Пн-Вс с 10:00 до 22:00", "+7 (846) 123-45-67, home@javazoodom.ru", "Самара, ул. Лесная, д. 5", "[Ссылка на карту](https://www.google.com/maps/place/53.195873,50.", "+7 (846) 123-45-68", null);
            shelter.setId(4);
            return shelter;
        }
        throw new IllegalArgumentException("Unsupported shelter: " + nameShelter);
    }

    public Map<String, Shelter> shelterMap = new HashMap<String, Shelter>(Map.of(
            createShelter("Приют Друзей").getNameShelter(), createShelter("Приют Друзей"),
            createShelter("Питомец").getNameShelter(), createShelter("Питомец"),
            createShelter("Убежище лап").getNameShelter(), createShelter("Убежище лап"),
            createShelter("ЗооДом").getNameShelter(), createShelter("ЗооДом")));

    public void shelterRegistry() {
        createShelter("Приют Друзей").setPets(defaultDataPet.petList(createShelter("Приют Друзей")));
        createShelter("Питомец").setPets(defaultDataPet.petList(createShelter("Питомец")));
        createShelter("Убежище лап").setPets(defaultDataPet.petList(createShelter("Убежище лап")));
        createShelter("ЗооДом").setPets(defaultDataPet.petList(createShelter("ЗооДом")));
    }

    public Shelter shelterInfo(String nameShelter) {
        return shelterMap.get(nameShelter);
    }
}
