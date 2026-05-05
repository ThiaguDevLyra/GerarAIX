package com.lyra.studio;

import android.app.Activity;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.*;

@DesignerComponent(version = 1, description = "Custom WebViewer que se adapta ao teclado virtual, impedindo que o conteúdo seja coberto.", category = ComponentCategory.EXTENSION, nonVisible = false, iconName = "images/web.png")
@SimpleObject(external = true)
public class AdaptiveWebViewer extends AndroidViewComponent {
    private WebView webView;
    private Activity activity;

    public AdaptiveWebViewer(ComponentContainer container) {
        super(container);
        activity = container.$context();

        // Inicializa o WebView customizado
        webView = new WebView(activity);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        // Adiciona o componente visual ao container
        container.$add(this);

        // Ajusta a janela principal para redimensionar quando o teclado virtual aparecer
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public android.view.View getView() {
        return webView;
    }

    @SimpleFunction(description = "Acessa uma URL específica.")
    public void GoToUrl(String url) {
        webView.loadUrl(url);
    }

    @SimpleFunction(description = "Ativa ou desativa a adaptação do teclado virtual na tela.")
    public void SetKeyboardAdaptation(boolean enable) {
        if (enable) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        } else {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        }
    }
}