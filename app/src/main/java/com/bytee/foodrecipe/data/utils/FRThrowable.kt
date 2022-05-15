package com.bytee.foodrecipe.data.utils

class FRThrowable(val errorCode: Int? = 0, val errorMessage: String? = null, val errorTitle: String? = null) : Throwable(errorMessage)