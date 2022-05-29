package pl.mareklangiewicz.udemo

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.*
import pl.mareklangiewicz.uwidgets.*

@Composable
fun UDemo() {
    UTabs(
        "UDemo 0" to { UDemo0() },
        "UDemo 1" to { UText("TODO UDemo1") },
        "UDemo 2" to { UText("TODO UDemo2") },
    )
}

@Composable
fun UDemo0() {
    UColumn {
        UBox { UBox { URow { UDemoTexts(3) } } }
        UBox { UBox { URow { UDemoTexts(15) } } }
        CompositionLocalProvider(LocalUBackground provides Color.hsl(200f, .4f, .8f, .6f)) {
            UBox { UBox { UBox { UDemoTexts() } } }
        }
        UBox { UBox { UColumn { UDemoTexts() } } }
    }
}

@Composable
private fun UDemoTexts(
    count: Int = 20,
    boxed: Boolean = true,
    center: Boolean = true,
    bold: Boolean = true,
    mono: Boolean = true,
) {
    for (i in 1..count) {
        val c = 'A' + i - 1
        val s = "$c".repeat(i)
        if (boxed) UBoxedText(s, center, bold, mono)
        else UText(s, center, bold, mono)
    }
}