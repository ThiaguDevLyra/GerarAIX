package com.lyra.studio;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.*;

@DesignerComponent(
    version = 1,
    description = "Um WebView customizado que se ajusta automaticamente para não ser coberto pelo teclado virtual.",
    category = ComponentCategory.EXTENSION,
    nonVisible = false,
    iconName = "images/extension.png"
)
@SimpleObject(external = true)
public class KeyboardResizingWebView extends AndroidViewComponent {

    private WebView webView;
    private Activity activity;

    public KeyboardResizingWebView(ComponentContainer container) {
        super(container);
        this.activity = (Activity) container.$context();
        this.webView = new WebView(this.activity);

        // Configurações básicas do WebView e habilitação de JavaScript
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.setWebViewClient(new WebViewClient());

        // Adiciona o componente de visualização à tela do App Inventor
        container.$add(this);

        // Ativa o ajuste de tela para o teclado por padrão ao iniciar
        SetKeyboardResize(true);
    }

    @Override
    public View getView() {
        return webView;
    }

    @SimpleFunction(description = "Carrega a URL especificada no WebView.")
    public void GoToUrl(String url) {
        this.webView.loadUrl(url);
    }

    @SimpleFunction(description = "Habilita ou desabilita o ajuste automático do WebView quando o teclado virtual é aberto.")
    public void SetKeyboardResize(final boolean enable) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (enable) {
                        // Redimensiona a janela para dar espaço ao teclado virtual
                        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    } else {
                        // Restaura o comportamento padrão da janela
                        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
                    }
                }
            });
        }
    }
}
