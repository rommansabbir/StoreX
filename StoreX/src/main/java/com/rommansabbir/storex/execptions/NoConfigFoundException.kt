package com.rommansabbir.storex.execptions

class NoConfigFoundException(override val message: String = "No valid instance found for this StoreXConfig") :
    Exception(message)