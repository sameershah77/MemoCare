package com.sameer.silentecho.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sameer.silentecho.R
import com.sameer.silentecho.databinding.ActivityMainBinding
import com.sameer.silentecho.fragment.ChatFragment
import com.sameer.silentecho.fragment.ScanFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firstTimeSP = getSharedPreferences("login", Context.MODE_PRIVATE)
        val firstTimeEditor = firstTimeSP.edit()
        firstTimeEditor.putBoolean("firstTime", true)
        firstTimeEditor.apply()

        //default fragment
        replaceFragment(ScanFragment())

        //code for bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val itemId = item.itemId
            if (itemId == R.id.nav_scan) {
                replaceFragment(ScanFragment())
            } else if (itemId == R.id.nav_chat) {
                replaceFragment(ChatFragment())
            }
            true
        }

    }

    fun replaceFragment(fragment: Fragment) {
        val fragManager = supportFragmentManager
        val fragTransaction = fragManager.beginTransaction()
        fragTransaction.replace(R.id.fragment_container, fragment)
        fragTransaction.commit()
    }

}