package com.example.fcm_21

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fcm_21.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var analytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        analytics = Firebase.analytics

        FirebaseMessaging.getInstance().isAutoInitEnabled = true

        FirebaseMessaging.getInstance().subscribeToTopic("nayhtetlynn").addOnCompleteListener {  }
        binding.btnSubscribe.setOnClickListener {
            FirebaseMessaging.getInstance().subscribeToTopic("nayhtetlynn")
        }

        binding.btnUnSubscribe.setOnClickListener {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("nayhtetlynn")
        }
    }
}