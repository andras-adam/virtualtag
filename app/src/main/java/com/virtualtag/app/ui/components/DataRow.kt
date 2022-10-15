package com.virtualtag.app.ui.components

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.virtualtag.app.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DataRow(title: String, content: String, icon: ImageVector) {
    val context = LocalContext.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onLongClick = {
                    clipboardManager.setText(AnnotatedString(content))
                    Toast
                        .makeText(
                            context,
                            context.getString(R.string.clipboard),
                            Toast.LENGTH_SHORT
                        )
                        .show()
                },
                onClick = { /* do nothing */ })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon, contentDescription = null, modifier = Modifier
                    .size(48.dp)
                    .padding(vertical = 4.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, end = 16.dp)
            ) {
                Text(
                    title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(content, modifier = Modifier.padding(bottom = 8.dp))
            }
        }
        Divider(color = Color.LightGray)
    }
}
