package com.rommansabbir.storex

import android.util.Base64
import java.security.GeneralSecurityException
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


internal object EncryptionTool {
    private const val ITERATION_COUNT = 1000
    private const val KEY_LENGTH = 256
    private const val PBKDF2_DERIVATION_ALGORITHM = "PBKDF2WithHmacSHA1"
    private const val CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding"
    private const val PKCS5_SALT_LENGTH = 32
    private const val DELIMITER = "]"
    private val random = SecureRandom()

    /**
     * Encrypt the given data.
     *
     * @param dataToEncrypt Data that need to be encrypted
     *
     * @return [String] Encrypted Data
     */
    fun encrypt(dataToEncrypt: String): String {
        val salt = generateSalt()
        val key = deriveKey(StoreXCore.encryptionKey, salt)
        val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
        val iv = generateIv(cipher.blockSize)
        val ivParams = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParams)
        val cipherText = cipher.doFinal(dataToEncrypt.toByteArray(Charsets.UTF_8))
        return String.format(
            "%s%s%s%s%s",
            toBase64(salt),
            DELIMITER,
            toBase64(iv),
            DELIMITER,
            toBase64(cipherText)
        )
    }

    /**
     * Decrypt the given data.
     *
     * @param dataToDecrypt Data that need to be encrypted
     *
     * @return [String] Decrypted Data
     */
    fun decrypt(dataToDecrypt: String): String {
        val fields =
            dataToDecrypt.split(DELIMITER.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        require(fields.size == 3) { "Invalid encrypted text format" }
        val salt = fromBase64(fields[0])
        val iv = fromBase64(fields[1])
        val cipherBytes = fromBase64(fields[2])
        val key = deriveKey(StoreXCore.encryptionKey, salt)
        val cipher = Cipher.getInstance(CIPHER_ALGORITHM)
        val ivParams = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, key, ivParams)
        val plaintext = cipher.doFinal(cipherBytes)
        return String(plaintext, Charsets.UTF_8)
    }

    private fun generateSalt(): ByteArray {
        val b = ByteArray(PKCS5_SALT_LENGTH)
        random.nextBytes(b)
        return b
    }

    private fun generateIv(length: Int): ByteArray {
        val b = ByteArray(length)
        random.nextBytes(b)
        return b
    }

    private fun deriveKey(password: String, salt: ByteArray?): SecretKey {
        try {
            val keySpec = PBEKeySpec(password.toCharArray(), salt!!, ITERATION_COUNT, KEY_LENGTH)
            val keyFactory = SecretKeyFactory.getInstance(PBKDF2_DERIVATION_ALGORITHM)
            val keyBytes = keyFactory.generateSecret(keySpec).encoded
            return SecretKeySpec(keyBytes, "AES")
        } catch (e: GeneralSecurityException) {
            throw RuntimeException(e)
        }
    }

    private fun toBase64(bytes: ByteArray): String {
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }

    private fun fromBase64(base64: String): ByteArray {
        return Base64.decode(base64, Base64.NO_WRAP)
    }

}