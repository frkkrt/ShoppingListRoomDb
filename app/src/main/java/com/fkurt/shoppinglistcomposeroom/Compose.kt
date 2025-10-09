package com.fkurt.shoppinglistcomposeroom

import android.graphics.Paint.Align
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    items: SnapshotStateList<String>,
    onSaveClick: suspend () -> Unit
) {
    var selectedIndex by remember { mutableStateOf(-1) }
    var isSaving by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // 🔹 Başlangıçta en az 1 satır ekle
    LaunchedEffect(Unit) {
        if (items.isEmpty()) {
            items.add("")
            selectedIndex = 0
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // 🔹 Toolbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFF0D47A1))
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Alışveriş Listesi",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                Toast.makeText(context, "Toolbar butonu tıklandı", Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Default.Info, contentDescription = "Info", tint = Color.White)
            }
        }

        // 🔹 Ürün listesi
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(8.dp)
        ) {
            itemsIndexed(items) { index, item ->
                val focusRequester = remember { FocusRequester() }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .clickable { selectedIndex = index },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = item,
                            onValueChange = { items[index] = it },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                                .focusRequester(focusRequester)
                                .onFocusChanged { state ->
                                    selectedIndex = if (state.isFocused) index else -1
                                },
                            singleLine = true,
                            label = { Text("Ürün ${index + 1}") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedBorderColor = Color(0xFF2196F3),
                                unfocusedBorderColor = Color.LightGray,
                                cursorColor = Color(0xFF2196F3)
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    items.add("")
                                    selectedIndex = items.lastIndex
                                }
                            )
                        )

                        // ❌ Sadece satır seçili ise göster
                        if (selectedIndex == index) {
                            IconButton(onClick = {
                                if (index in items.indices) {
                                    items.removeAt(index)
                                    selectedIndex = -1
                                }
                            }) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Sil",
                                    tint = Color(0xFFE12618)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 🔹 Alt butonlar (Kaydet) sadece kaydet butonu kaldı
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    scope.launch {
                        isSaving = true
                        delay(300)
                        onSaveClick()
                        isSaving = false
                        Toast.makeText(context, "Başarıyla kaydedildi!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3),
                    contentColor = Color.White
                )
            ) {
                Text("Kaydet", fontSize = 16.sp)
            }
        }
    }

    // 🔹 ProgressBar overlay
    if (isSaving) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x88000000))
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFF2196F3)
            )
        }
    }
}