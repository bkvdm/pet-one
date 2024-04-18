package tel.bvm.pet.model;

import jakarta.persistence.*;

import java.util.List;
@Entity(name = "shelter")
public class Shelters {
    @OneToMany(mappedBy = "shelter")
    private List<Pets> animal;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_shelter")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "operation_mode")
    private String operationMode;
    @Column(name = "contact")
    private String contact;
    @Column(name = "address")
    private String address;
    @Column(name = "drilling_director")
    private String drillingDirector;
    @Column(name = "security_contact")
    private String security_contact;

    public Shelters(List<Pets> animal, long id, String name, String operationMode, String contact, String address, String drillingDirector, String security_contact) {
        this.animal = animal;
        this.id = id;
        this.name = name;
        this.operationMode = operationMode;
        this.contact = contact;
        this.address = address;
        this.drillingDirector = drillingDirector;
        this.security_contact = security_contact;
    }

    public Shelters() {

    }

    public List<Pets> getAnimal() {
        return animal;
    }

    public void setAnimal(List<Pets> animal) {
        this.animal = animal;
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

    public String getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDrillingDirector() {
        return drillingDirector;
    }

    public void setDrillingDirector(String drillingDirector) {
        this.drillingDirector = drillingDirector;
    }

    public String getSecurity_contact() {
        return security_contact;
    }

    public void setSecurity_contact(String security_contact) {
        this.security_contact = security_contact;
    }
}