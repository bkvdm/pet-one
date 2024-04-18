package tel.bvm.pet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity(name = "daily_report")
public class DailyReports {
    @ManyToOne
    @JsonIgnore
    private Pets pet;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_daily_report")
    private long idDailyReport;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "well")
    private String well;

    @Column(name = "reaction")
    private String reaction;

    @Lob
    @Column(name = "foto_pet")
    private byte[] data;

    @Column(name = "is_check", nullable = false)
    private Boolean is_check;

    public DailyReports(Pets pet, long idDailyReport, LocalDateTime dateTime, String well, String reaction, byte[] data, Boolean is_check) {
        this.pet = pet;
        this.idDailyReport = idDailyReport;
        this.dateTime = dateTime;
        this.well = well;
        this.reaction = reaction;
        this.data = data;
        this.is_check = is_check;
    }

    public DailyReports() {

    }

    public Pets getPet() {
        return pet;
    }

    public void setPet(Pets pet) {
        this.pet = pet;
    }

    public long getIdDailyReport() {
        return idDailyReport;
    }

    public void setIdDailyReport(long idDailyReport) {
        this.idDailyReport = idDailyReport;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getWell() {
        return well;
    }

    public void setWell(String well) {
        this.well = well;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Boolean getIs_check() {
        return is_check;
    }

    public void setIs_check(Boolean is_check) {
        this.is_check = is_check;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyReports that = (DailyReports) o;
        return idDailyReport == that.idDailyReport && Objects.equals(pet, that.pet) && Objects.equals(dateTime, that.dateTime) && Objects.equals(well, that.well) && Objects.equals(reaction, that.reaction) && Objects.deepEquals(data, that.data) && Objects.equals(is_check, that.is_check);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pet, idDailyReport, dateTime, well, reaction, Arrays.hashCode(data), is_check);
    }

    @Override
    public String toString() {
        return "DailyReports{" +
                "pet=" + pet +
                ", idDailyReport=" + idDailyReport +
                ", dateTime=" + dateTime +
                ", well='" + well + '\'' +
                ", reaction='" + reaction + '\'' +
                ", data=" + Arrays.toString(data) +
                ", is_check=" + is_check +
                '}';
    }
}
