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

    @Column(name = "name_client")
    private String nameClient;

    @Column(name = "contact")
    private String contact;

    @OneToMany(mappedBy = "client")
    private List<Pet> pets;

    public Client(long chatId, String nameClient, String contact, List<Pet> pets) {
        this.chatId = chatId;
        this.nameClient = nameClient;
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

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
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
        return id == client.id && chatId == client.chatId && Objects.equals(nameClient, client.nameClient) && Objects.equals(contact, client.contact) && Objects.equals(pets, client.pets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, nameClient, contact, pets);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", nameClient='" + nameClient + '\'' +
                ", contact='" + contact + '\'' +
                ", pets=" + pets +
                '}';
    }
}
