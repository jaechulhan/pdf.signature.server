package net.prolancer.signature.common.Utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.DatatypeConverter;
import java.io.*;

@Slf4j
public class PdfStampUtils {

    public static final int DEFAULT_IMAGE_HEIGHT = 30;
    public static final int DEFAULT_IMAGE_WIDTH = 100;
    public static final int DEFAULT_ABSOLUTE_X = 390;
    public static final int DEFAULT_ABSOLUTE_Y = 50;
    public static final int DEFAULT_TEXT_X = 450;
    public static final int DEFAULT_TEXT_Y = 45;

    /**
     * Generate PdfStamp
     * @param is
     * @param base64Image
     * @return
     */
    public static byte[] generatePdfStamp(InputStream is, String base64Image, String text) {
        // Verify mandatory fields
        if (is == null || base64Image == null) {
            return null;
        }

        // PDF Stamp Process
        try {
            PdfReader reader = new PdfReader(is);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfStamper stamper = new PdfStamper(reader, baos);
            PdfContentByte content = stamper.getOverContent(1);

            int idx = base64Image.indexOf(",");
            base64Image = base64Image.substring(idx + 1);
            byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);

            Image image = Image.getInstance(imageBytes);

            // Scale
            image.scaleAbsoluteHeight(DEFAULT_IMAGE_HEIGHT);
            image.scaleAbsoluteWidth(DEFAULT_IMAGE_WIDTH);

            // Position
            image.setAbsolutePosition(DEFAULT_ABSOLUTE_X, DEFAULT_ABSOLUTE_Y);
            content.addImage(image);

            BaseFont bf = BaseFont.createFont();

            content.beginText();
            content.setFontAndSize(bf, 8);
            content.setColorFill(BaseColor.BLACK);
            content.setTextMatrix(70, 200);
            content.showTextAligned(Element.ALIGN_CENTER, text, DEFAULT_TEXT_X, DEFAULT_TEXT_Y, 0);

            content.endText();

            stamper.close();

            byte[] pdfBytes = baos.toByteArray();

            return pdfBytes;
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return null;
    }

    /**
     * Write Pdf File
     * @param pathname
     * @param pdfBytes
     */
    public static void writePdfFile(String pathname, byte[] pdfBytes) {
        FileOutputStream fos = null;

        try {
            File file = new File(pathname);
            fos = new FileOutputStream(file);
            fos.write(pdfBytes);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {}
            }
        }
    }
}
