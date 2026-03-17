package ci.nsu.moble.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    // Тег для логирования
    companion object {
        private const val TAG = "ColorLab"
    }

    private val colors = mapOf(
        "red" to Color.Red,
        "orange" to Color(0xFFFFA500),
        "yellow" to Color.Yellow,
        "green" to Color.Green,
        "blue" to Color.Blue,
        "indigo" to Color(0xFF4B0082),
        "violet" to Color(0xFF8F00FF)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Логируем создание активности
        Log.d(TAG, "onCreate: MainActivity создана")
        Log.i(TAG, "Загружено ${colors.size} цветов")

        setContent {
            Log.d(TAG, "setContent: Запуск ColorScreen")
            ColorScreen(colors)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: MainActivity запущена")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: MainActivity возобновлена")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: MainActivity приостановлена")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: MainActivity остановлена")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: MainActivity уничтожена")
    }
}

@Composable
fun ColorScreen(colors: Map<String, Color>) {
    var text by remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(Color(0xFFACAFB0)) }
    val context = LocalContext.current

    // Логируем при каждой рекомпозиции
    Log.d("ColorLab", "ColorScreen рекомпозиция, текущий текст: '$text'")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                Log.d("ColorLab", "Текст изменен: '$it'")
            },
            label = { Text("Введите цвет") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Приводим введенный текст к нижнему регистру для поиска
                val searchText = text.lowercase()
                Log.d("ColorLab", "Поиск цвета: '$searchText'")

                val color = colors[searchText]

                if (color != null) {
                    buttonColor = color
                    Toast.makeText(context, "Цвет применен: $text", Toast.LENGTH_SHORT).show()
                    Log.i("ColorLab", "Цвет '$text' найден и применен")
                } else {
                    Toast.makeText(context, "Цвет \"$text\" не найден", Toast.LENGTH_LONG).show()
                    Log.w("ColorLab", "Цвет '$text' не найден в списке доступных цветов")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Text("Применить цвет")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Вывод цветов в виде полосок
        Log.d("ColorLab", "Отображение списка из ${colors.size} цветов")
        colors.forEach { (name, color) ->
            ColorStrip(name, color)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ColorStrip(name: String, color: Color) {
    // Логируем отрисовку каждой полоски
    Log.d("ColorLab", "Отрисовка ColorStrip для цвета '$name'")

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Полоска цвета
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(color)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Название цвета под полоской
        Text(
            text = name.replaceFirstChar { it.uppercase() },
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}