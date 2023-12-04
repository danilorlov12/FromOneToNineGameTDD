package com.orlovdanylo.fromonetoninegame.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val closeFragment: MutableLiveData<Boolean> = MutableLiveData()
}