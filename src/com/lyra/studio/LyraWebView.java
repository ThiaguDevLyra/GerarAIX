package com.lyra.studio;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.*;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.ViewGroup;

/* Classe principal da extensao */
@DesignerComponent(version = 1, description = "Lyra WebView: Uma simples webview.", category = ComponentCategory.EXTENSION, nonVisible = true, iconName = "")
@SimpleObject(external = true)
public class LyraWebView extends AndroidNonVisibleComponent {
    private WebView webView;

    /* Construtor da extensao */
    public LyraWebView(ComponentContainer container) {
        super(container.$form());
        webView = new WebView(container.$context());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    }

    /* 
     * CORRECAO APLICADA:
     * O erro no build persistiu porque 'AndroidViewComponent' tambem e uma classe abstrata
     * (assim como HVArrangement) e nao possui a anotacao @DesignerComponent. Muitos compiladores
     * de extensoes falham ao tentar processar essas classes no Processor de Anotacoes.
     * 
     * SOLUCAO: Alterado o tipo do parametro para 'Object' e feito um casting seguro.
     * O tipo 'Object' e aceito universalmente pelo compilador e evita qualquer BuildException
     * causada pelo Annotation Processor. A verificacao em tempo de execucao (instanceof) mantem a seguranca.
     */
    @SimpleFunction(description = "Cria a WebView dentro de um arranjo (Vertical ou Horizontal).")
    public void createWebView(Object arrangement) {
        if (webView.getParent() != null) {
            ((ViewGroup) webView.getParent()).removeView(webView);
        }
        
        // Verificacao de seguranca para evitar ClassCastException caso o usuario passe um componente invalido
        if (arrangement instanceof AndroidViewComponent) {
            AndroidViewComponent viewComponent = (AndroidViewComponent) arrangement;
            if (viewComponent.getView() instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) viewComponent.getView();
                viewGroup.addView(webView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
    }

    /* Carrega a URL na webview */
    @SimpleFunction(description = "Carrega uma URL na WebView.")
    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    /* Recarrega a pagina */
    @SimpleFunction(description = "Recarrega a pagina atual.")
    public void reload() {
        webView.reload();
    }

    /* Volta para a pagina anterior */
    @SimpleFunction(description = "Retorna para a pagina anterior.")
    public void goBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }

    /* Avanca para a proxima pagina */
    @SimpleFunction(description = "Avanca para a proxima pagina.")
    public void goForward() {
        if (webView.canGoForward()) {
            webView.goForward();
        }
    }
}