package tel.bvm.pet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "forms")
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_form")
    private Long id;

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
    @JoinColumn(name = "content_form_id")
    @JsonIgnore
    private ContentForm contentForm;

    public Form(Long id, String filePath, long fileSize, String mediaType, byte[] dataForm, ContentForm contentForm) {
        this.id = id;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mediaType = mediaType;
        this.dataForm = dataForm;
        this.contentForm = contentForm;
    }

    public Form() {
    }
}
