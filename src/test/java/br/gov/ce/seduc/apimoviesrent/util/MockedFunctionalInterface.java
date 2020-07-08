package br.gov.ce.seduc.apimoviesrent.util;

import static lombok.AccessLevel.PRIVATE;

import java.util.function.Consumer;
import java.util.function.Function;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class MockedFunctionalInterface {
	public static <T>Function<T, T> mockedFunction() {
		return result -> result;
	}
	public static <T>Consumer<T> mockedConsumer() {
		return result -> {
			
		};
	}
}
