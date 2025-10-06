package com.example.calculatorapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculatorapp.ui.theme.CalculatorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val b0 = findViewById < Button >(R.id.b0);
        val b1 = findViewById < Button >(R.id.b1);
        val b2 = findViewById < Button >(R.id.b2);
        val b3 = findViewById < Button >(R.id.b3);
        val b4 = findViewById < Button >(R.id.b4);
        val b5 = findViewById < Button >(R.id.b5);
        val b6 = findViewById < Button >(R.id.b6);
        val b7 = findViewById < Button >(R.id.b7);
        val b8 = findViewById < Button >(R.id.b8);
        val b9 = findViewById < Button >(R.id.b9);

        val bM = findViewById<Button>(R.id.bM);
        val bP = findViewById<Button>(R.id.bP);
        val bE = findViewById<Button>(R.id.bE);
        val bMu = findViewById<Button>(R.id.bMu);
        val bDel = findViewById<Button>(R.id.bDe);

        val bC = findViewById<Button>(R.id.bC);
        val Result = findViewById<TextView>(R.id.textView2);

        var x = "";
        var f = 'O';
        var y = "";

        fun ShowR(){
            var txt = "";
            if(x != ""){
                txt += x;
            }
            if(f != 'O'){
                txt += f;
            }
            if(y != ""){
                txt += y;
            }
            Result.text = txt;
        }
        b0.setOnClickListener {
            if(f == 'O'){
                x += "0";
            } else {
                y += "0";
            }
            ShowR();
        }
        b1.setOnClickListener {
            if(f == 'O'){
                x += "1";
            } else {
                y += "1";
            }
            ShowR();
        }
        b2.setOnClickListener {
            if(f == 'O'){
                x += "2";
            } else {
                y += "2";
            }
            ShowR();
        }
        b3.setOnClickListener {
            if(f == 'O'){
                x += 3;
            } else {
                y += 3;
            }
            ShowR();
        }
        b4.setOnClickListener {
            if(f == 'O'){
                x += 4;
            } else {
                y += 4;
            }
            ShowR();
        }
        b5.setOnClickListener {
            if(f == 'O'){
                x += 5;
            } else {
                y += 5;
            }
            ShowR();
        }
        b6.setOnClickListener {
            if(f == 'O'){
                x += 6;
            } else {
                y += 6;
            }
            ShowR();
        }
        b7.setOnClickListener {
            if(f == 'O'){
                x += 7;
            } else {
                y += 7;
            }
            ShowR();
        }
        b8.setOnClickListener {
            if(f == 'O'){
                x += 8;
            } else {
                y += 8;
            }
            ShowR();
        }
        b9.setOnClickListener {
            if(f == 'O'){
                x += 9;
            } else {
                y += 9;
            }
            ShowR();
        }

        bM.setOnClickListener {
            if(x != ""){
                f = '-';
            }
            ShowR();
        }
        bP.setOnClickListener {
            if(x != ""){
                f = '+';
            }
            ShowR();
        }
        bMu.setOnClickListener {
            if(x != ""){
                f = '*';
            }
            ShowR();
        }
        bDel.setOnClickListener {
            if(x != ""){
                f = '/';
            }
            ShowR();
        }

        bE.setOnClickListener {
            if(f == '+'){
                Result.text = (x.toInt()+y.toInt()).toString();
            }
            else if(f == '-'){
                Result.text = (x.toInt()-y.toInt()).toString();
            }
            else if(f == '*'){
                Result.text = (x.toInt()*y.toInt()).toString();
            }
            else if(f == '/'){
                if(y != "0") {
                    Result.text = (x.toFloat() / y.toFloat()).toString();
                } else { Result.text = "ERROR!"; }
            }
        }

        bC.setOnClickListener {
            x = "";
            f = 'O';
            y = "";
            ShowR();
        }
    }
}
