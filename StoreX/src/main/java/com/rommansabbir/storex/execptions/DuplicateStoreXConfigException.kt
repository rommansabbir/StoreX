package com.rommansabbir.storex.execptions

class DuplicateStoreXConfigException(override val message: String = "Duplicate StoreXConfig found.") :
    Exception(message)