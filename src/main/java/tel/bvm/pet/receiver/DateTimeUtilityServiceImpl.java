package tel.bvm.pet.receiver;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class DateTimeUtilityServiceImpl implements DateTimeUtilityService {

    /**
     * Преобразует объект LocalDateTime в строку в ISO формате даты.
     * <p>
     * Этот метод принимает объект LocalDateTime и преобразует его в строковое представление даты
     * согласно стандарту ISO-8601 (например, "2024-06-01").
     *
     * @param localDateTime объект LocalDateTime, который должен быть преобразован.
     *                      Не может быть null.
     * @return строку с датой в формате ISO-8601.
     * @throws IllegalArgumentException если параметр 'localDateTime' является null.
     */
    @Override
    public String dateTimeToString(LocalDateTime localDateTime) {

        if (localDateTime == null) {
            throw new IllegalArgumentException("Parameter 'localDateTime' cannot be null");
        }
        DateTimeFormatter formatterLocalDateTime = DateTimeFormatter.ISO_LOCAL_DATE;
        return localDateTime.format(formatterLocalDateTime);
    }
}
