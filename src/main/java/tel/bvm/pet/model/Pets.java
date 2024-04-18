package tel.bvm.pet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity(name = "pets")
public class Pets {
    @JsonIgnore
    @ManyToOne
    private Shelters shelter;
    @OneToMany(mappedBy = "pet")
    private List<DailyReports> dailyReport;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "busy_free", nullable = false)
    private Boolean busyFree;
    @Column(name = "date_take")
    private LocalDateTime dateTake;

    public Pets(Shelters shelter, List<DailyReports> dailyReport, long id, String name, String type, Boolean busyFree, LocalDateTime dateTake) {
        this.shelter = shelter;
        this.dailyReport = dailyReport;
        this.id = id;
        this.name = name;
        this.type = type;
        this.busyFree = busyFree;
        this.dateTake = dateTake;
    }

    public Pets() {

    }

    public Shelters getShelter() {
        return shelter;
    }

    public void setShelter(Shelters shelter) {
        this.shelter = shelter;
    }

    public List<DailyReports> getDailyReport() {
        return dailyReport;
    }

    public void setDailyReport(List<DailyReports> dailyReport) {
        this.dailyReport = dailyReport;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pets pets = (Pets) o;
        return id == pets.id && Objects.equals(shelter, pets.shelter) && Objects.equals(dailyReport, pets.dailyReport) && Objects.equals(name, pets.name) && Objects.equals(type, pets.type) && Objects.equals(busyFree, pets.busyFree) && Objects.equals(dateTake, pets.dateTake);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shelter, dailyReport, id, name, type, busyFree, dateTake);
    }

    @Override
    public String toString() {
        return "Pets{" +
                "shelter=" + shelter +
                ", dailyReport=" + dailyReport +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", busyFree=" + busyFree +
                ", dateTake=" + dateTake +
                '}';
    }
}
