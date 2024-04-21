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

    @Column(name = "menu_number")
    private byte menuNumber;

    @ManyToMany(mappedBy = "buttonMenus")
    private Set<ContentForm> menus = new HashSet<>();

    public ButtonMenu(byte menuNumber, Set<ContentForm> menus) {
        this.menuNumber = menuNumber;
        this.menus = menus;
    }

    public ButtonMenu() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return id == that.id && menuNumber == that.menuNumber && Objects.equals(menus, that.menus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, menuNumber, menus);
    }

    @Override
    public String toString() {
        return "ButtonMenu{" +
                "id=" + id +
                ", menuNumber=" + menuNumber +
                ", menus=" + menus +
                '}';
    }
}
