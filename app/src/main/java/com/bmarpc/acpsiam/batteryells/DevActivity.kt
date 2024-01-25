package com.bmarpc.acpsiam.batteryells

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bmarpc.acpsiam.batteryells.ui.theme.BatterYellsTheme

val bmcLink = "https://www.buymeacoffee.com/acpSiam"
val fbLink = "https://www.facebook.com/acp.siam.kB"
val linkedInLink = "https://www.linkedin.com/in/acpsiam"
val githubLink = "https://www.github.com/acpsiam"
val email = "acpsiam@gmail.com"
val phone = "+8801836652701"

class DevActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BatterYellsTheme {
                Surface (
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold (
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "About DEV")
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
                        },
                        modifier = Modifier
                    ) {values ->
                        Home(modifier = Modifier.padding(values))
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(modifier: Modifier = Modifier) {

    val mContext = LocalContext.current

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        OutlinedCard(
            colors = CardDefaults
                .cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
            modifier = modifier
                .fillMaxWidth(0.9f)
        ) {
            Column(
                modifier = Modifier
                    .padding(22.dp)
                    .fillMaxWidth()
            )
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .size(100.dp),
                        shape = CircleShape
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterHorizontally),
                            painter = painterResource(id = R.drawable.dev_transparent),
                            contentDescription = "dev_icon",
                            contentScale = ContentScale.Inside
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "acpSiam",
                    color = MaterialTheme.colorScheme.error,
                    fontFamily = FontFamily(Font(R.font.abril_fatface)),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    MyButton(
                        buttonType = "dial",
                        painter = painterResource(id = R.drawable.call_24px),
                        buttonText = "(+880) 1836652701",
                        extraIcons = listOf(
                            painterResource(id = R.drawable.whatsapp),
                            painterResource(id = R.drawable.bkash_transparent),
                            painterResource(id = R.drawable.nagad_logo_transparent),
                        )
                    )

                    MyButton(
                        buttonType = "mail",
                        painter = painterResource(id = R.drawable.mail_24px),
                        buttonText = "acpsiam@gmail.com"
                    )

                    MyButton(
                        buttonType = "fb",
                        painter = painterResource(id = R.drawable.facebook),
                        buttonText = "acp.siam.kB"
                    )

                    MyButton(
                        buttonType = "github",
                        painter = painterResource(id = R.drawable.github),
                        buttonText = "acpSiam"
                    )

                    MyButton(
                        buttonType = "linkedIn",
                        painter = painterResource(id = R.drawable.ic_linkedin_24),
                        buttonText = "acpSiam"
                    )


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ElevatedCard(
                            colors = CardDefaults
                                .cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                ),
                            modifier = Modifier
                                .wrapContentSize()
                                .clip(RoundedCornerShape(12.dp))
                                .combinedClickable(
                                    onClick = {
                                        mContext.startActivity(
                                            Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse(bmcLink)
                                            )
                                        )
                                    },
                                    onLongClick = {
                                        copyToClipboard(
                                            context = mContext,
                                            label = "batterYells BMC",
                                            data = bmcLink
                                        )
                                    }
                                )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.bmc),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(12.dp)
                                    .width(120.dp)
                                    .wrapContentHeight()
                            )
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyButton(
    buttonType: String,
    painter: Painter,
    buttonText: String,
    extraIcons: List<Painter> = emptyList()
) {

    val mContext = LocalContext.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(40.dp)
            .padding(bottom = 6.dp)
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            modifier = Modifier
                .fillMaxHeight()
                .clip(CircleShape)
                .combinedClickable(
                    onLongClick = {
                        val content = when (buttonType) {
                            "dial" -> "Phone: $phone"
                            "fb" -> "Facebook: $fbLink"
                            "github" -> "GitHub: $githubLink"
                            "linkedIn" -> "LinkedIn: $linkedInLink"
                            "mail" -> "Email: $email"
                            else -> null
                        }
                        if (content != null) {
                            copyToClipboard(mContext, "BatterYells", content)
                        }
                    },
                    onClick = {
                        val intent = when (buttonType) {
                            "dial" -> Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                            "fb" -> Intent(Intent.ACTION_VIEW, Uri.parse(fbLink))
                            "github" -> Intent(Intent.ACTION_VIEW, Uri.parse(githubLink))
                            "linkedIn" -> Intent(Intent.ACTION_VIEW, Uri.parse(linkedInLink))
                            "mail" -> Intent(Intent.ACTION_SENDTO).apply {
                                data = Uri.parse("mailto:$email")
                                putExtra(Intent.EXTRA_SUBJECT, "from BatterYells")
                            }

                            else -> null
                        }
                        intent?.let {
                            mContext.startActivity(it)
                        }
                    }
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 24.dp, end = 24.dp),
            ) {
                Icon(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(20.dp),
                    painter = painter,
                    contentDescription = buttonText,
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    fontFamily = FontFamily(Typeface.DEFAULT_BOLD),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    text = buttonText,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        for (icon in extraIcons) {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 2.dp),
                painter = icon,
                contentDescription = "",
                tint = Color.Unspecified
            )
        }
    }
}


fun copyToClipboard(context: Context, label: String, data: String) {
    (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
        ClipData(ClipData.newPlainText(label, data))
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ImageCardPreview() {
    Home()
}

