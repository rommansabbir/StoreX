package com.rommansabbir.storex.execptions

class NoStoreAbleObjectFound(override val message: String = "No StoreAbleObject found associated with this key") :
    Exception(message)