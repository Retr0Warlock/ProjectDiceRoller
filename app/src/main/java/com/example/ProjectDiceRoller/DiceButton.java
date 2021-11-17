package com.example.ProjectDiceRoller;
import java.util.Random;

public class DiceButton
{
    // Variables
    int sides;
    int rolls;

    // Non-Default Constructor
    public DiceButton(int userSides, int userRolls)
    {
        sides = userSides;
        rolls = userRolls;
    }

    // the main function of the class - rolling and totalling a number of dice rolls
    public int rollDice()
    {
        int total = 0;

        // allows rolls to be decremented without changing original number
        int numRolls = rolls;

        // simulate the number of rolls
        Random random = new Random();
        while(numRolls != 0)
        {
            // 0 becomes 1 and the largest value is possible
            int face = random.nextInt(sides) + 1;
            total += face;
            numRolls--;
        }

        return total;
    }
}
