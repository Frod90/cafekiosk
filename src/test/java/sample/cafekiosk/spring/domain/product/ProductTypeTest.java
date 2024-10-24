package sample.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class ProductTypeTest {

	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다")
	@Test
	void containsStockType() {

		// given
		ProductType productType = ProductType.HANDMADE;

		// when
		boolean result = ProductType.containsStockType(productType);

		//then
		assertThat(result).isFalse();
	}

	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다")
	@Test
	void containsStockType2() {

		// given
		ProductType productType = ProductType.BAKERY;

		// when
		boolean result = ProductType.containsStockType(productType);

		//then
		assertThat(result).isTrue();
	}

	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다")
	@CsvSource({"HANDMADE,false", "BOTTLE,true", "BAKERY,true"})
	@ParameterizedTest
	void containsStockType3(ProductType productType, boolean expected) {

		// when
		boolean result = ProductType.containsStockType(productType);

		//then
		assertThat(result).isEqualTo(expected);
	}

	private static Stream<Arguments> provideProductTypesForCheckingStockType() {
		return Stream.of(
			Arguments.of(ProductType.HANDMADE, false),
			Arguments.of(ProductType.BOTTLE, true),
			Arguments.of(ProductType.BAKERY, true)
		);
	}

	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다")
	@MethodSource("provideProductTypesForCheckingStockType")
	@ParameterizedTest(name = "{index} ==> ''{0}'' is {1}")
	void containsStockType4(ProductType productType, boolean expected) {

		// when
		boolean result = ProductType.containsStockType(productType);

		//then
		assertThat(result).isEqualTo(expected);
	}

}