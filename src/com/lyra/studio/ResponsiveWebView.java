package com.lyra.studio;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.*;

@DesignerComponent(version = 1, description = "Componente WebView que se ajusta automaticamente ao teclado virtual.", category = ComponentCategory.EXTENSION, nonVisible = false, iconName = "images/extension.png")
@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public class ResponsiveWebView extends AndroidViewComponent {
    private WebView webView;
    private Activity activity;

    public ResponsiveWebView(ComponentContainer container) {
        super(container);
        this.activity = container.$context();

        this.webView = new WebView(this.activity);
        
        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        
        this.webView.setWebViewClient(new WebViewClient());
        this.webView.setWebChromeClient(new WebChromeClient());

        container.$add(this);

        Window window = this.activity.getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public View getView() {
        return webView;
    }

    @SimpleFunction(description = "Carrega uma URL no WebView.")
    public void irParaUrl(String url) {
        webView.loadUrl(url);
    }

    @SimpleFunction(description = "Recarrega a pagina atual.")
    public void recarregar() {
        webView.reload();
    }

    @SimpleFunction(description = "Habilita ou desabilita o ajuste automatico do layout para o teclado virtual.")
    public void ajustarAoTeclado(boolean ajustar) {
        Window window = activity.getWindow();
        if (ajustar) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        } else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
    }
}