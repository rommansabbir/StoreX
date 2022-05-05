package com.rommansabbir.storex.execptions

class InvalidEncryptionKeyException(@JvmField override val message: String = "Invalid encryption key") :
    Exception(message)