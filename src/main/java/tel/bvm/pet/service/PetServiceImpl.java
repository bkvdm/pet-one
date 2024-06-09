package tel.bvm.pet.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tel.bvm.pet.model.*;
import tel.bvm.pet.receiver.PackListToPageService;
import tel.bvm.pet.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class PetServiceImpl implements PetService {

    private static final Logger log = LoggerFactory.getLogger(PetServiceImpl.class);
    private final PetRepository petRepository;
    private final PackListToPageService packListToPageService;
    private final ShelterRepository shelterRepository;
    private final ViewPetRepository viewPetRepository;
    private final DailyReportRepository dailyReportRepository;
    private final ClientRepository clientRepository;

    public PetServiceImpl(PetRepository petRepository, PackListToPageService packListToPageService, ShelterRepository shelterRepository, ViewPetRepository viewPetRepository, DailyReportRepository dailyReportRepository, ClientRepository clientRepository) {
        this.petRepository = petRepository;
        this.packListToPageService = packListToPageService;
        this.shelterRepository = shelterRepository;
        this.viewPetRepository = viewPetRepository;
        this.dailyReportRepository = dailyReportRepository;
        this.clientRepository = clientRepository;
    }

    Logger logger = LoggerFactory.getLogger(PetServiceImpl.class);

    /**
     * Получает страницу со свободными питомцами всех видов.
     * <p>
     * Этот метод возвращает список DTO (Data Transfer Object) свободных питомцев, т.е. питомцев, доступных для усыновления.
     * Результаты пагинируются в соответствии с указанными номером страницы и размером страницы.
     *
     * @param pageNumber Номер страницы (начиная с 0).
     * @param pageSize   Количество элементов на странице.
     * @return Список DTO свободных питомцев.
     */
    @Override
    public List<PetDto> getAllFreePets(Integer pageNumber, Integer pageSize) {
        return packListToPageService.packListToPage(pageNumber, pageSize,
                convertPetsToPetDtoList(petRepository.findAllByBusyFree(true)));
    }

    /**
     * Получает страницу со свободными котами.
     * <p>
     * Аналогично методу getAllFreePets, но возвращает только котов, которые доступны для усыновления.
     * Используется для фильтрации питомцев по видам, в данном случае - коты.
     *
     * @param pageNumber Номер страницы (начиная с 0).
     * @param pageSize   Количество элементов на странице.
     * @return Список DTO свободных котов.
     */
    @Override
    public List<PetDto> getAllFreePetsCats(Integer pageNumber, Integer pageSize) {
        return packListToPageService.packListToPage(pageNumber, pageSize,
                convertPetsToPetDtoList(petRepository.findByBusyFreeAndViewPetNameViewPet(true, ViewPet.NameViewPet.CAT)));
    }

    /**
     * Получает страницу со свободными собаками.
     * <p>
     * Функционирует подобно методу getAllFreePets, однако выборка ограничивается собаками, доступными для усыновления.
     * Таким образом, позволяет пользователям получать информацию исключительно о собаках.
     *
     * @param pageNumber Номер страницы (начиная с 0).
     * @param pageSize   Количество элементов на странице.
     * @return Список DTO свободных собак.
     */
    @Override
    public List<PetDto> getAllFreePetsDogs(Integer pageNumber, Integer pageSize) {
        return packListToPageService.packListToPage(pageNumber, pageSize,
                convertPetsToPetDtoList(petRepository.findByBusyFreeAndViewPetNameViewPet(true, ViewPet.NameViewPet.DOG)));
    }

    /**
     * Конвертирует сущность питомца в DTO.
     *
     * @param pet объект питомца для конвертации.
     * @return DTO объект питомца.
     */
    private PetDto convertPetToPetDto(Pet pet) {

        return new PetDto(pet.getId(),
                pet.getNamePet(),
                "http://localhost:8080/picture-pet/" + pet.getId() + "/picture-pet",
                pet.getViewPet().getNameViewPet().toString(),
                pet.getShelter().getId(),
                pet.getShelter().getNameShelter());
    }

    /**
     * Конвертирует список сущностей питомцев в список DTO.
     *
     * @param pets список питомцев для конвертации.
     * @return список DTO объектов питомцев.
     */
    public List<PetDto> convertPetsToPetDtoList(List<Pet> pets) {
        return pets.stream().map(pet -> convertPetToPetDto(pet)).collect(Collectors.toList());
    }

    /**
     * Поиск питомца по его идентификатору.
     *
     * @param petId Идентификатор питомца.
     * @return Optional объект питомца.
     */
    @Override
    public Optional<Pet> findPetById(Long petId) {
        return Optional.ofNullable(petRepository.findPetById(petId));
    }

    /**
     * Добавляет нового питомца в систему на основе предоставленного DTO.
     *
     * @param petDto DTO нового питомца.
     * @return Сохраненный объект питомца.
     */
    @Override
    public Pet addPet(PetDto petDto) {
        Set<String> enumNames = Stream.of(ViewPet.NameViewPet.values())
                .map(ViewPet.NameViewPet::getDisplayName)
                .collect(Collectors.toSet());

        String nameDto = petDto.getViewPet();
        boolean isValidNameViewPet = enumNames.contains(nameDto);

        if (!isValidNameViewPet) {
            throw new NoSuchElementException("Unknown Pet Species: " + nameDto);
        }

        ViewPet viewPet = getViewPetByEnumValue(getViewPetNameByDisplayName(nameDto));

        Optional<Shelter> shelterOptional = shelterRepository.findById(petDto.getIdShelter());
        if (!shelterOptional.isPresent()) {
            throw new NoSuchElementException("Shelter not found with id: " + petDto.getIdShelter());
        }

        Shelter shelter = shelterOptional.get();
        Pet pet = new Pet(shelter, null, viewPet, petDto.getNamePet(), true, null, null, null);

        return petRepository.save(pet);
    }

    /**
     * Получает объект ViewPet по его перечислению.
     *
     * @param petEnumValue перечисление ViewPet.
     * @return объект ViewPet.
     */
    public ViewPet getViewPetByEnumValue(ViewPet.NameViewPet petEnumValue) {
        return viewPetRepository.findByNameViewPet(petEnumValue)
                .orElseThrow(() -> new NoSuchElementException("ViewPet not found for enum value: " + petEnumValue));
    }

    /**
     * Получает перечисление NameViewPet по отображаемому имени.
     *
     * @param displayName Отображаемое имя вида питомца.
     * @return перечисление NameViewPet.
     */
    public ViewPet.NameViewPet getViewPetNameByDisplayName(String displayName) {
        for (ViewPet.NameViewPet value : ViewPet.NameViewPet.values()) {
            if (value.getDisplayName().equals(displayName)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No enum constant for display name: " + displayName);
    }

    /**
     * Подсчитывает базовую статистику питомцев со статусом, указанным в параметре.
     *
     * @param status Статус, по которому проводится подсчет.
     * @return Количество питомцев, соответствующих статусу.
     */
    @Override
    public long petsForBaseStats(String status) {
        return switch (status) {
            case "totalNumberPets" ->
                // Количество всех питомцев
                    petRepository.count();
            case "onProbation" ->
                // Количество питомцев на испытательном сроке
                    petRepository.countByBusyFreeTrueAndDateTakeNotNull();
            case "forAdoption" ->
                // Количество доступных для усыновления питомцев
                    petRepository.countByBusyFreeTrueAndDateTakeNull();
            case "quarantinedNotAdoption" ->
                // Количество питомцев на карантине
                    petRepository.countByBusyFreeFalseAndDateTakeNull();
            case "adopted" ->
                // Количество усыновленных питомцев
                    petRepository.countByBusyFreeFalseAndDateTakeNotNull();
            default -> throw new IllegalArgumentException("Unknown pets status: " + status);
        };
    }

    /**
     * Подсчитывает статистику питомцев для конкретного приюта, параметризированного статусом.
     *
     * @param status    Статус, по которому проводится подсчет.
     * @param idShelter Идентификатор приюта.
     * @return Количество питомцев, соответствующих заданному статусу в указанном приюте.
     */
    @Override
    public long petsForBaseStatsByIdShelter(String status, long idShelter) {
        return switch (status) {
            // Отдельно по приютам:
            case "onProbationIdShelter" ->
                // Количество питомцев на испытательном сроке
                    petRepository.countByShelterIdAndBusyFreeTrueAndDateTakeNotNull(idShelter);
            case "forAdoptionIdShelter" ->
                // Количество доступных для усыновления питомцев
                    petRepository.countByShelterIdAndBusyFreeTrueAndDateTakeNull(idShelter);
            case "quarantinedNotAdoptionIdShelter" ->
                // Количество питомцев на карантине
                    petRepository.countByShelterIdAndBusyFreeFalseAndDateTakeNull(idShelter);
            case "adoptedIdShelter" ->
                // Количество усыновленных питомцев
                    petRepository.countByShelterIdAndBusyFreeFalseAndDateTakeNotNull(idShelter);
            default -> throw new IllegalArgumentException("Unknown pets status: " + status);
        };
    }

    /**
     * Получает множество идентификаторов питомцев для проверки волонтерами на основе начальной проверки ежедневных отчетов.
     * <p>
     * Этот метод производит набор проверок ежедневных отчетов питомцев, включая отсутствие фотографии,
     * пустые поля реакции и самочувствия, а также сверяет время отправки отчетов.
     * Возвращает карту, где каждому фрагменту текста соответствует множество идентификаторов питомцев,
     * которые нуждаются в проверке по определенным критериям.
     *
     * @param localDateTime Дата и время, на основе которых проводятся проверки ежедневных отчетов.
     * @return Карта с множеством идентификаторов питомцев, распределенных по типам проверок.
     * @throws NoSuchElementException если ежедневные отчеты недоступны для запрошенной даты.
     */
    @Override
    public Map<FragmentNameText, Set<Long>> getIdPetForVolunteerCheckAndResultInitialCheckDailyReports(LocalDateTime localDateTime) {
        Set<Long> idPetDailyReportNotCompleted = new HashSet<>();
        Set<Long> idPetPictureNone = new HashSet<>();
        Set<Long> idPetReactionIsBlank = new HashSet<>();
        Set<Long> idPetWellIsBlank = new HashSet<>();
        Set<Long> idPetDailyReportTimeIsAfter = new HashSet<>();
        Set<Long> idPetDailyReportNo = new HashSet<>();
        Set<Long> getIdDailyReportForVolunteerCheck = new HashSet<>();
        LocalDateTime deadlineDailyReport = localDateTime.toLocalDate().atTime(21, 0);
        LocalDateTime daySubmissionDailyReport = localDateTime.toLocalDate().atTime(23, 59, 59);

        Set<Long> petIdsForDailyReport = getPetIdsForDailyReport();

        List<Set<DailyReport>> dailyReportsAvailabilityCheck = new ArrayList<>();

        for (Long idPet : petIdsForDailyReport) {
            dailyReportsAvailabilityCheck.add(dailyReportsByPetIdAndDateTime(idPet, daySubmissionDailyReport));
        }

        boolean allDailyReportsAreEmpty = dailyReportsAvailabilityCheck.stream().allMatch(Set::isEmpty);

        if (allDailyReportsAreEmpty) {
            throw new NoSuchElementException("There are no daily reports available for the requested date: " + localDateTime);
        }

        Map<FragmentNameText, Set<Long>> resultInitialCheck = new HashMap<>();

        for (Long idPet : petIdsForDailyReport) {
            Set<DailyReport> dailyReports = dailyReportsByPetIdAndDateTime(idPet, daySubmissionDailyReport);
            Optional<DailyReport> latestReportOptional = dailyReports.stream()
                    .max(Comparator.comparing(DailyReport::getDateTime));

            if (latestReportOptional.isPresent()) {
                DailyReport dailyReportRelevant = latestReportOptional.get();

                long fileSize = Optional.ofNullable(dailyReportRelevant.getPictureDailyReport())
                        .map(PictureDailyReport::getFileSize)
                        .orElse(0L);

                boolean isAfterDeadline = Optional.ofNullable(dailyReportRelevant.getDateTime())
                        .map(dateTime -> dateTime.isAfter(deadlineDailyReport))
                        .orElse(false);

                boolean reactionBlankOrNull = Optional.ofNullable(dailyReportRelevant.getReaction())
                        .map(String::isBlank)
                        .orElse(true);

                boolean wellBlankOrNull = Optional.ofNullable(dailyReportRelevant.getWell())
                        .map(String::isBlank)
                        .orElse(true);

                if (fileSize == 0 ||
                        reactionBlankOrNull || wellBlankOrNull) {

                    if (fileSize == 0) {
                        idPetPictureNone.add(idPet);
                    }
                    if (reactionBlankOrNull) {
                        idPetReactionIsBlank.add(idPet);
                    }
                    if (wellBlankOrNull) {
                        idPetWellIsBlank.add(idPet);
                    }
                    idPetDailyReportNotCompleted.add(idPet);

                } else {
                    getIdDailyReportForVolunteerCheck.add(dailyReportRelevant.getIdDailyReport());

                    if (isAfterDeadline) {
                        idPetDailyReportTimeIsAfter.add(idPet);
                    }
                }

            } else {
                idPetDailyReportNo.add(idPet);
            }
        }

        resultInitialCheck.put(FragmentNameText.PET_DAILY_REPORT_NO, idPetDailyReportNo);
        resultInitialCheck.put(FragmentNameText.PET_WHICH_DAILY_REPORT_NOT_COMPLETED, idPetDailyReportNotCompleted);
        resultInitialCheck.put(FragmentNameText.PET_PICTURE_NONE, idPetPictureNone);
        resultInitialCheck.put(FragmentNameText.PET_REACTION_IS_BLANK, idPetReactionIsBlank);
        resultInitialCheck.put(FragmentNameText.PET_WELL_IS_BLANK, idPetWellIsBlank);
        resultInitialCheck.put(FragmentNameText.PET_WHICH_DAILY_REPORT_TIME_IS_AFTER, idPetDailyReportTimeIsAfter);
        resultInitialCheck.put(FragmentNameText.DAILY_REPORT_FOR_VOLUNTEER_CHECK, getIdDailyReportForVolunteerCheck);

        return resultInitialCheck;
    }

    /**
     * Получает набор идентификаторов питомцев, которые имеют ежедневные отчеты.
     * <p>
     * Метод фильтрует питомцев, по признаку на испытательном сроке у клиента, и возвращает их идентификаторы.
     * Если соответствующие записи не найдены, выбрасывается исключение.
     *
     * @return Набор идентификаторов питомцев, для которых доступны ежедневные отчеты.
     * @throws NoSuchElementException если список отчетов пуст.
     */
    public Set<Long> getPetIdsForDailyReport() {
        Set<Long> idPetForDailyReport = petRepository.findPetIdsWhereBusyFreeAndDateTakeNotNull();

        if (idPetForDailyReport.isEmpty()) {
            throw new NoSuchElementException("The list of reports is empty for the current date" + LocalDateTime.now());
        }

        return idPetForDailyReport;
    }

    /**
     * Возвращает набор ежедневных отчетов для указанного идентификатора питомца и даты.
     * <p>
     * Этот метод ищет все ежедневные отчеты, которые были поданы за заданную дату для определенного питомца,
     * и возвращает их в виде набора.
     *
     * @param idPet         Идентификатор питомца, для которого осуществляется поиск.
     * @param localDateTime Дата и время, за которое осуществляется поиск отчетов.
     * @return Набор найденных ежедневных отчетов.
     */
    public Set<DailyReport> dailyReportsByPetIdAndDateTime(Long idPet, LocalDateTime localDateTime) {
        return dailyReportRepository.findDailyReportsByPetIdAndDateTime(idPet, localDateTime);
    }

    /**
     * Производит предварительную проверку ежедневных отчетов и формирует сообщения для клиентов в Телеграмм-боте.
     * <p>
     * Этот метод анализирует предварительные результаты проверок, собирает сообщения о необходимости вмешательства
     * на основании различных критериев, и готовит уведомления для отправки.
     *
     * @param localDateTime Дата и время, на основе которых проводится проверка отчетов.
     * @throws NoSuchElementException если не найден питомец по идентификатору.
     *                                //TODO Отправить сформированные сообщения клиентам через Телеграмм-бота.
     */
    @Override
    public void dailyReportPreCheckMessages(LocalDateTime localDateTime) {

        Map<FragmentNameText, Set<Long>> resultPreCheckDailyReport = getIdPetForVolunteerCheckAndResultInitialCheckDailyReports(localDateTime);

        Map<Long, List<String>> messageAssemblyPackList = new HashMap<>();

        Set<FragmentNameText> reasons = Set.of(
                FragmentNameText.PET_DAILY_REPORT_NO,
                FragmentNameText.PET_WHICH_DAILY_REPORT_NOT_COMPLETED,
                FragmentNameText.PET_PICTURE_NONE,
                FragmentNameText.PET_REACTION_IS_BLANK,
                FragmentNameText.PET_WELL_IS_BLANK);

        for (FragmentNameText reason : reasons) {

            for (Long idPet : (resultPreCheckDailyReport.get(reason))) {

                Optional<Pet> optionalPet = Optional.ofNullable(petRepository.findPetById(idPet));

                if (!optionalPet.isPresent()) {
                    logger.error("Pet with id {} not found.", idPet);

                } else {
                    Pet pet = optionalPet.get();

                    StringBuilder stringPack = new StringBuilder();

                    stringPack.append(reason.getDisplayName()).
                            append(messageAssembly(pet, localDateTime));

                    List<String> addingMessage = messageAssemblyPackList.computeIfAbsent(pet.getClient().getChatId(), k -> new ArrayList<>());
                    addingMessage.add(stringPack.toString());
                }
            }
        }

        for (Long idPet : (resultPreCheckDailyReport.get(FragmentNameText.PET_WHICH_DAILY_REPORT_TIME_IS_AFTER))) {

            StringBuilder stringWithoutEnd = new StringBuilder();

            Optional<Pet> optionalPet = Optional.ofNullable(petRepository.findPetById(idPet));

            if (!optionalPet.isPresent()) {
                logger.error("Pet with id {} not found.", idPet);

            } else {

                Pet pet = optionalPet.get();

                LocalDate date = localDateTime.toLocalDate();
                String viewPet = pet.getViewPet().getNameViewPet().getDisplayName().toLowerCase();
                String namePet = pet.getNamePet();
                String nameClient = pet.getClient().getNameClient();
                long idClient = pet.getClient().getId();

                long numberDays = ChronoUnit.DAYS.between(
                        pet.getDateTake(),
                        pet.getDailyReports().get(pet.getDailyReports().size() - 1).getDateTime()
                );

                stringWithoutEnd.append(FragmentNameText.PET_WHICH_DAILY_REPORT_TIME_IS_AFTER.getDisplayName()).
                        append(nameClient).append(" ").append("(").append(idClient).append(")").
                        append(FragmentNameText.DAILY_REPORT_DECLINED_ONE.getDisplayName()).
                        append(date).
                        append(FragmentNameText.DAILY_REPORT_DECLINED_TWO.getDisplayName()).
                        append(viewPet).
                        append(FragmentNameText.DAILY_REPORT_DECLINED_THREE.getDisplayName()).
                        append(namePet).
                        append(FragmentNameText.DAILY_REPORT_DECLINED_FOUR.getDisplayName()).
                        append(numberDays);

                List<String> addingMessage = messageAssemblyPackList.computeIfAbsent(pet.getClient().getChatId(), k -> new ArrayList<>());
                addingMessage.add(stringWithoutEnd.toString());
            }
        }
        //TODO направить полученный Map<Long, String> messageAssemblyPack, где Long это chartId, как сообщение клиенту в Телеграмм-бот
    }

    /**
     * Собирает сообщение для клиента о состоянии ежедневных отчетов питомца.
     *
     * @param pet           Объект питомца, для которого формируется сообщение.
     * @param localDateTime Дата и время, используемые для формирования сообщения.
     * @return Сформированное сообщение для клиента.
     */
    public String messageAssembly(Pet pet, LocalDateTime localDateTime) {

        StringBuilder stringPack = new StringBuilder();

        LocalDate date = localDateTime.toLocalDate();
        String viewPet = pet.getViewPet().getNameViewPet().getDisplayName().toLowerCase();
        String namePet = pet.getNamePet();
        String nameClient = pet.getClient().getNameClient();
        long idClient = pet.getClient().getId();

        long numberDays = ChronoUnit.DAYS.between(
                pet.getDateTake(),
                pet.getDailyReports().get(pet.getDailyReports().size() - 1).getDateTime()
        );

        stringPack.append(nameClient).append(" ").append("(").append(idClient).append(")").
                append(FragmentNameText.DAILY_REPORT_DECLINED_ONE.getDisplayName()).
                append(date).
                append(FragmentNameText.DAILY_REPORT_DECLINED_TWO.getDisplayName()).
                append(viewPet).
                append(FragmentNameText.DAILY_REPORT_DECLINED_THREE.getDisplayName()).
                append(namePet).
                append(FragmentNameText.DAILY_REPORT_DECLINED_FOUR.getDisplayName()).
                append(numberDays).
                append(FragmentNameText.DAILY_REPORT_DECLINED_FIVE.getDisplayName());

        return stringPack.toString();
    }

    /**
     * Проверяет наличие ежедневных отчетов для заданной даты.
     * <p>
     * Метод подсчитывает количество отчетов за указанную дату, что позволяет установить их наличие или отсутствие.
     *
     * @param localDateTime Дата, для которой осуществляется проверка наличия отчетов.
     * @return true, если отчеты за указанную дату существуют, иначе false.
     */
    @Override
    public Boolean checkIfReportsExistForDate(LocalDateTime localDateTime) {

        LocalDateTime startOfDay = localDateTime.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1); // Начало следующего дня

        return dailyReportRepository.countByDateTimeBetween(startOfDay, endOfDay) > 0;
    }

    /**
     * Получает набор {@link Pet}, дата усыновления (dateTake) которых приходится на начало и конец конкретного дня.
     * Конкретно используется для питомцев с периодом решения от 30 до 44 дней, находящихся на испытательном сроке.
     *
     * @param localDateTime Время и дата, в которую должно быть принято решение.
     * @return Набор объектов {@link Pet}.
     * @throws IllegalArgumentException Если список питомцев пуст на указанную дату.
     */
    public Set<Pet> renewAdoptReturnPet(LocalDateTime localDateTime) {

        LocalDateTime startOfDay = localDateTime.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        Set<Pet> starterPetGroup = petRepository.findAllByBusyFreeAndDateTakeBetween(startOfDay, endOfDay);

        if (starterPetGroup.isEmpty()) {
//            throw new IllegalArgumentException("Pet list is empty as of date: " + localDateTime.toLocalDate());
            logger.info("Pet list (30 or 44 days) is empty as of date: {}", localDateTime.toLocalDate());
        }
        return starterPetGroup;
    }

    /**
     * Получает набор {@link Pet} с датой усыновления (dateTake), равной или более поздней, чем указанный LocalDateTime.
     * Этот метод используется для ситуаций, когда испытательный срок питомца составляет 60 и более дней.
     *
     * @param localDateTime Нижняя граница даты и времени для возврата питомцев, находящихся на испытательном сроке.
     * @return Набор объектов {@link Pet}.
     * @throws IllegalArgumentException Если список питомцев пуст на указанную дату.
     */
    public Set<Pet> adoptReturnPet(LocalDateTime localDateTime) {
//        Set<Pet> ultimatePetGroup = petRepository.findAllByBusyFreeAndDateTakeLessThanOrEqualTo(localDateTime);
        Set<Pet> ultimatePetGroup = petRepository.findAllByBusyFreeAndDateTakeLessThanOrEqualTo(localDateTime);

        if (ultimatePetGroup.isEmpty()) {
//            throw new IllegalArgumentException("Pet list is empty as of date: " + localDateTime.toLocalDate());
            logger.info("Pet list (60 days or more) is empty as of date: {}", localDateTime.toLocalDate());
        }
        return ultimatePetGroup;
    }

    /**
     * Определяет идентификаторы питомцев на основе принятых решений в различные временные интервалы, определенные dayDecision.
     * Объединяет результаты методов renewAdoptReturnPet и adoptReturnPet для различных сроков принятия решений.
     *
     * @param localDateTime Время и дата начала периода принятия решений.
     * @return Объединенный набор идентификаторов питомцев из методов renewAdoptReturnPet и adoptReturnPet.
     */
    @Override
    public Set<Long> petOnDateDecision(LocalDateTime localDateTime) {
        Set<Integer> dayDecision = Set.of(60, 44, 30);

        Set<Long> petIds = new HashSet<>();

        for (Integer day : dayDecision) {
            Set<Pet> pets = (day == 60) ? adoptReturnPet(localDateTime.minusDays(day))
                    : renewAdoptReturnPet(localDateTime.minusDays(day));
            petIds.addAll(pets.stream()
                    .map(Pet::getId)
                    .collect(Collectors.toSet()));
        }
        return petIds;
    }

    /**
     * Усыновление питомцев на определённую дату по списку idPet,
     * подсчёт количества успешно усыновленных питомцев из списка полученных idPet на указанную дату.
     * Метод проверяет доступность питомцев для усыновления (основываясь на их текущем статусе,
     * который должен быть помечен как "свободен": об этом указывает булево "busy_free")
     * и принятие решения об усыновлении, список которых получает волонтёр из другого эндпоинта
     * /decisions (getPetDecisionsByDate) на основе даты и списка ID питомцев.
     *
     * @param obtainedIdPets Множество идентификаторов питомцев, подлежащих проверке.
     * @param localDateTime  Дата и время, на которые должно быть принято решение об усыновлении.
     * @return Строка, содержащая информацию о количестве успешно усыновленных питомцев и ID клиентов,
     * которым эти питомцы были усыновлены. В случае отсутствия свободных питомцев выбрасывает исключение.
     * @throws NoSuchElementException если не найдено свободных питомцев для усыновления.
     */
    @Override
    public String countSuccessfullyAdoptionPet(Set<Long> obtainedIdPets, LocalDateTime localDateTime) {
        Set<Long> availableIdPets = petRepository.findIdsByBusyFree(true);

        Set<Long> adoptivePetClientId = new HashSet<>();

        if (availableIdPets.isEmpty()) {
            throw new NoSuchElementException("No free pets found");
        }

        Set<Long> decisionPetsIds = petOnDateDecision(localDateTime);

        for (Long idPet : obtainedIdPets) {
            if (availableIdPets.contains(idPet) && decisionPetsIds.contains(idPet)) {
                petRepository.updateBusyFreeStatusById(false, idPet);
                petRepository.findClientIdByPetId(idPet).ifPresentOrElse(
                        clientId -> {
                            adoptivePetClientId.add(clientId);
                            //TODO здесь нужно отправить сообщение клиентам, получить их chartId, с поздравлением об усыновлении с именем контента: CONGRATULATION_ADOPTION
                        },
                        () -> logger.warn("Клиент для питомца с ID: {} не найден.", idPet)
                );
            } else {
                logger.info("A pet with this ID {} number cannot be adopted in this date {} ", idPet, localDateTime.toLocalDate());
            }
        }

        return "Количество усыновлённых питомцев из возможных, по предложенному списку, равно: "
                + adoptivePetClientId.size() + ". "
                + "Поздравление с усыновлением отправлены клиентам с идентификатором id: " + adoptivePetClientId.toString();
    }

    /**
     * Обрабатывает возврат питомцев в приют или продление испытательного срока,
     * для питомцев 30 или 44 день: продление возврат,
     * для питомцев 60 дней испытательного срока, только возврат.
     *
     * <p>Для питомцев из предоставленного набора id питомцев данная процедура проверяет,
     * готовы ли они к усыновлению и соответствуют ли указанной дате принятия решения.
     * Если условия выполнены, статус питомца обновляется, и планируется отправка сообщения
     * о возврате в приют соответствующим клиентам.</p>
     *
     * <p>Дополнительно, для питомцев, находящихся на испытательном сроке, происходит
     * проверка возможности продления этого срока и отправка соответствующих уведомлений.</p>
     *
     * @param obtainedIdPets Набор ID питомцев для обработки.
     * @param localDateTime  Дата, на основании которой производится проверка и решение.
     * @return Строка с информацией о количестве питомцев, требующих действий, и отпраленных сообщениях.
     * @throws NoSuchElementException Если в системе не найдены питомцы, готовые к усыновлению.
     */
    @Override
    public String returningPetToShelterOrExtendingProbationPeriod(Set<Long> obtainedIdPets, LocalDateTime localDateTime) {
        Set<Long> availableIdPets = petRepository.findIdsOfPetsReadyForAdoption();

        Set<Long> returnPetClientId = new HashSet<>();

        if (availableIdPets.isEmpty()) {
            throw new NoSuchElementException("No free pets found");
        }

        Set<Long> decisionPetsIds = petOnDateDecision(localDateTime);

        for (Long idPet : obtainedIdPets) {
            if (availableIdPets.contains(idPet) && decisionPetsIds.contains(idPet)) {
                petRepository.updatePetStatusAndClearDateTake(idPet);
                petRepository.findClientIdByPetId(idPet).ifPresentOrElse(
                        clientId -> {
                            returnPetClientId.add(clientId);
                            //TODO здесь нужно отправить сообщение клиентам, получить их chartId, с сообщением о необходимости возврата питомца в приют RETURN_INSTRUCTION
                        },
                        () -> logger.warn("Клиент для питомца с ID: {} не найден.", idPet)
                );
            } else {
                logger.info("A pet with this ID {} number cannot be adopted in this date {} ", idPet, localDateTime.toLocalDate());
            }
        }

        Set<Pet> petsExtendProbationPeriod = renewAdoptReturnPet(localDateTime);

        Set<Long> idClientForPetsRenew = petsExtendProbationPeriod.stream()
                .filter(pet -> pet.getClient() != null)
                .map(pet -> pet.getClient().getId())
                .collect(Collectors.toSet());
        //TODO здесь нужно отправить сообщение клиентам, у которых наступил 30 или 44 день испытательного срока, для его продления

        return "Количество питомцев, которых необходимо вернуть, прервав испытательный срок (на 30 или 44 день), по предложенному списку, равно: "
                + returnPetClientId.size() + ". Клиентам этих питомцев с id " + returnPetClientId.toString() + " отправлены ообщения о возврате питомца в приют с инструкцией по возврату."
                + "Информация о продлении испытательного срока отравлены " + idClientForPetsRenew.size() + " клиентам с идентификатором id: " + idClientForPetsRenew.toString();
    }

    /**
     * Осуществляет передачу питомцев обратно в приют пользователем.
     * Метод проверяет, могут ли заданные питомцы быть возвращены в приют на основании текущего статуса,
     * и обрабатывает возврат питомцев, соответствующих критериям.
     *
     * @param idPetForTransfer набор идентификаторов питомцев, которые пользователь желает вернуть в приют.
     * @return строка, сообщающая о результатах операции, включая идентификаторы успешно возвращенных питомцев,
     * и идентификаторы питомцев, которые не могут быть возвращены.
     * @throws NoSuchElementException если в базе данных нет питомцев, подходящих под заданные критерии для возврата.
     */
    @Override
    public String transferPetToShelterByClient(Set<Long> idPetForTransfer) {

        Set<Long> petIdForCustomerReturns = petRepository.findAvailablePetIds();

        if (petIdForCustomerReturns.isEmpty()) {
            throw new NoSuchElementException("There are no pets that can be returned to the shelter");
        }

        Set<Long> returnedPetIds = new HashSet<>();
        Set<Long> petIdsCanNotBeReturned = new HashSet<>();

        for (Long idPet : idPetForTransfer) {
            if (petIdForCustomerReturns.contains(idPet)) {
                returnedPetIds.add(idPet);
                petRepository.disconnectPetFromClient(idPet);
            } else {
                logger.info("Pets with these IDs {} cannot be returned to the shelter", idPet);
                petIdsCanNotBeReturned.add(idPet);
            }
        }

        return String.format("Питомцы с идентификаторами %s, возвращены в приют." +
                "Питомцы с идентификаторами %s не могут быть возвращены, " +
                "либо значения id питомца переданы не корректные, " +
                "поскольку данные в таблице питомцев с такими id в базе данных не имеют требуемых параметров исполнить возврат", returnedPetIds.toString(), petIdsCanNotBeReturned.toString());
    }

    /**
     * Производит перевод питомца в оперативное управление клиента на испытательный период.
     * Передает питомца клиенту, если оба доступны (питомец не занят и клиент существует).
     *
     * @param idPet    Идентификатор питомца, который передается клиенту.
     * @param idClient Идентификатор клиента, которому передается питомец.
     * @param dateTake Дата начала испытательного периода владения питомцем.
     * @throws NoSuchElementException если нет доступных питомцев для передачи или список клиентов пуст.
     */
    @Override
    public void transferPetToClientForProbationaryPeriod(long idPet, long idClient, LocalDateTime dateTake) {

        logger.info("Attempting to transfer pet with id {} to client with id {} on date {}", idPet, idClient, dateTake);

        Set<Long> availablePetsId = petRepository.findAvailablePetsId();
        if (availablePetsId.isEmpty()) {
            logger.warn("Transfer failed: No pets available for transfer to the client");
            throw new NoSuchElementException("No pets available for transfer to the client");
        }

        Set<Long> availableClientsId = clientRepository.findAllClientIds();
        if (availableClientsId.isEmpty()) {
            logger.warn("Transfer failed: Client's list is empty");
            throw new NoSuchElementException("Client's list is empty");
        }

        if (availablePetsId.contains(idPet) && availableClientsId.contains(idClient)) {
            petRepository.updatePetClientAndDateTake(idPet, idClient, dateTake);
            logger.info("Successfully transferred pet with id {} to client with id {} on date {}", idPet, idClient, dateTake);
        } else {
            if (!availablePetsId.contains(idPet)) {
                logger.warn("Transfer failed: Pet with id {} not available for transfer", idPet);
            }
            if (!availableClientsId.contains(idClient)) {
                logger.warn("Transfer failed: Client with id {} not found in available clients list", idClient);
            }
        }
    }
}
