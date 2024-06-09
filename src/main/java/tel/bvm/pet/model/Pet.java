package tel.bvm.pet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet")
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_shelter", nullable = false)
    @JsonIgnore
    private Shelter shelter;

    @ManyToOne
    @JoinColumn(name = "id_client")
    @JsonIgnore
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_view_pet", nullable = false)
    @JsonIgnore
    private ViewPet viewPet;

    @Column(name = "name_pet")
    private String namePet;

    @Column(name = "busy_free", nullable = false)
    private Boolean busyFree;

    @Column(name = "date_take")
    private LocalDateTime dateTake;

    @OneToMany(mappedBy = "pet")
    @JsonIgnore
    private List<DailyReport> dailyReports;

    @OneToOne(mappedBy = "pet")
    @JsonIgnore
    private PicturePet picturePet;

    public Pet(Shelter shelter, Client client, ViewPet viewPet, String namePet, Boolean busyFree, LocalDateTime dateTake, List<DailyReport> dailyReports, PicturePet picturePet) {
        this.shelter = shelter;
        this.client = client;
        this.viewPet = viewPet;
        this.namePet = namePet;
        this.busyFree = busyFree;
        this.dateTake = dateTake;
        this.dailyReports = dailyReports;
        this.picturePet = picturePet;
    }

    public Pet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ViewPet getViewPet() {
        return viewPet;
    }

    public void setViewPet(ViewPet viewPet) {
        this.viewPet = viewPet;
    }

    public String getNamePet() {
        return namePet;
    }

    public void setNamePet(String namePet) {
        this.namePet = namePet;
    }

    public Boolean getBusyFree() {
        return busyFree;
    }

    public void setBusyFree(Boolean busyFree) {
        this.busyFree = busyFree;
    }

    public LocalDateTime getDateTake() {
        return dateTake;
    }

    public void setDateTake(LocalDateTime dateTake) {
        this.dateTake = dateTake;
    }

    public List<DailyReport> getDailyReports() {
        return dailyReports;
    }

    public void setDailyReports(List<DailyReport> dailyReports) {
        this.dailyReports = dailyReports;
    }

    public PicturePet getPicturePet() {
        return picturePet;
    }

    public void setPicturePet(PicturePet picturePet) {
        this.picturePet = picturePet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return id == pet.id && Objects.equals(shelter, pet.shelter) && Objects.equals(client, pet.client) && Objects.equals(viewPet, pet.viewPet) && Objects.equals(namePet, pet.namePet) && Objects.equals(busyFree, pet.busyFree) && Objects.equals(dateTake, pet.dateTake) && Objects.equals(dailyReports, pet.dailyReports) && Objects.equals(picturePet, pet.picturePet);
    }

    @Override
    public int hashCode() {
//        return Objects.hash(id, shelter, client, viewPet, namePet, busyFree, dateTake, dailyReports, picturePet);
        return Objects.hash(id, namePet);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", namePet='" + namePet + '\'' +
                ", busyFree=" + busyFree +
                ", dateTake=" + dateTake +
                '}';
    }
}