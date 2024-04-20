package tel.bvm.pet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "daily_reports")
public class DailyReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_daily_report")
    private long idDailyReport;

    @ManyToOne
    @JoinColumn(name = "id_pet", nullable = false)
//    @JsonIgnore
    private Pet pet;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "well")
    private String well;

    @Column(name = "reaction")
    private String reaction;

    @Lob
    @Column(name = "picture_daily_report")
    private byte[] pictureDailyReport;

    @Column(name = "is_check", nullable = false)
    private Boolean isCheck;

    public DailyReport(long idDailyReport, Pet pet, LocalDateTime dateTime, String well, String reaction, byte[] pictureDailyReport, Boolean isCheck) {
        this.idDailyReport = idDailyReport;
        this.pet = pet;
        this.dateTime = dateTime;
        this.well = well;
        this.reaction = reaction;
        this.pictureDailyReport = pictureDailyReport;
        this.isCheck = isCheck;
    }

    public DailyReport() {
    }

    public long getIdDailyReport() {
        return idDailyReport;
    }

    public void setIdDailyReport(long idDailyReport) {
        this.idDailyReport = idDailyReport;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
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

    public byte[] getPictureDailyReport() {
        return pictureDailyReport;
    }

    public void setPictureDailyReport(byte[] pictureDailyReport) {
        this.pictureDailyReport = pictureDailyReport;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyReport that = (DailyReport) o;
        return idDailyReport == that.idDailyReport && Objects.equals(pet, that.pet) && Objects.equals(dateTime, that.dateTime) && Objects.equals(well, that.well) && Objects.equals(reaction, that.reaction) && Objects.deepEquals(pictureDailyReport, that.pictureDailyReport) && Objects.equals(isCheck, that.isCheck);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDailyReport, pet, dateTime, well, reaction, Arrays.hashCode(pictureDailyReport), isCheck);
    }

    @Override
    public String toString() {
        return "DailyReport{" +
                "idDailyReport=" + idDailyReport +
                ", pet=" + pet +
                ", dateTime=" + dateTime +
                ", well='" + well + '\'' +
                ", reaction='" + reaction + '\'' +
                ", pictureDailyReport=" + Arrays.toString(pictureDailyReport) +
                ", isCheck=" + isCheck +
                '}';
    }
}
