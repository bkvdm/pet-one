package tel.bvm.pet.receiver;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileTypeMatchingServiceImpl implements FileTypeMatchingService {

    /**
     * Проверяет, является ли предоставленный файл изображением.
     * <p>
     * Этот метод пытается открыть поток ввода из файла и считать его с помощью ImageIO.
     * Если файл успешно считывается как изображение, метод возвращает true.
     * В противном случае, если файл не может быть считан как изображение, возвращается false.
     *
     * @param file файл, который необходимо проверить.
     * @return true, если файл успешно считан как изображение, иначе false.
     * @throws RuntimeException если возникает IOException при чтении файла.
     */
    @Override
    public boolean isValidImage(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            try {
                ImageIO.read(is).toString();
                return true;
            } catch (Exception e) {
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file (not image format)", e);
        }
    }

    /**
     * Проверяет, является ли предоставленный файл документом PDF.
     * <p>
     * Метод пытается открыть файл через BufferedInputStream и загрузить его как PDF с помощью библиотеки PDFBox.
     * Если документ успешно загружается и затем закрывается, файл считается валидным PDF и метод возвращает true.
     * Если в процессе чтения или загрузки возникает IOException, предполагается, что файл не является допустимым PDF, и возвращается false.
     *
     * @param file файл, который необходимо проверить.
     * @return true, если файл является допустимым PDF, иначе false.
     * @throws RuntimeException если в ходе проверки происходят другие исключения.
     */
    @Override
    public boolean isValidPdf(MultipartFile file) {
        try {
            try (InputStream is = new BufferedInputStream(file.getInputStream())) {
                PDDocument pdDocument = PDDocument.load(is);
                pdDocument.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading file (not pdf format)", e);
        }
    }
}
