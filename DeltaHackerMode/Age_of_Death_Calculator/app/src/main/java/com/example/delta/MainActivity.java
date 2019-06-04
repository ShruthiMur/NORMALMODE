package com.example.delta;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delta.R;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {
    EditText Guesseditt;
    EditText Ageeditt;
    Button Guessbutton;
    Button b;
    TextView GuessTextV;
    TextView Result_text_view;
    TextView Report_text_view;
    LinearLayout port;

    int Age;
    int Value;
    int Success, Failure;
    SharedPreferences sharedPreferences;
    String message;
    private String str_guess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b = (Button) findViewById(R.id.SetButton);
        Guesseditt = (EditText) findViewById(R.id.Guesses);
        str_guess = Guesseditt.getText().toString();
        port = (LinearLayout) findViewById(R.id.ContainPort);
        sharedPreferences = getSharedPreferences("SuccessnFailure", Context.MODE_PRIVATE);
        Success = sharedPreferences.getInt("Correct_Guess", 0);
        Failure = sharedPreferences.getInt("Incorrect_Guess", 0);

        if (savedInstanceState == null) {
            Value = 0;
            Guesseditt = (EditText) findViewById(R.id.Guesses);
            Guessbutton = (Button) findViewById(R.id.Guessbutton);
            GuessTextV = (TextView) findViewById(R.id.GuessTextView);
            GuessTextV.setVisibility(View.INVISIBLE);
            Guesseditt.setVisibility(View.INVISIBLE);
            Guessbutton.setVisibility(View.INVISIBLE);
        }
    }

    public void Set(View view) {
        Ageeditt = (EditText) findViewById(R.id.Deathage);
        Guesseditt = (EditText) findViewById(R.id.Guesses);
        Guessbutton = (Button) findViewById(R.id.Guessbutton);
        GuessTextV = (TextView) findViewById(R.id.GuessTextView);
        b = (Button) findViewById(R.id.SetButton);
        String x = Ageeditt.getText().toString();
        if (TextUtils.isEmpty(x)) {
            Toast.makeText(this, "Please enter the age.", Toast.LENGTH_SHORT).show();
            Ageeditt.requestFocus();
            return;
        } else {
            Age = Integer.parseInt(x);
            if (Age > 100 || Age < 1) {
                Toast.makeText(this, "Please enter the age from 1 to 100", Toast.LENGTH_SHORT).show();
                Ageeditt.setText("");
                return;
            } else {
                Ageeditt.setText("Age saved successfully");
                GuessTextV.setVisibility(View.VISIBLE);
                Guesseditt.setVisibility(View.VISIBLE);
                Guessbutton.setVisibility(View.VISIBLE);
                b.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void Guess(View view) {

        str_guess = Guesseditt.getText().toString();
        if (TextUtils.isEmpty(str_guess)) {
            Toast.makeText(this, "Please enter the age.", Toast.LENGTH_SHORT).show();
            Guesseditt.requestFocus();
            return;
        } else {
            int Guess = Integer.parseInt(str_guess);

            if (Guess > 100 || Guess < 1) {
                Toast.makeText(this, "Please enter your guess from 1 to 100", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Value += 1;
                Compare(Age, Guess);
                Guesseditt.setText("");
            }
        }
    }

    private void Compare(int Age, int Guess) {

        int Diff = Math.abs(Age - Guess);
        if (Diff == 0) {
            port = (LinearLayout) findViewById(R.id.ContainPort);
            port.setBackgroundColor(getResources().getColor(R.color.colour7));
        } else if ((0 < Diff) && (Diff < 10)) {
            port = (LinearLayout) findViewById(R.id.ContainPort);
            port.setBackgroundColor(getResources().getColor(R.color.Colour1));
        } else if ((10 <= Diff) && (Diff < 20)) {
            port = (LinearLayout) findViewById(R.id.ContainPort);
            port.setBackgroundColor(getResources().getColor(R.color.Colour2));
        } else if ((20 <= Diff) && (Diff < 30)) {
            port = (LinearLayout) findViewById(R.id.ContainPort);
            port.setBackgroundColor(getResources().getColor(R.color.Colour3));
        } else if ((30 <= Diff) && (Diff < 40)) {
            port = (LinearLayout) findViewById(R.id.ContainPort);
            port.setBackgroundColor(getResources().getColor(R.color.Colour4));
        } else if ((40 <= Diff) && (Diff < 50)) {
            port = (LinearLayout) findViewById(R.id.ContainPort);
            port.setBackgroundColor(getResources().getColor(R.color.Colour5));
        } else {
            port = (LinearLayout) findViewById(R.id.ContainPort);
            port.setBackgroundColor(getResources().getColor(R.color.Colour6));
        }

        if (Value < 7) {
            if (Guess < Age) {
                message = "Your guess is lower than the age.\nTry again with another guess.\nNo of tries =" + Value + "\nRemaining No of tries= " + (7 - Value) + "\n";
            } else if (Guess > Age) {
                message = "Your guess is greater than the age.\nTry again with another guess.\nNo of tries =" + Value + "\nRemaining No of tries= " + (7 - Value) + "\n";
            } else {
                message = "WOW!!! That's correct guess\n. Click Retry to again guess.";
                Success += 1;
                Guessbutton = (Button) findViewById(R.id.Guessbutton);
                Guessbutton.setVisibility(View.INVISIBLE);
            }
        } else if (Value == 7) {
            if (Guess < Age) {
                message = "Your guess is lower than the age.\n";
                Failure += 1;
            } else if (Guess > Age) {
                message = "Your guess is greater than the age.\n";
                Failure += 1;
            } else {
                message = "WOW!!! That's correct guess\n";
                Success += 1;
            }
            message = message.concat(("Your guesses are all over as you have exceeded the limit.\nNo worries.\nTry again with another age of death.\nClick RETRY to continue."));
            Guessbutton = (Button) findViewById(R.id.Guessbutton);
            Guessbutton.setVisibility(View.INVISIBLE);
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Correct_Guess", Success);
        editor.putInt("Incorrect_Guess", Failure);
        editor.commit();

        String report_message = "No. of times correctly and incorrectly guessed are " + Success + " " + Failure;
        Report_text_view = (TextView) findViewById(R.id.report);
        Result_text_view = (TextView) findViewById(R.id.result);
        Result_text_view.setText((message));
        Report_text_view.setText(report_message);
    }


    public void Retry(View view) {
        Value = 0;
        setContentView(R.layout.activity_main);
        Result_text_view = (TextView) findViewById(R.id.result);
        Result_text_view.setText("");
        Report_text_view = (TextView) findViewById(R.id.report);
        Report_text_view.setText("");

        Guesseditt = (EditText) findViewById(R.id.Guesses);
        Guessbutton = (Button) findViewById(R.id.Guessbutton);
        GuessTextV = (TextView) findViewById(R.id.GuessTextView);
        GuessTextV.setVisibility(View.INVISIBLE);
        Guesseditt.setVisibility(View.INVISIBLE);
        Guessbutton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("Age", Age);
        outState.putInt("Value", Value);
        int color = ((ColorDrawable) port.getBackground()).getColor();
        outState.putInt("color", color);
        outState.putInt("Status", b.getVisibility());
        Result_text_view = (TextView) findViewById(R.id.result);
        Report_text_view = (TextView) findViewById(R.id.report);
        String mess = Result_text_view.getText().toString();
        String rep = Report_text_view.getText().toString();
        outState.putString("mess", mess);
        outState.putString("rep", rep);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int color = savedInstanceState.getInt("color");
        port.setBackgroundColor(color);
        Value = savedInstanceState.getInt("Value");
        Age = savedInstanceState.getInt("Age");
        str_guess = savedInstanceState.getString("Guess");
        b.setVisibility(savedInstanceState.getInt("Status"));
        String mess = savedInstanceState.getString("mess");
        String rep = savedInstanceState.getString("rep");
        Result_text_view = (TextView) findViewById(R.id.result);
        Report_text_view = (TextView) findViewById(R.id.report);
        Result_text_view.setText(mess);
        Report_text_view.setText(rep);
    }
}