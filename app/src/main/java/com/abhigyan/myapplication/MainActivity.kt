package com.abhigyan.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Keep
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.abhigyan.myapplication.ui.theme.MyApplicationTheme
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class MainActivity: ComponentActivity() {
    private val flag = "JSpFP9lFjCgXOdP7EfO7yyDa+bf/SJY5Ahv+Es8ucAA=XikuZPeCuELWj35PL8rG1w=="
    private val password = "R QRQF O EO CNI UVGVVNYX"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var text by remember{ mutableStateOf("") }
            val correct by remember{ derivedStateOf { text == DECRYPT_PASSWORD(password, d(flag.slice(44..67),password)) } }
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        TextField(
                            value = text,
                            onValueChange = { text = it.uppercase() },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = { Text(text = "Enter password to get the flag") })
                        if(correct){
                            Box(modifier = Modifier.height(20.dp))
                            Text(text = d(flag.slice(0..43),text))
                        }
                    }
                }
            }
        }
    }
    private fun DECRYPT_PASSWORD(cipherText: String ,cipherKey:String):String{
        val keyLength=cipherKey.length
        var flag=""
        var i=0
        for (letter in cipherText){
            if(letter==' '){
                flag+=' '
            }else {
                val diff = letter-cipherKey[i]
                if(diff<0){
                    flag+=(diff+91).toChar()
                }else {
                    flag+=(diff+65).toChar()
                }
                i=(i+1)%keyLength
            }
        }
        return flag
    }

    fun d(e: String, f: String): String {
        try{
            val secretKeySpec = SecretKeySpec(f.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
            val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(e))
            return String(decryptedBytes)
        } catch (e: Exception){
            println(e)
            return ""
        }
    }
}

//fun encrypt(data: String, password: String): String {
//    val secretKeySpec = SecretKeySpec(password.toByteArray(), "AES")
//    val cipher = Cipher.getInstance("AES")
//    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
//    val encryptedBytes = cipher.doFinal(data.toByteArray())
//    return Base64.getEncoder().encodeToString(encryptedBytes)
//}
//fun e(plainText:String , cipherKey : String):String{
//    val alphabet = " ABCDEFGHIJKLMNOPQRSTUVWXYZ"
//    val plainTextUpper = plainText.uppercase()
//    val cipherKeyUpper = cipherKey.uppercase()
//    var key = 0
//    var cipherText = ""
//
//    for(letter in plainTextUpper){
//        val indexOfShift = (alphabet.indexOf(letter)+alphabet.indexOf(cipherKeyUpper[key])).mod(alphabet.length)
//        Log.d("VigenereCipher" , "This is the shift key $indexOfShift")
//        cipherText+= alphabet[indexOfShift]
//        key+=1
//        if(key>cipherKey.length-1){
//            key=0
//        }
//    }
//    return cipherText
//}