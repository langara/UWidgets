@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE", "EXPOSED_PARAMETER_TYPE", "EXPOSED_PROPERTY_TYPE")

package pl.mareklangiewicz.usystem

import androidx.compose.runtime.*
import androidx.compose.ui.createSkiaLayer
import androidx.compose.ui.geometry.*
import org.w3c.dom.*
import androidx.compose.ui.native.ComposeLayer
import org.jetbrains.compose.web.dom.*
import org.jetbrains.skiko.wasm.*

@Composable fun UCanvasWindow(
    attrs: AttrBuilderContext<HTMLCanvasElement>? = null,
    content: @Composable () -> Unit,
) = Canvas(attrs = {
    attrs?.invoke(this)
    ref {
        var disposed = false
        var window: UCanvasWindow? = null
        onWasmReady { // TODO: what exactly has to wait on onWasmReady ?
            disposed && return@onWasmReady
            window = UCanvasWindow(it).apply { setContent(content) }
        }
        onDispose { window?.dispose(); disposed = true }
    }
})

/** @see androidx.compose.ui.window.ComposeWindow */
private class UCanvasWindow(canvas: HTMLCanvasElement) {

    val layer = ComposeLayer(
        layer = createSkiaLayer(),
        showSoftwareKeyboard = { println("TODO showSoftwareKeyboard in JS") },
        hideSoftwareKeyboard = { println("TODO hideSoftwareKeyboard in JS") },
        getTopLeftOffset = { Offset.Zero },
    )

    init {
        layer.layer.attachTo(canvas)
        canvas.setAttribute("tabindex", "0")
        layer.layer.needRedraw()
        val scale = layer.layer.contentScale
        layer.setSize((canvas.width / scale).toInt(), (canvas.height / scale).toInt())
    }

    fun setContent(content: @Composable () -> Unit) = layer.setContent(content = content)

    fun dispose() = layer.dispose()
    // FIXME_later fix bug: (when leaving UDemo3 tab):
    //    Preconditions.kt?8576:98 Uncaught IllegalStateException {message: 'ComposeScene is closed'...
    // (maybe we somehow try to close the scene twice?)
}