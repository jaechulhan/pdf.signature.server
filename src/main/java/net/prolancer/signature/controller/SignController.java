package net.prolancer.signature.controller;

import lombok.extern.slf4j.Slf4j;
import net.prolancer.signature.common.contants.AppConstants;
import net.prolancer.signature.common.entity.ResponseHandler;
import net.prolancer.signature.entity.Signature;
import net.prolancer.signature.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(AppConstants.REST_API_PREFIX)
@Slf4j
public class SignController {

    @Autowired
    private SignService signService;

    @PostMapping("/sign")
    public ResponseEntity<Object> signature(@Valid @RequestBody Signature signature) {
        if (signService.createSign(signature)) {
            return ResponseHandler.ok("Successfully Signed", null);
        }
        return ResponseHandler.error("Signed Error", signature);
    }

}
