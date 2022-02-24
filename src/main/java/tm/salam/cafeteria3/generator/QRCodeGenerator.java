package tm.salam.cafeteria3.generator;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

@Component
public class QRCodeGenerator {

    private final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode_employees";
    private final int width = 200;
    private final int height = 200;

    public String GenerateQRCode(String text)
            throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(QR_CODE_IMAGE_PATH + "/" + text + ".png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        return text + ".png";
    }

    public String decodeQRCode(MultipartFile multipartFile) throws IOException {

        File convFile = new File(StringUtils.cleanPath(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();


        BufferedImage bufferedImage = ImageIO.read(convFile);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {

            Result result = new MultiFormatReader().decode(bitmap);
            convFile.delete();

            return result.getText();

        } catch (NotFoundException e) {

            convFile.delete();

            return "-1";
        }
    }

    @Bean
    HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {

        return new BufferedImageHttpMessageConverter();
    }

}