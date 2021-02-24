package com.dimitar.diceapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sensorManager: SensorManager;

    var sensorSaveX = 0;
    var sensorSaveY = 0;
    var sensorSaveZ = 0;
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (
                event!!.values[0] > (sensorSaveX + 3).toFloat() || event!!.values[0] < (sensorSaveX - 3).toFloat() ||
                event!!.values[1] > (sensorSaveY + 3).toFloat() || event!!.values[1] < (sensorSaveY - 3).toFloat() ||
                event!!.values[2] > (sensorSaveZ + 3).toFloat() || event!!.values[2] < (sensorSaveZ - 3).toFloat()
        ) {
            rollDice()
            sensorSaveX = event!!.values[0].toInt()
            sensorSaveY = event!!.values[1].toInt()
            sensorSaveZ = event!!.values[2].toInt()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager;
        sensorManager.registerListener(
                this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    private fun rollDice() {
        val dice = Dice(6);
        val diceRoll = dice.roll();
        val resultTextView: TextView = findViewById(R.id.textView)
        resultTextView.text = diceRoll.toString()
    }


}