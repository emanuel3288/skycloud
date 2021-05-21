package com.example.skycloud

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_ciudades.*
import kotlinx.android.synthetic.main.activity_main.*

class Ciudades : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ciudades)

        title="Pronostico"




        boton_editext.setOnClickListener {

            val intent=Intent(this,MainActivity::class.java)



            intent.putExtra("mensaje",editext.text.toString())


            startActivity(intent)
        }


    }
}