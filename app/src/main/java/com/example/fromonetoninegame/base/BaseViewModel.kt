package com.example.fromonetoninegame.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val closeFragment: MutableLiveData<Boolean> = MutableLiveData()
}