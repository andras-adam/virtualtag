package com.virtualtag.app.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.virtualtag.app.R
import com.virtualtag.app.db.Card
import com.virtualtag.app.ui.components.Dialog
import com.virtualtag.app.ui.components.PrimaryButton
import com.virtualtag.app.ui.components.SecondaryButton
import com.virtualtag.app.viewmodels.CardViewModel

@Composable
fun CardScreen(
    model: CardViewModel,
    id: String,
    editCard: (id: String) -> Unit,
    goBack: () -> Unit,
    goHome: () -> Unit
) {
    val context = LocalContext.current
    val card =
        model.getCardById(id)
            .observeAsState(Card(id = "0", name = "Unknown card", color = "#fff8f8f8"))
    var deleteDialogOpen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(card.value.name) },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            null
                        )
                    }
                }
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(8.dp), color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text("Card details - placeholder")
                Row {
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(end = 4.dp)
                    ) {
                        PrimaryButton(
                            text = stringResource(R.string.edit),
                            onClick = { editCard(card.value.id) },
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                        )
                    }
                    Column(
                        Modifier
                            .weight(1f)
                            .padding(start = 4.dp)
                    ) {
                        SecondaryButton(
                            text = stringResource(R.string.delete),
                            onClick = { deleteDialogOpen = true },
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }

    if (deleteDialogOpen) {
        Dialog(
            closeDialog = { }, title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Outlined.Cancel,
                        null, modifier = Modifier
                            .padding(top = 24.dp)
                            .size(72.dp),
                        tint = Color.Red
                    )
                    Text(
                        stringResource(R.string.delete_confirm),
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }
            }, description = {
                Text(
                    stringResource(R.string.delete_confirm_description),
                    color = MaterialTheme.colors.secondary,
                    textAlign = TextAlign.Center
                )
            }, confirmButton = {
                SecondaryButton(
                    text = "Cancel",
                    onClick = { deleteDialogOpen = false }, modifier = Modifier.padding(top = 4.dp)
                )
            }, dismissButton = {
                PrimaryButton(
                    text = "OK",
                    onClick = {
                        model.deleteCard(card.value)
                        deleteDialogOpen = false
                        Toast.makeText(
                            context,
                            context.getString(R.string.card_deleted_success),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        goHome()
                    }, modifier = Modifier
                )
            }
        )
    }
}
