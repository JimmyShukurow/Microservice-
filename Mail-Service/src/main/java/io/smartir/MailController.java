package io.smartir;

import org.springframework.web.bind.annotation.*;

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
    @GetMapping("status")
    public String getStatus() {
        return "Mail-service is working";
    }
}
