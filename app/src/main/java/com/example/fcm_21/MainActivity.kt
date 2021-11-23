package com.example.fcm_21

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fcm_21.databinding.ActivityMainBinding
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        binding.btnSubscribe.setOnClickListener {
            FirebaseMessaging.getInstance().subscribeToTopic("nayhtetlynn")
        }

        binding.btnUnSubscribe.setOnClickListener {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("nayhtetlynn")
        }
    }
}