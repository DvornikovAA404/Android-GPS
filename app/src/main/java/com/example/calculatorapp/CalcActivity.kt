package com.example.calculatorapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class CalcActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)

        val b0 = findViewById<Button>(R.id.b0);
        val b1 = findViewById<Button>(R.id.b1);
        val b2 = findViewById<Button>(R.id.b2);
        val b3 = findViewById<Button>(R.id.b3);
        val b4 = findViewById<Button>(R.id.b4);
        val b5 = findViewById<Button>(R.id.b5);
        val b6 = findViewById<Button>(R.id.b6);
        val b7 = findViewById<Button>(R.id.b7);
        val b8 = findViewById<Button>(R.id.b8);
        val b9 = findViewById<Button>(R.id.b9);

        val bM = findViewById<Button>(R.id.bM);
        val bP = findViewById<Button>(R.id.bP);
        val bE = findViewById<Button>(R.id.bE);
        val bMu = findViewById<Button>(R.id.bMu);
        val bDel = findViewById<Button>(R.id.bDe);

        val bC = findViewById<Button>(R.id.bC);
        val Result = findViewById<TextView>(R.id.textView2);

        var req = "";

        b0.setOnClickListener {
            req += 0;
            Result.text = req;
        }
        b1.setOnClickListener {
            req += 1;
            Result.text = req;
        }
        b2.setOnClickListener {
            req += 2;
            Result.text = req;
        }
        b3.setOnClickListener {
            req += 3;
            Result.text = req;
        }
        b4.setOnClickListener {
            req += 4;
            Result.text = req;
        }
        b5.setOnClickListener {
            req += 5;
            Result.text = req;
        }
        b6.setOnClickListener {
            req += 6;
            Result.text = req;
        }
        b7.setOnClickListener {
            req += 7;
            Result.text = req;
        }
        b8.setOnClickListener {
            req += 8;
            Result.text = req;
        }
        b9.setOnClickListener {
            req += 9;
            Result.text = req;
        }

        bM.setOnClickListener {
            req += "-";
            Result.text = req;
        }
        bP.setOnClickListener {
            req += "+";
            Result.text = req;
        }
        bMu.setOnClickListener {
            req += "*";
            Result.text = req;
        }
        bDel.setOnClickListener {
            req += "/";
            Result.text = req;
        }

        bE.setOnClickListener {
            req += ";"
            var x = "";
            var y = "";
            var f = "";
            var res = "";
            if(req == ";"){
                Result.text = "Error! Пустое выражение.";
                return@setOnClickListener;
            }
            for (i in req.indices) {
                if (req[i].isDigit()) {
                    if (f == "") {
                        x += req[i];
                    } else {
                        y += req[i];
                    }
                } else {
                    f += req[i];
                }
                if (req[i+1] == '+' || req[i+1] == '-' || req[i+1] == '*' || req[i+1] == '/' || req[i+1] == ';') {

                    if (f == "+") {
                        x = (x.toInt() + y.toInt()).toString();
                        y = "";
                        f = "";
                        break;
                    }
                    if (f == "-") {
                        x = (x.toInt() - y.toInt()).toString();
                        y = "";
                        f = "";
                        break;
                    }
                    if (f == "*") {
                        x = (x.toInt() * y.toInt()).toString();
                        y = "";
                        f = "";
                        break;
                    }
                    if (f == "/") {
                        if(y == "0"){
                            Result.text = "Error! x/0";
                            return@setOnClickListener;
                        }
                        x = (x.toInt() / y.toInt()).toString();
                        y = "";
                        f = "";
                        break;
                    }
                }
                if(req[i+1] == ';') { break; }
            }
            Result.text = x;

        }
        bC.setOnClickListener {
            req = "";
            Result.text = req;
        }
    }
}
