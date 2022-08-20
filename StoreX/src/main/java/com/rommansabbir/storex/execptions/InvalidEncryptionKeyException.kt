package com.rommansabbir.storex.execptions

class InvalidEncryptionKeyException(override val message: String = "Invalid encryption key") :
    Exception(message)