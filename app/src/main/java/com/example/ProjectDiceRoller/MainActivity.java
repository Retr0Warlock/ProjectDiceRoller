package com.example.ProjectDiceRoller;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Vibrator;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    // two variables for the user's custom dice throw
    int customSides = 0;
    int customRoll = 0;

    int total;
    int newMod;

    // here's something different!
    private Vibrator hapticFeedback;

    // uses button press itself as a "parameter" to roll the dice
    public void rollDice(int sides, int rolls)
    {
        // creates a DiceButton object with the correct parameters and rolls it
        DiceButton dice = new DiceButton(sides, rolls);
        total = dice.rollDice();

        // sends the total to the sum result
        final TextView sumText = findViewById(R.id.sum_text);
        String sum = Integer.toString(total);
        sumText.setText(sum);

        // sends the dice and number of rolls to the results
        final TextView sidesAndRollsText = findViewById(R.id.sides_and_rolls_text);
        String result = "D" + sides + " x " + rolls;
        sidesAndRollsText.setText(result);

        // small bit of feedback for "rolling" a die
        hapticFeedback.vibrate(45);
    }

    // Doubles the current total for critical hits
    public void criticalRoll() {
        total = total*2;
        final TextView sumText = findViewById(R.id.sum_text);
        String sum = Integer.toString(total);
        sumText.setText(sum);
    }

    // Adds given modifier to the current total
    public void addModifier(int mod) {
        total += mod;
        final TextView sumText = findViewById(R.id.sum_text);
        String sum = Integer.toString(total);
        sumText.setText(sum);
    }

    // Subtracts given modifier from current total
    public void subModifier(int mod) {
        if(total < mod){
            total = 0;
        } else {
            total -= mod;
        }
        final TextView sumText = findViewById(R.id.sum_text);
        String sum = Integer.toString(total);
        sumText.setText(sum);
    }


    // sets up the spinner for selecting a custom die
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        // converts die selection to the raw number of sides
        String numSides = (String) parent.getItemAtPosition(pos);
        customSides = Integer.parseInt(numSides.substring(1));
    }

    public void onNothingSelected(AdapterView<?> parent) {} // does nothing for now

    // handles all of the buttons
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // initializes vibration functionality for when a die is "rolled"
        hapticFeedback = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        // hides the action bar for near-fullscreen
        if(getSupportActionBar() != null)
            this.getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        // roll a D4
        Button buttonD4 = findViewById(R.id.d4);
        buttonD4.setOnClickListener(view -> rollDice(4, 1));

        // roll a D6
        Button buttonD6 = findViewById(R.id.d6);
        buttonD6.setOnClickListener(view -> rollDice(6, 1));

        // roll a D8
        Button buttonD8 = findViewById(R.id.d8);
        buttonD8.setOnClickListener(view -> rollDice(8, 1));

        // roll a D10
        Button buttonD10 = findViewById(R.id.d10);
        buttonD10.setOnClickListener(view -> rollDice(10, 1));

        // roll a D12
        Button buttonD12 = findViewById(R.id.d12);
        buttonD12.setOnClickListener(view -> rollDice(12, 1));

        // roll a D20
        Button buttonD20 = findViewById(R.id.d20);
        buttonD20.setOnClickListener(view -> rollDice(20, 1));

        // roll a D100
        Button buttonD100 = findViewById(R.id.d100);
        buttonD100.setOnClickListener(view -> rollDice(100, 1));

        // roll the custom choices
        Button buttonCustom = findViewById(R.id.dCustom);
        buttonCustom.setOnClickListener(view -> rollDice(customSides, customRoll));

        //Button to add modifiers to the rolled number
        Button buttonDAdd = findViewById(R.id.dAdd);
        EditText modifier1 = findViewById(R.id.dModifier);
        modifier1.setOnEditorActionListener((v, actionId, event) -> {
            newMod = Integer.parseInt(String.valueOf(modifier1.getText()));
            return false;
        });
        buttonDAdd.setOnClickListener(view -> addModifier(newMod));

        //Button to Subtract modifiers from the number rolled
        Button buttonDSub = findViewById(R.id.dSubtract);
        EditText modifier2 = findViewById(R.id.dModifier);
        modifier2.setOnEditorActionListener((v, actionId, event) -> {
            newMod = Integer.parseInt(String.valueOf(modifier2.getText()));
            return false;
        });
        buttonDSub.setOnClickListener(view -> subModifier(newMod));

        //Button to double the number rolled for Critical hits.
        Button buttonDCritical = findViewById(R.id.dCritical);
        buttonDCritical.setOnClickListener(view -> criticalRoll());

        /* Change to allow user to choose how many sides the die has.*/

        // allows user to choose dice to roll
        Spinner diceSpinner = findViewById(R.id.dice_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dice_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diceSpinner.setAdapter(adapter);
        diceSpinner.setOnItemSelectedListener(this);

        // allows user to choose rolls for a custom roll
        EditText numRolls = findViewById(R.id.num_rolls);
        numRolls.setOnEditorActionListener((v, actionId, event) -> {
            // translates EditText to String to Integer... whew!
            customRoll = Integer.parseInt(String.valueOf(numRolls.getText()));
            return false;
        });
    }
}