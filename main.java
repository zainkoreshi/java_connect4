import java.util.Scanner;

/**
 * @author: ______Zain Zafar Koreshi (21202141)_________
 *
 *          For the instruction of the assignment please refer to the assignment
 *          GitHub.
 *
 *          Plagiarism is a serious offense and can be easily detected. Please
 *          don't share your code to your classmate even if they are threatening
 *          you with your friendship. If they don't have the ability to work on
 *          something that can compile, they would not be able to change your
 *          code to a state that we can't detect the act of plagiarism. For the
 *          first commit of plagiarism, regardless you shared your code or
 *          copied code from others, you will receive 0 with an addition of 5
 *          mark penalty. If you commit plagiarism twice, your case will be
 *          presented in the exam board and you will receive a F directly.
 *
 *          If you cannot work out the logic of the assignment, simply contact
 *          us on Piazza. The teaching team is more the eager to provide
 *          you help. We can extend your submission due if it is really
 *          necessary. Just please, don't give up.
 */
public class main {

    /**
     * Total number of rows of the game board. Use this constant whenever possible.
     */
    public static final int HEIGHT = 6;
    /**
     * Total number of columns of the game board. Use this constant whenever
     * possible.
     */
    public static final int WIDTH = 8;

    /**
     * Your main program. You don't need to change this part. This has been done for
     * you.
     */
    public static void main(String[] args) {
        new main().runOnce();
    }

    /**
     * Your program entry. There are two lines missing. Please complete the line
     * labeled with TODO. You can, however, write more than two lines to complete
     * the logic required by TODO. You are not supposed to modify any part other
     * than the TODOs.
     */
    void runOnce() {
        // For people who are not familiar with constants - HEIGHT and WIDTH are two
        // constants defined above. These two constants are visible in the entire
        // program. They cannot be further modified, i.e., it is impossible to write
        // HEIGHT = HEIGHT + 1; or WIDTH = 0; anywhere in your code. However, you can
        // use
        // these two constants as a reference, i.e., row = HEIGHT - 1, for example.

        int[][] board = new int[HEIGHT][WIDTH];
        char[] symbols = {'1', '2'};
        int player = 1;
        printBoard(board, symbols);

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while (!isGameOver(board) && !quit) {
            System.out.println("Player " + player + ", please enter a command. Press 'h' for help");
            char s = scanner.next().charAt(0);
            switch (s) {
                case 'h':
                case 'H':
                    printHelpMenu();
                    break;
                case 'c':
                case 'C':
                    changeSymbol(player, symbols);
                    break;
                case 'q':
                case 'Q':
                    quit = true;
                    System.out.println("Bye~");
                    continue;
                case 'r':
                    restart(board);
                    printBoard(board, symbols);
                    continue;
                default:
                    if (!validate(s, board)) {
                        System.out.println("Wrong input!, please do again");
                        continue;
                    }

                    // convert the char 's' to the integer 'column', with the value 0 to 7
                    int column;

                    column = s - '0';

                    fillBoard(board, column, player);
                    printBoard(board, symbols);
                    if (isGameOver(board)) {
                        System.out.println("Player " + player + ", you win!");
                        break;
                    }
                    else if (checkMate(player, board))
                        System.out.println("Check mate!");
                    else if (check(player, board))
                        System.out.println("Check!");

                    // between the integers 1 and 2.
                    if (player == 1) {
                        player++;
                    }
                    else {
                        player--;
                    }
            } // end switch
        } // end while
    }

    /**
     * Reset the board to the initial state
     *
     * @param board - the game board array
     */

    // created the board again

    void restart(int[][] board) {
        // print the upper border, and number the columns
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = 0;
            }
        }
    }

    /**
     * It allows a player to choose a new symbol to represents its chess.
     * This method should ask the player to enter a new symbol so that symbol is not
     * the same as its opponent.
     * Otherwise the player will need to enter it again until they are different.
     *
     * @param player  - the player who is about to change its symbol
     * @param symbols - the symbols array storing the players' symbols.
     **/

    void changeSymbol(int player, char[] symbols) {
        Scanner in = new Scanner(System.in);

        do {
            System.out.println("Enter the new symbol: ");
            // if player 1's turn then change the first symbol
            if (player == 1) {
                symbols[0] = in.next().charAt(0);
            }
            // if player 2's turn then change the second symbol
            else if (player == 2) {
                symbols[1] = in.next().charAt(0);
            }
        } while (symbols[0] == symbols[1]);
        // as long as both are different
    }

    /**
     * This method returns true if the player "player" plays immediately, he/she may
     * end the game. This warns the other player to
     * place his/her next block in a correct position.
     *
     * @param player - the player who is about to win if the other player does not
     *               stop him
     * @param board  - the 2D array of the game board.
     * @return true if the player is about to win, false if the player is not.
     */
    boolean check(int player, int[][] board) {

        // scan the whole board
        for (int i = 0; i < board.length - 1; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // search for a spot with neither 1 or 2
                if (board[i][j] == 0) {
                    // if the spot below that is full
                    if (board[i + 1][j] != 0) {
                        // put the players symbol there, if the game over condition is satisfied that means they are one step away from winning
                        board[i][j] = player;
                        if (isGameOver(board) == true) {
                            //reset value of the board
                            board[i][j] = 0;
                            return true;
                        } else
                            // reset the value of the point
                            board[i][j] = 0;
                    }
                }
            }
        }

        return false;
    }

    /**
     * This method is very similar to the method check. However, a check-mate move
     * means no matter how the other player place his/her next block, in the next
     * turn the player can win the game with certain move.
     * <p>
     * A check-mate move must be a check move. Not all check moves are check-mate
     * move.
     *
     * @param player - the player who is about to win no matter what the other
     *               player does
     * @param board  - the 2D array of the game board/
     * @return true if the player is about to win
     */
    boolean checkMate(int player, int[][] board) {

        int checkCount = 0;

        // scan the whole board
        for (int i = 0; i < board.length - 1; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // search for a spot with neither 1 or 2
                if (board[i][j] == 0) {
                    // if the spot below that is full
                    if (board[i + 1][j] != 0) {
                        // put the players symbol there, if the game over condition is satisfied that means they are one step away from winning
                        board[i][j] = player;
                        if (isGameOver(board) == true) {
                            //reset value of the board
                            board[i][j] = 0;
                            checkCount++;
                        } else
                            board[i][j] = 0;
                    }
                }
            }
        }

        if (checkCount > 1) {
            return true;
        }

        return false;
    }

    /**
     * Validate if the input is valid. This input should be one of the character
     * '0', '1', '2', '3,' ..., '7'.
     * The column corresponding to that input should not be full.
     *
     * @param input - the character of the column that the block is intended to
     *              place
     * @param board - the game board
     * @return - true if it is valid, false if it is invalid (e.g., '8', 'c', '@',
     * EOT (which has an unicode 4) )
     */

    boolean validate(char input, int[][] board) {

        // input must be these values
        if (input == '0' || input == '1' || input == '2' || input == '3' || input == '4' || input == '5' || input == '6' || input == '7') {

            //  if the topmost value is not zero then there is no space, otherwise there is
            if (board[0][input - '0'] != 0) {
                return false;
            }

            return true;
        }

        return false;

    }

    /**
     * Given the column (in integer) that a player wish to place his/her block,
     * update the gameboard. You may assume that the input has been validated before
     * calling this method, i.e., there always has room to place the block when
     * calling this method.
     *
     * @param board  - the game board
     * @param column - the column that the player want to places its block
     * @param player - 1 or 2, the player.
     */
    void fillBoard(int[][] board, int column, int player) {

        // test for every place within the column
        for(int i = HEIGHT - 1; i >= 0; i--) {
            // only if its empty we will place the player in it
            if (board[i][column] == 0) {
                board[i][column] = player;
                break;
            }
        }
    }

    /**
     * Print the Help Menu. Please try to understand the switch case in runOnce and
     * Provide a one line comment about the purpose of each symbol.
     */
    void printHelpMenu() {

        // add instructions on how to play and what each switch case does
        System.out.println("~-~-~-~-~-~-~-~-~-~-~-HELP MENU!!~-~-~-~-~-~-~-~-~-~-~-");
        System.out.println("- Connect four dots in a sequence to win.");
        System.out.println("- The sequence can be horizontal, vertical or diagonal.");
        System.out.println("- Press 'c' to change your symbol.");
        System.out.println("- Press 'q' to quit the game.");
        System.out.println("- Press 'r' to restart the game.");
        System.out.println("~-~-~-~-~-~-~-~-~-~-~-~-ENJOY!!~-~-~-~-~-~-~-~-~-~-~-~-");

    }


    /**
     * Determine if the game is over. Game is over if and only if one of the player
     * has a connect-4 or the entire gameboard is fully filled.
     *
     * @param board - the game board
     * @return - true if the game is over, false other wise.
     */
    boolean isGameOver(int[][] board) {

        // create a variable with a predefined true value
        boolean full = true;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                if (board[i][j] == 0) {
                    full = false;
                    continue;
                }

                // check for horizontal sequence
                if (j < WIDTH - 3 && board[i][j] == board[i][j + 1] && board[i][j] == board[i][j + 2] && board[i][j] == board[i][j + 3]) {
                    return true;
                }
                // check for vertical sequence
                if (i < HEIGHT - 3 && board[i][j] == board[i + 1][j] && board[i][j] == board[i + 2][j] && board[i][j] == board[i + 3][j]) {
                    return true;
                }

                // check for diagonal sequences
                if (i < HEIGHT - 3 && j < WIDTH - 3 && board[i][j] == board[i + 1][j + 1] && board[i][j] == board[i + 2][j + 2] && board[i][j] == board[i + 3][j + 3]) {
                    return true;
                }
                if (i < HEIGHT - 3 && j >= 3 && board[i][j] == board[i + 1][j - 1] && board[i][j] == board[i + 2][j - 2] && board[i][j] == board[i + 3][j - 3]) {
                    return true;
                }

            }
        }

        // return value of full
        return full;
    }

    /**
     * Print the game board in a particular format. The instruction can be referred
     * to the GitHub or the demo program. By default, Player 1 uses the character
     * '1' to represent its block. Player 2 uses the character '2'. They can be
     * overrided by the value of symbols array. This method does not change the
     * value of the gameboard nor the symbols array.n
     *
     * @param board   - the game board to be printed.
     * @param symbols - the symbols that represents player 1 and player 2.
     */

    void printBoard(int[][] board, char[] symbols) {

        // print the upper border, and number the columns
        System.out.println(" 01234567 ");
        System.out.println(" -------- ");

        for (int i = 0; i < board.length; i++) {
            for (int j = -1; (j < board[i].length + 1); j++) {

                // print the left border
                if (j == -1) {
                    System.out.print("|");
                }

                // for the columns 0 to 7
                if (j >= 0 && j < board[i].length) {
                    // Default is 0, so it should be empty
                    if (board[i][j] == 0) {
                        System.out.print(" ");
                    }
                    // If player 1, add player 1's symbol
                    else if (board[i][j] == 1) {
                        System.out.print(symbols[0]);
                    }
                    // If player 2, add player 2's symbol
                    else if (board[i][j] == 2) {
                        System.out.print(symbols[1]);
                    }
                }

                // print the right border
                if (j == board[i].length) {
                    System.out.print("|");
                }

            }
            // move to next line after every row
            System.out.println();
        }
        // print the lower border
        System.out.println(" -------- ");
    }
}