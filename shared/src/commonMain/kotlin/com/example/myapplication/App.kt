package com.example.myapplication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource

import myapplication.shared.generated.resources.Res
import myapplication.shared.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var playerName by remember { mutableStateOf("") }
            var selectedMora by remember { mutableStateOf("剪刀") }
            val moraOptions = listOf("剪刀", "石頭", "布")

            // 1. 新增：儲存電腦出拳與勝利者的狀態
            var computerMora by remember { mutableStateOf("未定") }
            var winner by remember { mutableStateOf("未定") }

            OutlinedTextField(
                value = playerName,
                onValueChange = { playerName = it },
                label = { Text("請輸入玩家姓名") }
            )

            Text(
                text = "請輸入姓名以開始遊戲",
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 18.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                moraOptions.forEach { option ->
                    RadioButton(
                        selected = (selectedMora == option),
                        onClick = { selectedMora = option }
                    )
                    Text(
                        text = option,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            }

            // 2. 修改：猜拳按鈕加入邏輯判斷
            Button(
                onClick = {
                    // 如果沒有輸入名字，就先不執行
                    if (playerName.isEmpty()) return@Button

                    // 電腦隨機出拳
                    computerMora = moraOptions.random()

                    // 判斷勝負
                    winner = when {
                        selectedMora == computerMora -> "平手"
                        (selectedMora == "剪刀" && computerMora == "布") ||
                                (selectedMora == "石頭" && computerMora == "剪刀") ||
                                (selectedMora == "布" && computerMora == "石頭") -> "玩家勝利"
                        else -> "電腦勝利"
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("猜拳")
            }

            // 3. 更新：將結果顯示區塊的文字綁定變數
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("名字")
                    Text(playerName.ifEmpty { "無" })
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("勝利者")
                    Text(winner) // 對應勝負變數
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("我方出拳")
                    Text(selectedMora)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("電腦出拳")
                    Text(computerMora) // 對應電腦出拳變數
                }
            }
        }
    }
}