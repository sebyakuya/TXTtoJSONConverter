package whatsapp.converter;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		TXTtoJSONConverter t = new TXTtoJSONConverter();
		;
		try {
			t.convert(args);
		} catch (IOException e) {
			System.err.println("I/O Exception. Fix your arguments");
		}
	}
}
