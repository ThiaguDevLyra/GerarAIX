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
     * CORREÇÃO APLICADA:
     * O processador de anotações do MIT App Inventor não suporta tipos genéricos como 'Object' como 
     * parâmetros de blocos (@SimpleFunction). Isso gera uma falha no build durante a compilação. 
     * A tentativa anterior de contornar a limitação usando 'Object' foi exatamente o que causou o erro.
     * 
     * SOLUÇÃO: Restauramos o tipo do parâmetro para 'HVArrangement' (que abrange arranjos verticais e 
     * horizontais) ou 'AndroidViewComponent', que são componentes válidos e reconhecidos pelo 
     * Annotation Processor do App Inventor sem disparar BuildExceptions.
     */
    @SimpleFunction(description = "Cria a WebView dentro de um arranjo (Vertical ou Horizontal).")
    public void createWebView(HVArrangement arrangement) {
        if (webView.getParent() != null) {
            ((ViewGroup) webView.getParent()).removeView(webView);
        }
        
        if (arrangement != null && arrangement.getView() instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) arrangement.getView();
            viewGroup.addView(webView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
