package com.soleel.finanzas.core.ui.utils

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Smartphone 420dpi",
    showBackground = true,
    device = "spec:width=411dp,height=891dp,dpi=420"
)
annotation class LongDevicePreview

@Preview(
    name = "Smartphone - 320dpi",
    showBackground = true,
    device = "spec:width=360dp,height=640dp,dpi=320"
)
annotation class ShortDevicePreview

@Preview(
    name = "Tablet",
    showBackground = true,
    device = "spec:width=800dp,height=1280dp,dpi=240"
)
annotation class TabletPreview