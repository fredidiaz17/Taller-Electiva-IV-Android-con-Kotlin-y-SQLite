/*Fredi Díaz
* Electiva 4
* Noviembre 4, 2026
* */
package com.example.registroestudiantes
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "EstudiantesDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val tabla = """
CREATE TABLE estudiantes (
id INTEGER PRIMARY KEY AUTOINCREMENT,
nombre TEXT,
apellido TEXT,
edad INTEGER,
correo TEXT
)
""".trimIndent()
        db.execSQL(tabla)
    }
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS estudiantes")
        onCreate(db)
    }
    // INSERTAR REGISTRO
    fun insertar(
        nombre: String,
        apellido: String,
        edad: Int,
        correo: String
    ): Boolean {
        val db = writableDatabase
        val valores = ContentValues()
        valores.put("nombre", nombre)
        valores.put("apellido", apellido)
        valores.put("edad", edad)
        valores.put("correo", correo)
        val resultado = db.insert(
            "estudiantes",
            null,
            valores
        )
        db.close()
        return resultado != -1L
    }
    // CONSULTAR REGISTROS
    fun consultar(): String {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM estudiantes",
            null
        )
        val resultado = StringBuilder()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(0)
            val nombre = cursor.getString(1)
            val apellido = cursor.getString(2)
            val edad = cursor.getInt(3)
            val correo = cursor.getString(4)
            resultado.append(
                "ID: $id\n" +
                        "Nombre: $nombre $apellido\n" +
                        "Edad: $edad\n" +
                        "Correo: $correo\n\n"
            )
        }
        cursor.close()
        db.close()
        return resultado.toString()
    }
}