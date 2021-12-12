package com.rommansabbir.storex.execptions

class DuplicateStoreXConfigException(@JvmField override val message: String = "Duplicate StoreXConfig found.") :
    RuntimeException(message)