package tel.bvm.pet.postConstruct;

import org.springframework.stereotype.Component;

import java.sql.SQLException;

import jakarta.annotation.PostConstruct;
import tel.bvm.pet.model.Pet;
import tel.bvm.pet.model.Shelter;
import tel.bvm.pet.model.ViewPet;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Map;
import java.sql.Timestamp;

@Component
public class TransferDatabase {

    private final DefaultDataPet defaultDataPet;
    private final DefaultDataShelter defaultDataShelter;

    private final DataSource dataSource;
    private final DefaultDataViewPet defaultDataViewPet;

    public TransferDatabase(DefaultDataPet defaultDataPet, DefaultDataShelter defaultDataShelter, DataSource dataSource, DefaultDataViewPet defaultDataViewPet) {
        this.defaultDataPet = defaultDataPet;
        this.defaultDataShelter = defaultDataShelter;
        this.dataSource = dataSource;
        this.defaultDataViewPet = defaultDataViewPet;
    }

    @PostConstruct
    void init() throws SQLException {
        insertDataShelter();
        insertDataViewPet();
        insertDataPet();
    }

    void insertDataShelter() throws SQLException {
        final Connection connection = dataSource.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO shelters(name_shelter, operation_mode, contact, address, drilling_director, security_contact) VALUES(?, ?, ?, ?, ?, ?)")) {

            for (Map.Entry<String, Shelter> entry : defaultDataShelter.shelterMap.entrySet()) {
                Shelter shelter = entry.getValue();
                String nameShelter = shelter.getNameShelter();
                String operationMode = shelter.getOperationMode();
                String contact = shelter.getContact();
                String address = shelter.getAddress();
                String drillingDirector = shelter.getDrillingDirector();
                String security_contact = shelter.getSecurity_contact();

                statement.setString(1, nameShelter);
                statement.setString(2, operationMode);
                statement.setString(3, contact);
                statement.setString(4, address);
                statement.setString(5, drillingDirector);
                statement.setString(6, security_contact);
                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }

    void insertDataViewPet() throws SQLException {
        final Connection connection = dataSource.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO view_pets(name_view_pet) VALUES(?)")) {

            for (Map.Entry<ViewPet.NameViewPet, ViewPet> entry : defaultDataViewPet.viewPetMap.entrySet()) {
                ViewPet viewPet = entry.getValue();
                ViewPet.NameViewPet nameViewPet = viewPet.getNameViewPet();

                statement.setString(1, nameViewPet.toString());
                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connection.close();
        }
    }

    void insertDataPet() throws SQLException {

        try (Connection connection = dataSource.getConnection(); PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO pets(id_shelter, id_view_pet, name_pet, busy_free, date_take) VALUES(?, ?, ?, ?, ?)")) {

            for (Pet pet : defaultDataPet.petList) {
                long shelter = pet.getShelter().getId();
                long viewPet = pet.getViewPet().getId();
                String namePet = pet.getNamePet();
                Boolean busyFree = pet.getBusyFree();
                LocalDateTime dateTake = pet.getDateTake();

                statement.setLong(1, shelter);
                statement.setLong(2, viewPet);

                statement.setString(3, namePet);
                statement.setBoolean(4, busyFree);
                if (dateTake != null) {
                    statement.setTimestamp(5, Timestamp.valueOf(dateTake));
                } else {
                    statement.setNull(5, java.sql.Types.TIMESTAMP);
                }
                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
