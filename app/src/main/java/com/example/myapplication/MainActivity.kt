package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.math.cos
import kotlin.math.sin


class MainActivity : AppCompatActivity() {
    var isBouncing = true

    var maxCoords = arrayOf(0, 0)
    var viewSize = arrayOf(0, 0)
    var direction = Random().nextDouble() * 360
    var speed = 0.005f
    var position = arrayOf(0f, 0f)


    private lateinit var view: TextView
    private lateinit var container: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view = findViewById(R.id.itemView)
        container = findViewById(R.id.containerView)

        container.setOnClickListener {
            isBouncing = !isBouncing
        }
        view.setOnClickListener {
            position = arrayOf(maxCoords[0] / 2.toFloat(), maxCoords[1] / 2.toFloat())
            direction = Random().nextDouble() * 360
            view.x = position[0]
            view.y = position[1]
        }
        view.animate

        Handler(mainLooper).postDelayed({
            setSizes()
            bounce()
            Toast.makeText(this, "AAAAAAAAAA", Toast.LENGTH_SHORT).show()
        }, 1000)
    }


    fun setSizes() {
        maxCoords = arrayOf(container.measuredWidth, container.measuredHeight)
        viewSize = arrayOf(view.measuredWidth, view.measuredHeight)
        position = arrayOf(view.x, view.y)
    }


    fun bounce() {
        CoroutineScope(EmptyCoroutineContext).launch {
            while (true) {
                if (isBouncing) {
                    Log.d("©", position.contentToString())
                    position = getNewCoords(speed, direction, position[0], position[1])
                    checkBounds()
                    view.x = position[0]
                    view.y = position[1]
                }
            }
        }.start()

    }


    fun checkBounds() {
        if (maxCoords[0] - (position[0] + viewSize[0]) < 1) direction = 180 - direction
        if (maxCoords[1] - (position[1] + viewSize[1]) < 1) direction = 360 - direction
        if (position[0] < 1) direction = 180 - direction
        if (position[1] < 1) direction = 360 - direction


        if (direction >= 360) direction -= 360
        if (direction < 0) direction += 360
    }

    fun getNewCoords(speed: Float, alpha: Double, x: Float, y: Float): Array<Float> {
        val x1: Float = (x + speed * cos(Math.toRadians(alpha))).toFloat()
        val y1: Float = (y + speed * sin(Math.toRadians(alpha))).toFloat()
        return arrayOf(x1, y1)
    }
}