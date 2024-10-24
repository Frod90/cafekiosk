package sample.cafekiosk.spring.client.mail;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailSendClient {

	public boolean sendMail(String fromEmail, String toEmail, String title, String content) {

		log.info("Sending mail to " + toEmail);
		throw new IllegalArgumentException("메일 전송");
		// return true;
	}

	public void a() {
		log.info("a");
	}

	public void b() {
		log.info("b");
	}

	public void c() {
		log.info("c");
	}

}
