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
import androidx.compose.material3.Checkbox
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
        var customerName by remember { mutableStateOf("") }
        var mainCourse by remember { mutableStateOf("漢堡套餐") }
        val mainOptions = listOf("漢堡套餐", "炸雞套餐", "披薩套餐")

        // Checkbox 需要獨立的 Boolean 變數來記錄是否被打勾
        var addFries by remember { mutableStateOf(false) }
        var addDrink by remember { mutableStateOf(false) }

        var orderResult by remember { mutableStateOf("") }

        // 這裡修改了 padding，將 top 設為 64.dp 把整個畫面往下推，避開頂部狀態列
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 64.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Lab4: 點餐系統", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

            // 1. 顧客姓名輸入
            OutlinedTextField(
                value = customerName,
                onValueChange = { customerName = it },
                label = { Text("請輸入顧客姓名或桌號") }
            )

            // 2. 主餐選擇 (單選：RadioButton)
            Text("選擇主餐：", modifier = Modifier.padding(top = 16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                mainOptions.forEach { option ->
                    RadioButton(
                        selected = (mainCourse == option),
                        onClick = { mainCourse = option }
                    )
                    Text(option, modifier = Modifier.padding(end = 8.dp))
                }
            }

            // 3. 附餐選擇 (複選：Checkbox)
            Text("加購附餐：", modifier = Modifier.padding(top = 16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = addFries,
                    onCheckedChange = { addFries = it } // 打勾狀態改變時觸發
                )
                Text("加購薯條 (+50元)", modifier = Modifier.padding(end = 16.dp))

                Checkbox(
                    checked = addDrink,
                    onCheckedChange = { addDrink = it }
                )
                Text("加購飲料 (+30元)")
            }

            // 4. 送出訂單按鈕
            Button(
                onClick = {
                    if (customerName.isEmpty()) {
                        orderResult = "請先輸入顧客姓名！"
                        return@Button
                    }

                    // 根據 Checkbox 狀態組合字串
                    val friesText = if (addFries) "薯條 " else ""
                    val drinkText = if (addDrink) "飲料 " else ""
                    val extraMsg = if (addFries || addDrink) "\n加購項目：$friesText$drinkText" else "\n加購項目：無"

                    // 將結果顯示在下方
                    orderResult = "訂單明細\n顧客：$customerName\n主餐：$mainCourse$extraMsg"
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("送出訂單")
            }

            // 5. 顯示點餐結果
            if (orderResult.isNotEmpty()) {
                Text(
                    text = orderResult,
                    modifier = Modifier.padding(top = 32.dp),
                    fontSize = 18.sp
                )
            }
        }
    }
}