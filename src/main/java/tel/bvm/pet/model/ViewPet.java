package tel.bvm.pet.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "view_pets")
public class ViewPet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_view_pet")
    private long id;

    @Column(name = "name_view_pet")
    private String nameViewPet;

    @OneToMany(mappedBy = "viewPet")
    private List<Pet> pets;

    public ViewPet(long id, List<Pet> pets, String nameViewPet) {
        this.id = id;
        this.pets = pets;
        this.nameViewPet = nameViewPet;
    }

    public ViewPet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public String getNameViewPet() {
        return nameViewPet;
    }

    public void setNameViewPet(String nameViewPet) {
        this.nameViewPet = nameViewPet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewPet viewPet = (ViewPet) o;
        return id == viewPet.id && Objects.equals(pets, viewPet.pets) && Objects.equals(nameViewPet, viewPet.nameViewPet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pets, nameViewPet);
    }

    @Override
    public String toString() {
        return "ViewPet{" +
                "id=" + id +
                ", pets=" + pets +
                ", nameViewPet='" + nameViewPet + '\'' +
                '}';
    }
}
