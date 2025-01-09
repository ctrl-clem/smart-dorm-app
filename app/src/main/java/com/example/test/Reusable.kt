package com.example.test

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.sharp.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test.ui.theme.TestTheme
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.toList
import java.util.ArrayList

@Composable
fun TopBar(string: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Magenta)
            .padding(20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Title (Centered)
            Box(modifier = Modifier.weight(7f), contentAlignment = Alignment.Center) {
                Text(text = string, color = Color.White)
            }

        }
    }
}



@Composable
fun BookButton(
    onClick:  () -> Unit = {},

    ){
    Column(
        //verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 50.dp)
    ) {
        ExtendedFloatingActionButton(
            containerColor = Color.Magenta,
            contentColor = Color.White,
            onClick =  onClick,
            text = { Text(text = "Book it") },
            icon = { Icon(Icons.Sharp.Check, "Extended floating action button.") },


            )
    }

}

@Preview
@Composable
fun BookButtonPreview(){
    BookButton(onClick = {})
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentedButton() {
    var selectedIndex by remember { mutableStateOf(0) }
    val options = listOf("Tomorrow", "In 2 days", "In 3 days")
    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                onClick = { selectedIndex = index },
                selected = index == selectedIndex
            ) {
                Text(label)
            }
        }
    }
}

@Preview
@Composable
fun SegmentedButtonPreview(){
    SegmentedButton()
}
