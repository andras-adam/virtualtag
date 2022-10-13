package com.virtualtag.app.ui.screens

import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import androidx.compose.foundation.layout.*
import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Cancel
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
import com.virtualtag.app.ui.components.MifareClassicView
import com.virtualtag.app.ui.components.MifareUltralightView
import com.virtualtag.app.R
import com.virtualtag.app.ui.components.Dialog
import com.virtualtag.app.ui.components.PrimaryButton
import com.virtualtag.app.ui.components.SecondaryButton
import com.virtualtag.app.viewmodels.CardViewModel

@Composable
fun CardScreen(
    model: CardViewModel,
    id: Int,
    editCard: (id: Int) -> Unit,
    goBack: () -> Unit,
    goHome: () -> Unit
) {
    val card = model.getCardById(id).observeAsState(null)
    val context = LocalContext.current
    var deleteDialogOpen by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(card.value?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = { editCard(card.value?.id ?: 0) }) {
                        Icon(Icons.Filled.Edit, null)
                    }
                    IconButton(onClick = { deleteDialogOpen = true }) {
                        Icon(Icons.Filled.Delete, null)
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
            Column(modifier = Modifier.fillMaxWidth()) {
                if (card.value != null) {
                    if (card.value?.techList?.contains(MifareClassic::class.java.name) == true) {
                        MifareClassicView(card = card.value!!)
                    } else if (card.value?.techList?.contains(MifareUltralight::class.java.name) == true) {
                        MifareUltralightView(card = card.value!!)
                    } else {
                        Text(stringResource(R.string.not_supported))
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
                    text = stringResource(R.string.cancel),
                    onClick = { deleteDialogOpen = false }, modifier = Modifier.padding(top = 4.dp)
                )
            }, dismissButton = {
                PrimaryButton(
                    text = stringResource(R.string.delete),
                    onClick = {
                        model.deleteCard(card.value!!)
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
