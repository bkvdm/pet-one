package tel.bvm.pet.receiver;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tel.bvm.pet.model.CommonFileTemplate;

import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

@Service
public class FileUtilityServiceImpl implements FileUtilityService {

    /**
     * Отправляет содержимое файла клиенту в ответ HTTP.
     * <p>
     * Этот метод наполняет HttpServletResponse байтами из CommonFileTemplate и использует
     * тип содержимого и длину содержимого из шаблона файла для правильной установки
     * заголовков ответа.
     *
     * @param response           HttpServletResponse, в который будет записано содержимое файла.
     * @param commonFileTemplate шаблон файла с данными и метаданными необходимыми для ответа.
     * @return HttpServletResponse заполненный данными файла.
     * @throws IOException      если возникает ошибка ввода-вывода во время записи в ответ.
     * @throws RuntimeException если возникает ошибка ввода-вывода при написании изображения в ответ.
     */
    @Override
    public HttpServletResponse viewFileResponse(HttpServletResponse response, CommonFileTemplate commonFileTemplate) throws IOException {
        byte[] imageData = commonFileTemplate.getDataForm();
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(commonFileTemplate.getMediaType());
            response.setContentLength(imageData.length);
            response.getOutputStream().write(imageData);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write image to response", e);
        }
        return response;
    }

    /**
     * Генерирует изображение определенного размера для хранения в базе данных.
     * <p>
     * Этот метод считывает изображение с диска, масштабирует его до заданной ширины,
     * сохраняя пропорции, и возвращает масштабированное изображение в виде массива байтов.
     *
     * @param filePath путь к файлу изображения на диске.
     * @param width    желаемая ширина конечного изображения.
     * @return массив байтов масштабированного изображения.
     * @throws IOException если возникают ошибки при чтении файла или при его масштабировании.
     */
    @Override
    public byte[] generateFileForDataBase(Path filePath, int width) throws IOException {

        try (InputStream inputStream = new FileInputStream(filePath.toFile())) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 16384);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BufferedImage image = ImageIO.read(bufferedInputStream);

            int height = image.getHeight() * width / image.getWidth();
            BufferedImage previewPicturePet = new BufferedImage(width, height, image.getType());
            Graphics2D drawable = previewPicturePet.createGraphics();
            drawable.drawImage(image, 0, 0, width, height, null);
            drawable.dispose();

            ImageIO.write(previewPicturePet, getExtensions(filePath.getFileName().toString()), byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }

    /**
     * Возвращает расширение файла из его полного имени.
     * <p>
     * Этот метод ищет последнюю точку в имени файла и возвращает всё, что следует за ней.
     * Это считается расширением файла. Если точка в имени файла отсутствует, результат будет пустой строкой.
     *
     * @param fileName Имя файла, для которого необходимо найти расширение.
     * @return Расширение файла или пустую строку, если расширение не найдено.
     * @throws NullPointerException если передан null в качестве имени файла.
     */
    @Override
    public String getExtensions(String fileName) {
        return Objects.requireNonNull(fileName.substring(fileName.lastIndexOf(".") + 1));
    }

    /**
     * Генерирует массив байтов из файла PDF для сохранения в базе данных.
     * <p>
     * Этот метод читает файл PDF и преобразует его содержимое в массив байтов.
     * Может использоваться для хранения содержимого файла PDF в БД в форме BLOB.
     *
     * @param filePath Путь к файлу PDF на диске.
     * @return Массив байтов, представляющий файл PDF.
     * @throws IOException если в процессе чтения файла произошла ошибка ввода-вывода.
     */
    @Override
    public byte[] generateFilePdfForDataBase(Path filePath) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath.toFile());
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[16384];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    /**
     * Сохраняет загруженный файл на диск.
     * <p>
     * Если родительский каталог пути назначения не существует, метод создаст его.
     * Если по указанному пути файл уже существует, он будет удален перед сохранением нового файла.
     * Затем метод сохраняет содержимое загруженного файла в новый файл по заданному пути.
     *
     * @param formFile Загружаемый файл, полученный от пользователя.
     * @param filePath Путь, по которому файл будет сохранен на диске.
     * @throws IOException если в процессе работы метода произошла ошибка ввода-вывода.
     */
    @Override
    public void saveFile(MultipartFile formFile, Path filePath) throws IOException {

        if (!Files.exists(filePath.getParent())) {
            Files.createDirectories(filePath.getParent());
        }

        Files.deleteIfExists(filePath);

        try (
                InputStream inputStream = formFile.getInputStream();
                OutputStream outputStream = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 16384);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 16384)
        ) {
            bufferedInputStream.transferTo(bufferedOutputStream);
        }
    }
}
