package com.example.registroacademico.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.registroacademico.model.dao.*
import com.example.registroacademico.model.entities.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        Estudiante::class,
        Asistencia::class,
        DetalleAsistencia::class,
        Docente::class,
        Materia::class,
        Curso::class,
        Usuario::class,
        Inscripcion::class
    ],
    version = 11,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun estudianteDao(): EstudianteDao
    abstract fun asistenciaDao(): AsistenciaDao
    abstract fun detalleAsistenciaDao(): DetalleAsistenciaDao
    abstract fun docenteDao(): DocenteDao
    abstract fun materiaDao(): MateriaDao
    abstract fun cursoDao(): CursoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun inscripcionDao(): InscripcionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "registro_academico_db"
                ).fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val context: Context
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }

            override fun onOpen(db: androidx.sqlite.db.SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        seedDatabase(database.usuarioDao())
                    }
                }
            }

            suspend fun seedDatabase(usuarioDao: UsuarioDao) {
                if (usuarioDao.contarUsuarios() == 0) {
                    val admin = Usuario(
                        nombre = "Administrador",
                        email = "admin@correo.com",
                        password = "admin",
                        rol = "admin"
                    )
                    usuarioDao.insertar(admin)
                }
            }
        }
    }
}
