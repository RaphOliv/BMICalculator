package com.raphaeloliveira.bmicalculator.domain

class BmiCalculator {
    fun calculateBmi(height: Float, weight: Float): Float {
        val heightSquared = height * height
        return weight / heightSquared
    }
}