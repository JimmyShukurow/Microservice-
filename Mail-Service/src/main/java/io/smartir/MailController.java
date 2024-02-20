package io.smartir;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mail")
public class MailController {

    private final MailService mailService;


    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("send-email")
    public void sendMail(@RequestBody MailRequest request) {
        mailService.sendMail(request);
    }

    @PostMapping("add-user")
    public void addUser(@RequestBody MailChimpRequest request) {
        mailService.addUserToMailChimp(request);
    }
}
