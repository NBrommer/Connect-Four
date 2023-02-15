package connectfour;

/*
* The file Board.java handles all of the board edits, as well as constructing the
* board string that will be printed out from TextUI.
*@author Nathan Brommersma
*/

public class Board{

    private char[][] theBoard = new char[6][7];


    //Constructor for Board class
    public Board(){

        int i;
        int j;

        for(i = 0; i < 6; i++) {
            for(j = 0; j < 7; j++) {
                theBoard[i][j] = ' ';
            }
        }
    }

    //returns the char of the board
    protected char[][] getBoard() {
        return theBoard;
    }

    //Sets the board with a passed in board
    protected void setBoard(char[][] newBoard) {
        theBoard = newBoard;
    }

    /*setPiece sets a specific piece of the board. i and choice are passed in to find location,
    *and char turn is passed in to know which piece goes.
    */
    protected void setPiece(int i, int choice, char turn) {
        theBoard[i][choice] = turn;
    }

    //toString returns a string of the board
    public String toString() {
        int i;
        int j;

        String toPrint = "";

        for(i = 0; i < 6; i++) {
            toPrint = toPrint + '|';
            for(j = 0; j < 7; j++) {
                toPrint = toPrint + theBoard[i][j];
                toPrint = toPrint + '|';
            }
            toPrint = toPrint + '\n';
        }

        return toPrint;
    }
}