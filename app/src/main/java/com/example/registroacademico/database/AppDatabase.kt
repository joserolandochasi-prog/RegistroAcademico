import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.registroacademico.model.entities.Asistencia
import com.example.registroacademico.model.entities.Estudiante

@Database(
    entities = [Asistencia::class, Estudiante::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

}