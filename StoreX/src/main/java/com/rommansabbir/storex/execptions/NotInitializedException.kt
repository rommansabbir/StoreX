package com.rommansabbir.storex.execptions

class NotInitializedException(@JvmField override val message: String = "StoreX is not initialized properly") :
    Exception(message)