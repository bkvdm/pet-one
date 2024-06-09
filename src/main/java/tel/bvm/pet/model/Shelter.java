package tel.bvm.pet.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shelters")
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_shelter")
    private long id;

    @Column(name = "name_shelter")
    private String nameShelter;

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

    @OneToMany(mappedBy = "shelter")
    private List<Pet> pets;

    public Shelter(String nameShelter, String operationMode, String contact, String address, String drillingDirector, String security_contact, List<Pet> pets) {
        this.nameShelter = nameShelter;
        this.operationMode = operationMode;
        this.contact = contact;
        this.address = address;
        this.drillingDirector = drillingDirector;
        this.security_contact = security_contact;
        this.pets = pets;
    }

    public Shelter() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameShelter() {
        return nameShelter;
    }

    public void setNameShelter(String nameShelter) {
        this.nameShelter = nameShelter;
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

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return id == shelter.id && Objects.equals(nameShelter, shelter.nameShelter) && Objects.equals(operationMode, shelter.operationMode) && Objects.equals(contact, shelter.contact) && Objects.equals(address, shelter.address) && Objects.equals(drillingDirector, shelter.drillingDirector) && Objects.equals(security_contact, shelter.security_contact) && Objects.equals(pets, shelter.pets);
    }

    @Override
    public int hashCode() {
//        return Objects.hash(id, nameShelter, operationMode, contact, address, drillingDirector, security_contact, pets);
        return Objects.hash(id, nameShelter);
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "id=" + id +
                ", nameShelter='" + nameShelter + '\'' +
                ", operationMode='" + operationMode + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", drillingDirector='" + drillingDirector + '\'' +
                ", security_contact='" + security_contact + '\'' +
                ", pets=" + pets +
                '}';
    }
}