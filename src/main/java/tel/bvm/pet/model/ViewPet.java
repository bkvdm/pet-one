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

    public enum NameViewPet {
        CAT("Кошка"),
        DOG("Собака");
        private String displayName;

        NameViewPet(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
//    public enum NameViewPet {CAT, DOG}

    @Enumerated(EnumType.STRING)
    @Column(name = "name_view_pet", nullable = false)
    private NameViewPet nameViewPet;

    @OneToMany(mappedBy = "viewPet")
    private List<Pet> pets;

    public ViewPet(NameViewPet nameViewPet, List<Pet> pets) {
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

    public NameViewPet getNameViewPet() {
        return nameViewPet;
    }

    public void setNameViewPet(NameViewPet nameViewPet) {
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
