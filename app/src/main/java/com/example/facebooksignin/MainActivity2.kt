package com.example.facebooksignin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.facebooksignin.databinding.ActivityMain2Binding
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.GraphRequest
import com.facebook.login.LoginManager

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val accessToken=AccessToken.getCurrentAccessToken()

        val request = GraphRequest.newMeRequest(accessToken) { obj, response ->
            // Application code
            binding.textView.text=obj?.getString("name").toString()


            val picture=obj?.getJSONObject("picture")?.getJSONObject("data")?.getString("url")
            Log.d("waqar","${picture.toString()}")
            Glide.with(this).load(picture).into(binding.imageView)

        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name,link,picture.type(large)")
        request.parameters = parameters
        request.executeAsync()


        callbackManager = CallbackManager.Factory.create()
        binding.button2.setOnClickListener {
            LoginManager.getInstance().logOut()
            if (LoginManager.getInstance().logOut().equals(true)){
                startActivity(Intent(this,MainActivity::class.java))
            }
            else{
                Toast.makeText(this,"Logout Cancelled", Toast.LENGTH_LONG).show()
            }

        }

    }
}