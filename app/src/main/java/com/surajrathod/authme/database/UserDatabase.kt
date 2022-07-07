package com.surajrathod.authme.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.surajrathod.authme.model.User

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase(){

    abstract fun userDao() : UserDao


    companion object{

        @Volatile
        private var INSTANCE : UserDatabase? = null

        fun getDatabase(context: Context) : UserDatabase{

            if(INSTANCE==null)
            {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_db"
                    ).build()
                }
            }

            return INSTANCE!!
        }
    }


}