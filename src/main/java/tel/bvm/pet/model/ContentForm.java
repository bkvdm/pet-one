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

    @Column(name = "name_content")
    private String nameContent;

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

    public ContentForm(long id, String nameContent, String content, Form form, Set<ButtonMenu> buttonMenus) {
        this.id = id;
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

    public String getNameContent() {
        return nameContent;
    }

    public void setNameContent(String nameContent) {
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

    public void setButtonMenus(Set<ButtonMenu> buttons) {
        this.buttonMenus = buttons;
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
                ", buttons=" + buttonMenus +
                '}';
    }
}
