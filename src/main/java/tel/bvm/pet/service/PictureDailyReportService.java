package tel.bvm.pet.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import tel.bvm.pet.model.PictureDailyReport;

import java.io.IOException;

public interface PictureDailyReportService {
    void uploadPictureDailyReport(long idDailyReport, MultipartFile pictureDailyReportFile) throws IOException;

    PictureDailyReport findPictureByDailyReportId(long idDailyReport);

    HttpServletResponse response(HttpServletResponse response, long idDailyReport) throws IOException;
}
