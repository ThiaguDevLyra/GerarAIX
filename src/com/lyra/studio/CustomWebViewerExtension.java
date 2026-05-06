package com.lyra.studio;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.*;
import android.app.Activity;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

@DesignerComponent(
    version = 1,
    description = "Extensão Custom WebViewer com recursos avançados e adaptação ao teclado virtual.",
    category = ComponentCategory.EXTENSION,
    nonVisible = true,
    iconName = "images/extension.png"
)
@SimpleObject(external = true)
public class CustomWebViewerExtension extends AndroidNonvisibleComponent {

    private Activity activity;

    public CustomWebViewerExtension(ComponentContainer container) {
        super(container.$form());
        this.activity = container.$context();
    }

    // Ajusta a janela do aplicativo para ser redimensionada quando o teclado aparecer
    @SimpleFunction(description = "Ajusta a tela para que o teclado não cubra o conteúdo do webviewer, redimensionando o layout.")
    public void AdaptarAoTeclado() {
        if (activity != null) {
            // Define o modo de entrada suave da janela para SOFT_INPUT_ADJUST_RESIZE
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

    // Aplica recursos avançados de forma simplificada a um componente WebViewer já existente
    @SimpleFunction(description = "Aplica configurações avançadas (DomStorage, Zoom, ViewPort) a um componente WebViewer nativo.")
    public void ConfigurarWebViewerAvancado(WebViewer webViewer) {
        if (webViewer != null) {
            WebView view = (WebView) webViewer.getView();
            WebSettings settings = view.getSettings();
            
            // Habilita o armazenamento DOM, essencial para sites modernos funcionarem corretamente
            settings.setDomStorageEnabled(true);
            
            // Garante que o JavaScript esteja ativado
            settings.setJavaScriptEnabled(true);
            
            // Permite que o WebViewer se comporte de forma responsiva ajustando ao ViewPort
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            
            // Habilita a função de zoom sem mostrar os botões de controle nativos na tela
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(false);
        }
    }
}