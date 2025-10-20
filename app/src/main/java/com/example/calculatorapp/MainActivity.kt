package com.example.calculatorapp

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.activity.ComponentActivity

private lateinit var calc: Button
private lateinit var media : Button
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calc = findViewById<Button>(R.id.b1)
        media = findViewById<Button>(R.id.b2)
    }

    override fun onResume(){
        super.onResume()
        calc.setOnClickListener(){
            val CalcIntent = Intent(this, CalcActivity::class.java)
            startActivity(CalcIntent)
        }

        media.setOnClickListener {
            val MediaIntent = Intent(this, MediaActivity::class.java)
            startActivity(MediaIntent)
        }
    }
}
