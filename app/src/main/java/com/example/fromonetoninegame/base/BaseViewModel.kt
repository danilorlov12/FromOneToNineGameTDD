package com.example.fromonetoninegame.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    val closeFragment: MutableLiveData<Boolean> = MutableLiveData()
}