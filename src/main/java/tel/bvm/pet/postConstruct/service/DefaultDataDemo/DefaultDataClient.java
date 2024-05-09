package tel.bvm.pet.postConstruct.service.DefaultDataDemo;

import org.springframework.stereotype.Component;
import tel.bvm.pet.model.Client;

import java.util.ArrayList;
import java.util.Set;

@Component
public class DefaultDataClient {

    public Set<Client> clients = Set.of(
            new Client(456789012, "Дмитрий Смирнов",
                    "тел.: +79003334455,\n" +
                            "адрес: Казань, ул. Баумана, 15\n" +
                            "[Казань, Россия](https://yandex.ru/maps/43/kazan,\n" +
                            "mail: dmitry.smirnov@mail.ru,\n" +
                            "telegram: @DmitrySmirnov", new ArrayList<>()),
            new Client(567890123, "Мария Волкова",
                    "тел.: +79115556677,\n" +
                            "адрес: Новосибирск, Красный проспект, 202\n" +
                            "[Новосибирск, Россия](https://yandex.ru/maps/65/novosibirsk),\n" +
                            "mail: maria.volkova@mail.ru,\n" +
                            "telegram: @MariaVolkova", new ArrayList<>()),
            new Client(678901234, "Сергей Алексеев",
                    "тел.: +79226667788,\n" +
                            "адрес: Нижний Новгород, ул. Рождественская, 33\n" +
                            "[Нижний Новгород, Россия](https://yandex.ru/maps/66/nizhny-novgorod),\n" +
                            "mail: sergey.alexeev@mail.ru,\n" +
                            "telegram: @SergeyAlexeev", new ArrayList<>()),
            new Client(789012345, "Елена Романова",
                    "тел.: +79337778899,\n" +
                            "адрес: Челябинск, проспект Ленина, 76\n" +
                            "[Челябинск, Россия](https://yandex.ru/maps/10374/chelyabinsk),\n" +
                            "mail: elena.romanova@mail.ru,\n" +
                            "telegram: @ElenaRomanova", new ArrayList<>()),
            new Client(890123456, "Александр Белов",
                    "тел.: +79448889900,\n" +
                            "адрес: Омск, ул. Красный Путь, 12\n" +
                            "[Омск, Россия](https://yandex.ru/maps/66/omsk),\n" +
                            "mail: alexander.belov@mail,\n" +
                            "telegram: @AleksandrBelov", new ArrayList<>()));
}
