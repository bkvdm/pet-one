package tel.bvm.pet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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
}
