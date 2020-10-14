package com.bruno.aybar.composechallenges.batman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.ui.platform.setContent

class BatmanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BatmanTheme {
                BatmanPage()
            }
        }
    }
}