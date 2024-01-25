package com.bmarpc.acpsiam.batteryells

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bmarpc.acpsiam.batteryells.ui.theme.BatterYellsTheme
import com.bmarpc.acpsiam.batteryells.MyCard as MyCard1

class BatteryTipsActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BatterYellsTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {

                    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

                    Scaffold(
                        modifier = Modifier
                            .nestedScroll(connection = scrollBehavior.nestedScrollConnection)
                            .fillMaxSize(),
                        topBar = {
                            TopAppBar(
                                scrollBehavior = scrollBehavior,
                                title = {
                                    Text(text = "Battery Tips")
                                },
                                navigationIcon = {
                                    IconButton(
                                        onClick = {
                                            finish()
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "Go back"
                                        )
                                    }
                                }
                            )
                        }
                    ) { values ->
                        LazyColumn(
                            modifier = Modifier
                                .padding(values)
                                .padding(bottom = 20.dp)
                        ) {
                            item {
                                for (tips in resources.getStringArray(R.array.battery_tips)) {
                                    val cardColor = when {
                                        tips.contains("[GOOD]") -> MaterialTheme.colorScheme.primaryContainer
                                        tips.contains("[NEUTRAL]") -> MaterialTheme.colorScheme.secondaryContainer
                                        tips.contains("[DANGEROUS]") -> MaterialTheme.colorScheme.errorContainer
                                        else -> MaterialTheme.colorScheme.primary
                                    }
                                    MyCard1(
                                        batteryTip = tips
                                            .replace("[GOOD] ", "")
                                            .replace("[NEUTRAL] ", "")
                                            .replace("[DANGEROUS] ", ""),
                                        cardColor = cardColor
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCard(
    batteryTip: String,
    cardColor: Color = MaterialTheme.colorScheme.primaryContainer
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = cardColor
//            containerColor = Color(
//                red = (cardColor.red + Random.nextFloat()).coerceIn(0f, 1f),
//                green = cardColor.green + Random.nextFloat().coerceIn(0f, 1f),
//                blue = cardColor.blue + Random.nextFloat().coerceIn(0f, 1f),
//                alpha = 1f
//            )
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 12.dp, end = 12.dp, top = 8.dp)
    ) {
        Text(
            text = batteryTip,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.tertiary,
            fontFamily = FontFamily(Font(R.font.open_sans))
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyCardPreview() {
    BatterYellsTheme {
        Column {
            MyCard1("Androiddfb suvvussbpsvsss[v un uh  yu byu yug yg yb uy n u yy gg y u ui uin uh ui oij ij u8 u9 oik9ij u9j 9ik 9i j8 h78j 9ik j h j i i9j u8h 78 9ij u9ij 8h uj oi u9h y8 i ij y8h u")
            MyCard1("Android")
        }
    }
}