package com.bmarpc.acpsiam.batteryells

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.background
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.layout.wrapContentWidth
import androidx.glance.text.FontFamily
import androidx.glance.text.FontStyle
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle

class BatteryWidgetReceiever : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = BatteryWidget()
}

class BatteryWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme() {
                WidgetContents(getBatteryTemperature(context), context)
            }
        }
    }
}

private fun getBatteryTemperature(context: Context): Float {
    val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    val temp = intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)?.toFloat() ?: 0f
    return temp
}


@Composable
fun WidgetContents(
    batteryTemperature: Float,
    context: Context
) {
    val temperature = batteryTemperature
    var currentBatteryTemperature by remember { mutableStateOf(temperature) }

    Row(
        modifier = GlanceModifier
            .padding(20.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .cornerRadius(6.dp)
            .background(
                day = Color(0xFFD1E6F3),
                night = Color(0xFF364954)
            )
    ) {
        Column(
            modifier = GlanceModifier
                .fillMaxHeight()
                .wrapContentWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = (currentBatteryTemperature / 10f).toString() + "°C",
                modifier = GlanceModifier
                    .padding(bottom = 8.dp),
                style = TextStyle(
                    color = ColorProvider(
                        day = Color(0xff0e2c3d),
                        night = Color(0xFFD1E6F3)
                    ),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            )
            Text(
                text = "by BatterYells",
                style = TextStyle(
                    color = ColorProvider(
                        day = Color(0xff0e2c3d),
                        night = Color(0xFFD1E6F3)
                    ),
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Serif,
                    fontStyle = FontStyle.Italic
                )
            )
        }
        Column(
            modifier = GlanceModifier
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Device\nTemperature",
                style = TextStyle(
                    color = ColorProvider(
                        day = Color(0xff0e2c3d),
                        night = Color(0xFFD1E6F3)
                    ),
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.End
                ),
                modifier = GlanceModifier
                    .padding(bottom = 8.dp)
            )
            androidx.glance.layout.Box(
                contentAlignment = Alignment.Center,
                modifier = GlanceModifier
                    .cornerRadius(4.dp)
                    .height(30.dp)
                    .width(30.dp)
                    .background(
                        day = Color(0xFF4A7EA7),
                        night = Color(0xFFB5C9D7)
                    )
                    .clickable {
                        currentBatteryTemperature = getBatteryTemperature(context)
                    }
            ) {

                Image(
                    provider = ImageProvider(resId = R.drawable.round_refresh_24),
                    contentDescription = "Refresh"
                )
            }
        }
    }
}


@Composable
@Preview
fun WidgetContentsPreview() {
    WidgetContents(batteryTemperature = 29.9f, context = LocalContext.current)
}
