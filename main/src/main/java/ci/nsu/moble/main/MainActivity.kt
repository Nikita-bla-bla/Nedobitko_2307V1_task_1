package ci.nsu.moble.main

import android.os.Bundle
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
        setContent { ColorScreen(colors) }
    }
}

@Composable
fun ColorScreen(colors: Map<String, Color>) {
    var text by remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(Color(0xFFACAFB0)) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Введите цвет") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Приводим введенный текст к нижнему регистру для поиска
                val color = colors[text.lowercase()]

                if (color != null) {
                    buttonColor = color
                    Toast.makeText(context, "Цвет применен: $text", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Цвет \"$text\" не найден", Toast.LENGTH_LONG).show()
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
        colors.forEach { (name, color) ->
            ColorStrip(name, color)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ColorStrip(name: String, color: Color) {
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
            text = name.capitalize(),
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}