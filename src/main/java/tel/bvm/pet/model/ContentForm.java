package tel.bvm.pet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "content_forms")
public class ContentForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_content")
    private Long id;

    @Column(name = "content")
    private String content;

    @OneToOne(mappedBy = "contentForm")
    @JsonIgnore
    private Form form;

    public ContentForm(Long id, String content, Form form) {
        this.id = id;
        this.content = content;
        this.form = form;
    }

    public ContentForm() {
    }
}
