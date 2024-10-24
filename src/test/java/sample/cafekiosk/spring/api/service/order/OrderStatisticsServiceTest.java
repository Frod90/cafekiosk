package sample.cafekiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static sample.cafekiosk.spring.domain.product.ProductType.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.order.OrderStatus;
import sample.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

class OrderStatisticsServiceTest extends IntegrationTestSupport {

	@Autowired
	private OrderStatisticsService orderStatisticsService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MailSendHistoryRepository mailSendHistoryRepository;

	@AfterEach
	void tearDown() {
		orderProductRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
		mailSendHistoryRepository.deleteAllInBatch();
	}

	@DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다")
	@Test
	void sendOrderStatisticsMail() {
		// given
		LocalDateTime now = LocalDateTime.of(2023, 3, 5, 10, 0);

		Product product01 = createProduct(HANDMADE, "001", 1000);
		Product product02 = createProduct(HANDMADE, "002", 2000);
		Product product03 = createProduct(HANDMADE, "003", 3000);
		List<Product> products = List.of(product01, product02, product03);
		productRepository.saveAll(products);

		Order order01 = createPaymentCompletedOrder(products, LocalDateTime.of(2023, 3, 4, 23, 59, 59));
		Order order02 = createPaymentCompletedOrder(products, now);
		Order order03 = createPaymentCompletedOrder(products, LocalDateTime.of(2023, 3, 5, 23, 59, 50));
		Order order04 = createPaymentCompletedOrder(products, LocalDateTime.of(2023, 3, 6, 0, 0));

		// stubbing
		when(mailSendClient.sendMail(any(String.class), any(String.class), any(String.class), any(String.class)))
			.thenReturn(true);

		// when
		boolean result = orderStatisticsService.sendOrderStatisticsMail(
			LocalDate.of(2023, 3, 5),
			"test@test.com"
		);

		// then
		assertThat(result).isTrue();

		List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
		assertThat(histories).hasSize(1)
			.extracting("content")
			.contains("총 매출 합계는 12000원입니다.");
	}

	private Order createPaymentCompletedOrder(List<Product> products, LocalDateTime now) {
		Order order = Order.builder()
			.products(products)
			.orderStatus(OrderStatus.PAYMENT_COMPLETED)
			.registeredDateTime(now)
			.build();

		return orderRepository.save(order);
	}

	private static Product createProduct(
		ProductType type, String productNumber, int price) {
		return Product.builder()
			.productNumber(productNumber)
			.type(type)
			.sellingStatus(ProductSellingStatus.SELLING)
			.name("메뉴 이름")
			.price(price)
			.build();
	}

}