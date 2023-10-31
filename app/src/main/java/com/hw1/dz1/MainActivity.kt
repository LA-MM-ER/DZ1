package com.hw1.dz1

import com.hw1.dz1.ui.theme.*
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddNumberBox()
        }
    }
}

@Preview
@Composable
fun AddNumberBox() {
    var currPage by rememberSaveable {mutableStateOf(0)}
    var boxCount by rememberSaveable { mutableStateOf(0) }
    var temp: Int
    //var number by remember {mutableStateOf(0)}
    var boxnum by rememberSaveable {mutableStateOf(0)}
    when (currPage) {
        0 -> {
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())) {
                val configuration = LocalConfiguration.current
                when (configuration.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        temp = 4
                    }
                    else -> {
                        temp = 3
                    }
                }
                val rows = boxCount / temp + if (boxCount % temp == 0) 0 else 1 //Вычисляет количество строк для ящиков
                repeat(rows) { row ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        repeat(temp) { column ->
                            val index = row * temp + column //Вычисляет индекс текущего ящика в одномерном массиве, используя текущую строку и столбец
                            if (index < boxCount) {
                                val number = index + 1
                                Box(
                                    modifier = Modifier
                                        .size(boxsz)
                                        .background(if (number % 2 == 0) Color.Red else Color.Blue)
                                        .clickable(onClick = {
                                            currPage = 1
                                            boxnum = number
                                        })
                                ) {
                                    Text(text = number.toString(), color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
            Box (modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd){
                ElevatedButton(onClick = { boxCount++ }) {
                    Text(btntxt, color = Color.Blue)
                }
            }
        }
        1 -> {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(if (boxnum % 2 == 0) Color.Red else Color.Blue)) {
                Text(text = boxnum.toString(), color = Color.White)
            }
            Box (modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd){
                /*ElevatedButton(onClick = { currPage = 0 }) {
                    Text("Home", color = Color.Blue)
                }*/
                BackHandler(enabled = true, onBack = {
                    currPage = 0
                })
            }

        }
    }
}


