package tel.bvm.pet.model;

public interface CommonFileTemplate {
    
    long getId();

    String getFilePath();

    long getFileSize();

    String getMediaType();

    byte[] getDataForm();
}
