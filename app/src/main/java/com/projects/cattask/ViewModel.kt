package com.projects.cattask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel: ViewModel() {

    var inputText: String = ""

    var logList: MutableLiveData<List<String>> = MutableLiveData<List<String>>()

    fun addToLog(value: String) {
        logList.value = logList.value?.plus(value) ?: listOf(value)
    }

}