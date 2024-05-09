package tel.bvm.pet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.Arrays;
import java.util.Objects;

//import org.hibernate.annotations.Type;

@Entity
@Table(name = "forms")
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_form")
    private long id;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private long fileSize;

    @Column(name = "media_type")
    private String mediaType;

    @Lob
//    @Type(type = "org.hibernate.type.BinaryType")
//    @Type()
    @Column(name = "data_form")
    private byte[] dataForm;

    @OneToOne
    @JoinColumn(name = "id_content")
    @JsonIgnore
    private ContentForm contentForm;

    public Form(String filePath, long fileSize, String mediaType, byte[] dataForm, ContentForm contentForm) {
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.dataForm = dataForm;
        this.contentForm = contentForm;
    }

    public Form() {
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

    public ContentForm getContentForm() {
        return contentForm;
    }

    public void setContentForm(ContentForm contentForm) {
        this.contentForm = contentForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Form form = (Form) o;
        return id == form.id && fileSize == form.fileSize && Objects.equals(filePath, form.filePath) && Objects.equals(mediaType, form.mediaType) && Objects.deepEquals(dataForm, form.dataForm) && Objects.equals(contentForm, form.contentForm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filePath, fileSize, mediaType, Arrays.hashCode(dataForm), contentForm);
    }

    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", dataForm=" + Arrays.toString(dataForm) +
                ", contentForm=" + contentForm +
                '}';
    }
}
