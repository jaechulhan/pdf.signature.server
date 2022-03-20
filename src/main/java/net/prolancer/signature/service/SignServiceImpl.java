package net.prolancer.signature.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.extern.slf4j.Slf4j;
import net.prolancer.signature.common.Utils.PdfStampUtils;
import net.prolancer.signature.entity.Signature;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SignServiceImpl implements SignService {

    @Autowired
    private VelocityEngine velocityEngine;

    /**
     * Create eSignature
     * @param signature
     * @return
     */
    @Override
    public boolean createSign(Signature signature) {
        boolean result = false;
        InputStream is = null;

        try {
            this.createPdf();
            is = new FileInputStream("/Users/jaechulhan/Downloads/dummy.pdf");

            byte[] pdfBytes = PdfStampUtils.generatePdfStamp(is, signature.getSignImage(), signature.getSignDate());

            if (pdfBytes != null) {
                PdfStampUtils.writePdfFile("/Users/jaechulhan/Downloads/dummy_signed.pdf", pdfBytes);
                result = true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {}
            }
        }

        return result;
    }

    /**
     * Create PDF file
     */
    private void createPdf() {
        PdfWriter pdfWriter = null;

        // create a new document
        Document document = new Document();

        try {
            String html = this.generateHTML();

            document = new Document();
            // document header attributes
            document.addAuthor("Jaechul Han");
            document.addCreationDate();
            document.addProducer();
            document.addCreator("net.prolancer");
            document.addTitle("HTML to PDF using itext");
            document.setPageSize(PageSize.LETTER);

            OutputStream file = new FileOutputStream(new File("/Users/jaechulhan/Downloads/dummy.pdf"));
            pdfWriter = PdfWriter.getInstance(document, file);

            // open document
            document.open();

            XMLWorkerHelper xmlWorkerHelper = XMLWorkerHelper.getInstance();
            xmlWorkerHelper.getDefaultCssResolver(true);
            xmlWorkerHelper.parseXHtml(pdfWriter, document, new StringReader(html));

            log.info("PDF generated successfully");
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            // close the document
            if (document != null) {
                document.close();
            }
            // close the writer
            if (pdfWriter != null) {
                pdfWriter.close();
            }
        }
    }

    /**
     * Generate HTML
     * @return
     * @throws Exception
     */
    private String generateHTML() {
        Map<String, Object> data = new HashMap<>();

        String title = "Apple Product List";

        List<Map<String, String>> productList = new ArrayList<>();

        Map<String, String> map = new HashMap<>();
        map.put("name", "Mac Pro M1 256MB");
        map.put("price", "$1299.00");
        productList.add(map);

        map = new HashMap<>();
        map.put("name", "iPhone 13");
        map.put("price", "$999.00");
        productList.add(map);

        map = new HashMap<>();
        map.put("name", "iPad");
        map.put("price", "$799.00");
        productList.add(map);

        data.put("title", title);
        data.put("productList", productList);

        // add that list to a VelocityContext
        VelocityContext context = new VelocityContext();
        context.put("data", data);

        // get the Template
        Template template = velocityEngine.getTemplate("templates/product_list.vm");

        // render the template into a Writer, here a StringWriter
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }

}
