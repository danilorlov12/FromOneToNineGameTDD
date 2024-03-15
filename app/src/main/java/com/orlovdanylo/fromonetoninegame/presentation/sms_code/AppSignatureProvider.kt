package com.orlovdanylo.fromonetoninegame.presentation.sms_code

import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Arrays

class AppSignatureProvider(context: Context) : ContextWrapper(context) {

    companion object {
        private const val HASH_TYPE = "SHA-256"
        private const val NUM_HASHED_BYTES = 9
        private const val NUM_BASE64_CHAR = 11
    }

    fun getAppHashCode(): String {
        return getAppSignatures().joinToString { it }
    }

    private fun getAppSignatures(): List<String> {
        val appCodes = arrayListOf<String>()
        try {
            val signatures = packageManager.getPackageInfo(packageName,
                PackageManager.GET_SIGNATURES).signatures

            signatures.forEach { signature ->
                hash(packageName, signature.toCharsString())?.let {
                    appCodes.add(String.format("%s", it))
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return appCodes
    }

    private fun hash(packageName: String, signature: String): String? {
        val appInfo = "$packageName $signature"
        try {
            val messageDigest = MessageDigest.getInstance(HASH_TYPE)
            messageDigest.update(appInfo.toByteArray(StandardCharsets.UTF_8))

            var hashSignature = messageDigest.digest()
            hashSignature = Arrays.copyOfRange(hashSignature, 0, NUM_HASHED_BYTES)

            val base64Hash = Base64.encodeToString(hashSignature, Base64.NO_PADDING or Base64.NO_WRAP)
            return base64Hash.substring(0, NUM_BASE64_CHAR)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return null
    }
}