package com.surajrathod.authme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.surajrathod.authme.network.NetworkService
import com.surajrathod.authme.util.DataStore
import com.surajrathod.authme.util.DataStore.JWT_TOKEN
import com.surajrathod.authme.util.DataStore.preferenceDataStoreAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch{
            val token = isLoggedIn(JWT_TOKEN)
            if(token!=null){
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                intent.putExtra(JWT_TOKEN,token)
                startActivity(intent)
                finish()
            }
        }


    }
    suspend fun isLoggedIn(key : String) : String? {
        val data = preferenceDataStoreAuth.data.first()
        return data[stringPreferencesKey(key)]
    }
}