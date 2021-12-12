package com.rommansabbir.storex

/**
 * Represent the configuration for [StoreX].
 * uId must be an unique identifier.
 *
 * @param uId, Unique identifier for the config
 * @param prefName, SharedPref name for the config
 */
data class StoreXConfig(val uId : String, val prefName : String)