package tel.bvm.pet.postConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import tel.bvm.pet.model.ButtonMenu;
import tel.bvm.pet.model.ContentForm;

import java.util.*;

@Component
public class DataButtonMenu {

//    DataContentForm dataContentForm = new DataContentForm();

    private final DataContentForm dataContentForm;

    @Autowired
    public DataButtonMenu(DataContentForm dataContentForm) {
        this.dataContentForm = dataContentForm;
    }

//    public DataButtonMenu(DataContentForm dataContentForm) {
//        this.dataContentForm = dataContentForm;
//    }

    public ButtonMenu createButtonMenu(ButtonMenu.NameButtonMenu nameButtonMenu) {

        if (nameButtonMenu == ButtonMenu.NameButtonMenu.GUEST_MENU) {

/*            Set<ContentForm> contentForms = new HashSet<>(Arrays.asList(
                    dataContentForm.createContentForm(ContentForm.NameContentForm.SHELTERS_INFO),
                    dataContentForm.createContentForm(ContentForm.NameContentForm.SING_UP_MENU),
                    dataContentForm.createContentForm(ContentForm.NameContentForm.RETURN_START_MENU),
                    dataContentForm.createContentForm(ContentForm.NameContentForm.CALLING_VOLUNTEER)));*/

            ButtonMenu buttonMenu = new ButtonMenu(ButtonMenu.NameButtonMenu.GUEST_MENU,
                    "Приветствуем вас в нашем приюте для собак и кошек! \uD83C\uDF1F Наша миссия - найти любящие и заботливые дома для наших милых питомцев. Каждое животное здесь ищет второй шанс на счастье и мы верим, что вместе мы можем им помочь. Давайте сделаем этот мир немного лучше, одного спасенного друга за раз! \uD83D\uDC15❤\uFE0F\uD83D\uDC08\n" +
                            "\n" +
                            "Вот как мы можем начать наше позитивное путешествие вместе:\n" +
                            "\n" +
                            "- Познакомьтесь с нашими восхитительными питомцами! Каждый из них имеет свою уникальную историю и характер. Кто знает, может ваш новый лучший друг уже ждет вас здесь? \uD83D\uDC3E\n" +
                            "  \n" +
                            "- Поддержите нашу миссию! Даже если вы не можете принять животное у себя дома, есть много других способов помочь: от добровольческой работы до финансовой поддержки. Любая помощь на вес золота! \uD83D\uDC96\n" +
                            "\n" +
                            "- Расскажите о нас друзьям! Чем больше людей узнает о нашем приюте, тем выше шанс, что наши питомцы найдут свои дома. Поделитесь нашей историей в социальных сетях и помогите нам распространить слово! \uD83D\uDCE2\n" +
                            "\n" +
                            "Мы несем ответственность за тех, кого приручили. Вместе мы можем сделать мир лучше для бездомных животных, потому что каждая маленькая лапка заслуживает счастья и любви.",
//                    contentForms);
                    null);

            buttonMenu.setId(1);

            return buttonMenu;

        } else if (nameButtonMenu == ButtonMenu.NameButtonMenu.REGISTRATION_PRODUCE) {

//            Set<ContentForm> contentForms = new HashSet<>(Arrays.asList(
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.SING_UP),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.RETURN_START_MENU),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.CALLING_VOLUNTEER)));

            ButtonMenu buttonMenu = new ButtonMenu(ButtonMenu.NameButtonMenu.REGISTRATION_PRODUCE,
                    "**Регистрация пользователя**\uD83D\uDCCB\n" +
                            "\n" +
                            "Привет!\uD83C\uDF1F\n" +
                            "\n" +
                            "Чтобы завершить регистрацию, введите ваши данные в следующем формате:\n" +
                            "\n" +
                            "✏\uFE0F Имя Фамилия <пробел> \uD83D\uDCDE +7-9**-***-**-**\n" +
                            "\n" +
                            "Благодарим за ваше участие! Давайте вместе сделаем мир лучше! \uD83D\uDC3E",
//                    contentForms);
                    null);

            buttonMenu.setId(2);

            return buttonMenu;

        } else if (nameButtonMenu == ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_NO_PETS) {

//            Set<ContentForm> contentForms = new HashSet<>(Arrays.asList(
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.SHELTERS_INFO),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.PET_ALL_INFO),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.PET_CAT_INFO),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.PET_DOG_INFO),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.CONTINUE_NEXT_MENU),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.RETURN_START_MENU),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.CALLING_VOLUNTEER)));

            ButtonMenu buttonMenu = new ButtonMenu(ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_NO_PETS,
                    "Добро пожаловать обратно в наш телеграмм-канал \uD83C\uDF89, дорогой друг приюта для животных! \uD83D\uDC15\uD83D\uDC08\uD83D\uDC96 Мы так рады видеть здесь тех, кто не остается равнодушным к судьбам наших мохнатых питомцев. Ваш поддержка и любовь невероятно важны для нас и животных, которые все еще ищут свой дом. \uD83C\uDF1F\uD83C\uDFE1\n" +
                            "\n" +
                            "Вместе мы можем делать большие дела! \uD83D\uDCAA\uD83C\uDF1F\n" +
                            "\n" +
                            "Спасибо, что вы с нами! Ваша поддержка помогает менять мир к лучшему, маленькие лапки \uD83D\uDC3E.",
//                    contentForms);
                    null);

            buttonMenu.setId(3);

            return buttonMenu;

        } else if (nameButtonMenu == ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_WITH_PETS) {

//            Set<ContentForm> contentForms = new HashSet<>(Arrays.asList(
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.SEND_DAILY_REPORT),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.SHELTERS_INFO),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.PET_ALL_INFO),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.PET_CAT_INFO),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.PET_DOG_INFO),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.CONTINUE_NEXT_MENU),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.RETURN_START_MENU),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.CALLING_VOLUNTEER)));

            ButtonMenu buttonMenu = new ButtonMenu(ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_WITH_PETS,
                    "Добро пожаловать обратно в наш телеграмм-канал \uD83C\uDF89, дорогой друг приюта для животных! \uD83D\uDC15\uD83D\uDC08\uD83D\uDC96 Мы так рады видеть здесь тех, кто не остается равнодушным к судьбам наших мохнатых питомцев. Ваша поддержка и любовь невероятно важны для нас и животных, которые все еще ищут свой дом. \uD83C\uDF1F\uD83C\uDFE1\n" +
                            "\n" +
                            "Вместе мы можем делать большие дела! \uD83D\uDCAA\uD83C\uDF1F\n" +
                            "\n" +
                            "Спасибо, что вы с нами! Ваша поддержка помогает менять мир к лучшему, маленькие лапки \uD83D\uDC3E.\n" +
                            "\n" +
                            "И не забудьте, что сейчас у вас есть особенная миссия - забота о питомце на испытательном сроке. \uD83D\uDC8C\uD83D\uDC3E Пожалуйста, напоминаем вам отправить свой ежедневный отчет о состоянии питомца, как того требует наше соглашение. Это очень важно для нас, чтобы убедиться, что наш малыш чувствует себя любимым и комфортным в вашем доме. \uD83C\uDFE1\uD83D\uDC95\n" +
                            "\n" +
                            "Мы ценим ваше время и усилия, посвященные этому важному делу, и хотим вас заверить, что каждый ваш отчет - это вклад в будущее питомца.",
//                    contentForms);
                    null);

            buttonMenu.setId(4);

            return buttonMenu;

        } else if (nameButtonMenu == ButtonMenu.NameButtonMenu.ADOPTION_INFO) {

//            Set<ContentForm> contentForms = new HashSet<>(Arrays.asList(
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.DOCUMENT),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.REFUSAL),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.RETURN_INSTRUCTION),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.CYNOLOG_VERIFY),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.ADVICE_CYNOLOG),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.SHELTER_TERRITORY_RULE),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.HOME_CAT),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.HOME_KITTEN),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.HOME_DOG),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.HOME_PUPPY),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.KEEPING_PET_DISABLE),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.RETURN_PREVIOUS_MENU),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.RETURN_START_MENU),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.CALLING_VOLUNTEER)));

            ButtonMenu buttonMenu = new ButtonMenu(ButtonMenu.NameButtonMenu.ADOPTION_INFO,
                    "\uD83C\uDF1F Добро пожаловать в путеводитель по усыновлению питомца! \uD83C\uDF08 \n" +
                            "\n" +
                            "Здесь вы найдете всю необходимую информацию, чтобы ваш путь к новому члену семьи был максимально гладким и радостным! \uD83D\uDC3E\uD83D\uDC96 Наш телеграмм-бот создан, чтобы помочь вам на каждом шагу этого волнующего процесса, начиная от первой встречи с питомцем и заканчивая моментом, когда вы уже счастливо живете вместе. Мы уверены, что это руководство станет вашим надежным помощником и сделает процесс усыновления питомца проще и понятнее.",
//                    contentForms);
                    null);

            buttonMenu.setId(5);

            return buttonMenu;

        } else if (nameButtonMenu == ButtonMenu.NameButtonMenu.END_REPORT_CYCLE) {

//            Set<ContentForm> contentForms = new HashSet<>(Arrays.asList(
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.SEND_DAILY_REPORT),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.RETURN_PREVIOUS_MENU),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.RETURN_START_MENU),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.CALLING_VOLUNTEER)));

            ButtonMenu buttonMenu = new ButtonMenu(ButtonMenu.NameButtonMenu.END_REPORT_CYCLE,
                    "\uD83C\uDF89 Спасибо за Ваш Ежедневный Отчет! \uD83C\uDF89\n" +
                            "\n" +
                            "Уважаемый друг наших пушистых подопечных, от всей души благодарим Вас за время и усилия, вложенные в создание ежедневного отчета о Вашем питомце! \uD83D\uDC3E\uD83D\uDC96 Ваша забота и внимание к деталям не только помогают нашим четвероногим друзьям чувствовать себя любимыми и защищенными, но и освещают ваш путь совместной адаптации и радости.\n" +
                            "\n" +
                            "Мы искренне ценим вашу исполнительность и признательны за соблюдение соглашений, заключенных между вами и приютом. Это показывает ваше большое сердце и настоящую приверженность благополучию ваших новых членов семьи. Мы с нетерпением ожидаем узнать больше о ваших будущих приключениях и успехах вместе. \uD83C\uDF08\uD83D\uDC15\u200D\uD83E\uDDBA\n" +
                            "\n" +
                            "Что Вы хотели бы сделать дальше?\n" +
                            "\n" +
                            "1. \uD83D\uDD04 Предоставить еще один отчет - Вы можете поделиться дополнительным отчетом о другом питомце или добавить что-то к уже отправленному отчету.  \n" +
                            "2. ⬅\uFE0F Вернуться к предыдущему меню - Продолжить наше интерактивное общение или выбрать другие действия.\n" +
                            "3. \uD83C\uDFE1 Вернуться в стартовое меню - Изучите другие функции и возможности нашего сервиса для поддержки ваших питомцев.\n" +
                            "4. \uD83C\uDD98 Вызов волонтёра для помощи или консультации - Если у вас возникли вопросы или нужна помощь по сервису, наша команда готова предоставить поддержку и ответить на ваши вопросы.\n" +
                            "\n" +
                            "Мы здесь, чтобы помочь вам и вашему питомцу на каждом этапе вашего совместного пути.",
//                    contentForms);
                    null);

            buttonMenu.setId(6);

            return buttonMenu;

        } else if (nameButtonMenu == ButtonMenu.NameButtonMenu.START_MENU) {

//            Set<ContentForm> contentForms = new HashSet<>(Arrays.asList(
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.START),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.CALLING_VOLUNTEER)));

            ButtonMenu buttonMenu = new ButtonMenu(ButtonMenu.NameButtonMenu.START_MENU,
                    "Вместе к счастью наших четвероногих друзей! \uD83D\uDC3E✨\n" +
                            "\n" +
                            "Нажмите \"Начать\" и погрузитесь в мир, где забота и любовь обитают в каждом уголке! \uD83C\uDF08❤\uFE0F Откройте для себя, как множество маленьких поступков может преобразить жизнь наших питомцев, делая их счастливее с каждым днем. Вместе мы в силах создать яркое и теплое будущее для наших четвероногих спутников, наполненное безмерной радостью, нежностью и позитивными эмоциями. \uD83D\uDC15\uD83D\uDC96\uD83D\uDC08\n" +
                            "\n" +
                            "Вперед к нашему общему будущему, где каждому питомцу найдется любящий дом и сердце, готовое принять его! Ваше участие является ключом к миру, где каждое животное чувствует себя нужным и любимым.",
//                    contentForms);
                    null);

            buttonMenu.setId(7);

            return buttonMenu;

        } else if (nameButtonMenu == ButtonMenu.NameButtonMenu.END_SUCCESSFUL_REGISTRATION) {

//            Set<ContentForm> contentForms = new HashSet<>(Arrays.asList(
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.START),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.RETURN_START_MENU),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.CALLING_VOLUNTEER)));

            ButtonMenu buttonMenu = new ButtonMenu(ButtonMenu.NameButtonMenu.END_SUCCESSFUL_REGISTRATION,
                    "\uD83C\uDF89 Поздравляем с успешной регистрацией! \uD83C\uDF89\n" +
                            "\n" +
                            "Добро пожаловать в нашу дружную семью поклонников четвероногих! Мы искренне рады, что вы присоединились к нам и теперь являетесь полноценным членом невероятного сообщества приюта для животных. \uD83D\uDC3E❤\uFE0F Ваше участие открывает двери к миру, где каждый может сделать настоящее волшебство, помогая нашим пушистым друзьям найти теплый дом и любящие сердца.\n" +
                            "\n" +
                            "Как зарегистрированный пользователь, теперь у вас есть доступ к эксклюзивной информации о всех приютах, а также профилях питомцев, которые ищут новую семью. \uD83C\uDFE1\uD83D\uDC15\u200D\uD83E\uDDBA Ознакомьтесь с их историями, вдохновляйтесь и, возможно, найдите своего нового лучшего друга, который ждет именно вас!\n" +
                            "\n" +
                            "Мы верим, что вместе мы сможем сделать этот мир немного лучше, одну судьбу за другой. Спасибо, что вы с нами! Приготовьтесь к волнующему пути добра, заботы и бесконечной любви.",
//                    contentForms);
                    null);

            buttonMenu.setId(8);

            return buttonMenu;

        } else if (nameButtonMenu == ButtonMenu.NameButtonMenu.END_UNSUCCESSFUL_REGISTRATION) {

//            Set<ContentForm> contentForms = new HashSet<>(Arrays.asList(
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.SING_UP),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.RETURN_PREVIOUS_MENU),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.RETURN_START_MENU),
//                    dataContentForm.createContentForm(ContentForm.NameContentForm.CALLING_VOLUNTEER)));

            ButtonMenu buttonMenu = new ButtonMenu(ButtonMenu.NameButtonMenu.END_UNSUCCESSFUL_REGISTRATION,
                    "\uD83C\uDF1F Ой, похоже, что-то пошло не так. \uD83C\uDF1F\n" +
                            "\n" +
                            "Кажется, на пути к нашему дружному семейству возникло небольшое препятствие. Но не волнуйтесь! Это всего лишь маленький камушек на вашем пути к большому приключению и возможности сделать мир лучше для наших четвероногих друзей. \uD83D\uDC3E\uD83D\uDC96\n" +
                            "\n" +
                            "Иногда техника задирает нас, но это не повод для печали! Мы уверены, что ваши намерения и желаение помогать нашим пушистым подопечным сильнее любых технических неполадок. Попробуйте, пожалуйста, пройти регистрацию еще раз - мы верим, вас ждет успех! \uD83D\uDE80✨\n" +
                            "\n" +
                            "Если по какой-то причине вы столкнетесь с трудностями снова, не стесняйтесь обратиться к нам за помощью. Мы всегда рядом, чтобы поддержать вас и помочь преодолеть любые препятствия на пути к доброму делу.",
//                    contentForms);
                    null);

            buttonMenu.setId(9);

            return buttonMenu;
        }

        throw new IllegalArgumentException("Unsupported buttonMenu: " + nameButtonMenu);
    }

    public Map<ButtonMenu.NameButtonMenu, ButtonMenu> buttonMenuMap() {

        Map<ButtonMenu.NameButtonMenu, ButtonMenu> buttonMenuMap = new HashMap<>();

        buttonMenuMap.put(ButtonMenu.NameButtonMenu.GUEST_MENU, createButtonMenu(ButtonMenu.NameButtonMenu.GUEST_MENU));
        buttonMenuMap.put(ButtonMenu.NameButtonMenu.REGISTRATION_PRODUCE, createButtonMenu(ButtonMenu.NameButtonMenu.REGISTRATION_PRODUCE));
        buttonMenuMap.put(ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_NO_PETS, createButtonMenu(ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_NO_PETS));
        buttonMenuMap.put(ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_WITH_PETS, createButtonMenu(ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_WITH_PETS));
        buttonMenuMap.put(ButtonMenu.NameButtonMenu.ADOPTION_INFO, createButtonMenu(ButtonMenu.NameButtonMenu.ADOPTION_INFO));
        buttonMenuMap.put(ButtonMenu.NameButtonMenu.END_REPORT_CYCLE, createButtonMenu(ButtonMenu.NameButtonMenu.END_REPORT_CYCLE));
        buttonMenuMap.put(ButtonMenu.NameButtonMenu.START_MENU, createButtonMenu(ButtonMenu.NameButtonMenu.START_MENU));
        buttonMenuMap.put(ButtonMenu.NameButtonMenu.END_SUCCESSFUL_REGISTRATION, createButtonMenu(ButtonMenu.NameButtonMenu.END_SUCCESSFUL_REGISTRATION));
        buttonMenuMap.put(ButtonMenu.NameButtonMenu.END_UNSUCCESSFUL_REGISTRATION, createButtonMenu(ButtonMenu.NameButtonMenu.END_UNSUCCESSFUL_REGISTRATION));

        return buttonMenuMap;
    }

    Map<ButtonMenu.NameButtonMenu, ButtonMenu> buttonMenuMap = buttonMenuMap();

    public Set<ButtonMenu> buttonMenus(ContentForm contentForm) {

        Set<ButtonMenu> buttonMenus = new HashSet<>();

        if (createButtonMenu(ButtonMenu.NameButtonMenu.GUEST_MENU).getContentForm().contains(contentForm)) {
            buttonMenus.add(createButtonMenu(ButtonMenu.NameButtonMenu.GUEST_MENU));
        } else if (createButtonMenu(ButtonMenu.NameButtonMenu.REGISTRATION_PRODUCE).getContentForm().contains(contentForm)) {
            buttonMenus.add(createButtonMenu(ButtonMenu.NameButtonMenu.REGISTRATION_PRODUCE));
        } else if (createButtonMenu(ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_NO_PETS).getContentForm().contains(contentForm)) {
            buttonMenus.add(createButtonMenu(ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_NO_PETS));
        } else if (createButtonMenu(ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_WITH_PETS).getContentForm().contains(contentForm)) {
            buttonMenus.add(createButtonMenu(ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_WITH_PETS));
        } else if (createButtonMenu(ButtonMenu.NameButtonMenu.ADOPTION_INFO).getContentForm().contains(contentForm)) {
            buttonMenus.add(createButtonMenu(ButtonMenu.NameButtonMenu.ADOPTION_INFO));
        } else if (createButtonMenu(ButtonMenu.NameButtonMenu.END_REPORT_CYCLE).getContentForm().contains(contentForm)) {
            buttonMenus.add(createButtonMenu(ButtonMenu.NameButtonMenu.END_REPORT_CYCLE));
        } else if (createButtonMenu(ButtonMenu.NameButtonMenu.START_MENU).getContentForm().contains(contentForm)) {
            buttonMenus.add(createButtonMenu(ButtonMenu.NameButtonMenu.START_MENU));
        } else if (createButtonMenu(ButtonMenu.NameButtonMenu.END_SUCCESSFUL_REGISTRATION).getContentForm().contains(contentForm)) {
            buttonMenus.add(createButtonMenu(ButtonMenu.NameButtonMenu.END_SUCCESSFUL_REGISTRATION));
        } else if (createButtonMenu(ButtonMenu.NameButtonMenu.END_UNSUCCESSFUL_REGISTRATION).getContentForm().contains(contentForm)) {
            buttonMenus.add(createButtonMenu(ButtonMenu.NameButtonMenu.END_UNSUCCESSFUL_REGISTRATION));
        }

        return buttonMenus;
    }
}
//    Map<ButtonMenu.NameButtonMenu, ButtonMenu> buttonMenuMap = new HashMap<ButtonMenu.NameButtonMenu, ButtonMenu>(Map.of(
//            ButtonMenu.NameButtonMenu.GUEST_MENU, createButtonMenu(ButtonMenu.NameButtonMenu.GUEST_MENU),
//            ButtonMenu.NameButtonMenu.REGISTRATION_PRODUCE, createButtonMenu(ButtonMenu.NameButtonMenu.REGISTRATION_PRODUCE),
//            ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_NO_PETS, createButtonMenu(ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_NO_PETS),
//            ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_WITH_PETS, createButtonMenu(ButtonMenu.NameButtonMenu.CLIENT_REGISTERED_WITH_PETS),
//            ButtonMenu.NameButtonMenu.ADOPTION_INFO, createButtonMenu(ButtonMenu.NameButtonMenu.ADOPTION_INFO),
//            ButtonMenu.NameButtonMenu.END_REPORT_CYCLE, createButtonMenu(ButtonMenu.NameButtonMenu.END_REPORT_CYCLE),
//            ButtonMenu.NameButtonMenu.START_MENU, createButtonMenu(ButtonMenu.NameButtonMenu.START_MENU),
//            ButtonMenu.NameButtonMenu.END_SUCCESSFUL_REGISTRATION, createButtonMenu(ButtonMenu.NameButtonMenu.END_SUCCESSFUL_REGISTRATION),
//            ButtonMenu.NameButtonMenu.END_UNSUCCESSFUL_REGISTRATION, createButtonMenu(ButtonMenu.NameButtonMenu.END_UNSUCCESSFUL_REGISTRATION)
//    ));

