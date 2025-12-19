package com.bussiness.curemegptapp.ui.screen.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.curemegptapp.R

@Composable
fun MedicationsAndAllergies(
    medications: List<String>,
    allergies: List<String>
) {


    Column(
        modifier = Modifier.padding(20.dp)
    ) {

        // ------- Header -------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Current Medications",
                fontSize = 14.sp,
                color = Color(0xFF697383),
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium
            )
            Image(
                painter = painterResource(id = R.drawable.ic_edit_icon_cirlcular),
                contentDescription = "Edit",
                modifier = Modifier.size(45.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ------- Medications LazyRow -------
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(medications) { item ->
                MedicationChip(item, Color(0xFFD5D2F3))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Allergies",
            fontSize = 14.sp,
            color = Color(0xFF697383),
            fontFamily = FontFamily(Font(R.font.urbanist_medium)),
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ------- Allergies LazyRow -------
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(allergies) { item ->
                MedicationChip(item, Color(0xFFF8E3E7))
            }
        }


    }

}


@Composable
fun MedicationChip(text: String, backgroundColor: Color) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            fontSize = 12.sp,
            color = if (backgroundColor == Color(0xFFD5D2F3)) Color(0xFF211C64) else Color(
                0xFFF31D1D
            )
        )
    }
}