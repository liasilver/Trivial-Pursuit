/*
Program description: This program is a slightly adapted version of the classic board game Trivial Pursuit. Players must answer trivia questions to progress through the game.  

By: Lia Silver and Saumya Khati

Last edited: September 29, 2019
*/
//important import statements needed to run project. 

import java.util.Scanner;
import java.io.*;
import java.util.Arrays;
import java.lang.*;

class Main extends Questions {

    //boolean testing needed for tracing statements all throughout the program. 
    boolean testing = false;

    //Scanner and buffered reader used for reading user input. 
    private Scanner sc = new Scanner(System.in);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {

        //Calling constructor. 
        new Main();
    }

    //Constructor method. 
    Main() throws Exception {

        // declaring important variables 

        //Boolean variables to keep track of user turn
        boolean playerOneTurn = true;

        //Keeping track of pieces for player 1 and 2
        boolean geoPieceOne = false;
        boolean entPieceOne = false;
        boolean hisPieceOne = false;
        boolean sciPieceOne = false;

        boolean geoPieceTwo = false;
        boolean entPieceTwo = false;
        boolean hisPieceTwo = false;
        boolean sciPieceTwo = false;

        //Stores dice roll. 
        int dice = 0;

        //1 means user 1 won and 2 meanse user 2 wins. 
        int whichUserWon = 0;

        //Creating ints to hold coordinates for both players.
        int x1 = 0;
        int y1 = 3;
        int x2 = 6;
        int y2 = 3;

        //Creating int to hold coordinates for both players (that we can manipulate without having to repeat)
        int x3 = 0;
        int y3 = 0;

        gamePlay(dice, x1, y1, x2, y2, x3, y3, playerOneTurn, geoPieceOne, entPieceOne, hisPieceOne, sciPieceOne, geoPieceTwo, entPieceTwo, hisPieceTwo, sciPieceTwo, whichUserWon);

    }//end of constructor.

    //Method to display the board. 
    public void boardSetup(int x1, int y1, int x2, int y2) {
        boolean[][] validSquares = new boolean[7][7];
        //Set all of board border to true
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                validSquares[x][y] = true;
            }
        }

        //Setting "middle" of board to false so only border is usable.
        for (int i = 1; i < 6; i++) {
            for (int j = 1; j < 6; j++) {
                validSquares[i][j] = false;
            }
        }

        //Game start - printing [] for normal squares in board and {} for special squares
        System.out.println();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {

                //If both users are in a corner and same tile.  
                if (x1 == x2 && y1 == y2 && i == x1 && j == y1 && (i == 0 || i == 6) && (j == 0 || j == 6)) {
                    System.out.print("{3}");
                }

                //if both users are in same tile but not a "special" corner. 
                else if (x1 == x2 && y1 == y2 && i == x1 && j == y1) {
                    System.out.print("[3]");
                }

                //If user 1 is in any of the corners, prints 1 with curly brackets.
                else if ((i == 0 && j == 0 && x1 == 0 && y1 == 0) || (i == 0 && j == 6 && x1 == 0 && y1 == 6) || (i == 6 && j == 0 && x1 == 6 && y1 == 0) || (i == 6 && j == 6 && x1 == 6 && y1 == 6)) {
                    System.out.print("{1}");

                    //If user 2 is in any of the corners.     
                } else if ((i == 0 && j == 0 && x2 == 0 && y2 == 0) || (i == 0 && j == 6 && x2 == 0 && y2 == 6) || (i == 6 && j == 0 && x2 == 6 && y2 == 0) || (i == 6 && j == 6 && x2 == 6 && y2 == 6)) {
                    System.out.print("{2}");

                    //Prints the box user 1 is in.     
                } else if (i == x1 && j == y1) {
                    System.out.print("[1]");

                    //Prints the box user 2 is in. 
                } else if (i == x2 && j == y2) {
                    System.out.print("[2]");

                    //Following print empty "regular" squares or special "squares"   
                } else if (i == 0 && j == 6) {
                    System.out.print("{ }");
                } else if (i == 6 && j == 0) {
                    System.out.print("{ }");
                } else if (i == 0 && j == 0) {
                    System.out.print("{ }");
                } else if (i == 6 && j == 6) {
                    System.out.print("{ }");
                } else if (validSquares[i][j] == true) {
                    System.out.print("[ ]");

                    //prints space for an invalid square which includes all squares excluding border ones. 
                } else if (validSquares[i][j] == false) {
                    System.out.print("   ");
                }
            }
            System.out.println();//Making sure each row is on a new line. 
        }
    }//End of board setup method.

    //Method to simulate dice roll for users. Returns dice value as int.  
    private int diceRoll(boolean playerOneTurn) {
        int dice = (int) (Math.random() * 6) + 1;
        System.out.println("\nYou rolled a " + dice + "!");
        return dice;
    }//end of dice roll method. 

    //Method to ask either user to roll the dice. 
    private void promptForRoll(boolean playerOneTurn) throws Exception {
        if (playerOneTurn) {
            System.out.print("User 1, press enter to roll the dice.");
            br.readLine();
        } else if (!playerOneTurn) {
            System.out.print("User 2, press enter to roll the dice.");
            br.readLine();
        }
    }//end of prompt for roll method. 

    //method to determine where player moves by taking in their coordinates and displaying possible directions to move in and returns the value. 
    private String playerMove(int x3, int y3) {

        //Declaring variable to store move. 
        String move = "";

        //if player is in top left corner.
        if (x3 == 0 && y3 == 0) {
            System.out.print("Please use WASD to make your move. You can go down(S) or right(D): ");
            move = sc.next();

            //ensuring correct input. 
            do {
                if (!move.equalsIgnoreCase("D") && !move.equalsIgnoreCase("S")) {
                    System.out.print("Invalid choice. Please enter down(S) or right(D): ");
                    move = sc.next();
                }
            } while (!move.equalsIgnoreCase("D") && !move.equalsIgnoreCase("S"));
        }
        //if player is in top right corner. 
        else if (x3 == 0 && y3 == 6) {
            System.out.print("Please use WASD to make your move. You can go down(S) or left(A): ");
            move = sc.next();

            do {
                if (!move.equalsIgnoreCase("A") && !move.equalsIgnoreCase("S")) {
                    System.out.print("Invalid choice. Please enter down(S) or left(A): ");
                    move = sc.next();
                }
            } while (!move.equalsIgnoreCase("A") && !move.equalsIgnoreCase("S"));
        }
        //If player is in bottom left corner.
        else if (x3 == 6 && y3 == 0) {
            System.out.print("Please use WASD to make your move. You can go up(W) or right(D): ");
            move = sc.next();

            do {
                if (!move.equalsIgnoreCase("W") && !move.equalsIgnoreCase("d")) {
                    System.out.print("Invalid choice. Please enter up(W) or right(D): ");
                    move = sc.next();
                }
            } while (!move.equalsIgnoreCase("w") && !move.equalsIgnoreCase("d"));
        }
        //If player is in bottom right corner. 
        else if (x3 == 6 && y3 == 6) {
            System.out.print("Please use WASD to make your move. You can go up(W) or left(A): ");
            move = sc.next();

            do {
                if (!move.equalsIgnoreCase("A") && !move.equalsIgnoreCase("w")) {
                    System.out.print("Invalid choice. Please enter up(W) or left(A): ");
                    move = sc.next();
                }
            } while (!move.equalsIgnoreCase("A") && !move.equalsIgnoreCase("w"));
        }
        //If player is in the top row or bottom row but not in a special corner. 
        else if (((x3 == 0) && (y3 != 0 && y3 != 6)) || ((x3 == 6) && (y3 != 0 && y3 != 6))) {
            System.out.print("Please use WASD to make your move. You can go left(A) or right(D): ");
            move = sc.next();
            do {
                if (!move.equalsIgnoreCase("D") && !move.equalsIgnoreCase("A")) {
                    System.out.print("Invalid choice. Please enter left(A) or right(D): ");
                    move = sc.next();
                }
            } while (!move.equalsIgnoreCase("D") && !move.equalsIgnoreCase("A"));
        }
        //If player is in left or right column but not in the corner. 
        else if ((y3 == 0 && (x3 != 0 && x3 != 6)) || ((y3 == 6 && (x3 != 0 && x3 != 6)))) {
            System.out.print("Please use WASD to make your move. You can go up(W) or down(S): ");
            move = sc.next();
            do {
                if (!move.equalsIgnoreCase("W") && !move.equalsIgnoreCase("S")) {
                    System.out.print("Invalid choice. Please enter up(W) or down(S): ");
                    move = sc.next();
                }
            } while (!move.equalsIgnoreCase("W") && !move.equalsIgnoreCase("S"));
        }
        return move; //returns the direction user chose. 
    }//end of player move method. 

    //Method that changes the position of the player using direction, coordinates and the dice roll value.  
    private int[] changePosition(int x3, int y3, int dice, String direction) {

        //If user is in top left corner.
        if (x3 == 0 && y3 == 0) {
            do {
                if (!direction.equalsIgnoreCase("d") && !direction.equalsIgnoreCase("s")) {
                    System.out.print("Invalid input. Please choose right (D) or down(S): ");
                    direction = sc.next();
                }
            } while (!direction.equalsIgnoreCase("d") && !direction.equalsIgnoreCase("s"));

            //Changing the value to x or y depending on which direction the user goes. 
            if (direction.equalsIgnoreCase("d")) {
                y3 += dice; //If they go left, only y val. will change.
            } else if (direction.equalsIgnoreCase("s")) {
                x3 += dice; //If they go down, only x val. will change. 
            }
        }
        //If user is in top right corner. 
        else if (x3 == 0 && y3 == 6) {
            do {
                if (!direction.equalsIgnoreCase("a") && !direction.equalsIgnoreCase("s")) {
                    System.out.print("Invalid input. Please choose left (A) or down(S): ");
                    direction = sc.next();
                }
            } while (!direction.equalsIgnoreCase("a") && !direction.equalsIgnoreCase("s"));

            if (direction.equalsIgnoreCase("a")) {
                y3 -= dice;
            } else if (direction.equalsIgnoreCase("s")) {
                x3 += dice;
            }
        }
        //If user is in bottom left corner.
        else if (x3 == 6 && y3 == 0) {
            do {
                if (!direction.equalsIgnoreCase("d") && !direction.equalsIgnoreCase("w")) {
                    System.out.print("Invalid input. Please choose up (W) or right(D): ");
                    direction = sc.next();
                }
            } while (!direction.equalsIgnoreCase("w") && !direction.equalsIgnoreCase("d"));

            if (direction.equalsIgnoreCase("w")) {
                x3 -= dice;
            } else if (direction.equalsIgnoreCase("d")) {
                y3 += dice;
            }
        }
        //If user is in bottom right corner. 
        else if (x3 == 6 && y3 == 6) {
            do {
                if (!direction.equalsIgnoreCase("a") && !direction.equalsIgnoreCase("w")) {
                    System.out.print("Invalid input. Please choose left (A) or up(W): ");
                    direction = sc.next();
                }
            } while (!direction.equalsIgnoreCase("a") && !direction.equalsIgnoreCase("w"));

            if (direction.equalsIgnoreCase("a")) {
                y3 -= dice;
            } else if (direction.equalsIgnoreCase("w")) {
                x3 -= dice;
            }
        }
        //If user is in the top row but not a corner. 
        else if ((x3 == 0 && (y3 != 0 || y3 != 6))) {
            do {
                if (!direction.equalsIgnoreCase("d") && !direction.equalsIgnoreCase("a")) {
                    System.out.print("Invalid input. Please choose right (D) or left(A): ");
                    direction = sc.next();
                }
            } while (!direction.equalsIgnoreCase("A") && !direction.equalsIgnoreCase("D"));

            int totalPosition = y3 + dice;
            int canMoveVal = y3 - dice; //This is used to determine if the move amount causes user to switch to a different side of the board.

            if (direction.equalsIgnoreCase("A")) {

                if (canMoveVal < 0) {
                    //If canMoveVal is neg, it means user must be switching to the left col. thus y is 0 and x is changing. Same logic used for loops below. 
                    y3 = 0;
                    x3 += canMoveVal * -1;
                } else if (canMoveVal >= 0) {
                    y3 -= dice;
                }

            } else if (direction.equalsIgnoreCase("D")) {
                if (totalPosition <= 6) {
                    y3 += dice;
                } else if (totalPosition > 6) {
                    int passBoundary = totalPosition - 6;
                    y3 = 6;
                    x3 += passBoundary;
                }
            }
        }
        //if user is in the bottom row but not a corner. 
        else if (x3 == 6 && (y3 != 0 && y3 != 6)) {
            do {
                if (!direction.equalsIgnoreCase("d") && !direction.equalsIgnoreCase("a")) {
                    System.out.print("Invalid input. Please choose right (D) or left(A): ");
                    direction = sc.next();
                }
            } while (!direction.equalsIgnoreCase("A") && !direction.equalsIgnoreCase("D"));

            int totalPosition = y3 + dice;
            int canMoveVal = y3 - dice;

            if (direction.equalsIgnoreCase("A")) {
                if (canMoveVal < 0) {
                    y3 = 0;
                    x3 -= canMoveVal * -1;
                } else if (canMoveVal >= 0) {
                    y3 -= dice;
                }
            } else if (direction.equalsIgnoreCase("d")) {
                if (totalPosition <= 6) {
                    y3 += dice;
                } else if (totalPosition > 6) {
                    int passBound = totalPosition - 6;
                    y3 = 6;
                    x3 -= passBound;
                }
            }
        }
        //If user is in the left column and not a corner. 
        else if (y3 == 0 && (x3 != 0 && x3 != 6)) {
            do {
                if (!direction.equalsIgnoreCase("w") && !direction.equalsIgnoreCase("s")) {
                    System.out.print("Invalid input. Please choose up(W) or down(S): ");
                    direction = sc.next();
                }
            } while (!direction.equalsIgnoreCase("w") && !direction.equalsIgnoreCase("s"));

            int totalPosition = x3 + dice;
            int canMoveVal = x3 - dice;

            if (direction.equalsIgnoreCase("w")) {
                if (canMoveVal < 0) {
                    x3 = 0;
                    y3 += canMoveVal * -1;
                } else if (canMoveVal >= 0) {
                    x3 -= dice;
                }
            } else if (direction.equalsIgnoreCase("s")) {
                if (totalPosition <= 6) {
                    x3 += dice;
                } else if (totalPosition > 6) {
                    int passBound = totalPosition - 6;
                    x3 = 6;
                    y3 += passBound;
                }
            }
        }
        //If user is in right column but not in a corner. 
        else if (y3 == 6 && (x3 != 0 && x3 != 6)) {
            do {
                if (!direction.equalsIgnoreCase("w") && !direction.equalsIgnoreCase("s")) {
                    System.out.print("Invalid input. Please choose up(W) or down(S): ");
                    direction = sc.next();
                }
            } while (!direction.equalsIgnoreCase("w") && !direction.equalsIgnoreCase("s"));

            int totalPosition = x3 + dice;
            int canMoveVal = x3 - dice;
            if (direction.equalsIgnoreCase("w")) {
                if (canMoveVal < 0) {
                    x3 = 0;
                    y3 -= canMoveVal * -1;
                } else if (canMoveVal >= 0) {
                    x3 -= dice;
                }
            } else if (direction.equalsIgnoreCase("s")) {
                if (totalPosition <= 6) {
                    x3 += dice;
                } else if (totalPosition > 6) {
                    int passBound = totalPosition - 6;
                    x3 = 6;
                    y3 -= passBound;
                }
            }
        }
        int[] newCoordinates = {x3, y3};
        return newCoordinates;
    }//end of changePosition method.

    //Method that asks question depending on level choice and coordinates. 
    private boolean askQuestion(boolean playerOneTurn, int x3, int y3, int levelChoice) {
         
        Questions qObject = new Questions(); //Used to call arrays of the questions class.
        int Qnum = (int) (Math.random() * 15); //Used to ask random question when on a regular square.
        int QCategory = (int) (Math.random() * 4);//Used to choose random category when on a regular square. 

        //Store ans given and real answer of question. 
        String ansGiven = "";
        char realAns=' ';
        boolean ansCorrectly = false;

        //If user chose easy level, question will be asked from that.  
        if (levelChoice == 1) {

            //Depending on whose turn, appropriate message will be displayed. 
            if (playerOneTurn) {
                System.out.println("User 1, here is your question:");
            } else System.out.println("User 2, here is your question:");

            //If user is in a special square, question of the square's category is called. 
            if (x3 == 0 && y3 == 0) {
                System.out.println(qObject.easyGeoQ[Qnum]);
                ansGiven = sc.next();
                realAns = qObject.easyGeoA[Qnum];
            } else if (x3 == 0 && y3 == 6) {
                System.out.println(qObject.easyEntQ[Qnum]);
                ansGiven = sc.next();
                realAns = qObject.easyEntA[Qnum];
            } else if (x3 == 6 && y3 == 0) {
                System.out.println(qObject.easyHisQ[Qnum]);
                ansGiven = sc.next();
                realAns = qObject.easyHisA[Qnum];
            } else if (x3 == 6 && y3 == 6) {
                System.out.println(qObject.easySciQ[Qnum]);
                ansGiven = sc.next();
                realAns = qObject.easySciA[Qnum];
            } else {

              //Ask question when on normal square. Category is randomized and question # is randomized. 
                if (QCategory == 0) {
                    System.out.println(qObject.easyGeoQ[Qnum]);
                    ansGiven = sc.next();
                    realAns = qObject.easyGeoA[Qnum];
                } else if (QCategory == 1) {
                    System.out.println(qObject.easyEntQ[Qnum]);
                    ansGiven = sc.next();
                    realAns = qObject.easyEntA[Qnum];
                } else if (QCategory == 2) {
                    System.out.println(qObject.easyHisQ[Qnum]);
                    ansGiven = sc.next();
                    realAns = qObject.easyHisA[Qnum];
                } else if (QCategory == 3) {
                    System.out.println(qObject.easySciQ[Qnum]);
                    ansGiven = sc.next();
                    realAns = qObject.easySciA[Qnum];
                }
            }
            //If ans is not one letter, then it is invalid. 
            do {
                if(ansGiven.length()>1){
                  System.out.print("Invalid answer. Please input a, b or c: ");
                  ansGiven = sc.next(); 
                }
            } while (ansGiven.length()>1);
            
            //Store ans as a char if it is one character only. 
            char ansGivenC=ansGiven.charAt(0);

            //If ans is one char but not a valid option then asks again. 
            do{
                if (ansGivenC != 'a' && ansGivenC != 'b' && ansGivenC != 'c') {
                    System.out.print("Invalid answer. Please input a, b or c: ");
                    ansGivenC = sc.next().charAt(0); 
                }              
            }while(ansGivenC != 'a' && ansGivenC != 'b' && ansGivenC != 'c');

            //If correct or not display appropriate message and change value of variables. 
            if (ansGivenC == realAns) {
                System.out.println("Correct answer! You can roll again!");
                System.out.println();
                ansCorrectly = true;
            } else if (ansGivenC != realAns) {
                if (playerOneTurn)
                    System.out.println("Incorrect answer! It is User 2's turn.");
                else System.out.println("Incorrect answer! it is User 1's turn.");
                ansCorrectly = false;
            }

        //Calling the hard question/ans arrays.     
        } else if (levelChoice == 2) {
            if (playerOneTurn) {
              System.out.println("User 1, here is your question:");
            } else System.out.println("User 2, here is your question:");
            
            //If on special square. 
            if (x3 == 0 && y3 == 0) {
                System.out.println(qObject.hardGeoQ[Qnum]);
                ansGiven = sc.next();
                realAns = qObject.hardGeoA[Qnum];
            } else if (x3 == 0 && y3 == 6) {
                System.out.println(qObject.hardEntQ[Qnum]);
                ansGiven = sc.next();
                realAns = qObject.hardEntA[Qnum];
            } else if (x3 == 6 && y3 == 0) {
                System.out.println(qObject.hardHisQ[Qnum]);
                ansGiven = sc.next();
                realAns = qObject.hardHisA[Qnum];
            } else if (x3 == 6 && y3 == 6) {
                System.out.println(qObject.hardSciQ[Qnum]);
                ansGiven = sc.next();
                realAns = qObject.hardSciA[Qnum];
            } else {

                //If on a normal square, question + category randomized.  
                if (QCategory == 0) {
                    System.out.println(qObject.hardGeoQ[Qnum]);
                    ansGiven = sc.next();
                    realAns = qObject.hardGeoA[Qnum];
                } else if (QCategory == 1) {
                    System.out.println(qObject.hardEntQ[Qnum]);
                    ansGiven = sc.next();
                    realAns = qObject.hardEntA[Qnum];
                } else if (QCategory == 2) {
                    System.out.println(qObject.hardHisQ[Qnum]);
                    ansGiven = sc.next();
                    realAns = qObject.hardHisA[Qnum];
                } else if (QCategory == 3) {
                    System.out.println(qObject.hardSciQ[Qnum]);
                    ansGiven = sc.next();
                    realAns = qObject.hardSciA[Qnum];
                }
            }
            //Ensuring only one char as ans. 
            do {
                if(ansGiven.length()>1){
                  System.out.print("Invalid answer. Please input a, b or c: ");
                  ansGiven = sc.next(); 
                }
            } while (ansGiven.length()>1);
            
            char ansGivenC=ansGiven.charAt(0);

            //Ensuring validity of ans. 
            do{
                if (ansGivenC != 'a' && ansGivenC != 'b' && ansGivenC != 'c') {
                    System.out.print("Invalid answer. Please input a, b or c: ");
                    ansGivenC = sc.next().charAt(0); 
                }              
            }while(ansGivenC != 'a' && ansGivenC != 'b' && ansGivenC != 'c');

            //Displaying message depending on whether ans is correct or not. 
            if (ansGivenC == realAns) {
                System.out.println("Correct answer! You can roll again!");
                System.out.println();
                ansCorrectly = true;
            } else if (ansGivenC != realAns) {
                if (playerOneTurn)
                    System.out.println("Incorrect answer! It is User 2's turn.");
                else System.out.println("Incorrect answer! it is User 1's turn.");
                ansCorrectly = false;
            }
        }
        return ansCorrectly;
    }// end of ask question method. 
  
    //Displays menu at the beginning of the game. 
    private int menu() throws Exception {
        int levelChoice = 0;
        //displaying menu  
        System.out.println("\n\nWelcome to Trivial Pursuit! Please select one of the following options: Press 1 for play, 2 for instructions, and 3 for exit.");
        int input = sc.nextInt();

        //Ensuring option chosen is valid. 
        do {
            if (input != 1 && input != 2 && input != 3) {
                System.out.print("Invalid input! Please enter 1, 2 or 3: ");
                input = sc.nextInt();
            }
        } while (input != 1 && input != 2 && input != 3);

        //Nested if loop to handle different options chosen by user. 
        if (input == 1) {
            System.out.println("Please select the level you would like to play: \n1) Easy \n2) Hard");
            levelChoice = sc.nextInt();

            do {
                if (levelChoice != 1 && levelChoice != 2) {
                    System.out.println("Invalid input! Please enter 1(easy) or 2(hard) to start playing: ");
                    levelChoice = sc.nextInt();
                }
            } while (levelChoice != 1 && levelChoice != 2);

        //Displaying instructions.     
        } else if (input == 2) {
            System.out.print("\n\t\t\t\tINSTRUCTIONS \n\n\t1) The objective of this two player game is to complete \n\tyour 'pie', each player has 4 pie slots for 4 different \n\tcategories: Geography, Entertainment, History, and Science. \n\n\t2) To begin, you will be prompted to roll the dice.\n\n\t3) Then, you can move up(w), down(s), right(d), or left(a)\n\t using the WASD keys. \n\n\t4) There are two types of squares you can land on.\n\tA special \"corner\" square has { } brackets and a normal square \n\thas [ ] brackets. \n\n\t5) When you land on a special square, you will get a question \n\tof a category depending on which corner you are at. \n\n\t6) Top left is the Geography corner, top right is the \n\tEntertainment corner, bottom left is the History corner \n\tand bottom right is the Science corner. \n\n\t7) On a normal square, you will get asked a random question \n\tfrom any of the four categories. \n\n\t8) To continue your turn, you must answer the given \n\tquestion correctly. \n\n\t9) Once you reach a corner square, you must answer a \n\tquestion correctly to receive a pie piece. \n\n\t10) You win the game once you have collected one pie piece\n\tfrom each special corner!\n\n\t11) NOTE: If both players land on the same tile in the game,\n\tthe number in the square indicating the users' position \n\twill change to a 3 instead of having a 1 or 2 inside!\n\n");

            //Asking user to start game and storing level choice.  
            System.out.print("Please press enter to play the game.\n");
            br.readLine();
            System.out.println("Please select the level you would like to play: \n1) Easy \n2) Hard");
            levelChoice = sc.nextInt();

            //Ensuring validity of input. 
            do {
                if (levelChoice != 1 && levelChoice != 2) {
                    System.out.println("Invalid input! Please enter 1(easy) or 2(hard) to start playing: ");
                    levelChoice = sc.nextInt();
                }
            } while (levelChoice != 1 && levelChoice != 2);

        //Exit game if user wishes to do so. 
        } else if (input == 3) {
            System.out.println("Thank you for playing our game, goodbye!");
            System.exit(0); //Close game if user exits. 
        }
        return levelChoice; 
    }//end of menu method.

    //Runs the menu and player turn method. 
    private void gamePlay(int dice, int x1, int y1, int x2, int y2, int x3, int y3, boolean playerOneTurn, boolean geoPieceOne, boolean entPieceOne, boolean hisPieceOne, boolean sciPieceOne, boolean geoPieceTwo, boolean entPieceTwo, boolean hisPieceTwo, boolean sciPieceTwo, int whichUserWon) throws Exception {
         
        int levelChoice = 1;//variable to hold level choice of user.
        levelChoice = menu(); //display menu and store level choice.  

        //Call player Turn method.   
        playerTurn(levelChoice, dice, x1, y1, x2, y2, x3, y3, playerOneTurn, geoPieceOne, entPieceOne, hisPieceOne, sciPieceOne, geoPieceTwo, entPieceTwo, hisPieceTwo, sciPieceTwo, whichUserWon);
    }//end of game play method. 

    //Method that essentially runs the game. Calls all other methods. 
    private void playerTurn(int levelChoice, int dice, int x1, int y1, int x2, int y2, int x3, int y3, boolean playerOneTurn, boolean geoPieceOne, boolean entPieceOne, boolean hisPieceOne, boolean sciPieceOne, boolean geoPieceTwo, boolean entPieceTwo, boolean hisPieceTwo, boolean sciPieceTwo, int whichUserWon) throws Exception {

        //declaring ansCorrectly variable. 
        boolean ansCorrectly = true;

        //test value of who won.
        if (testing)
            System.out.println("Value of which user won at the beginning of player turn method: " + whichUserWon);

        do {//Loop keeps running until someone wins. 

            if (testing)
                System.out.println("Value of which user won at the beginning of do while loop that checks if someone won: " + whichUserWon);

            do { //Loop keeps running as long as user keeps answering correctly (Hence the "reroll").

                if (testing)
                    System.out.println("Value of which user won at the beginning of do while loop that checks if someone answered correctly: " + whichUserWon);

                //turning netural coodinates to player 1 coordinates or player 2. 
                if (playerOneTurn) {
                    x3 = x1;
                    y3 = y1;
                } else {
                    x3 = x2;
                    y3 = y2;
                }

                boardSetup(x1, y1, x2, y2); //Displaying board.
                promptForRoll(playerOneTurn); //asking user to roll dice. 
                dice = diceRoll(playerOneTurn); //storing dice roll value. 

                if (testing) System.out.println("dice value: " + dice);

                String move = playerMove(x3, y3); //Storing direction user chose to move. 

                int[] newPos = changePosition(x3, y3, dice, move); //storing new coordinates after move was made.

                //Storing new coordinates after user has moved. 
                x3 = newPos[0]; 
                y3 = newPos[1];

                //Depending on whose turn, the "changed" coordinates will equal to user 1's or user 2's so it becomes "their" move in the end. 
                if (playerOneTurn) {
                    x1 = x3;
                    y1 = y3;
                } else {
                    x2 = x3;
                    y2 = y3;
                }
                
                boardSetup(x1, y1, x2, y2);//Displays board after user has moved.
                ansCorrectly = askQuestion(playerOneTurn, x3, y3, levelChoice);//Asks question and stores result. 

                if (testing) System.out.println("value of ansCorrectly: " + ansCorrectly);

                //If user is at special square, headQuarteSquare method is called to deal with piece wins. 
                if ((x3 == 0 && y3 == 0) || (x3 == 0 && y3 == 6) || (x3 == 6 && y3 == 0) || (x3 == 6 && y3 == 6)) {

                    if (testing)
                        System.out.println("Value of which user inside if loop that checks if user is in a special square: " + whichUserWon);

                    if (testing)
                        System.out.println("value of all four pieces inside of loop condition for specialsquare within playerTurn for user 2: " + geoPieceTwo + " " + entPieceTwo + " " + hisPieceTwo + " " + sciPieceTwo);

                    //Stores the pieces that user 1 and 2 have acquired or not as true and false. 
                    boolean[] categoryPieceCount = headQuarterSquare(playerOneTurn, x3, y3, geoPieceOne, entPieceOne, hisPieceOne, sciPieceOne, geoPieceTwo, entPieceTwo, hisPieceTwo, sciPieceTwo, ansCorrectly, whichUserWon);

                    //information received from the method gets stored into appropriate variables. 
                    geoPieceOne = categoryPieceCount[0];
                    entPieceOne = categoryPieceCount[1];
                    hisPieceOne = categoryPieceCount[2];
                    sciPieceOne = categoryPieceCount[3];
                    geoPieceTwo = categoryPieceCount[4];
                    entPieceTwo = categoryPieceCount[5];
                    hisPieceTwo = categoryPieceCount[6];
                    sciPieceTwo = categoryPieceCount[7];

                    if (testing)
                        System.out.println("value of all four pieces inside of loop condition for specialsquare within playerTurn for user 2: " + geoPieceTwo + " " + entPieceTwo + " " + hisPieceTwo + " " + sciPieceTwo);
                }
                if (testing)
                    System.out.println("Value of which user won within before it is stored using who won method: " + whichUserWon);

                //If either user has all four pieces, whichUserWon method gets called.
                if ((geoPieceOne && entPieceOne && hisPieceOne && sciPieceOne) || (geoPieceTwo && entPieceTwo && hisPieceTwo && sciPieceTwo)) {

                    //Stores who won. Values possible are 0 (no one), 1 (user 1), 2 (user 2). 
                    whichUserWon = whoWon(playerOneTurn, geoPieceOne, entPieceOne, hisPieceOne, sciPieceOne, geoPieceTwo, entPieceTwo, hisPieceTwo, sciPieceTwo, whichUserWon);
                }

                if (testing)
                    System.out.println("Value of which user won after it is stored using who won method: " + whichUserWon);

                if (testing)
                    System.out.println("value of all four pieces inside of loop condition for specialsquare within playerTurn for user 2: " + geoPieceTwo + " " + entPieceTwo + " " + hisPieceTwo + " " + sciPieceTwo);

            } while (ansCorrectly && whichUserWon == 0);

            if (testing) System.out.println("Value of which user won after first do while loop: " + whichUserWon);

            //If someone answered incorrectly, and no one has won, then turns get switched and playerTurn method is called again to run other user's turn. 
            if (!ansCorrectly && playerOneTurn && whichUserWon == 0) {
                playerOneTurn = false;
                playerTurn(levelChoice, dice, x1, y1, x2, y2, x3, y3, playerOneTurn, geoPieceOne, entPieceOne, hisPieceOne, sciPieceOne, geoPieceTwo, entPieceTwo, hisPieceTwo, sciPieceTwo, whichUserWon);
            } else if (!ansCorrectly && !playerOneTurn && whichUserWon == 0) {
                if (testing)
                    System.out.println("Value of which user won inside if loop that switches turn to player 1 if they answer incorrectly and nobody has won: " + whichUserWon);
                playerOneTurn = true;
                playerTurn(levelChoice, dice, x1, y1, x2, y2, x3, y3, playerOneTurn, geoPieceOne, entPieceOne, hisPieceOne, sciPieceOne, geoPieceTwo, entPieceTwo, hisPieceTwo, sciPieceTwo, whichUserWon);
            }
        } while (whichUserWon == 0);

        if (testing) System.out.println("value of which user won at end of do while loop: " + whichUserWon);

        //Display appropriate message depending on who won and end the game. 
        if (whichUserWon == 1) {
            System.out.println("\n\tCONGRATULATIONS User 1!! You won the game!");
            System.exit(0);
        } else if (whichUserWon == 2) {
            System.out.println("\n\tCONGRATULATIONS User 2!! You won the game!");
            System.exit(0);
        }
    }//end of player turn method. 

    //Method to deal with the winning of pieces if user ans question correctly when on a special square. 
    private boolean[] headQuarterSquare(boolean playerOneTurn, int x3, int y3, boolean geoPieceOne, boolean entPieceOne, boolean hisPieceOne, boolean sciPieceOne, boolean geoPieceTwo, boolean entPieceTwo, boolean hisPieceTwo, boolean sciPieceTwo, boolean ansCorrectly, int whichUserWon) {

        //For user 1, if they ans correctly on a special square, they win piece of that square's category. 
        if (playerOneTurn) {

            if (testing)
                System.out.println("value of all four pieces inside of loop condition for specialsquare within playerTurn for user 2: " + geoPieceTwo + " " + entPieceTwo + " " + hisPieceTwo + " " + sciPieceTwo);

            if (x3 == 0 && y3 == 0 && ansCorrectly && !geoPieceOne) {
                geoPieceOne = true;
                System.out.println("Congratulations, User 1! You have won the Geography piece.");
                if (testing) System.out.println("value of ent piece one after correctly answering: " + entPieceOne);
            } else if (x3 == 0 && y3 == 6 && ansCorrectly && !entPieceOne) {
                entPieceOne = true;
                System.out.println("Congratulations, User 1! You have won the Entertainment piece.");
            } else if (x3 == 6 && y3 == 0 && ansCorrectly && !hisPieceOne) {
                hisPieceOne = true;
                System.out.println("Congratulations, User 1! You have won the History piece.\nCollect the remaining pieces to win the game!");
            } else if (x3 == 6 && y3 == 6 && ansCorrectly && !sciPieceOne) {
                sciPieceOne = true;
                System.out.println("Congratulations, User 1! You have won the Science piece.\nCollect the remaining pieces to win the game!");
            }

        //Same for player two.     
        } else if (!playerOneTurn) {

            if (x3 == 0 && y3 == 0 && ansCorrectly && !geoPieceTwo) {
                geoPieceTwo = true;
                System.out.println("Congratulations, User 2! You have won the Geography piece.\nCollect the remaining pieces to win the game!");
            } else if (x3 == 0 && y3 == 6 && ansCorrectly && !entPieceTwo) {
                entPieceTwo = true;
                System.out.println("Congratulations, User 2! You have won the Entertainment piece.\nCollect the remaining pieces to win the game!");
            } else if (x3 == 6 && y3 == 0 && ansCorrectly && !hisPieceTwo) {
                hisPieceTwo = true;
                System.out.println("Congratulations, User 2! You have won the History piece.\nCollect the remaining pieces to win the game!");
            } else if (x3 == 6 && y3 == 6 && ansCorrectly && !sciPieceTwo) {
                sciPieceTwo = true;
                System.out.println("Congratulations, User 2! You have won the Science piece.\nCollect the remaining pieces to win the game!");
            }
        }

        //stores the pieces that have been won or not for both users and is returned by the method. 
        boolean[] categoryPieceCount = {geoPieceOne, entPieceOne, hisPieceOne, sciPieceOne, geoPieceTwo, entPieceTwo, hisPieceTwo, sciPieceTwo};

        if (testing)
            System.out.println("value of all four pieces inside of loop condition for specialsquare within playerTurn for user 2: " + geoPieceTwo + " " + entPieceTwo + " " + hisPieceTwo + " " + sciPieceTwo);

        return categoryPieceCount;
    }//end of head quarter square method. 

    //Method which returns an appropriate int value signifying who has won. 
    private int whoWon(boolean playerOneTurn, boolean geoPieceOne, boolean entPieceOne, boolean hisPieceOne, boolean sciPieceOne, boolean geoPieceTwo, boolean entPieceTwo, boolean hisPieceTwo, boolean sciPieceTwo, int whichUserWon) {

        //Depending on which user has their pieces, they will win. 
        if (geoPieceOne && entPieceOne && hisPieceOne && sciPieceOne) {
            if (testing) System.out.println("Value of which user won within testing loop: " + whichUserWon);
            whichUserWon = 1;
        } else if (geoPieceTwo && entPieceTwo && hisPieceTwo && sciPieceTwo) {
            whichUserWon = 2;
        }
        if (testing) System.out.println("Value of which user at the end of who Won method: " + whichUserWon);
        return whichUserWon;
    }//end of who won method.  
}//end of class. 
