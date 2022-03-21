package net.prolancer.signature.controller;

import lombok.extern.slf4j.Slf4j;
import net.prolancer.signature.common.Utils.MediaUtils;
import net.prolancer.signature.common.contants.AppConstants;
import net.prolancer.signature.common.entity.ResponseHandler;
import net.prolancer.signature.entity.Signature;
import net.prolancer.signature.service.SignService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping(AppConstants.REST_API_PREFIX)
@Slf4j
public class SignController {

    @Autowired
    private SignService signService;

    /**
     * Signature
     * @param signature
     * @return
     */
    @PostMapping("/sign")
    public ResponseEntity<Object> signature(@Valid @RequestBody Signature signature) {
        if (signService.createSign(signature)) {
            return ResponseHandler.ok("Successfully Signed", null);
        }
        return ResponseHandler.error("Signed Error", signature);
    }

    /**
     * File View
     * @param filePath
     * @param response
     */
    @GetMapping("fileView")
    public void fileView(@RequestParam(value = "filePath", required = true) String filePath, HttpServletResponse response) {
        FileInputStream fis = null;
        OutputStream out = null;

        try {
            File file = new File("/Users/jaechulhan/Downloads/" + filePath);
            String extension = FilenameUtils.getExtension(file.getName());

            int fileSize = (int) file.length();
            byte buf[] = new byte[fileSize];
            int n;

            fis = new FileInputStream(file);

            HttpHeaders headers = new HttpHeaders();
            MediaType mediaType = MediaUtils.getMediaType(extension);
            headers.setContentType(mediaType);

            response.reset();
            response.setContentType(mediaType.toString());

            out = response.getOutputStream();
            while((n = fis.read(buf)) != -1) {
                out.write(buf,0, n);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {}
            }
        }

    }

}
