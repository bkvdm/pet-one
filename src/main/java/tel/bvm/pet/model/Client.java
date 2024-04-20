package tel.bvm.pet.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clients")
public class Client {

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

    @OneToMany(mappedBy = "client")
    private List<Pet> pets;

    public Client(long id, long chatId, String name, String contact, List<Pet> pets) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.contact = contact;
        this.pets = pets;
    }

    public Client() {
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

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && chatId == client.chatId && Objects.equals(name, client.name) && Objects.equals(contact, client.contact) && Objects.equals(pets, client.pets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, contact, pets);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", pets=" + pets +
                '}';
    }
}
