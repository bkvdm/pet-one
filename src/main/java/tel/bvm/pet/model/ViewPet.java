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

    public ViewPet(String nameViewPet, List<Pet> pets) {
        this.nameViewPet = nameViewPet;
        this.pets = pets;
    }

    public ViewPet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameViewPet() {
        return nameViewPet;
    }

    public void setNameViewPet(String nameViewPet) {
        this.nameViewPet = nameViewPet;
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
        ViewPet viewPet = (ViewPet) o;
        return id == viewPet.id && Objects.equals(nameViewPet, viewPet.nameViewPet) && Objects.equals(pets, viewPet.pets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameViewPet, pets);
    }

    @Override
    public String toString() {
        return "ViewPet{" +
                "id=" + id +
                ", nameViewPet='" + nameViewPet + '\'' +
                ", pets=" + pets +
                '}';
    }
}
