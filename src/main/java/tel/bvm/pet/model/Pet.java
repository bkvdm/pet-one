package tel.bvm.pet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet")
    private long id;

    //    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_shelter")
    private Shelter shelter;

    //    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    //    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_view_pet", nullable = false)
    private ViewPet viewPet;

    @Column(name = "name_pet")
    private String namePet;

    @Column(name = "busy_free", nullable = false)
    private Boolean busyFree;

    @Column(name = "date_take")
    private LocalDateTime dateTake;

    @Lob
    @Column(name = "picture_pet")
    private byte[] picturePet;

    @OneToMany(mappedBy = "pet")
    private List<DailyReport> dailyReports;

    public Pet(long id, Shelter shelter, Client client, ViewPet viewPet, String namePet, Boolean busyFree, LocalDateTime dateTake, byte[] picturePet, List<DailyReport> dailyReports) {
        this.id = id;
        this.shelter = shelter;
        this.client = client;
        this.viewPet = viewPet;
        this.namePet = namePet;
        this.busyFree = busyFree;
        this.dateTake = dateTake;
        this.picturePet = picturePet;
        this.dailyReports = dailyReports;
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

    public byte[] getPicturePet() {
        return picturePet;
    }

    public void setPicturePet(byte[] picturePet) {
        this.picturePet = picturePet;
    }

    public List<DailyReport> getDailyReports() {
        return dailyReports;
    }

    public void setDailyReports(List<DailyReport> dailyReports) {
        this.dailyReports = dailyReports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return id == pet.id && Objects.equals(shelter, pet.shelter) && Objects.equals(client, pet.client) && Objects.equals(viewPet, pet.viewPet) && Objects.equals(namePet, pet.namePet) && Objects.equals(busyFree, pet.busyFree) && Objects.equals(dateTake, pet.dateTake) && Objects.deepEquals(picturePet, pet.picturePet) && Objects.equals(dailyReports, pet.dailyReports);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shelter, client, viewPet, namePet, busyFree, dateTake, Arrays.hashCode(picturePet), dailyReports);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", shelter=" + shelter +
                ", client=" + client +
                ", viewPet=" + viewPet +
                ", namePet='" + namePet + '\'' +
                ", busyFree=" + busyFree +
                ", dateTake=" + dateTake +
                ", picturePet=" + Arrays.toString(picturePet) +
                ", dailyReports=" + dailyReports +
                '}';
    }
}