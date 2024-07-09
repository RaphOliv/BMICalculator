package com.raphaeloliveira.bmicalculator.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class BmiIndicatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var bmiValue: Float = 0f
    set(value) {
        field = value
        invalidate()
    }

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width
        val height = height
        val radius = min(width, height) / 2f
        val centerX = width / 2f
        val centerY = height - 20f

        drawBackground(canvas, centerX, centerY, radius)
        drawPointer(canvas, centerX, centerY, radius)
    }

    private fun drawBackground(canvas: Canvas, centerX: Float, centerY: Float, radius: Float) {
        val classificationColors = mapOf(
            "<18.5" to Color.BLUE,
            "18.5-24.9" to Color.GREEN,
            "25-29.9" to Color.YELLOW,
            "30-39.9" to Color.rgb(255, 165, 0),
            ">=40" to Color.RED
        )

        val startAngle = 180f
        val sweepAngle = 180f / classificationColors.size

        classificationColors.keys.forEachIndexed { index, key ->
            paint.color = classificationColors[key] ?: Color.BLACK

            canvas.drawArc(
                centerX - radius, centerY - radius,
                centerX + radius, centerY + radius,
                startAngle + index * sweepAngle, sweepAngle, true, paint
            )
        }
    }

    private fun drawPointer(canvas: Canvas, centerX: Float, centerY: Float, radius: Float) {
        var angle = when {
            bmiValue <= 18.5f -> mapValue(bmiValue, 0f, 18.5f, 180f, 216f)
            bmiValue <= 25f -> mapValue(bmiValue, 18.5f, 25f, 216f, 252f)
            bmiValue <= 30f -> mapValue(bmiValue, 25f, 30f, 252f, 288f)
            bmiValue <= 40f -> mapValue(bmiValue, 30f, 40f, 288f, 324f)
            else -> mapValue(bmiValue, 40f, 50f, 324f, 360f)
        }

        angle = angle.coerceAtMost(360f)

        val angleInRadians = Math.toRadians(angle.toDouble()).toFloat()
        val pointerLength = radius * 0.8f
        val pointerX = centerX + pointerLength * cos(angleInRadians)
        val pointerY = centerY + pointerLength * sin(angleInRadians)

        paint.color = Color.BLACK
        paint.strokeWidth = 12f
        paint.style = Paint.Style.FILL

        val arrowPath = android.graphics.Path()
        arrowPath.moveTo(pointerX, pointerY)

        val baseAngle = angleInRadians + Math.PI
        val arrowSize = 30

        arrowPath.lineTo(
            (pointerX + arrowSize * cos(baseAngle - 0.5)).toFloat(),
            (pointerY + arrowSize * sin(baseAngle - 0.5)).toFloat()
        )

        arrowPath.lineTo(
            (pointerX + arrowSize * cos(baseAngle + 0.5)).toFloat(),
            (pointerY + arrowSize * sin(baseAngle + 0.5)).toFloat()
        )
        arrowPath.close()

        canvas.drawPath(arrowPath, paint)

        paint.shader = null

        paint.color = Color.BLACK
        paint.strokeWidth = 4f
        canvas.drawLine(centerX, centerY, pointerX, pointerY, paint)
    }

    private fun mapValue(value: Float, fromLow: Float, fromHigh: Float, toLow: Float, toHigh: Float): Float {
        return toLow + (value - fromLow) * (toHigh - toLow) / (fromHigh - fromLow)
    }

    fun bmiValue(result: Float) {
        bmiValue = result
    }
}