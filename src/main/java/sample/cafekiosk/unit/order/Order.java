package sample.cafekiosk.unit.order;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sample.cafekiosk.unit.beverages.Beverage;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {

	private final LocalDateTime orderDateTime;
	private final List<Beverage> beverages;

	public static Order of(LocalDateTime orderDateTime, List<Beverage> beverages) {
		return new Order(orderDateTime, beverages);
	}

}
