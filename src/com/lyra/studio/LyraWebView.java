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
     * O erro no build ocorre porque a classe 'HVArrangement' (inserida em tentativas anteriores)
     * e uma classe abstrata no codigo-fonte do App Inventor e nao expoe diretamente um @DesignerComponent.
     * Como resultado, o Annotation Processor (ComponentProcessor) que converte o codigo Java
     * para a estrutura de blocos .aix nao consegue mapear esse tipo, causando a falha silenciosa ou 
     * BuildException.
     * 
     * SOLUCAO: Reverter para 'AndroidViewComponent' (tipo que o compilador de blocos entende) 
     * e adicionar uma verificacao segura 'instanceof ViewGroup' para evitar o ClassCastException 
     * que voce estava tentando resolver anteriormente.
     */
    @SimpleFunction(description = "Cria a WebView dentro de um arranjo (Vertical ou Horizontal).")
    public void createWebView(AndroidViewComponent arrangement) {
        if (webView.getParent() != null) {
            ((ViewGroup) webView.getParent()).removeView(webView);
        }
        
        // Verificacao de seguranca para evitar ClassCastException caso o usuario passe um componente invalido
        if (arrangement.getView() instanceof ViewGroup) {
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