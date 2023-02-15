package connectfour;

import java.util.Scanner;

/*
* The TextUI.java file handles all the printing and user input of the program.
* It also contains the main.
*
* @author Nathan Brommersma
*/

public class TextUI{

    //TextUI's toString function
    public String toString() {
        String theString = "TextUI toString test";
        return theString;
    }

    //Prints out the board
    private void printBoard(Board theBoard) {
        System.out.println(theBoard.toString());
    }

    //Prints out the board and takes user input for what to do
    private int chooseNumber(String theBoardS, char turn) {
        boolean loop = true;

        int input = 0;

        Scanner scan = new Scanner(System.in);

        System.out.print(theBoardS);
        System.out.println(" 1 2 3 4 5 6 7");
        System.out.println("It is " + turn + "'s turn.");

        while(loop) {
            input = 0;
            System.out.print("Choose 1 to 7 for placement, ");
            System.out.println("8 for save, 9 to load, 0 to exit");
            try {
                input = scan.nextInt();

            if (input > 9 || input < 0) {
                    System.out.println("Invalid input");
                } else {
                    loop = false;
            }

            } catch (Exception e) {
                System.out.println("Invalid input");
                scan.next();
            }
        }
        return input;
    }

    //Prints if coloumn is full
    private void invalidInput() {
        System.out.println("Coloumn is full! Try again.");
    }

    //Prints out the victory message for the player who won
    private void printWin(String theBoardS, char turn) {
        System.out.println(theBoardS);
        System.out.println("Player " + turn + " wins!");
    }

    //Reads in file name from user input
    private String readFileInput() {
        String input;

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter file name:");

        input = scan.nextLine();

        return input;
    }

    //Prints if error with file
    private void badFile() {
        System.out.println("Did not load or save, error with file");
    }

    //Print if file saved successfuly
    private void fileSaveSuccesful() {
        System.out.println("File saved successfully");
    }

    //print if there was error saving file
    private void fileNoSave() {
        System.out.println("Error saving file");
    }

    //Print if game is a tie
    private void printTie() {
        System.out.println("Game is a tie!");
    }

    //Main, the text is close together because checkstyle does not allow more than 50 lines
    public static void main(String[] args){
        TextUI theUI = new TextUI();
        Board theBoard = new Board();
        ConnectFour theGame = new ConnectFour();

        int choice;
        int locationToUpdate;
        boolean loop = true;
        char[][] loadedBoard;

        while((theGame.checkWin(theBoard.getBoard())) && theGame.checkTie(theBoard.getBoard())) {
            loop = true;
            while(loop) {
                choice = theUI.chooseNumber(theBoard.toString(), theGame.getTurn());
                if (choice > 0 && choice < 8) {
                        locationToUpdate = theGame.checkPlace(choice - 1, theBoard.getBoard());
                        if (locationToUpdate >= 0) {
                            theBoard.setPiece(locationToUpdate, choice - 1, theGame.getTurn());
                            theGame.changeTurn();
                            loop = false;
                        } else {
                            theUI.invalidInput();
                        }
                } else if(choice == 9) {
                    loadedBoard = theGame.loadFromFile(theUI.readFileInput());
                    if(loadedBoard[0][0] != 'E') {
                        theBoard.setBoard(loadedBoard);
                        theGame.decideTurn(loadedBoard);
                    } else {
                        theUI.badFile();
                    }
                } else if(choice == 8) {
                    if(theGame.saveToFile(theUI.readFileInput(), theBoard.getBoard())) {
                        theUI.fileSaveSuccesful();
                    } else {
                        theUI.fileNoSave();
                    }
                } else if(choice == 0) {
                    return;
                }
            }
        }
        if(!(theGame.checkTie(theBoard.getBoard()))) {
            theUI.printTie();
        } else {
            theGame.changeTurn();
            theUI.printWin(theBoard.toString(), theGame.getTurn());
        }
    }
}

