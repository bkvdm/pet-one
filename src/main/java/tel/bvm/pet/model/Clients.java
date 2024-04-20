package tel.bvm.pet.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "clients")
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private long id;

    @Column(name = "chat_id", nullable = false)
    private long chatId;

    @Column(name = "name")
    private String name;

    @Column(name = "contact")
    private String contact;

    public Clients(long id, long chatId, String name, String contact) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.contact = contact;
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
        Clients clients = (Clients) o;
        return id == clients.id && chatId == clients.chatId && Objects.equals(name, clients.name) && Objects.equals(contact, clients.contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, contact);
    }

    @Override
    public String toString() {
        return "Clients{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
