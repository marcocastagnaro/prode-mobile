package com.example.prode_mobile.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [LeagueAndSeason::class, ProdeResult::class], version = 3)
abstract class ProdeMobileDatabase : RoomDatabase() {
    abstract fun leagueDao(): LeagueDao
    abstract fun prodeResultDao(): ProdeResultDao

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
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Verificar si la tabla prode_results ya existe, si no, se crea una nueva
                database.execSQL("""
            CREATE TABLE IF NOT EXISTS prode_results (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                matchId INTEGER NOT NULL,
                localGoals INTEGER NOT NULL,
                visitorGoals INTEGER NOT NULL,
                points INTEGER NOT NULL
            )
        """)

                // Renombramos la tabla antigua para conservar los datos.
                database.execSQL("ALTER TABLE prode_results RENAME TO prode_results_old")

                // Creamos la nueva tabla con el esquema actualizado.
                database.execSQL("""
            CREATE TABLE prode_results (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                matchId INTEGER NOT NULL,
                localGoals INTEGER NOT NULL,
                visitorGoals INTEGER NOT NULL,
                winner TEXT NOT NULL
            )
        """)

                // Copiamos los datos de la tabla antigua a la nueva tabla.
                database.execSQL("""
            INSERT INTO prode_results (id, matchId, localGoals, visitorGoals, winner)
            SELECT id, matchId, localGoals, visitorGoals, '' AS winner
            FROM prode_results_old
        """)

                // Eliminamos la tabla antigua.
                database.execSQL("DROP TABLE prode_results_old")
            }
        }


        fun getDatabase(context: Context): ProdeMobileDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProdeMobileDatabase::class.java,
                    "prode_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3) // Agrega la migración aquí
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
