package tel.bvm.pet.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "button_menus")
public class ButtonMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_button_menu")
    private long id;

    public enum NameButtonMenu {

        GUEST_MENU("Гость"),
        REGISTRATION_PRODUCE("Процедура регистрации"),
        CLIENT_REGISTERED_NO_PETS("Клиент зарегистрирован, без питомцев"),
        CLIENT_REGISTERED_WITH_PETS("Клиент зарегистрирован, питомцы на испытании"),
        ADOPTION_INFO("Информация для усыновления"),
        END_REPORT_CYCLE("Меню окончания цикла сдачи отчёта"),
        START_MENU("Начало"),
        END_SUCCESSFUL_REGISTRATION("Успешная регистрация клиента"),
        END_UNSUCCESSFUL_REGISTRATION("Неуспешная регистрация клиента"),
        TRANSMISSION_EXCEPTION("Команда не распознана");

        private String displayName;

        NameButtonMenu(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_name", nullable = false)
    private NameButtonMenu nameButtonMenu;

    @Column(name = "menu_header")
    private String menuHeader;

    @ManyToMany(mappedBy = "buttonMenus", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<ContentForm> contentForm = new HashSet<>();

    public ButtonMenu(NameButtonMenu nameButtonMenu, String menuHeader, Set<ContentForm> contentForm) {
        this.nameButtonMenu = nameButtonMenu;
        this.menuHeader = menuHeader;
        this.contentForm = contentForm;
    }

    public ButtonMenu() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public NameButtonMenu getNameButtonMenu() {
        return nameButtonMenu;
    }

    public void setNameButtonMenu(NameButtonMenu nameButtonMenu) {
        this.nameButtonMenu = nameButtonMenu;
    }

    public String getMenuHeader() {
        return menuHeader;
    }

    public void setMenuHeader(String menuHeader) {
        this.menuHeader = menuHeader;
    }

    public Set<ContentForm> getContentForm() {
        return contentForm;
    }

    public void setContentForm(Set<ContentForm> contentForm) {
        this.contentForm = contentForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ButtonMenu that = (ButtonMenu) o;
        return id == that.id && nameButtonMenu == that.nameButtonMenu && Objects.equals(menuHeader, that.menuHeader) && Objects.equals(contentForm, that.contentForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameButtonMenu, menuHeader, contentForm);
    }

    @Override
    public String toString() {
        return "ButtonMenu{" +
                "id=" + id +
                ", nameButtonMenu=" + nameButtonMenu +
                ", menuHeader='" + menuHeader + '\'' +
                ", contentForm=" + contentForm +
                '}';
    }
}
