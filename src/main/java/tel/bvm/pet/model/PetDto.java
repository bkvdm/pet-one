package tel.bvm.pet.model;

import java.util.Objects;

public class PetDto {

    private long idPet;

    private String namePet;

    private String picturePetEncode;

    private String viewPet;

    private long idShelter;

    private String nameShelter;

    public PetDto() {
    }

    public PetDto(long idPet, String namePet, String picturePetEncode, String viewPet, long idShelter, String nameShelter) {
        this.idPet = idPet;
        this.namePet = namePet;
        this.picturePetEncode = picturePetEncode;
        this.viewPet = viewPet;
        this.idShelter = idShelter;
        this.nameShelter = nameShelter;
    }

    public long getIdPet() {
        return idPet;
    }

    public void setIdPet(long idPet) {
        this.idPet = idPet;
    }

    public String getNamePet() {
        return namePet;
    }

    public void setNamePet(String namePet) {
        this.namePet = namePet;
    }

    public String getPicturePetEncode() {
        return picturePetEncode;
    }

    public void setPicturePetEncode(String picturePetEncode) {
        this.picturePetEncode = picturePetEncode;
    }

    public String getViewPet() {
        return viewPet;
    }

    public void setViewPet(String viewPet) {
        this.viewPet = viewPet;
    }

    public long getIdShelter() {
        return idShelter;
    }

    public void setIdShelter(long idShelter) {
        this.idShelter = idShelter;
    }

    public String getNameShelter() {
        return nameShelter;
    }

    public void setNameShelter(String nameShelter) {
        this.nameShelter = nameShelter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetDto petDto = (PetDto) o;
        return idPet == petDto.idPet && idShelter == petDto.idShelter && Objects.equals(namePet, petDto.namePet) && Objects.equals(picturePetEncode, petDto.picturePetEncode) && Objects.equals(viewPet, petDto.viewPet) && Objects.equals(nameShelter, petDto.nameShelter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPet, namePet, picturePetEncode, viewPet, idShelter, nameShelter);
    }
}
