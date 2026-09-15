//package com.TestingApp;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.*;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.data.Offset.offset;
//@Slf4j
//@SpringBootTest
//class TestingAppApplicationTests {
//
//	@BeforeEach
//	void setup() {
//		log.info("starting the method, setting up config");
//	}
//
//	@AfterEach
//	void tearDown() {
//		log.info("tearing down the method");
//	}
//
//	@BeforeAll
//	static void setupOnce() {
//		log.info("Setup once....");
//	}
//
//	@AfterAll
//	static void tearDownOnce() {
//		log.info("Tearing down all...");
//	}
//
//	@Test
//	void contextLoads() {
//		log.info("test 1 is running");
//	}
//
//	@Test
//	@DisplayName("displayNameAddTwoNumber")
//	void addTwoNumber() {
//		log.info("test 2 is running");
//	}
//
//	@Test
//	void testNumberOne() {
//		int x = 3;
//		int y = 5;
//		int result = x + y;
//
//		assertThat(result)
//				.isEqualTo(8)
//				.isCloseTo(9, offset(1));
//	}
//
//	@Test
//	void testNumberTwo() {
//
//	}
//	void testDivideTwoNumber_where_dominator_is_zero_ThenArithmeticException(){
//		int a=20;
//		int b=20;
//		assertThatThownBy(()->divideTwoNumber(a,b))
//				.instanceOf(ArithmeticException.class)
//				.hasMessage("Tried to divide by zero");
//
//	}
//
//	int addTwoNumbers(int a, int b) {
//		return a + b;
//	}
//	double divideTwoNumber(int a ,int b){
//		try{
//			return  a/b;
//		} catch (ArithmeticException e) {
//			log.error("Arithmetic exception occur " + e.getLocalizedMessage());
//			throw  new ArithmeticException("Tried to divide by zero");
//		}
//	}
//}
package com.TestingApp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.data.Offset.offset;

@Slf4j
@SpringBootTest
class TestingAppApplicationTests {

	@BeforeEach
	void setup() {
		log.info("starting the method, setting up config");
	}

	@AfterEach
	void tearDown() {
		log.info("tearing down the method");
	}

	@BeforeAll
	static void setupOnce() {
		log.info("Setup once....");
	}

	@AfterAll
	static void tearDownOnce() {
		log.info("Tearing down all...");
	}

	@Test
	void contextLoads() {
		log.info("test 1 is running");
	}

	@Test
	@DisplayName("displayNameAddTwoNumber")
	void addTwoNumber() {
		log.info("test 2 is running");
	}

	@Test
	void testNumberOne() {
		int x = 3;
		int y = 5;
		int result = x + y;

		assertThat(result)
				.isEqualTo(8)
				.isCloseTo(9, offset(1));
	}

	@Test
	void testNumberTwo() {
	}

	@Test
	void testDivideTwoNumber_where_denominator_is_zero_ThenArithmeticException() {

		int a = 20;
		int b = 0;

		assertThatThrownBy(() -> divideTwoNumber(a, b))
				.isInstanceOf(ArithmeticException.class)
				.hasMessage("Tried to divide by zero");
	}

	int addTwoNumbers(int a, int b) {
		return a + b;
	}

	double divideTwoNumber(int a, int b) {
		try {
			return a / b;
		} catch (ArithmeticException e) {
			log.error("Arithmetic exception occurred " + e.getLocalizedMessage());
			throw new ArithmeticException("Tried to divide by zero");
		}
	}
}
