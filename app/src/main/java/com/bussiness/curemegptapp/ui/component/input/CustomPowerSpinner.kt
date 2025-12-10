package com.bussiness.curemegptapp.ui.component.input

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.curemegptapp.R

@Composable
fun CustomPowerSpinner(
    modifier: Modifier = Modifier,
    modifierDropDown: Modifier = Modifier,
    selectedText: String = "Select",
    onSelectionChanged: (String) -> Unit = {},
    menuPadding: Dp = 24.dp,
    reasons: List<String> = listOf()
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedReason by remember { mutableStateOf(selectedText) }

    Box(modifier = modifier.background(Color.White) ) {
        // Main button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(  interactionSource = remember { MutableInteractionSource() },
                    indication = null){ expanded = !expanded },
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFF697383)),
            shape = RoundedCornerShape(50.dp),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedReason,
                    color = if (selectedReason == "Select") Color(0xFF697383) else Color(0xFF697383),
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.urbanist_regular)),

                    )

                Image(
                    painter = painterResource(if (expanded) R.drawable.ic_dropdown_show else R.drawable.ic_dropdown_icon),
                    contentDescription = "",
                )
            }
        }

 /*       // Dropdown Menu
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier
//                .padding(horizontal = 15.dp, vertical = 5.dp)
//                .fillMaxWidth()
//                .border(width = (1).dp, color = Color(0xFF808080), shape = RoundedCornerShape(8.dp))
//                .background(color = Color.White , shape = RoundedCornerShape(8.dp)),
//
//            containerColor = Color.Unspecified
//        ) {
//            reasons.forEachIndexed { index, reason ->
//                DropdownMenuItem(
//
//                    text = {
//                        if (reason == selectedReason) {
//                            Text(
//                                text = reason,
//                                color = Color.White,
//                                fontSize = 16.sp
//                            )
//                        } else {
//                            Text(
//                                text = reason,
//                                color = Color.Black,
//                                fontSize = 16.sp
//                            )
//                        }
//                    },
//                    onClick = {
//                        selectedReason = reason
//                        expanded = false
//                        onSelectionChanged(reason)
//                    }
//                )
////                if (index != reasons.lastIndex) {
////                    Divider(
////                        color = Color(0xFF4A4A4A),
////                        thickness = 1.dp
////                    )
////                }
//            }
//        }*/
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = modifierDropDown.fillMaxWidth().heightIn(max = 300.dp)
                .padding(horizontal = 15.dp, vertical = 5.dp)
               // .border(1.dp, Color(0xFF808080), RoundedCornerShape(12.dp))
                .background(Color.White, RoundedCornerShape(12.dp)),
            containerColor = Color.White,
           // elevation = MenuDefaults.DropdownMenuElevation(0.dp)   // 🔥 NO SHADOW
            shape = RoundedCornerShape(12.dp),

        ) {

            reasons.forEachIndexed { index, reason ->

                val isSelected = reason == selectedReason

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(
                            brush = if (isSelected)
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF4338CA),
                                        Color(0xFF211C64)
                                    )
                                )
                            else Brush.linearGradient(listOf(Color.Unspecified, Color.Unspecified)),
                            shape = RoundedCornerShape(50.dp)
                        )

                        .clickable( interactionSource = remember { MutableInteractionSource() },
                        indication = null){
                            selectedReason = reason
                            expanded = false
                            onSelectionChanged(reason)
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {

                    Text(
                        text = reason,
                        color = if (isSelected) Color.White else Color.Black,
                        fontSize = 16.sp
                    )
                }
            }
        }

    }
}