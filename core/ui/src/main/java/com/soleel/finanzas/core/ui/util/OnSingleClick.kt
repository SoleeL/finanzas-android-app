package com.soleel.finanzas.core.ui.util

import android.annotation.SuppressLint
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.semantics.Role
import com.soleel.finanzas.core.common.eventmanager.SingleEventManager


@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.onSingleClick(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    singleEventManager: SingleEventManager,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {

    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = {
            singleEventManager.processEvent {
                onClick()
            }
        },
        role = role,
        indication = LocalIndication.current,
        interactionSource = remember { MutableInteractionSource() }
    )
}