package cat.dam.andy.persistentcounter

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterApp(applicationContext)
        }
    }
}

@Composable
fun CounterApp(context: Context) {
    // Obtenir les SharedPreferences
    val sharedPreferences = context.getSharedPreferences("CounterPrefs", Context.MODE_PRIVATE)

    // Llegir el valor inicial del comptador
    var counter by rememberSaveable { mutableStateOf(sharedPreferences.getInt("counter", 0)) }
    var isRunning by rememberSaveable { mutableStateOf(false) }

    // Guardar el valor del comptador a SharedPreferences
    fun saveCounter(value: Int) {
        sharedPreferences.edit().putInt("counter", value).apply()
    }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000L) // Cada segon
            counter++
            saveCounter(counter) // Guardar el valor actualitzat
        }
    }

    // Disseny de la interfície d'usuari
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Box per al text de "Comptador" i el comptador alineat a la dreta
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Comptador: ",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.CenterStart) // Alineat a l'esquerra del Box
            )

            Text(
                text = "$counter",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .align(Alignment.Center) // Alineat al centre del Box
                    .padding(start = 16.dp) // Padding entre el text i el número
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth() // Utilitza tot l'ample disponible
            ) {
                Button(
                    onClick = { isRunning = !isRunning },
                    modifier = Modifier.weight(1f) // Utilitza el mateix espai per a tots els botons
                ) {
                    Text(text = if (isRunning) "Pausa" else if (counter > 0) "Continuar" else "Iniciar")
                }
                Button(
                    onClick = {
                        isRunning = false
                        counter = 0
                        saveCounter(0)
                    },
                    modifier = Modifier.weight(1f) // Utilitza el mateix espai per a tots els botons
                ) {
                    Text(text = "Reset")
                }
            }
        }
    }
}




