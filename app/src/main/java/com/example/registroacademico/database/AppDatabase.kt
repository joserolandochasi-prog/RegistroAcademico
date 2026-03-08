package com.example.registroacademico.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.registroacademico.model.dao.AsistenciaDao
import com.example.registroacademico.model.dao.DetalleAsistenciaDao
import com.example.registroacademico.model.dao.DocenteDao
import com.example.registroacademico.model.dao.EstudianteDao
import com.example.registroacademico.model.dao.UsuarioDao
import com.example.registroacademico.model.entities.Asistencia
import com.example.registroacademico.model.entities.DetalleAsistencia
import com.example.registroacademico.model.entities.Docente
import com.example.registroacademico.model.entities.Estudiante
import com.example.registroacademico.model.entities.Usuario

@Database(
    entities = [
        Usuario::class,
        Docente::class,
        Estudiante::class,
        Asistencia::class,
        DetalleAsistencia::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun docenteDao(): DocenteDao
    abstract fun estudianteDao(): EstudianteDao
    abstract fun asistenciaDao(): AsistenciaDao
    abstract fun detalleAsistenciaDao(): DetalleAsistenciaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "registro_academico_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}