package tel.bvm.pet.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "volunteers")
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_volunteer")
    private long id;

    @Column(name = "chat_id", nullable = false)
    private long chatId;

    @Column(name = "name_volunteer")
    private String nameVolunteer;

    @Column(name = "contact")
    private String contact;

    @Column(name = "last_assigned")
    private LocalDateTime lastAssigned;

    public Volunteer(long chatId, String nameVolunteer, String contact, LocalDateTime lastAssigned) {
        this.chatId = chatId;
        this.nameVolunteer = nameVolunteer;
        this.contact = contact;
        this.lastAssigned = lastAssigned;
    }

    public Volunteer() {
    }

    public LocalDateTime getLastAssigned() {
        return lastAssigned;
    }

    public void setLastAssigned(LocalDateTime lastAssigned) {
        this.lastAssigned = lastAssigned;
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

    public String getNameVolunteer() {
        return nameVolunteer;
    }

    public void setNameVolunteer(String nameVolunteer) {
        this.nameVolunteer = nameVolunteer;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Volunteer volunteer = (Volunteer) object;
        return id == volunteer.id && chatId == volunteer.chatId && Objects.equals(nameVolunteer, volunteer.nameVolunteer) && Objects.equals(contact, volunteer.contact) && Objects.equals(lastAssigned, volunteer.lastAssigned);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, nameVolunteer, contact, lastAssigned);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", nameVolunteer='" + nameVolunteer + '\'' +
                ", contact='" + contact + '\'' +
                ", lastAssigned=" + lastAssigned +
                '}';
    }
}
