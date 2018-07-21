package bot.scanner;

import java.util.Scanner;

import bot.Bot;
import bot.DataParser;

public class ChatScanner {

	public static void main(String[] args) {

		String msg = "";

		// construct a data parser
		DataParser dp = new DataParser();

		// construct new bot with level 0 as default and given data parser
		Bot bot = new Bot("0", dp);

		// Create a Scanner object which takes System.in object
		// this makes us read values from the console

		Scanner scanner = new Scanner(System.in);

		// printing the result on the console
		System.out.println(bot.getMessage());

		System.out.println();

		while (!"stop".equals(msg)) {

			msg = scanner.nextLine();
			bot.send(msg);
			System.out.println(bot.getMessage());

		}
		System.out.println("byby!");
		scanner.close();
		System.exit(0);
	}

}