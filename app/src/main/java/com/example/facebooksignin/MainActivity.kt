package com.example.facebooksignin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.facebooksignin.databinding.ActivityMainBinding
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var callbackManager: CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager, object :FacebookCallback<LoginResult>{
            override fun onCancel() {
                Toast.makeText(this@MainActivity,"Login Cancelled",Toast.LENGTH_LONG).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(this@MainActivity,"Login Error: ${error.message}",Toast.LENGTH_LONG).show()
            }

            override fun onSuccess(result: LoginResult) {
                Toast.makeText(this@MainActivity,"Login SuccessFully: ${result.accessToken}",Toast.LENGTH_LONG).show()
                startActivity(Intent(this@MainActivity,MainActivity2::class.java))
            }

        })

        binding.facebookSignIn.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this,listOf("public_profile"))
        }

    }

    private val signInFacebook=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//        callbackManager.onActivityResult()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}