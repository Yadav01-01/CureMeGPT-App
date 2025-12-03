package com.bussiness.curemegptapp.ui.component.input


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.data.model.BottomItem
import com.bussiness.curemegptapp.navigation.AppDestination
import com.bussiness.curemegptapp.util.AppConstant
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily


@Composable
fun CustomBottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {

    val items = listOf(
        BottomItem(AppConstant.BOTTOM_NAV_HOME, R.drawable.ic_home_icon, AppDestination.Home::class.qualifiedName!!),
        BottomItem(AppConstant.BOTTOM_NAV_SCHEDULE, R.drawable.ic_schedule_icon, AppDestination.Schedule::class.qualifiedName!!),
        BottomItem(AppConstant.BOTTOM_NAV_FAMILY, R.drawable.ic_family_icon, AppDestination.Family::class.qualifiedName!!),
        BottomItem(AppConstant.BOTTOM_NAV_REPORTS, R.drawable.ic_report_icon, AppDestination.Reports::class.qualifiedName!!)
    )

    Box(
        Modifier
            .fillMaxWidth().background(Color.Transparent)

    ) {

        // Background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(135.dp)
                .background(Color.Transparent)
        )

        Row(
            Modifier
                .fillMaxWidth().height(90.dp)
                .padding(top = 18.dp)
                .background(Color.White, RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp))
                .padding(horizontal = 8.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            items.forEachIndexed { index, item ->

                if (index == 2) Spacer(modifier = Modifier.width(50.dp))

                BottomBarItem(
                    item = item,
                    selected = selectedIndex == index,
                    onClick = { onItemSelected(index) }
                )
            }
        }

        // ⭐ Center Button
//        Box(
//            modifier = Modifier
//                .size(70.dp)
//                .align(Alignment.TopCenter)
//        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_middle_icon),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopCenter)
                    .size(140.dp)
            )
       // }
    }
}

@Composable
fun BottomBarItem(
    item: BottomItem,
    selected: Boolean,
    onClick: () -> Unit
) {

    val textColor = if (selected) Color(0xFF6750FF) else Color.Black

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null   // ⭐ Ripple removed
            ) {
                onClick()
            }

    ) {

        // ⭐ Drawable background when selected
        if (selected) {
            Image(
                painter = painterResource(id = R.drawable.selected_item_bg),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
                   // .clip(RoundedCornerShape(18.dp))
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {

            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = item.label,
                tint = if (selected) Color(0xFF372EA6) else Color.Black,
                modifier = Modifier.size(22.dp)
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = item.label,
                fontSize = 12.sp,
                color = textColor,
                fontFamily = FontFamily(Font(R.font.urbanist_regular)),
                fontWeight = FontWeight.Normal,
            )
            Spacer(Modifier.height(4.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CustomBottomBarPreview() {
    CustomBottomBar(
        selectedIndex = 0,
        onItemSelected = {}
    )
}

