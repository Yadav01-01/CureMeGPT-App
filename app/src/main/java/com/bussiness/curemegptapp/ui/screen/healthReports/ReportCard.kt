package com.bussiness.curemegptapp.ui.screen.healthReports

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.ui.screen.main.schedule.EditDeleteMenu

//ReportCard


@Composable
fun ReportCard() {
   var checkedState by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        shape = RoundedCornerShape(40.dp),
        color = Color(0xFFF6F5FC),
        border =  BorderStroke(1.dp, Color(0xFFE0DFE5))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min) .padding(20.dp)
        ) {
            // Icon Column
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(30.dp))
                    .border(
                        color = Color(0xFF181818),
                        width = 2.dp,
                        shape = RoundedCornerShape(40.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_app_reporting_icon),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Content Column
            Column(
                modifier = Modifier.weight(1f)
            ) {


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Dental X-ray Analysis",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                            fontWeight = FontWeight.Medium,
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "For: Peter Logan",
                            fontSize = 14.sp,
                            color = Color(0xFF4338CA),
                            fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                            fontWeight = FontWeight.Normal
                        )
                    }


                    EditDeleteMenu(
                        modifier = Modifier,
                        onEditClick = {  },
                        onDeleteClick = { })
                }


                Spacer(modifier = Modifier.height(4.dp))

var priority = "Attention"
                // Medication Type
                PriorityImageTag(
                    label = priority,
                    color = if (priority == "Attention") Color(0xFFF31D1D) else Color(0xFF19BB9B),
                    backgroundColor = if (priority == "Attention") Color(0xFFF6DFE6) else Color(0xFFD3ECEC),
                    borderColor = if (priority == "Attention") Color(0xFFF31D1D) else Color(0xFF19BB9B),
                )

                Spacer(modifier = Modifier.height(10.dp))



                // Days
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_date_icon_rotate),
                        contentDescription = null,
                        modifier = Modifier.size(29.dp),
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "08/26/2025",
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_note_health_icon),
                        contentDescription = null,
                        modifier = Modifier.size(29.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "All blood markers within normal range. Excellent overall health indicators.",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.width(6.dp))

                }

                Surface(
                    shape = RoundedCornerShape(30.dp),
                    color = Color(0xFFCAC7F0),
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text(
                        text = "2 Files",
                        fontSize = 10.sp,
                        color = Color(0xFF211C64),
                        fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }




            }
        }
    }
}


@Composable
fun PriorityImageTag(label: String, color: Color,borderColor : Color,backgroundColor : Color) {
    Box(
        modifier = Modifier
            .border(1.dp, borderColor, RoundedCornerShape(50.dp))
            .background( backgroundColor, RoundedCornerShape(50.dp))
            .padding(vertical = 3.dp, horizontal = 8.dp)
    ) {

        Row{

            Image(painter = painterResource(id= R.drawable.ic_attention_icon_red),
                contentDescription = null,
                )
            Spacer(Modifier.width(5.dp))

            Text(
                text = label,
                color = color,
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
fun ReportCardPreview() {
    val navController = rememberNavController()
    ReportCard()
}