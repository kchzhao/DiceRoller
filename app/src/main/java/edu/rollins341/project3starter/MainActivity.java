package edu.rollins341.project3starter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Layout;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

//HC:Kamila Chzhao
public class MainActivity extends AppCompatActivity implements RollLengthDialogFragment.OnRollLengthSelectedListener {

    public static final int MAX_DICE = 5;

    private int mVisibleDice;
    private Dice[] mDice;
    private ImageView[] mDiceImageViews;
    private Menu mMenu;
    private CountDownTimer mTimer;
    private long mTimerLength = 2000;
    private int mCurrentDie;

    private View column2;

    private int selectedColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an array of Dice
        mDice = new Dice[MAX_DICE];
        for (int i = 0; i < MAX_DICE; i++) {
            mDice[i] = new Dice(i + 1);
        }

        // Create an array of ImageViews
        mDiceImageViews = new ImageView[MAX_DICE];
        mDiceImageViews[0] = findViewById(R.id.dice1);
        mDiceImageViews[1] = findViewById(R.id.dice2);
        mDiceImageViews[2] = findViewById(R.id.dice3);
        mDiceImageViews[3] = findViewById(R.id.dice4);
        mDiceImageViews[4] = findViewById(R.id.dice5);

        // All dice are initially visible
        mVisibleDice = MAX_DICE;

        registerForContextMenu(mDiceImageViews[0]);

        showDice();

        // Register context menus for all dice and tag each die
        for (int i = 0; i < mDiceImageViews.length; i++) {
            registerForContextMenu(mDiceImageViews[i]);
            mDiceImageViews[i].setTag(i);
        }
    }

    @Override
    public void onRollLengthClick(int which) {
        // Convert to milliseconds
        mTimerLength = 1000L * (which + 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    private void showDice() {
        // Display only the number of dice visible
        for (int i = 0; i < mVisibleDice; i++) {
            Drawable diceDrawable = ContextCompat.getDrawable(this, mDice[i].getImageId());
            mDiceImageViews[i].setImageDrawable(diceDrawable);
            mDiceImageViews[i].setContentDescription(Integer.toString(mDice[i].getNumber()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Determine which menu option was chosen
        if (item.getItemId() == R.id.action_one) {
            changeDiceVisibility(1);
            showDice();
            return true;
        }
        else if (item.getItemId() == R.id.action_two) {
            changeDiceVisibility(2);
            showDice();
            return true;
        }
        else if (item.getItemId() == R.id.action_three) {
            changeDiceVisibility(3);
            showDice();
            return true;
        }
        else if (item.getItemId() == R.id.action_four) {
            changeDiceVisibility(4);
            showDice();
            return true;
        }
        else if (item.getItemId() == R.id.action_five) {
            changeDiceVisibility(5);
            showDice();
            return true;
        }
        else if (item.getItemId() == R.id.action_stop) {
            mTimer.cancel();
            item.setVisible(false);
            mMenu.findItem(R.id.action_roll_length).setVisible(true);
            mMenu.findItem(R.id.action_roll).setVisible(true);
            return true;
        }
        else if (item.getItemId() == R.id.action_roll) {
            rollDice();
            return true;

        }
        else if (item.getItemId() == R.id.action_roll_length) {
            RollLengthDialogFragment dialog = new RollLengthDialogFragment();
            dialog.show(getSupportFragmentManager(), "rollLengthDialog");
            return true;
        }
        else if(item.getItemId() == R.id.action_six){
            Intent intent = new Intent(this, ChangeColorActivity.class);
            mColorResultLauncher.launch(intent);
            return true;
        }
        else if(item.getItemId() == R.id.action_seven){
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        mCurrentDie = (int) v.getTag();   // Which die is selected?
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_one) {
            mDice[mCurrentDie].addOne();
            showDice();
            return true;
        }
        else if (item.getItemId() == R.id.subtract_one) {
            mDice[mCurrentDie].subtractOne();
            showDice();
            return true;
        }
        else if (item.getItemId() == R.id.roll) {
           //rollDice();
            rollDice(mDice[mCurrentDie]);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    private void changeDiceVisibility(int numVisible) {
        mVisibleDice = numVisible;
        LinearLayout column2 = findViewById(R.id.column2);

        if (numVisible <= 3) {
            column2.setVisibility(View.GONE);

        // Make dice visible
        for (int i = 0; i < numVisible; i++) {
            mDiceImageViews[i].setVisibility(View.VISIBLE);
        }
        }
        else if(numVisible > 3){
            column2.setVisibility(View.VISIBLE);

                // Make dice visible
                for (int i = 0; i < numVisible; i++) {
                    mDiceImageViews[i].setVisibility(View.VISIBLE);
                }
        }
        // Hide remaining dice
        for (int i = numVisible; i < MAX_DICE; i++) {
            mDiceImageViews[i].setVisibility(View.GONE);
        }
    }

    private void rollDice() {
        mMenu.findItem(R.id.action_roll).setVisible(false);
        mMenu.findItem(R.id.action_roll_length).setVisible(false);
        mMenu.findItem(R.id.action_stop).setVisible(true);

        if (mTimer != null) {
            mTimer.cancel();
        }

        mTimer = new CountDownTimer(mTimerLength, 100) {
            public void onTick(long millisUntilFinished) {
                for (int i = 0; i < mVisibleDice; i++) {
                    mDice[i].roll();
                }
                showDice();
            }

            public void onFinish() {
                mMenu.findItem(R.id.action_stop).setVisible(false);
                mMenu.findItem(R.id.action_roll_length).setVisible(true);
                mMenu.findItem(R.id.action_roll).setVisible(true);
            }
        }.start();
    }

    private void rollDice(Dice selectedDie) {
        mMenu.findItem(R.id.action_roll).setVisible(false);
        mMenu.findItem(R.id.action_roll_length).setVisible(false);
        mMenu.findItem(R.id.action_stop).setVisible(true);
//
        if (mTimer != null) {
            mTimer.cancel();
        }

        mTimer = new CountDownTimer(mTimerLength, 100) {
            public void onTick(long millisUntilFinished) {
                for (int i = 0; i < mVisibleDice; i++) {
                    selectedDie.roll();
                }
                showDice();
            }

            public void onFinish() {
                mMenu.findItem(R.id.action_stop).setVisible(false);
                mMenu.findItem(R.id.action_roll_length).setVisible(true);
                mMenu.findItem(R.id.action_roll).setVisible(true);
            }
        }.start();
    }

        ActivityResultLauncher<Intent> mColorResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                int colorId = data.getIntExtra(ChangeColorActivity.EXTRA_COLOR, R.color.red_900);
                                selectedColor = ContextCompat.getColor(MainActivity.this, colorId);
                                setDiceColor();
                            }
                        }
                    }
                });

        private void setDiceColor(){
            for (int i = 0; i < mDiceImageViews.length; i++) {
                mDiceImageViews[i].setColorFilter(selectedColor);
            }
        }
    }


