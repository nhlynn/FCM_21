package com.example.fcm_21

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.fcm_21.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onResume() {
        super.onResume()

        val resultIntent = intent
        val title = resultIntent.getStringExtra("title")
        val message = resultIntent.getStringExtra("body")
        val imagesUrl=resultIntent.getStringExtra("image")

        binding.tvTitle.text=title
        binding.tvDescription.text = message
        Glide.with(this).load("$imagesUrl")
            .error(R.mipmap.ic_launcher).into(binding.ivImage)
    }
}