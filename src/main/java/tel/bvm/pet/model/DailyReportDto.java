package tel.bvm.pet.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class DailyReportDto {

    private long petId;

    private LocalDateTime dateTime;

    private String well;

    private String reaction;

    private Boolean isCheck;

    private long pictureDailyReportId;

    private String pictureDailyReportEncode;

    public DailyReportDto() {
    }

    public DailyReportDto(long petId, LocalDateTime dateTime, String well, String reaction, Boolean isCheck, long pictureDailyReportId, String pictureDailyReportEncode) {
        this.petId = petId;
        this.dateTime = dateTime;
        this.well = well;
        this.reaction = reaction;
        this.isCheck = isCheck;
        this.pictureDailyReportId = pictureDailyReportId;
        this.pictureDailyReportEncode = pictureDailyReportEncode;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getWell() {
        return well;
    }

    public void setWell(String well) {
        this.well = well;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }

    public long getPictureDailyReportId() {
        return pictureDailyReportId;
    }

    public void setPictureDailyReportId(long pictureDailyReportId) {
        this.pictureDailyReportId = pictureDailyReportId;
    }

    public String getPictureDailyReportEncode() {
        return pictureDailyReportEncode;
    }

    public void setPictureDailyReportEncode(String pictureDailyReportEncode) {
        this.pictureDailyReportEncode = pictureDailyReportEncode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyReportDto that = (DailyReportDto) o;
        return petId == that.petId && pictureDailyReportId == that.pictureDailyReportId && Objects.equals(dateTime, that.dateTime) && Objects.equals(well, that.well) && Objects.equals(reaction, that.reaction) && Objects.equals(isCheck, that.isCheck) && Objects.equals(pictureDailyReportEncode, that.pictureDailyReportEncode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(petId, dateTime, well, reaction, isCheck, pictureDailyReportId, pictureDailyReportEncode);
    }
}
