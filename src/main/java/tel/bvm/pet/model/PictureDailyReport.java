package tel.bvm.pet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "picture_daily_reports")
public class PictureDailyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_picture_daily_report")
    private long id;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "media_type")
    private String mediaType;

    @Lob
    @Column(name = "data_form")
    private byte[] dataForm;

    @OneToOne
    @JoinColumn(name = "id_daily_report")
    @JsonIgnore
    private DailyReport dailyReport;

    public PictureDailyReport(String filePath, long fileSize, String mediaType, byte[] dataForm, DailyReport dailyReport) {
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.dataForm = dataForm;
        this.dailyReport = dailyReport;
    }

    public PictureDailyReport() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getDataForm() {
        return dataForm;
    }

    public void setDataForm(byte[] dataForm) {
        this.dataForm = dataForm;
    }

    public DailyReport getDailyReport() {
        return dailyReport;
    }

    public void setDailyReport(DailyReport dailyReport) {
        this.dailyReport = dailyReport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureDailyReport that = (PictureDailyReport) o;
        return id == that.id && fileSize == that.fileSize && Objects.equals(filePath, that.filePath) && Objects.equals(mediaType, that.mediaType) && Objects.deepEquals(dataForm, that.dataForm) && Objects.equals(dailyReport, that.dailyReport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filePath, fileSize, mediaType, Arrays.hashCode(dataForm), dailyReport);
    }

    @Override
    public String toString() {
        return "PictureDailyReport{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", dataForm=" + Arrays.toString(dataForm) +
                ", dailyReport=" + dailyReport +
                '}';
    }
}
