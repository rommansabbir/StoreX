package com.rommansabbir.storex.execptions

class NoConfigFoundException(@JvmField override val message: String = "No valid instance found for this StoreXConfig") :
    RuntimeException(message)