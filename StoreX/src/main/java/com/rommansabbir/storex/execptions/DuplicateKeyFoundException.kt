package com.rommansabbir.storex.execptions

class DuplicateKeyFoundException(override val message: String = "This subscriber is already present in the StoreX subscribers list. Make sure your SUBSCRIBER_ID is unique or else remove the current subscriber and add new one") :
    Exception(message)