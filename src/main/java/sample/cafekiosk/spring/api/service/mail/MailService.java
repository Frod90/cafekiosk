package sample.cafekiosk.spring.api.service.mail;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

@RequiredArgsConstructor
@Service
public class MailService {

	private final MailSendClient mailClient;
	private final MailSendHistoryRepository mailSendHistoryRepository;
	private final MailSendClient mailSendClient;

	public boolean sendMail(String fromEmail, String toEmail, String title, String content) {

		boolean result = mailClient.sendMail(fromEmail, toEmail, title, content);

		if (result) {
			mailSendHistoryRepository.save(MailSendHistory.builder()
				.fromEmail(fromEmail)
				.toEmail(toEmail)
				.title(title)
				.content(content)
				.build()
			);

			mailSendClient.a();
			mailSendClient.b();
			mailSendClient.c();

			return true;
		}

		return false;
	}
}
