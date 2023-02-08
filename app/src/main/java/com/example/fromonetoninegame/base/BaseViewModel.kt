package com.example.fromonetoninegame.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    val closeFragment: MutableLiveData<Boolean> = MutableLiveData()
}