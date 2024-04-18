package tel.bvm.pet.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity(name = "shelter")
public class Shelters {
    @OneToMany(mappedBy = "shelter")
    private List<Pets> pet;
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

    public Shelters(List<Pets> pet, long id, String name, String operationMode, String contact, String address, String drillingDirector, String security_contact) {
        this.pet = pet;
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

    public List<Pets> getPet() {
        return pet;
    }

    public void setPet(List<Pets> pet) {
        this.pet = pet;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelters shelters = (Shelters) o;
        return id == shelters.id && Objects.equals(pet, shelters.pet) && Objects.equals(name, shelters.name) && Objects.equals(operationMode, shelters.operationMode) && Objects.equals(contact, shelters.contact) && Objects.equals(address, shelters.address) && Objects.equals(drillingDirector, shelters.drillingDirector) && Objects.equals(security_contact, shelters.security_contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pet, id, name, operationMode, contact, address, drillingDirector, security_contact);
    }

    @Override
    public String toString() {
        return "Shelters{" +
                "pet=" + pet +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", operationMode='" + operationMode + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", drillingDirector='" + drillingDirector + '\'' +
                ", security_contact='" + security_contact + '\'' +
                '}';
    }
}