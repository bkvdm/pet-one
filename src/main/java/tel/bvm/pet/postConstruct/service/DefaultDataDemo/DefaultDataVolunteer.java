package tel.bvm.pet.postConstruct.service.DefaultDataDemo;

import org.springframework.stereotype.Component;
import tel.bvm.pet.model.Volunteer;

import java.util.Set;

@Component
public class DefaultDataVolunteer {

    public Set<Volunteer> volunteers = Set.of(
            new Volunteer(345678901, "Ольга Кузнецова",
                    "тел.: +79009876543,\n" +
                            "адрес: Екатеринбург, ул. Ленина, 50\n" +
                            "[Екатеринбург, Россия](https://yandex.ru/maps/54/yekaterinburg),\n" +
                            "mail: olga.kuznecova@mail.ru\n" +
                            "telegram: @OlgaKuzVolunteer"),
            new Volunteer(1234567891, "Анна Михайлова",
                    "тел.: +79001234567,\n" +
                            "адрес: Москва, ул. Тверская, 7\n" +
                            "[Москва, Россия](https://yandex.ru/maps/213/moscow)," +
                            "mail: anna.mikh@mail.ru,\n" +
                            "telegram: @AnnaMikhVolunteer"),
            new Volunteer(234567890, "Иван Петров",
                    "тел.: +79007654321,\n," +
                            "Санкт-Петербург, адрес: Невский пр., 28\n" +
                            "[Санкт-Петербург, Россия](https://yandex.ru/maps/2/saint-petersburg),\n" +
                            "mail: ivan.petrov@mail.ru,\n" +
                            "telegram: @IvanPetrovHelp"));
}
