package com.surajrathod.authme.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.surajrathod.authme.model.User

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: UserEntity)

    @Query("Select * from userTable where emailId = :email")
    fun getUser(email : String) : LiveData<UserEntity>
}