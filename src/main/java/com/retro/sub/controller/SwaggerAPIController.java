package com.retro.sub.controller;

import com.retro.sub.CommonService;
import com.retro.sub.dtos.PaymentDto;
import com.retro.sub.dtos.PaymentPayload;
import com.retro.sub.dtos.SubmitDto;
import com.retro.sub.dtos.SubmitResponse;
import com.retro.sub.dtos.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.net.URI;

import static com.retro.sub.config.ConstantStorage.ROOT_API_URL;

@Controller
public class SwaggerAPIController {

    @Value(value = "${client.id}")
    private String clientId;

    @Value(value = "${local.root.url}")
    private String rootPath;

    @Autowired
    private CommonService commonService;

    @GetMapping(value = "/sign")
    public ResponseEntity initButton() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(ROOT_API_URL + "/signin?client_id=" + clientId +
                "&redirect_uri=" + rootPath + "/auth&response_type=code&scope=pisp&state=mock"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String submitForm(Model model) {
        model.addAttribute("submit", new SubmitDto());
        return "submit";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String executeSubmit(@ModelAttribute SubmitDto submit, Model model) {
        SubmitResponse submitResponse = commonService.processPayment(PaymentDto.builder()
                .authorization(submit.getToken())
                .payload(PaymentPayload.fromSubmitDto(new SubmitDto()))
                .build());
        if (submitResponse.isFailed()) {
            submit.setErrorHandler(submitResponse.getMessage());
            model.addAttribute("submit", submit);
            return "result-negative";
        }
        model.addAttribute("submit", submit);
        return "result";
    }

    @GetMapping(value = "/auth")
    public String receiveAccessToken(@RequestParam("code") @NotNull String code, Model model) {
        TokenResponse tokenResponse = commonService.checkAuth(code);
        SubmitDto submitDto = new SubmitDto();
        if (tokenResponse.existsToken()) {
            submitDto.setToken(tokenResponse.getToken());
        } else {
            submitDto.setErrorHandler(tokenResponse.getErrorMessage());
        }
        model.addAttribute("submit", submitDto);
        return "submit";
    }
}
