package com.rommansabbir.storex

/**
 * Represent the configuration for [StoreX].
 * uId must be an unique identifier.
 * [:: Having Encryption Enabled is a good practice for caching ::]
 *
 * @param uId, Unique identifier for the config
 * @param prefName, SharedPref name for the config
 * @param writeOrGetAsFileUsingCacheDirectory, If you want to write/get as file to/from Cache Directory.
 * If you enabled [writeOrGetAsFileUsingCacheDirectory], [prefName] or [SharedPref] will be ignored.
 */
data class StoreXConfig(
    val uId: String,
    val prefName: String,
    val writeOrGetAsFileUsingCacheDirectory: Boolean = false
)