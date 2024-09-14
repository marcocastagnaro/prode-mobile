package com.example.prode_mobile.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [LeagueAndSeason::class], version = 2)
abstract class ProdeMobileDatabase : RoomDatabase() {
    abstract fun leagueDao(): LeagueDao

    companion object {
        @Volatile
        private var INSTANCE: ProdeMobileDatabase? = null

        // Define la migración
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Primero, renombramos la tabla actual para conservar los datos.
                database.execSQL("ALTER TABLE leagues RENAME TO leagues_old")

                // Luego, creamos la nueva tabla con el esquema actualizado.
                database.execSQL("""
                    CREATE TABLE leagues (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        league_id INTEGER NOT NULL,
                        country_id INTEGER NOT NULL,
                        name TEXT NOT NULL,
                        active INTEGER NOT NULL,
                        image_path TEXT NOT NULL,
                        category INTEGER NOT NULL,
                        seasonId INTEGER NOT NULL
                    )
                """)

                // Copiamos los datos de la tabla antigua a la nueva tabla.
                database.execSQL("""
                    INSERT INTO leagues (id, league_id, country_id, name, active, image_path, category, seasonId)
                    SELECT id, 0 AS league_id, 0 AS country_id, leagueName AS name, 1 AS active, '' AS image_path, 0 AS category, seasonId
                    FROM leagues_old
                """)

                // Finalmente, eliminamos la tabla antigua.
                database.execSQL("DROP TABLE leagues_old")
            }
        }

        fun getDatabase(context: Context): ProdeMobileDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProdeMobileDatabase::class.java,
                    "prode_database"
                )
                    .addMigrations(MIGRATION_1_2) // Agrega la migración aquí
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
