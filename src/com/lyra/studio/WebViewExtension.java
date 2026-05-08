package com.lyra.studio;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.*;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

@DesignerComponent(version = 1, description = "Uma simples extensão de WebView para o App Inventor.", category = ComponentCategory.EXTENSION, nonVisible = true, iconName = "images/extension.png")
@SimpleObject(external = true)
/* 
 * CORREÇÃO: 
 * 1. Adicionada a anotação @UsesPermissions para solicitar a permissão de INTERNET.
 *    Sem essa permissão, a WebView será impedida de carregar URLs, falhando em tempo de execução e ocasionalmente na validação do build de extensões que usam WebView.
 */
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public class WebViewExtension extends AndroidNonvisibleComponent {
    private Activity activity;
    private WebView webView;

    /* Construtor da extensão inicializando o contexto */
    public WebViewExtension(ComponentContainer container) {
        super(container.$form());
        this.activity = container.$context();
    }

    @SimpleFunction(description = "Cria a WebView dentro de um arranjo (layout) e carrega a URL fornecida.")
    public void CriarWebView(AndroidViewComponent layout, String url) {
        if (webView == null) {
            webView = new WebView(activity);
            webView.setWebViewClient(new WebViewClient());
            /* Habilita execução de JavaScript na WebView */
            webView.getSettings().setJavaScriptEnabled(true);
            
            /* 
             * CORREÇÃO: 
             * 2. Importado e extraído para 'View' antes do cast.
             * 3. Verificação de instância (instanceof ViewGroup) garantindo que o componente é um contêiner válido.
             * 4. Trocado LinearLayout.LayoutParams por ViewGroup.LayoutParams genérico, 
             *    evitando ClassCastException caso o usuário passe um arranjo não-linear.
             */
            View view = layout.getView();
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                viewGroup.addView(webView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
        webView.loadUrl(url);
    }

    @SimpleFunction(description = "Navega para a página anterior no histórico da WebView.")
    public void Voltar() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        }
    }

    @SimpleFunction(description = "Avança para a próxima página no histórico da WebView.")
    public void Avancar() {
        if (webView != null && webView.canGoForward()) {
            webView.goForward();
        }
    }

    @SimpleFunction(description = "Recarrega a página atual da WebView.")
    public void Recarregar() {
        if (webView != null) {
            webView.reload();
        }
    }
}