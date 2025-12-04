package com.bussiness.curemegptapp.ui.screen.main.schedule


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.ui.component.GradientRedButton

@Composable
fun MedicationsCard(appointment: Appointment) {
    var showMenu by remember { mutableStateOf(false) }
    var checkedState by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        shape = RoundedCornerShape(40.dp),
        color = Color(0xFFF9F9FD),
        border =  BorderStroke(1.dp, Color(0xFFE7E6F8))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min) .padding(20.dp)
        ) {
            // Icon Column
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(30.dp))
                    .border(color = Color(0xFF181818), width = 2.dp, shape = RoundedCornerShape(40.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = appointment.icon),
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
                    Text(
                        text = appointment.title,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.weight(1f)
                    )


                    PostContentMenu(
                        modifier = Modifier,
                        checked = checkedState,
                        onCheckedChange = { checkedState = it },
                        onEditClick = {  },
                        onDeleteClick = {  })
                }




                Spacer(modifier = Modifier.height(4.dp))



                Text(
                    text = "For: ${appointment.patientName}",
                    fontSize = 14.sp,
                    color = Color(0xFF4338CA),
                    fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Medication Type: Medication",
                    fontSize = 14.sp,
                    color = Color(0xFF374151),
                    fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                    fontWeight = FontWeight.Normal
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Date and Time
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_calender_health_icon),
                        contentDescription = null,
                        modifier = Modifier.size(29.dp),

                        )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = appointment.date,
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_date_health_icon),
                        contentDescription = null,
                        modifier = Modifier.size(29.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = appointment.time,
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Location
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_location_health_icon),
                        contentDescription = null,
                        modifier = Modifier.size(29.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = appointment.location,
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                        fontWeight = FontWeight.Normal,
                        lineHeight = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Description
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_note_health_icon),
                        contentDescription = null,
                        modifier = Modifier.size(29.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = appointment.description,
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if(appointment.isVisibleItem){
                    // View Summary Button
                    GradientRedButton(
                        text = "View Summary",
                        icon = R.drawable.ic_summary_view_icon,
                        width = 155.dp,
                        height = 35.dp,
                        fontSize = 14.sp,
                        imageSize = 18.dp,
                        modifier = Modifier.align(Alignment.End),
                        gradientColors = listOf(
                            Color(0xFF4338CA),
                            Color(0xFF211C64)
                        ),
                        onClick = { /* Your action */ }
                    )
                }



            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MedicationsCardPreview() {

    val sampleAppointment = Appointment(
        icon = R.drawable.ic_calender_health_icon,
        title = "Dental Check-up",
        doctor = "Dr. Vipin Khatri",
        patientName = "Rohit Sharma",
        date = "12 Dec 2025",
        time = "10:30 AM",
        location = "CureMe Hospital, 2nd Floor",
        description = "Regular follow-up appointment for dental cleaning.",
        isVisibleItem = true
    )

    MedicationsCard(appointment = sampleAppointment)
}
