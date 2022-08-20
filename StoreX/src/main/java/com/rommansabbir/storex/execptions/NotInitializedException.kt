package com.rommansabbir.storex.execptions

class NotInitializedException(override val message: String = "StoreX is not initialized properly") :
    Exception(message)