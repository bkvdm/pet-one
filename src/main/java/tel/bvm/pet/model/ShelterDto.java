package tel.bvm.pet.model;

import java.util.Objects;

public class ShelterDto {

    private long idShelter;

    private String nameShelter;

    private String operationMode;

    private String contact;

    private String address;

    private String drillingDirector;

    private String securityContact;

    private String baseStats;

    public ShelterDto() {
    }

    public ShelterDto(long idShelter, String nameShelter, String operationMode, String contact, String address, String drillingDirector, String securityContact, String baseStats) {
        this.idShelter = idShelter;
        this.nameShelter = nameShelter;
        this.operationMode = operationMode;
        this.contact = contact;
        this.address = address;
        this.drillingDirector = drillingDirector;
        this.securityContact = securityContact;
        this.baseStats = baseStats;
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

    public String getSecurityContact() {
        return securityContact;
    }

    public void setSecurityContact(String securityContact) {
        this.securityContact = securityContact;
    }

    public String getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(String baseStats) {
        this.baseStats = baseStats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShelterDto that = (ShelterDto) o;
        return idShelter == that.idShelter && Objects.equals(nameShelter, that.nameShelter) && Objects.equals(operationMode, that.operationMode) && Objects.equals(contact, that.contact) && Objects.equals(address, that.address) && Objects.equals(drillingDirector, that.drillingDirector) && Objects.equals(securityContact, that.securityContact) && Objects.equals(baseStats, that.baseStats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idShelter, nameShelter, operationMode, contact, address, drillingDirector, securityContact, baseStats);
    }
}
