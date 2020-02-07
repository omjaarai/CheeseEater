//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           Cheese Eater
// Files:           Main
// Course:          CS 300 spring 2018
//
// Author:          Omjaa Rai
// Email:           orai@wisc.edu
// Lecturer's Name: Mouna Ayari Ben Hadj Kacem
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Sahana Iyer
// Partner Email:   siyer6@wisc.edu
// Lecturer's Name: Mouna Ayari Ben Hadj Kacem
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   X Write-up states that pair programming is allowed for this assignment.
//   X We have both read and understand the course Pair Programming Policy.
//  
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates 
// strangers, etc do.  If you received no outside help from either type of 
// source, then please explicitly indicate NONE.
//
// Persons:         None
// Online Sources:  None
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static final char CHEESE_WALL='#';
    public static final char EMPTY='.';
    public static final char CHEESE='%';


    public static void main(String[] args) {
        int roomWidth = 20;
        int roomHeight = 10;
        int cheeseCount=0;
        int[] mousePosition=new int[2];
        Scanner scnr=new Scanner(System.in);


        char[][] room = new char[roomHeight][roomWidth];
        Random random=new Random();
        // System.out.println(room.length);
        //System.out.println(room[0].length);
        placeWalls(room,20,random);
        //for(int i = 0; i < room.length; i++) {
        //  for(int j = 0; j < room[0].length; j++) {
        //    System.out.print(room[i][j]);
        //}
        // System.out.println(" ");
        //}


        int[][] cheesePositions = new int[10][2];
        placeCheeses(cheesePositions, room, random);


        //add mouse later
        int mousePosx=random.nextInt(20);
        int mousePosy=random.nextInt(10);


        System.out.print("Welcome to the Cheese Eater simulation.\r\n" + 
            "=======================================\r\n"+
            "Enter the number of steps for this simulation to run: ");
        int steps=scnr.nextInt();
        room[mousePosy][mousePosx]='@';
        printRoom(room, cheesePositions,mousePosx, mousePosy);


        while(steps>0) {
            room[mousePosy][mousePosx]='@';
            System.out.println("\nThe mouse has eaten "+ cheeseCount+" cheese!");
            printRoom(room, cheesePositions,mousePosx, mousePosy);
            System.out.print("Enter the next step you'd like the mouse to take (WASD):");
            char move= (scnr.next()).charAt(0);
            mousePosition= moveMouse(mousePosx, mousePosy, room, move);
            if(mousePosition!=null) {
            int nmousePosx=mousePosition[0];
            int nmousePosy=mousePosition[1];
            if(mousePosx!=nmousePosx|| mousePosy!=nmousePosy) {
                room[mousePosy][mousePosx]=EMPTY;
                mousePosx=nmousePosx;
                mousePosy=nmousePosy;
            }
            }
            else{
                continue;
            }
            // System.out.print(tryToEatCheese(mousePosx, mousePosy, cheesePositions));
            if(tryToEatCheese(mousePosx, mousePosy, cheesePositions)) {
                cheeseCount++;  
            }
            // for(int i=0; i< room.length;i++) {
            //   for(int j=0; j< room[i].length;j++) {
            //     if(room[mou][mousePosx]==CHEESE) {
            //       cheeseCount++;
            //    }
            //}
            // }
            steps--;


        }
        System.out.println("==================================================\n"+
            "Thank you for running the Cheese Eater simulation.");

        scnr.close();
    }



    public static void placeWalls(char[][] room, int numberOfWalls, Random randGen) {
        int rowNum=0;
        int colNum=0;


        for(int i=0; i<room.length;i++) {
            for(int j=0; j<room[i].length;j++) {

                room[i][j]=EMPTY;

            }
        }

        for(int i=0; i<numberOfWalls; i++) {
            rowNum=randGen.nextInt(room[0].length-1);
            colNum=randGen.nextInt(room.length-1);
            if(room[colNum][rowNum]==CHEESE_WALL)
                i--;


            else
                room[colNum][rowNum]=CHEESE_WALL;

        }



    }


    public static void placeCheeses(int[][] cheesePositions, char[][] room, Random randGen) {
        // int xPos=0;
        // int yPos=0;

        // for(int i=0;i<cheesePositions.length;i++) {
        // xPos=randGen.nextInt(room[0].length);
        // yPos=randGen.nextInt(room.length);

        //if(room[yPos][xPos]==CHEESE_WALL||room[yPos][xPos]==CHEESE) {
        //  i--;
        // }else {
        //  room[yPos][xPos]=CHEESE;
        // }

        int row = 0;
        int col = 0;
        for(int i = 0; i < cheesePositions.length; i++) {
            boolean valid = false;
            while(!valid) {
                row = randGen.nextInt(room.length);
                col = randGen.nextInt(room[0].length);
                if (room[row][col] != CHEESE_WALL) {
                    boolean freeSpot = true;
                    for(int j = 0; j < cheesePositions.length; j++) {
                        if (row == cheesePositions[j][1] && col == cheesePositions[j][0]) {
                            freeSpot = false;
                        }
                    }
                    if(freeSpot) {
                        valid = true;
                    }
                }
            }
            room[row][col] = CHEESE;
            cheesePositions[i][0] = col;
            cheesePositions[i][1] = row;
        }
        // }
    }



    public static void printRoom(char[][] room, int[][] cheesePositions, int mouseX, int mouseY) {

        // for(int i=0; i<room.length;i++) {
        //  for(int j=0; j<room[i].length;j++) {

        //  System.out.print(room[i][j]);

        // }

        //System.out.println();
        // }char empty = '.';
        char mouse = '@';
        boolean isCheese = false;
        for(int i = 0; i < room.length; i++) {
            for(int j = 0; j < room[0].length; j++) {
                isCheese = false;
                for(int c = 0; c < cheesePositions.length; c++) {
                    if (i == cheesePositions[c][1] && j == cheesePositions[c][0]) {
                        isCheese = true;
                    }
                }

                if(i == mouseY && j == mouseX) {
                    System.out.print(mouse);
                }
                else if (isCheese) {
                    System.out.print(CHEESE);
                }
                else {
                    System.out.print(room[i][j]);
                }
            }
            System.out.println();
        }

    }



    public static int[] moveMouse(int mouseX, int mouseY, char[][] room, char move) {
        int[] mousePosition={mouseX,mouseY};

        if(move=='w' || move=='W') {
            mouseY=mouseY-1;
        }else
            if(move=='a' || move=='A') {
                mouseX=mouseX-1;
            }else
                if(move=='s' || move=='S') {
                    mouseY=mouseY+1;
                }else
                    if(move=='d' || move=='D') {
                        mouseX=mouseX+1;
                    }

                    else {
                        System.out.print("WARNING: Didn't recognize move command: "+move );
                        return mousePosition;
                    }

        if( mouseX<0|| mouseY>(room.length-1)|| mouseY<0||mouseX>(room[0].length-1)) {
            System.out.print("WARNING: Mouse cannot move outside the room.");
            return null;

        }

        else if(room[mouseY][mouseX]==CHEESE_WALL) {
            System.out.print("Cannot move into wall");
            return null;

        }
        mousePosition[0]=mouseX;
        mousePosition[1]=mouseY;
        return mousePosition;
    }


    public static boolean tryToEatCheese(int mouseX, int mouseY, int[][] cheesePositions) {
        boolean result=false;
        for(int i=0; i< cheesePositions.length;i++) {
            if(mouseX==cheesePositions[i][0] && mouseY==cheesePositions[i][1]) {
                cheesePositions[i][0]=-1;
                cheesePositions[i][1]=-1; 

                result= true;
            }
        }

        return result;
    }



}

