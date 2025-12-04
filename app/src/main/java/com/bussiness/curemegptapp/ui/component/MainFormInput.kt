package com.bussiness.curemegptapp.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.bussiness.curemegptapp.R


@Composable
fun SettingHeader(title: String, onBackClick: () -> Unit) {

    Column(modifier = Modifier.background(Color.White)) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                start = 20.dp,
                end = 20.dp,
                top = 20.dp,
                bottom = 20.dp
            )
        ) {
            Row(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image( painter = painterResource(R.drawable.ic_back_nav_icon),
                    contentDescription = "", modifier = Modifier
                        .clickable { onBackClick() }
                        .wrapContentSize())
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                    color = Color.Black
                )
            }



        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(Color(0xFFEBE1FF)))

    }
}



@Composable
fun CommonHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.background(Color.White)) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                fontWeight = FontWeight.Medium
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth().height(2.dp).background(Color(0xFFEBE1FF)))

    }
}


@Composable
fun AppointmentMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onMarkComplete: () -> Unit,
    onReschedule: () -> Unit,
    onDelete: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        containerColor = Color.Unspecified,
        modifier = Modifier
            .width(200.dp).wrapContentHeight()
            .background(
                Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .border(1.dp, Color(0xFFE7E6F8), RoundedCornerShape(20.dp))
    ) {

        // Mark as Complete
        DropdownMenuItem(
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RoundedCustomCheckbox(
                        checked = checked,
                        onCheckedChange = { onCheckedChange(it) }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Mark As Complete",
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                                modifier =Modifier.padding(end = 10.dp)
                    )
                }
            },
            onClick = {
                onMarkComplete()
                onDismiss()
            }
        )

        // Reschedule
        DropdownMenuItem(
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_note_edit_icon),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Reschedule",
                        fontSize = 15.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                        modifier =Modifier.padding(end = 10.dp)
                    )
                }
            },
            onClick = {
                onReschedule()
                onDismiss()
            }
        )

        // Delete
        DropdownMenuItem(
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_delete_icon2),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Delete",
                        fontSize = 15.sp,
                        color = Color.Red,
                        fontFamily = FontFamily(Font(R.font.urbanist_medium)),
                        modifier =Modifier.padding(end = 10.dp)
                    )
                }
            },
            onClick = {
                onDelete()
                onDismiss()
            }
        )
    }
}

/*
@Composable
fun PostContentMenu(
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
                tint = Color.Black
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = Color.White,
            shape = RoundedCornerShape(15.dp) //  outer curve applied
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Edit",
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.edit_ic_p),
                        contentDescription = "Edit",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(20.dp)
                    )
                },
                onClick = {
                    expanded = false
                    onEditClick()
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Delete",
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.delete_mi),
                        contentDescription = "Delete",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(20.dp)
                    )
                },
                onClick = {
                    expanded = false
                    onDeleteClick()
                }
            )
        }
    }
}
 */



/*
@Composable
fun AppointmentMenuPopup(
    expanded: Boolean,
    onDismiss: () -> Unit,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onMarkComplete: () -> Unit,
    onReschedule: () -> Unit,
    onDelete: () -> Unit
) {
    if (expanded) {

        // Background click dismiss
        Box(
            Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { onDismiss() }
        )

        Popup(
            alignment = Alignment.TopEnd,
            onDismissRequest = onDismiss
        ) {

            Column(
                modifier = Modifier
                    .width(200.dp)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
                    .border(
                        1.dp,
                        Color(0xFFE7E6F8),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(vertical = 6.dp)
                    .shadow(6.dp, RoundedCornerShape(20.dp))
            ) {

                // Mark Complete
                MenuRow(
                    leading = {
                        RoundedCustomCheckbox(
                            checked = checked,
                            onCheckedChange = { onCheckedChange(it) }
                        )
                    },
                    text = "Mark As Complete",
                    textColor = Color.Black
                ) {
                    onMarkComplete()
                    onDismiss()
                }

                // Reschedule
                MenuRow(
                    leading = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_note_edit_icon),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    text = "Reschedule",
                    textColor = Color.Black
                ) {
                    onReschedule()
                    onDismiss()
                }

                // Delete
                MenuRow(
                    leading = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_delete_icon2),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    text = "Delete",
                    textColor = Color.Red
                ) {
                    onDelete()
                    onDismiss()
                }
            }
        }
    }
}

@Composable
fun MenuRow(
    leading: @Composable () -> Unit,
    text: String,
    textColor: Color,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        leading()
        Spacer(Modifier.width(10.dp))

        Text(
            text = text,
            fontSize = 15.sp,
            color = textColor,
            fontFamily = FontFamily(Font(R.font.urbanist_medium))
        )
    }
}

 */



@Composable
fun CancelButton(title : String = "Skip",onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .height(55.dp),
        shape = RoundedCornerShape(55),
        border = BorderStroke(1.dp, Color(0xFF697383)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = Color(0xFF181B1A)
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color(0xFF181B1A),
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily(Font(R.font.urbanist_medium))
        )
    }
}