package com.dev.fetchtest.ui.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
    val listState = rememberLazyListState()

    var selectedIndex by remember { mutableIntStateOf(listState.firstVisibleItemIndex) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .collect { isScrolling ->
                if (!isScrolling) {
                    selectedIndex =
                        listState.firstVisibleItemIndex // Update only when scrolling finishes
                }
            }
    }

    Column {
        // Header Row
        HeaderView()

        // List view
        BodyView(listState = listState, data = data)

        // Navigation Row
        BottomNavigationView(listState = listState, data = data, selectedIndex = selectedIndex)
    }
}

@Composable
fun RowScope.HeaderCell(text: String) {
    Text(
        text = text,
        modifier = Modifier.weight(1f),
        textAlign = TextAlign.Center
    )
}

@Composable
fun RowScope.ListCell(text: String, textColor: Color) {
    Text(
        text = text,
        color = textColor,
        modifier = Modifier.weight(1f),
        textAlign = TextAlign.Center
    )
}

fun getTextColor(backgroundColor: Color): Color {
    val luminance = backgroundColor.luminance()
    return if (luminance > 0.5) Color.Black else Color.White
}

@Composable
fun HeaderView() {
    val context = LocalContext.current
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
}

@Composable
fun ColumnScope.BodyView(listState: LazyListState, data: List<DataUIModel>) {
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
}

@Composable
fun BottomNavigationView(data: List<DataUIModel>, selectedIndex: Int, listState: LazyListState) {
    val coroutineScope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        data.forEachIndexed { index, item ->
            val isItemSelected = index == selectedIndex

            Spacer(modifier = Modifier.width(6.dp.takeIf { isItemSelected } ?: 4.dp))

            Button(
                modifier = Modifier.scale(1.1f.takeIf { isItemSelected } ?: 1.0f),
                border = BorderStroke(2.dp, Color.Black).takeIf { isItemSelected },
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

            Spacer(modifier = Modifier.width(6.dp.takeIf { isItemSelected } ?: 4.dp))
        }
    }
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