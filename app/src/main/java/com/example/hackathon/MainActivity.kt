package com.example.hackathon

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.example.hackathon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        findViewById<RadioButton>(R.id.full_body).setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("RADIO", "full_body is checked: $isChecked")
        }

        findViewById<RadioButton>(R.id.upper_back).setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d("RADIO", "upper_back is checked: $isChecked")
        }

        // when clicking the button, the request is made
        findViewById<Button>(R.id.bStart).setOnClickListener {

//            to start the massage, the three radio button groups should be checked
//            massage type
            var typeId = findViewById<RadioGroup>(R.id.massage_type).checkedRadioButtonId

//            pace
            var paceId = findViewById<RadioGroup>(R.id.pace_group).checkedRadioButtonId

//            time
            var timeId = findViewById<RadioGroup>(R.id.time_group).checkedRadioButtonId
            if(typeId != -1 && paceId != -1 && timeId != -1) {
                // find the radiobutton by returned id
                var type: RadioButton = findViewById(typeId)

                Log.d(
                    "MASSAGE TYPE: ",
                    "This type is selected: ${type.text}"
                )
                Log.d(
                    "MASSAGE PACE: ",
                    " pace selected: ${paceId}"
                )

                Log.d(
                    "TIME:",
                    "Time selected: ${timeId}"
                )
            }

            Log.d("RADIO", "Button is clicked")
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}