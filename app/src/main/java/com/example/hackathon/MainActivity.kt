package com.example.hackathon

import android.car.Car
import android.car.VehicleAreaType
import android.car.hardware.property.CarPropertyManager
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
    private lateinit var carPropertyManager: CarPropertyManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        carPropertyManager = Car.createCar(this).getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager

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

        findViewById<RadioGroup>(R.id.massage_type).setOnCheckedChangeListener { _, id ->
            val imageDrawable = when (id) {
                R.id.full_body -> R.drawable.car_seat_full_body
                R.id.upper_back -> R.drawable.car_seat_upper_back
                R.id.lower_back -> R.drawable.car_seat_lower_back
                R.id.neck -> R.drawable.car_seat_neck
                else -> R.drawable.car_seat_full_blue
            }
            findViewById<ImageView>(R.id.carSeatImageView).setImageResource(imageDrawable)
        }

        // when clicking the button, the request is made
        findViewById<Button>(R.id.bStart).setOnClickListener {

//            to start the massage, the three radio button groups should be checked
//            massage type
            var typeGroup = findViewById<RadioGroup>(R.id.massage_type)
            var selectedType = typeGroup.checkedRadioButtonId
            var type_index = typeGroup.indexOfChild(findViewById(selectedType))

//            pace
            var paceGroup = findViewById<RadioGroup>(R.id.pace_group)
            var paceSelected = paceGroup.checkedRadioButtonId
            var pace_index = paceGroup.indexOfChild(findViewById(paceSelected))

//            time
            var timeId = findViewById<RadioGroup>(R.id.time_group).checkedRadioButtonId

            var timeGroup = findViewById<RadioGroup>(R.id.time_group)
            var timeSelected = timeGroup.checkedRadioButtonId
            var time_index = timeGroup.indexOfChild(findViewById(timeSelected))

            if(type_index != -1 && pace_index != -1 && time_index != -1) {


                Log.d(
                    "MASSAGE TYPE: ",
                    "This type index is selected: ${type_index}"
                )
                Log.d(
                    "MASSAGE PACE: ",
                    " pace selected index: ${pace_index}"
                )

                Log.d(
                    "TIME:",
                    "Time selected index: ${time_index}"
                )

                val message = (((type_index shl 8) + pace_index shl 8) + time_index)

                try {
                    carPropertyManager.setIntProperty(0, VehicleAreaType.VEHICLE_AREA_TYPE_GLOBAL, message)
                } catch (e: Exception) {
                    Toast.makeText(this, "Error setting property", Toast.LENGTH_SHORT).show()
                }
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