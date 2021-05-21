package com.example.skycloud

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.skycloud.clases.Ciudad
import com.example.skycloud.clases.Main
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import javax.xml.transform.ErrorListener

class MainActivity : AppCompatActivity() {
    private var temperatura:TextView?=null
    private var estadoDelTiempo:TextView?=null
    private var  ciudades:TextView?=null
    private var velocidad:TextView?=null



    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        temperatura=findViewById(R.id.temperatura)
        estadoDelTiempo=findViewById(R.id.estadoDelTiempo)
        ciudades=findViewById(R.id.ciudad)
        velocidad=findViewById(R.id.velocidad_viento)




        val recibo=intent.getStringExtra("mensaje")

        Toast.makeText(this, "$recibo+10", Toast.LENGTH_SHORT).show()




        if (Network.hayRed(this)){
            //ejecutar solicitud HTTP
            //api key a9fe33b1e0db478ef0cd08aacaf0abb8
            // buenos aires id 3435910
            // santiago de tolu id 3666939
            //Buenos aires
            //solicitudHTTPVolley("http://api.openweathermap.org/data/2.5/weather?id=$recibo&appid=a9fe33b1e0db478ef0cd08aacaf0abb8&&units=metric&&lang=es")
            solicitudHTTPVolley("http://api.openweathermap.org/data/2.5/weather?q=$recibo&appid=a9fe33b1e0db478ef0cd08aacaf0abb8&&units=metric&&lang=es")

        }else{
            //MOSTRAR MENSAJE DE ERROR
            Toast.makeText(this, "No hay red.", Toast.LENGTH_SHORT).show()


        }




    }

    @SuppressLint("SetTextI18n")
    private fun solicitudHTTPVolley(url:String){

        val queue= Volley.newRequestQueue(this)
        val solicitud= StringRequest(Request.Method.GET,url, Response.Listener {
            response ->

            try {



                //Log.d("solicitudHTTPVolley",response)
                val gson= Gson()

                //machea json con la clase cuando hago una solicitud me trae un objeto

                val ciudad=gson.fromJson(response, Ciudad::class.java)



                //Log.d("Mi ciudad",ciudad.toString())
                ciudades?.text=ciudad.name

                estadoDelTiempo?.text=ciudad.weather?.get(0)?.description

                val iconos=ciudad.weather?.get(0)!!.icon

                val imagen="http://openweathermap.org/img/wn/$iconos@2x.png"

                //icono.setImageResource(ciudad.weather.get(0).icon)

                Picasso.with(this).load(imagen).into(imagen_de_icono)


                Toast.makeText(this, ciudad.weather?.get(0)?.icon.toString(), Toast.LENGTH_SHORT).show()





                when(ciudad.weather?.get(0)?.description!=null) {

                    ciudad.weather?.get(0)?.description == "cielo claro" -> fondo.setBackgroundResource(R.drawable.soleado)

                    ciudad.weather?.get(0)?.description == "nubes" -> fondo.setBackgroundResource(R.drawable.nublado)

                    ciudad.weather?.get(0)?.description == "algo de nubes" -> fondo.setBackgroundResource(R.drawable.pocasnubes)

                    ciudad.weather?.get(0)?.description == "nubes dispersas" -> fondo.setBackgroundResource(R.drawable.nublado)

                    ciudad.weather?.get(0)?.description == "muy nuboso" -> fondo.setBackgroundResource(R.drawable.nublado)

                    ciudad.weather?.get(0)?.description == "lluvia" -> fondo.setBackgroundResource(R.drawable.lluvia)

                    ciudad.weather?.get(0)?.description == "lluvia moderada" -> fondo.setBackgroundResource(R.drawable.lluvmod)


                }


                // reduzco aproximacion a 1 digito despues de la coma
                temperatura?.text=String.format("%.1f",ciudad?.main?.temp)+" °C"
                velocidad?.text= String.format("%.1f",ciudad?.wind?.speed)+" Km/h"


            }catch (e:Exception){


            }

        },Response.ErrorListener {


        })

        queue.add(solicitud)

    }



}