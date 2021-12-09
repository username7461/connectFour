/*******************************************************************************************/
/*
 *  
 *  Program    : ConnectFour
 *  Description: Write a four-in-a-line game in Java.
 *  
 *           Winning condition:
 *           Successive four discs of the same number aligned vertically, horizontally, 
 *           or diagonally before the opponent has reached the above condition.
 *  
 *  History    :
 *          10-11-2020 
 *              - New today
 *          - Created variable dictionary, variables added
 *          - Created main game rendering (Graphics)
 *          Bugs:
 *          (A) The grid prints way more zeros than expected
 *  
 *          11-11-2020
 *          - (A) fixed
 *          - Changed the main logic for rendering
 *          - Added loop statement for main game
 *          - Turn taking finished
 *          - Added a function able to read user inputs
 *          Bugs:
 *          1. User may exceed number of array, error check needed /w print message
 *  
 *          12-11-2020
 *          - Add user input to array
 *          - Check winning conditions (not working)
 *          Bugs:
 *          1. Won't check last before last row/ column
 *  
 *          15-11-2020
 *          - Added 4 full winning conditions & Changed some logic in winning conditions
 *          - All major functions completed
 *  
 *          21-11-2020
 *          - Created more methods
 *          - Moving away some components into a single method
 *          - Tie case added
 *          - Added more comments
 */
/*******************************************************************************************/

import java.util.Scanner;

public class ConnectFour{
    
     public static void main(String[] args) {
        // *****************Variable dictionary*****************
        Scanner kb = new Scanner(System.in); // Scanner object for input
        int[][] grid = new int[6][7]; // Store where the player chess places
        int input; // Stores user input
        int xAxis = 0; // Represent x coordinate in calculation
        int yAxis = 0; // Represent y coordinate in calculation
        int playerTurn = 1; // Player turn

        // Store successive count of the same playerChess
        int counterX = 0, counterY = 0, counterYX = 0, counterXY = 0;
        int xCoord = 0, yCoord = 0; // Save player coordinate
        int tieCount; // Stores Forming 7 in a (row, column=5) is tie
        boolean isTrue = true; // Decide when to end main loop
        
        // Print header
        printHeader();
        
        // *****************MAIN GAME*****************

        // Main loop
        while (isTrue) {

            // Resetting the numbers
            counterX = 0;
            counterY = 0;
            counterYX = 0;
            counterXY = 0;
            tieCount = 0;
            xCoord = 0;
            yCoord = 0;

            // *****************GRAPHICS*****************
            printBoard(yAxis, xAxis, grid);

            while (true) {
                System.out.print("\n" + "Player " + playerTurn + 
                        " type a column <0-6> or 9 to quit current game: ");

                // *****************READ USER INPUT*****************
                input = kb.nextInt();
                
                // input 0 - 6
                if ((input >= 0) && (input <= 6)) {
                    xCoord = input;
                    if ((yCoord < 6) && (grid[5][xCoord] == 0)) {
                        while ((grid[yCoord][xCoord] != 0) && (yCoord != 5)) {
                            yCoord++;
                        }
                        // WARNING!!! DO NOT CHANGE YCORD,XCOORD FROM NOW ON.
                        grid[yCoord][xCoord] = playerTurn;
                        break;
                    } else
                        System.out.println("Column " + xCoord + " is full!");
                }
                // input = 9
                else if (input == 9) {
                    System.out.println();
                    System.out.println("Bye Bye!");
                    isTrue = false;
                    break;
                }
                // Any exceptional case
                else
                    System.out.println("Range of column should be 0 to 6!");
            }

            // quit outer loop
            if (input == 9)
                break;

            // *****************CHECK WINNING CONDITIONS*****************

            counterX = checkHor(yCoord, playerTurn, grid);
            counterY = checkVert(xCoord, playerTurn, grid);
            counterXY = checkUpRight(xCoord, yCoord, playerTurn, grid);
            counterYX = checkBotleft(xCoord, yCoord, playerTurn, grid);

            // *****************Winning conditions*****************

            // Any successful segment == 4
            if ((counterX >= 4) || (counterY >= 4) || (counterYX >= 4) || 
                    (counterXY >= 4)) {
                System.out.println("Player " + playerTurn + " win this game!");
                printBoard(yAxis, xAxis, grid);
                break;
            }

            // *****************Tie*****************
            tieCount = isTie(grid);

            if (tieCount == 7) {
                System.out.println("It's a draw.");
                break;
            }

            // *****************TURN TAKING*****************
            playerTurn = turnTake(playerTurn);
        }
    }
    // *MAIN CLASS ENDED* *MAIN CLASS ENDED* *MAIN CLASS ENDED* *MAIN CLASS ENDED*

    // =============================Methods=============================
    
    // Print Header
    public static void printHeader(){
        System.out.println();
        System.out.println("C o n n e c t F o u r ");
        System.out.println("==========================================");
        System.out.println("Program created by usernameowo, 21-11-2020");
        System.out.println();
    }
    
    // Check whether the scenario is tied or not
    public static int isTie(int[][] grid) {
        int xAxis = 0;
        int yAxis = 5;
        int tieCount = 0;
        while (xAxis <= 6) {
            if (grid[yAxis][xAxis] != 0)
                tieCount++;
            xAxis++;
        }
        return tieCount;
    }

    // For x (left to right)
    public static int checkHor(int yCoord, int playerTurn, int[][] grid) {
        int xAxis, yAxis;
        int counterX = 0;

        yAxis = yCoord;
        xAxis = 0;

        while (xAxis <= 6) {
            if (grid[yAxis][xAxis] == playerTurn) {
                counterX++;
                if (counterX >= 4)
                    break;
            } else {
                counterX = 0;
            }
            xAxis++;
        }
        return counterX;
    }

    // For y (bottom to top)
    public static int checkVert(int xCoord, int playerTurn, int[][] grid) {
        int xAxis, yAxis;
        int counterY = 0;

        xAxis = xCoord;
        yAxis = 0;

        while (yAxis <= 5) {
            if (grid[yAxis][xAxis] == playerTurn) {
                counterY++;

                if (counterY >= 4)
                    break;

            } else {
                counterY = 0;
            }
            yAxis++;
        }
        return counterY;
    }

    // bottom left to top right
    public static int checkUpRight(int xCoord, int yCoord, 
            int playerTurn, int[][] grid) {
        int xAxis, yAxis;
        int counterXY = 0;
        int temp; // stores temporary numbers

        xAxis = xCoord;
        yAxis = yCoord;

        // find smallest
        if (xAxis < yAxis)
            temp = xAxis;
        else
            temp = yAxis;

        xAxis -= temp;
        yAxis -= temp;

        while ((yAxis <= 5) && (xAxis <= 6)) {
            if (grid[yAxis][xAxis] == playerTurn) {
                counterXY++;

                if (counterXY >= 4)
                    break;

            } else {
                counterXY = 0;
            }
            xAxis++;
            yAxis++;
        }
        return counterXY;
    }

    // bottom left to top right
    public static int checkBotleft(int xCoord, int yCoord, 
            int playerTurn, int[][] grid) {
        int xAxis, yAxis;
        int deltaX, deltaY; // Store distance between 2 endpoints
        int counterYX = 0;
        int temp; // stores temporary numbers

        // Endpoint 1 is yCoord & xCoord

        // Find distance
        deltaY = yCoord - 0;
        deltaX = 6 - xCoord;

        // Find smallest delta
        if (deltaX < deltaY)
            temp = deltaX;
        else
            temp = deltaY;
        
        // Find Endpoint 2
        yAxis = yCoord - temp;
        xAxis = xCoord + temp;

        while ((yAxis <= 5) && (xAxis >= 0)) {
            if (grid[yAxis][xAxis] == playerTurn) {
                counterYX++;
                if (counterYX >= 4)
                    break;
            } else {
                counterYX = 0;
            }
            xAxis--;
            yAxis++;
        }
        return counterYX;
    }

    // Pass to next player
    public static int turnTake(int playerTurn) {
        // Pass to player 2
        if (playerTurn == 1)
            playerTurn++;
        // Case player 2 - Pass to player 1
        else
            playerTurn--;
        return playerTurn;
    }

    // Printing the gameboard
    public static void printBoard(int yAxis, int xAxis, int[][] grid) {
        for (yAxis = 5; yAxis >= 0; yAxis--) {
            // Printing the left most numbers 5 to 0 (1st column)
            System.out.print(yAxis + " : ");
            for (xAxis = 0; xAxis <= 6; xAxis++) {
                System.out.print(grid[yAxis][xAxis] + " ");
            }
            System.out.println();
        }

        // Print lines (6th row)
        System.out.print("  +-------------- " + "\n");

        // Printing the lower most row 0 to 6 (last row)
        System.out.print("    ");
        for (int k = 0; k <= 6; k++) {
            System.out.print(k + " ");
        }
    }
}