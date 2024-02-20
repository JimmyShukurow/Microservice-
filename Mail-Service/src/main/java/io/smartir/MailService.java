package io.smartir;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static io.smartir.MailChimpConfig.*;

@Service
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final RestTemplate restTemplate;

    public MailService(JavaMailSender mailSender, RestTemplate restTemplate) {
        this.mailSender = mailSender;
        this.restTemplate = restTemplate;
    }

    public void sendMail(MailRequest request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlMsg = String.format(
                    "<div style=\"padding:25px;\"> Name: %1$s <br/> Surname: %2$s <br/> Email: %3$s <br/> Country Code:  + %4$s <br/> Phone Number: %5$s <br/> Message: %6$s <br/> </div>",
                    request.getName(),
                    request.getSurname(),
                    request.getEmail(),
                    request.getSelectedCountry(),
                    request.getPhoneNumber(),
                    request.getMessage()
            );
            helper.setText(htmlMsg, true);
            helper.setFrom("info@smartir.io");
            helper.setTo("info@smartir.io");
            helper.setSubject("Contact Form Submission");

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info("Exception in mail sending.");
        }
    }

    public void addUserToMailChimp(MailChimpRequest mailChimpRequest) {

        MailChimpRequestDTO requestDTO = new MailChimpRequestDTO(mailChimpRequest.getEmail(), STATUS, List.of("newsletter"));

        String auth = Base64.encodeBase64String(("user:" + API_KEY).getBytes());

        String datacenter = API_KEY.substring(API_KEY.lastIndexOf('-') + 1);
        String mailchimpUrl = "https://" + datacenter + ".api.mailchimp.com/3.0/lists/" + LIST_ID + "/members/" + mailChimpRequest.getEmail();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Basic " + auth);

        HttpEntity<Object> request = new HttpEntity<>(requestDTO, headers);

        var response = restTemplate.exchange(mailchimpUrl, HttpMethod.PUT, request,
                MailChimpResponseDTO.class);
        log.info(response.toString());
    }
}
