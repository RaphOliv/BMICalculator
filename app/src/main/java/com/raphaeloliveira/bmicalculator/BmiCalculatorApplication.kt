package com.raphaeloliveira.bmicalculator

import android.app.Application
import androidx.room.Room
import com.raphaeloliveira.bmicalculator.data.BmiCalculatorDataBase
import com.raphaeloliveira.bmicalculator.di.appModules
import org.koin.core.context.startKoin

class BmiCalculatorApplication: Application(){

    lateinit var db: BmiCalculatorDataBase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            BmiCalculatorDataBase::class.java, "database-bmicalculator"
        ).build()

        startKoin {
            modules(appModules.map { it(this@BmiCalculatorApplication) })
        }
    }

    fun getDatabase(): BmiCalculatorDataBase {
            return db
    }
}