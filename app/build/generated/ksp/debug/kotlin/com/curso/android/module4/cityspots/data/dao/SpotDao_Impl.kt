package com.curso.android.module4.cityspots.`data`.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.curso.android.module4.cityspots.`data`.entity.SpotEntity
import javax.`annotation`.processing.Generated
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class SpotDao_Impl(
  __db: RoomDatabase,
) : SpotDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSpotEntity: EntityInsertAdapter<SpotEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSpotEntity = object : EntityInsertAdapter<SpotEntity>() {
      protected override fun createQuery(): String = "INSERT OR ABORT INTO `spots` (`id`,`imageUri`,`latitude`,`longitude`,`title`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SpotEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.imageUri)
        statement.bindDouble(3, entity.latitude)
        statement.bindDouble(4, entity.longitude)
        statement.bindText(5, entity.title)
        statement.bindLong(6, entity.timestamp)
      }
    }
  }

  public override suspend fun insertSpot(spot: SpotEntity): Long = performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfSpotEntity.insertAndReturnId(_connection, spot)
    _result
  }

  public override fun getAllSpots(): Flow<List<SpotEntity>> {
    val _sql: String = "SELECT * FROM spots ORDER BY timestamp DESC"
    return createFlow(__db, false, arrayOf("spots")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfImageUri: Int = getColumnIndexOrThrow(_stmt, "imageUri")
        val _columnIndexOfLatitude: Int = getColumnIndexOrThrow(_stmt, "latitude")
        val _columnIndexOfLongitude: Int = getColumnIndexOrThrow(_stmt, "longitude")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<SpotEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SpotEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpImageUri: String
          _tmpImageUri = _stmt.getText(_columnIndexOfImageUri)
          val _tmpLatitude: Double
          _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude)
          val _tmpLongitude: Double
          _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _item = SpotEntity(_tmpId,_tmpImageUri,_tmpLatitude,_tmpLongitude,_tmpTitle,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getSpotById(id: Long): SpotEntity? {
    val _sql: String = "SELECT * FROM spots WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfImageUri: Int = getColumnIndexOrThrow(_stmt, "imageUri")
        val _columnIndexOfLatitude: Int = getColumnIndexOrThrow(_stmt, "latitude")
        val _columnIndexOfLongitude: Int = getColumnIndexOrThrow(_stmt, "longitude")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: SpotEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpImageUri: String
          _tmpImageUri = _stmt.getText(_columnIndexOfImageUri)
          val _tmpLatitude: Double
          _tmpLatitude = _stmt.getDouble(_columnIndexOfLatitude)
          val _tmpLongitude: Double
          _tmpLongitude = _stmt.getDouble(_columnIndexOfLongitude)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _result = SpotEntity(_tmpId,_tmpImageUri,_tmpLatitude,_tmpLongitude,_tmpTitle,_tmpTimestamp)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getSpotCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM spots"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
