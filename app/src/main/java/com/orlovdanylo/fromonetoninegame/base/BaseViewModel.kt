package com.orlovdanylo.fromonetoninegame.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    val closeFragment: MutableLiveData<Boolean> = MutableLiveData()
}