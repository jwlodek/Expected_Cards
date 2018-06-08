/**
 * Program that finds the expected winnings of a card game given the starting position
 * The solution uses dynamic programming to fill in a table of values,
 * and then returns the one requested by the user
 * 
 * @author Jakub Wlodek
 */
public class ExpectedCards {

	//constants. COLOR_SIZE is the number of cards of a particular color in a deck, and table holds expected values
	private final static int COLOR_SIZE = 26;
	private static double[][] table = new double[COLOR_SIZE+1][COLOR_SIZE+1];
	
	/**
	 * Simple function used to print out the table. Used primarily for debugging
	 */
	public static void print_table() {
		for(int b = 0; b<=COLOR_SIZE;b++) {
			for(int r = 0; r<=COLOR_SIZE;r++) {
				System.out.printf("%.2f ",table[b][r]);
			}
			System.out.println();
		}
	}
	
	/**
	 * function that uses the previous entries in the table to find the value at a particular position
	 * @param black_rem The amount of black cards remaining in the deck
	 * @param red_rem The amount of red cards remaining in the deck
	 * @return The value of the table position [black cards remaining][red cards remaining]
	 */
	public static double find_table_position(int black_rem, int red_rem) {
		//first we find the total remaining
		int cards_rem = black_rem+red_rem;
		//then, we find the probability of the player pulling each type of card. This is important
		//because we need to multiply the probability by the expected value of the position,
		//and then sum the two possible expected values to get the expected value at the current position
		double black_prob = ((double) black_rem)/((double)cards_rem);
		double red_prob = ((double) red_rem)/((double)cards_rem);
		double black_exp = black_prob*table[black_rem-1][red_rem];
		double red_exp = red_prob*table[black_rem][red_rem-1];
		//before we return, we check to see if the expected value is greater than our current result. If not, 
		//it is in our best interest to stop playing
		double expected = black_exp+red_exp;
		double current_bank = (COLOR_SIZE-black_rem)-(COLOR_SIZE-red_rem);
		if(expected>current_bank) {
			return expected;
		}
		else {
			return current_bank;
		}
	}
	
	/**
	 * Method for filling the table of expected values. We start with filling out our base cases:
	 * If there are no black cards left, then we don't pull any more cards (as we would be guaranteed to
	 * lose money), so the expected value is the value we currently have. If there are no red cards left,
	 * then we pull all of the remaining cards, and we end up with a balance of 0. All of the remaining
	 * positions are filled based on the two previous adjacent expectations, and the probability that 
	 * each occurs
	 */
	static void fill_expected_table(){
		for(int i = 0; i <= COLOR_SIZE; i++) {
			table[i][0] = 0;
		}
		for(int j = 0; j <= COLOR_SIZE; j++) {
			table[0][j] = (double) j;
		}
		for(int b = 1; b <= COLOR_SIZE; b++) {
			for(int r = 1; r <= COLOR_SIZE; r++) {
				table[b][r] = find_table_position(b,r);
			}
		}
	}
	
	/**
	 * main method. Starts by parsing provided arguments. Then it creates the table 
	 * of expected values, and prints the one that corresponds to the game state selected
	 * by the user.
	 * @param args
	 */
	public static void main(String[] args) {
		int black_cards = 0;
		int cards_in_hand = 0;
		if(args.length == 0) {
			black_cards = 0;
			cards_in_hand = 0;
		}
		else if(args.length == 1) {
			if(args[0].compareTo("-h")==0||args[0].compareTo("--help")==0) {
				System.out.println();
				System.out.println("---------------------HELP--------------------------------------");
				System.out.println("To find the expected outcome before the state of the game, run the program without arguments.");
				System.out.println("Otherwise, provide two integers as arguments. The first is the number of black cards in the player's");
				System.out.println("hand, and cannot be > 26, and the second is the total number of cards in the player's hand, and cannot be > 52.");
				System.out.println("Use the argument -h or --help to see this message.");
				return;
			}
			else {
				System.out.println("Please enter valid arguments, or use -h or --help for help");
				return;
			}
		}
		else if(args.length == 2) {
			try {
				black_cards = Integer.parseInt(args[0]);
				if(black_cards>26||black_cards<0) {
					System.out.println("Please enter two valid integers. Use -h or --help for help");
					return;
				}
				cards_in_hand = Integer.parseInt(args[1]);
				if(cards_in_hand>52||cards_in_hand<0) {
					System.out.println("Please enter two valid integers. Use -h or --help for help");
					return;
				}
				if(cards_in_hand < black_cards){
					System.out.println("There cannot be more black cards in your hand than total cards in your hand");
					return;
				}
			}catch(NumberFormatException n) {
				System.out.println("Please enter two valid integers. Use -h or --help for help");
				return;
			}
		}
		else {
			System.out.println("Please enter valid arguments, or use -h or --help for help");
			return;
		}
		fill_expected_table();
		//print_table();
		System.out.print("The expected value with "+black_cards+" black cards in your hand, and "+cards_in_hand+" cards in your hand is: ");
		System.out.printf("%.2f\n",table[COLOR_SIZE-black_cards][COLOR_SIZE-(cards_in_hand-black_cards)]);
	}
}
