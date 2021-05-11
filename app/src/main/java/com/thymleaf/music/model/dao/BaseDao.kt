package com.thymleaf.music.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*

interface BaseDao<T> {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg obj: T): LiveData<Int>

    @Delete
    fun delete(vararg obj: T): LiveData<Int>

    @Update
    fun update(vararg obj: T): LiveData<Int>

}