package com.peto.slottable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.peto.slottable.ui.theme.SlotTableTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SlotTableTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SlotTableDemo(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SlotTableDemo(modifier: Modifier = Modifier) {
    // Get the current composition via composer
    val composer = currentComposer
    val composition = composer.composition

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "안녕")
        Text(text = "Hello")

        Button(
            onClick = {
                // Log the SlotTable structure when button is clicked
                SlotTableInspector.logSlotTable(composition)
            }
        ) {
            Text("SlotTable 로그 출력")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SlotTableDemoPreview() {
    SlotTableTheme {
        SlotTableDemo()
    }
}
