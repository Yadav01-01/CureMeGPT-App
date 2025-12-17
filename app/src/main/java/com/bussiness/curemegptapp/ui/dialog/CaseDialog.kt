package com.bussiness.curemegptapp.ui.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bussiness.curemegptapp.R
import com.bussiness.curemegptapp.ui.component.CancelButton
import com.bussiness.curemegptapp.ui.component.GradientButton1


@Composable
fun CaseDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
//                        Box(
//                            modifier = Modifier
//                                .size(48.dp)
//                                .background(Color(0xFF5B47DB), CircleShape),
//                            contentAlignment = Alignment.Center
//                        ) {
                            Icon(
                                painter = painterResource( R.drawable.ic_new_case_icon),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                               .size(48.dp)
                            )
                       // }
                        Column {
                            Text(
                                "Start a New Case Chat?",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(painter = painterResource( R.drawable.ic_close), contentDescription = "Close", tint = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "This new case chat will be created only for Rose Logan (Spouse). Once created, you cannot switch members in the middle. The full case history will be saved in Rose Logan (Spouse)'s records.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
//                    OutlinedButton(
//                        onClick = onDismiss,
//                        modifier = Modifier
//                            .weight(1f)
//                            .height(48.dp),
//                        shape = RoundedCornerShape(24.dp),
//                        border = BorderStroke(2.dp, Color.LightGray)
//                    ) {
//                        Text("Cancel", color = Color.Black)
//                    }
                    CancelButton(
                        cancelText = "Cancel",
                        fontSize = 14.sp,
                        paddingHorizontal = 2.dp,
                        modifier = Modifier
                            .weight(0.7f)
                            .height(52.dp),
                        onClick = { onDismiss() }
                    )
//                    Button(
//                        onClick = onConfirm,
//                        modifier = Modifier
//                            .weight(1f)
//                            .height(48.dp),
//                        shape = RoundedCornerShape(24.dp),
//                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B47DB))
//                    ) {
//                        Text("Yes, Create Case Chat", fontSize = 13.sp)
//                    }

                    GradientButton1(
                        text = "Yes, Create Case Chat",
                        fontSize = 13.sp,
                        onClick = { onConfirm() },
                        modifier = Modifier
                            .weight(1.3f)
                            .height(52.dp)
                    )
                }
            }
        }
    }
}


