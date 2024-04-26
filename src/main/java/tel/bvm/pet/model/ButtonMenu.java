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
        REGISTRATION_PRODUCE("Процедура регистрации"),
        GUEST_MENU("Гость"),
        CLIENT_REGISTERED_WITH_PETS("Клиент зарегистрирован, питомцы на испытании"),
        CLIENT_REGISTERED_NO_PETS("Клиент зарегистрирован, без питомцев"),
        ADOPTION_INFO("Информация для усыновления");

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

    @Column(name = "menu_number")
    private byte menuNumber;

    @ManyToMany(mappedBy = "buttonMenus")
    private Set<ContentForm> menus = new HashSet<>();

    public ButtonMenu(NameButtonMenu nameButtonMenu, byte menuNumber, Set<ContentForm> menus) {
        this.nameButtonMenu = nameButtonMenu;
        this.menuNumber = menuNumber;
        this.menus = menus;
    }

    public ButtonMenu() {
    }

    public NameButtonMenu getNameButtonMenu() {
        return nameButtonMenu;
    }

    public void setNameButtonMenu(NameButtonMenu nameButtonMenu) {
        this.nameButtonMenu = nameButtonMenu;
    }

    public byte getMenuNumber() {
        return menuNumber;
    }

    public void setMenuNumber(byte menuNumber) {
        this.menuNumber = menuNumber;
    }

    public Set<ContentForm> getMenus() {
        return menus;
    }

    public void setMenus(Set<ContentForm> menus) {
        this.menus = menus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ButtonMenu that = (ButtonMenu) o;
        return id == that.id && menuNumber == that.menuNumber && nameButtonMenu == that.nameButtonMenu && Objects.equals(menus, that.menus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameButtonMenu, menuNumber, menus);
    }

    @Override
    public String toString() {
        return "ButtonMenu{" +
                "id=" + id +
                ", nameButtonMenu=" + nameButtonMenu +
                ", menuNumber=" + menuNumber +
                ", menus=" + menus +
                '}';
    }
}
