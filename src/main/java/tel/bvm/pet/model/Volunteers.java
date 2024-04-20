package tel.bvm.pet.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity(name = "volunteers")
public class Volunteers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_volunteer")
    private long id;

    @Column(name = "chat_id", nullable = false)
    private long chatId;

    @Column(name = "name")
    private String name;

    @Column(name = "contact")
    private String contact;

    public Volunteers(long id, long chatId, String name, String contact) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.contact = contact;
    }

    public Volunteers() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteers that = (Volunteers) o;
        return id == that.id && chatId == that.chatId && Objects.equals(name, that.name) && Objects.equals(contact, that.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, contact);
    }

    @Override
    public String toString() {
        return "Volunteers{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
