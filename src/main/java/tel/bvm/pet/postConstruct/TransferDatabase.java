package tel.bvm.pet.postConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

import jakarta.annotation.PostConstruct;
import tel.bvm.pet.model.*;
import tel.bvm.pet.postConstruct.service.DataContentMenuService;
import tel.bvm.pet.postConstruct.service.DefaultDataDemo.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Map;
import java.sql.Timestamp;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

@Component
public class TransferDatabase {

    private final DataContentMenuService dataContentMenuService;
    private final DefaultDataPet defaultDataPet;
    private final DefaultDataShelter defaultDataShelter;
    private final DefaultDataViewPet defaultDataViewPet;
    private final DataSource dataSource;
    private final DefaultDataVolunteer defaultDataVolunteer;
    private final DefaultDataClient defaultDataClient;

    @Autowired
    public TransferDatabase(DataContentMenuService dataContentMenuService, DefaultDataVolunteer defaultDataVolunteer, DefaultDataClient defaultDataClient, DefaultDataPet defaultDataPet, DefaultDataShelter defaultDataShelter, DataSource dataSource, DefaultDataViewPet defaultDataViewPet) {

        this.dataContentMenuService = dataContentMenuService;
        this.defaultDataPet = defaultDataPet;
        this.defaultDataShelter = defaultDataShelter;
        this.defaultDataViewPet = defaultDataViewPet;
        this.dataSource = dataSource;
        this.defaultDataVolunteer = defaultDataVolunteer;
        this.defaultDataClient = defaultDataClient;
    }

    @PostConstruct
    void init() throws SQLException {

        // Методы закомментированы после отработки и загрузки данных в базу данных
//
//        setupDataVolunteer();
//        setupDataClient();
//        setupMenu();
//        setupContent();
//        linkButtonMenuAndContentForm();
//        insertDataShelter();
//        insertDataViewPet();
//        insertDataPet();
    }

    void setupDataVolunteer() throws SQLException {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO volunteers(chat_id, name_volunteer, contact) VALUES(?, ?, ?)")) {

            for (Volunteer volunteer : defaultDataVolunteer.volunteers) {
                long chatId = volunteer.getChatId();
                String name = volunteer.getNameVolunteer();
                String contact = volunteer.getContact();
                statement.setLong(1, chatId);
                statement.setString(2, name);
                statement.setString(3, contact);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    void setupDataClient() throws SQLException {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO clients(chat_id, name_client, contact) VALUES(?, ?, ?)")) {

            for (Client client : defaultDataClient.clients) {
                long chatId = client.getChatId();
                String nameClient = client.getNameClient();
                String contact = client.getContact();
                statement.setLong(1, chatId);
                statement.setString(2, nameClient);
                statement.setString(3, contact);
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    void setupMenu() throws SQLException {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO button_menus(id_button_menu, menu_name, menu_header) VALUES(?, ?, ?)")) {

            for (Object entry : dataContentMenuService.contentMenuSet()) {
                if (entry instanceof ButtonMenu) {

                    ButtonMenu buttonMenu = (ButtonMenu) entry;

                    long idButtonMenu = buttonMenu.getId();
                    ButtonMenu.NameButtonMenu nameButtonMenu = buttonMenu.getNameButtonMenu();
                    String menuHeader = buttonMenu.getMenuHeader();
                    statement.setLong(1, idButtonMenu);
                    statement.setString(2, nameButtonMenu.toString());
                    statement.setString(3, menuHeader);
                    statement.addBatch();
                }

                statement.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void setupContent() throws SQLException {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO content_forms(id_content, name_content, content) VALUES (?, ?, ?)")) {

            for (Object entry : dataContentMenuService.contentMenuSet()) {
                if (entry instanceof ContentForm) {

                    ContentForm contentForm = (ContentForm) entry;

                    long idContentForm = contentForm.getId();
                    ContentForm.NameContentForm nameContentForm = contentForm.getNameContent();
                    ContentForm.NameContentForm nameContent = contentForm.getNameContent();
                    String content = contentForm.getContent();
                    statement.setLong(1, idContentForm);
                    statement.setString(2, nameContent.toString());
                    statement.setString(3, content);
                    statement.addBatch();
                }
            }

            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    void linkButtonMenuAndContentForm() throws SQLException {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO content_menu(id_content, id_button_menu) VALUES (?, ?)")) {

            for (Map.Entry<Long, Set<Long>> entry : dataContentMenuService.contentMenuIds().entrySet()) {
                Long buttonMenuId = entry.getKey();
                for (Long contentFormId : entry.getValue()) {
                    statement.setLong(1, contentFormId);
                    statement.setLong(2, buttonMenuId);
                    statement.addBatch();
                }
            }

            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                "INSERT INTO view_pets(id_view_pet, name_view_pet) VALUES(?, ?)")) {

            for (Map.Entry<ViewPet.NameViewPet, ViewPet> entry : defaultDataViewPet.viewPetMap.entrySet()) {
                ViewPet viewPet = entry.getValue();
                long id = viewPet.getId();
                ViewPet.NameViewPet nameViewPet = viewPet.getNameViewPet();

                statement.setLong(1, id);
                statement.setString(2, nameViewPet.toString());
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

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
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
