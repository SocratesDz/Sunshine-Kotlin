package com.sunshine.socratesdiaz.sunshine_kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .add(R.id.container, ForecastFragment())
                .commit()

        }
    }
}
