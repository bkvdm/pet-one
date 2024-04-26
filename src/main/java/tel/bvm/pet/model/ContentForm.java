package tel.bvm.pet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "content_forms")
public class ContentForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_content")
    private long id;

    public enum NameContentForm {

        // Контент текстом по кнопке. Название кнопки вызова контента.
        RULE("Знакомство с питомцем"),
        DOCUMENT("Документы"),
        TRANSPORTATION("Транспортировка питомца"),
        HOME_DOG("Дом для взрослой собаки"),
        HOME_PUPPY("Дом для щенка"),
        HOME_CAT("Дом для взрослой кошки"),
        HOME_KITTEN("Дом для котёнка"),
        CYNOLOG_VERIFY("Сертифицированные кинологи"),
        REFUSAL("Условия отказа"),
        ADVICE_CYNOLOG("Советы кинолога"),
        RETURN_INSTRUCTION("Возврат питомца в приют"),
        KEEPING_PET_DISABLE("Питомец с ограниченными возможностями"),
        SHELTER_TERRITORY_RULE("Правила на территории приюта"),

        // Кнопки перехода.
        RETURN_PREVIOUS_MENU("Назад"),
        RETURN_START_MENU("Возврат в начало"),

        // Кнопки обращения к базе данных.
        CALLING_VOLUNTEER("Помощь волонтёра"),
        SING_UP("Регистрация клиента"),
        SHELTERS_INFO("Информация о приютах"),
        PET_ALL_INFO("Питомцы для усыновления"),
        PET_CAT_INFO("Кошки для усыновления"),
        PET_DOG_INFO("Собаки для усыновления"),
        SEND_DAILY_REPORT("Отправить ежедневный отчёт"),

        // Контент, как дополнение к команде. Содержание контента.
        WELCOME_MESSAGE("Приветствие для гостя"),
        GREETING_REGISTRATION_CLIENT("Приветствие для зарегистрированного клиента"),
        GREETING_NEW_CLIENT("Приветствие для незарегистрированного клиента"),
        DAILY_REPORT_NOT_CORRECT("Ежедневный отчёт не принят и требует доработки"),
        DAILY_REPORT_CORRECT("Ежедневный отчёт принят"),
        EXTENSION_TERM("Продление испытательного срока на количество дней"),
        REMINDER_DAILY_REPORT("Напоминание о ежедневном отчёте по уходу за питомцем"),
        REGISTRATION_INFO("Информация для регистрации клиента"),
        CONGRATULATION_ADOPTION("Поздравление с усыновлением питомца");

        private String displayName;

        NameContentForm(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "name_content", nullable = false)
    private NameContentForm nameContent;

//    @Column(name = "name_content")
//    private String nameContent;

//    @Column(name = "name_content")
//    private String nameContent;

    @Column(name = "content")
    private String content;

    @OneToOne(mappedBy = "contentForm")
    @JsonIgnore
    private Form form;

    @ManyToMany
    @JoinTable(
            name = "content_menu",
            joinColumns = @JoinColumn(name = "id_content"),
            inverseJoinColumns = @JoinColumn(name = "id_button_menu")
    )
    private Set<ButtonMenu> buttonMenus = new HashSet<>();

    public ContentForm(NameContentForm nameContent, String content, Form form, Set<ButtonMenu> buttonMenus) {
        this.nameContent = nameContent;
        this.content = content;
        this.form = form;
        this.buttonMenus = buttonMenus;
    }

    public ContentForm() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NameContentForm getNameContent() {
        return nameContent;
    }

    public void setNameContent(NameContentForm nameContent) {
        this.nameContent = nameContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Set<ButtonMenu> getButtonMenus() {
        return buttonMenus;
    }

    public void setButtonMenus(Set<ButtonMenu> buttonMenus) {
        this.buttonMenus = buttonMenus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentForm that = (ContentForm) o;
        return id == that.id && Objects.equals(nameContent, that.nameContent) && Objects.equals(content, that.content) && Objects.equals(form, that.form) && Objects.equals(buttonMenus, that.buttonMenus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameContent, content, form, buttonMenus);
    }

    @Override
    public String toString() {
        return "ContentForm{" +
                "id=" + id +
                ", nameContent='" + nameContent + '\'' +
                ", content='" + content + '\'' +
                ", form=" + form +
                ", buttonMenus=" + buttonMenus +
                '}';
    }
}
