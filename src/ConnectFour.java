package connectfour;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;

/*
* The ConnectFour.java file keeps track of the turns, the win/tie conditions,
* the loading from file as well as the saving to file. It also includes a method
* to check whos turn it is after loading.
*
* @author Nathan Brommersma
*/

public class ConnectFour{

    private char turn;


    //Constructor for ConnectFour
    protected ConnectFour() {
        turn = '0';
    }

    //Returns the current turn
    protected char getTurn() {
        return turn;
    }

    //switches the turn
    protected void changeTurn() {
        if (turn == '0') {
            turn = 'X';
        } else {
            turn = '0';
        }
    }

    //Checks if the piece can be placed in the spot
    protected int checkPlace(int choice, char[][] boardTable) {

        int negativeIfInvalid = -1;
        int i;

        for(i = 5; i > -1; i--) {
            if(boardTable[i][choice] != '0' && boardTable[i][choice] != 'X') {
                negativeIfInvalid = i;
                break;
            }
        }

        return negativeIfInvalid;
    }

    //Uses filename to load game from file
    protected char[][] loadFromFile(String fileName) {
        int i = 0;
        int j;
        int k;
        int notFound = 0;
        char readCharacter;
        char[][] boardTable = new char[6][7];

        for(i = 0; i < 6; i++) {
            for(j = 0; j < 7; j++) {
                boardTable[i][j] = 'E';
            }
        }

        try {
            File theFile = new File("assets/" + fileName);
            FileReader reader = new FileReader(theFile);
            BufferedReader buffer = new BufferedReader(reader);
            while((i = buffer.read()) != -1) {
                readCharacter = (char) i;
                notFound = 0;
                for(j = 0; j < 6 && notFound == 0; j++) {
                    for(k = 0; k < 7 && notFound == 0; k++) {
                        if(boardTable[j][k] == 'E') {
                            notFound = 1;
                            if(readCharacter == '0') {
                                boardTable[j][k] = ' ';
                            } else if(readCharacter == '1') {
                                boardTable[j][k] = '0';
                            } else if(readCharacter == '2') {
                                boardTable[j][k] = 'X';
                            } else if(readCharacter == ',' || readCharacter == 10) {
                                //NoOp required this variable is useless
                                int variable = 0;
                            } else {
                                boardTable[0][0] = 'E';
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            return boardTable;
        }
        return boardTable;
    }

    //Uses filename and the board to save to file
    protected boolean saveToFile(String fileName, char[][] boardTable) {
        boolean fileSaved = true;
        int i;
        int j;
        try{
            File theFile = new File("assets/" + fileName);
            if(theFile.createNewFile()) {
                //Created file
                FileWriter thePen = new FileWriter(theFile, true);
                for(i = 0; i < 6; i++) {
                    for(j = 0; j < 7; j++) {
                        if(boardTable[i][j] == ' ') {
                            thePen.append("0");
                        } else if(boardTable[i][j] == '0') {
                            thePen.append("1");
                        } else if(boardTable[i][j] == 'X') {
                            thePen.append("2");
                        }
                        if(j != 6) {
                            thePen.append(","); 
                        }
                    }
                    thePen.append("\n");
                }
                thePen.close();
            } else {
                //File exists
                FileWriter thePen = new FileWriter(theFile);
                for(i = 0; i < 6; i++) {
                    for(j = 0; j < 7; j++) {
                        if(boardTable[i][j] == ' ') {
                            thePen.append("0");
                        } else if(boardTable[i][j] == '0') {
                            thePen.append("1");
                        } else if(boardTable[i][j] == 'X') {
                            thePen.append("2");
                        }
                        if(j != 6) {
                            thePen.append(","); 
                        }
                    }
                    thePen.append("\n");
                }
                thePen.close();
            }
        } catch(Exception e) {
            fileSaved = false;
        }
        return fileSaved;
    }

    //After loading game, decideTurn calculates who's turn it is to go
    protected void decideTurn(char[][] boardTable) {
        int i = 0;
        int j = 0;
        int zeroCount = 0;
        int xCount = 0;

        for(i = 0; i < 6; i++) {
            for(j = 0; j < 7; j++) {
                if(boardTable[i][j] == '0') {
                    zeroCount = zeroCount + 1;
                } else if(boardTable[i][j] == 'X') {
                    xCount = xCount + 1;
                }
            }
        }

        if(zeroCount < xCount || zeroCount == xCount) {
            turn = '0';
        } else if(xCount < zeroCount) {
            turn = 'X';
        }
    }

    //toString method for ConnectFour class
    public String toString() {
        String theString = "Connect Four Game";
        return theString;
    }

    //Checks the game for tie
    protected boolean checkTie(char[][] boardTable) {
        boolean tie = true;
        int counter = 0;
        int i;
        int j;
        for(i = 0; i < 6; i++) {
            for(j = 0; j < 7; j++) {
                if(boardTable[i][j] == 'X' || boardTable[i][j] == '0') {
                    counter = counter + 1;
                }
            }
        }

        if(counter == 42) {
            tie = false;
        }
        return tie;
    }

    //Checks game for win using verticalWin, diagonalWin, and horizontalWin
    protected boolean checkWin(char[][] boardTable) {
        //Check the 3 win types here
        boolean winner = true;
        if(verticalWin(boardTable)) {
            winner = false;
        } else if(horizontalWin(boardTable)) {
            winner = false;
        } else if(diagonalWin(boardTable)) {
            winner = false;
        }
        return winner;
    }

    //Checks for vertical wins
    private boolean verticalWin(char[][] boardTable) {
        int i;
        boolean winner;
        winner = false;

        for(i = 0; i < 7; i++) {

            if((boardTable[0][i] == '0' && boardTable[1][i] == '0'
                && boardTable[2][i] == '0' && boardTable[3][i] == '0')
                || (boardTable[1][i] == '0' && boardTable[2][i] == '0'
                && boardTable[3][i] == '0' && boardTable[4][i] == '0')
                || (boardTable[2][i] == '0' && boardTable[3][i] == '0'
                && boardTable[4][i] == '0' && boardTable[5][i] == '0')) {
                winner = true;
            } else if((boardTable[0][i] == 'X' && boardTable[1][i] == 'X'
                && boardTable[2][i] == 'X' && boardTable[3][i] == 'X')
                || (boardTable[1][i] == 'X' && boardTable[2][i] == 'X'
                && boardTable[3][i] == 'X' && boardTable[4][i] == 'X')
                || (boardTable[2][i] == 'X' && boardTable[3][i] == 'X'
                && boardTable[4][i] == 'X' && boardTable[5][i] == 'X')) {
                winner = true;
            }
        }
        return winner;
    }

    //Checks for horizontal wins
    private boolean horizontalWin(char[][] boardTable) {
        int i;
        boolean winner;
        winner = false;

        for(i = 0; i < 6; i++) {

            if ((boardTable[i][0] == '0' && boardTable[i][1] == '0'
                && boardTable[i][2] == '0' && boardTable[i][3] == '0')
                || (boardTable[i][1] == '0' && boardTable[i][2] == '0'
                && boardTable[i][3] == '0' && boardTable[i][4] == '0')
                || (boardTable[i][2] == '0' && boardTable[i][3] == '0'
                && boardTable[i][4] == '0' && boardTable[i][5] == '0')
                || (boardTable[i][3] == '0' && boardTable[i][4] == '0'
                && boardTable[i][5] == '0' && boardTable[i][6] == '0')) {
                winner = true;
            } else if ((boardTable[i][0] == 'X' && boardTable[i][1] == 'X'
                && boardTable[i][2] == 'X' && boardTable[i][3] == 'X')
                || (boardTable[i][1] == 'X' && boardTable[i][2] == 'X'
                && boardTable[i][3] == 'X' && boardTable[i][4] == 'X')
                || (boardTable[i][2] == 'X' && boardTable[i][3] == 'X'
                && boardTable[i][4] == 'X' && boardTable[i][5] == 'X')
                || (boardTable[i][3] == 'X' && boardTable[i][4] == 'X'
                && boardTable[i][5] == 'X' && boardTable[i][6] == 'X')) {
                winner = true;
            }
        }
        return winner;
    }

    //Checks for diagonal Wins
    private boolean diagonalWin(char[][] boardTable) {
        int i;
        int j;
        boolean winner;
        winner = false;

        //Decending diagonal check
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 3; j++) {
                if(((boardTable[j][i] == 'X') && (boardTable[j + 1][i + 1] == 'X')
                    && (boardTable[j + 2][i + 2] == 'X') && (boardTable[j + 3][i + 3] == 'X'))
                    || ((boardTable[j][i] == '0') && (boardTable[j + 1][i + 1] == '0')
                    && (boardTable[j + 2][i + 2] == '0') && (boardTable[j + 3][i + 3] == '0'))) {
                    winner = true;
                }
            }
        }

        //Ascending diagonal check
        for (i = 0; i < 4; i++) {
            for (j = 5; j > 2; j--) {
                if(((boardTable[j][i] == 'X') && (boardTable[j - 1][i + 1] == 'X')
                    && (boardTable[j - 2][i + 2] == 'X') && (boardTable[j - 3][i + 3] == 'X'))
                    || ((boardTable[j][i] == '0') && (boardTable[j - 1][i + 1] == '0')
                    && (boardTable[j - 2][i + 2] == '0') && (boardTable[j - 3][i + 3] == '0'))) {
                    winner = true;
                }
            }
        }
        return winner;
    }
}