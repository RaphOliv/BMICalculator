package com.raphaeloliveira.bmicalculator.di.module

import android.app.Application
import com.raphaeloliveira.bmicalculator.BmiCalculatorApplication
import org.koin.dsl.module

val historyDatabaseModule = {app: Application -> module {
    single { (app as BmiCalculatorApplication).getDatabase().getHistoryDao() }
}}