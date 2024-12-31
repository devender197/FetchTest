package com.dev.fetchtest.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev.fetchtest.R
import com.dev.fetchtest.network.Utils
import com.dev.fetchtest.network.model.response.DataResponseItem
import com.dev.fetchtest.ui.models.DataUIModel
import kotlinx.coroutines.launch
@Composable
fun ListView(data: List<DataUIModel>) {
    val context = LocalContext.current
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column {
        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HeaderCell(context.getString(R.string.header_id))
            HeaderCell(context.getString(R.string.header_name))
            HeaderCell(context.getString(R.string.header_list_id))
        }

        // LazyColumn
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = listState
        ) {
            items(items = data) { item ->
                Column(modifier = Modifier.background(item.backgroundColor)) {
                    item.dataResponseItems.forEach { data ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            ListCell(
                                text = (data.id ?: "").toString(),
                                textColor = getTextColor(item.backgroundColor)
                            )
                            ListCell(
                                text = data.name ?: "",
                                textColor = getTextColor(item.backgroundColor)
                            )
                            ListCell(
                                text = (data.listId ?: "").toString(),
                                textColor = getTextColor(item.backgroundColor)
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .background(Color.White)
                    )
                }
            }
        }

        // Navigation Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            data.forEachIndexed { index, item ->
                Button(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(index = index)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = item.backgroundColor,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "List ${item.dataResponseItems.firstOrNull()?.listId ?: (index + 1)}")
                }

                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

// Header Cell
@Composable
fun RowScope.HeaderCell(text: String) {
    Text(
        text = text,
        modifier = Modifier.weight(1f),
        textAlign = TextAlign.Center
    )
}

// List Cell with Dynamic Text Color
@Composable
fun RowScope.ListCell(text: String, textColor: Color) {
    Text(
        text = text,
        color = textColor,
        modifier = Modifier.weight(1f),
        textAlign = TextAlign.Center
    )
}

// Helper Function for Dynamic Text Color
fun getTextColor(backgroundColor: Color): Color {
    val luminance = backgroundColor.luminance()
    return if (luminance > 0.5) Color.Black else Color.White
}


@Preview
@Composable
fun Preview_ListView() {
    val data = List(10) {
        DataUIModel(
            backgroundColor = Utils.getRandomColor(),
            dataResponseItems = List(10) { // Or use repeat with MutableList
                DataResponseItem(
                    id = it,
                    listId = it,
                    name = "List id $it"
                )
            }
        )
    }
    MaterialTheme {
        Column(modifier = Modifier.background(Color.White)) {
            ListView(data)
        }
    }
}