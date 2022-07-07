package com.surajrathod.authme.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.surajrathod.authme.model.User

@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    fun insertUser(user: UserEntity)

    @Query("delete from userTable where emailId = :email")
    fun deleteUser(email : String)

    @Query("Select * from userTable where emailId = :email")
    fun getUser(email : String) : LiveData<UserEntity>

    @Update(onConflict = REPLACE)
    fun updateUser(user: UserEntity)
}