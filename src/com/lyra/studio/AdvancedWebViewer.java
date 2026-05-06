package com.lyra.studio;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.*;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.graphics.Rect;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;

@DesignerComponent(version = 1, description = "WebViewer customizado e avançado que se adapta ao teclado virtual, não permitindo que o teclado cubra o conteúdo.", category = ComponentCategory.EXTENSION, nonVisible = true, iconName = "images/extension.png")
@SimpleObject(external = true)
public class AdvancedWebViewer extends AndroidNonvisibleComponent {
    private Activity activity;
    private WebView webView;

    public AdvancedWebViewer(ComponentContainer container) {
        super(container.$form());
        this.activity = container.$context();
    }

    @SimpleFunction(description = "Cria o WebViewer avançado dentro de um arranjo (Layout) e ativa a adaptação ao teclado.")
    public void CriarWebViewer(AndroidViewComponent layout) {
        if (webView == null) {
            // Criação do WebView customizado
            webView = new WebView(activity);
            webView.setWebViewClient(new WebViewClient());
            webView.setWebChromeClient(new WebChromeClient());
            
            // Configurações avançadas simplificadas
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);

            // Adicionando ao layout do App Inventor
            ViewGroup viewGroup = (ViewGroup) layout.getView();
            viewGroup.addView(webView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            // Aplica a lógica para adaptar o teclado
            adaptarAoTeclado(viewGroup);
        }
    }

    @SimpleFunction(description = "Carrega uma URL no WebViewer customizado.")
    public void CarregarUrl(String url) {
        if (webView != null) {
            webView.loadUrl(url);
        }
    }

    @SimpleFunction(description = "Executa um script JavaScript no WebViewer.")
    public void ExecutarJavaScript(String script) {
        if (webView != null) {
            webView.evaluateJavascript(script, null);
        }
    }

    // Método privado que detecta a abertura do teclado e ajusta a interface
    private void adaptarAoTeclado(final View rootView) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = rootView.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;

                // Se o teclado cobre mais de 15% da tela, aplica um padding inferior
                if (keypadHeight > screenHeight * 0.15) {
                    rootView.setPadding(0, 0, 0, keypadHeight);
                } else {
                    // Restaura ao normal quando o teclado é fechado
                    rootView.setPadding(0, 0, 0, 0);
                }
            }
        });
    }
}