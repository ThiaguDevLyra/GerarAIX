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
     * Embora a tentativa anterior usasse HVArrangement, o Annotation Processor pode falhar em
     * algumas versoes ou forks do MIT App Inventor ao tentar resolver tipos muito especificos.
     * O tipo 'AndroidViewComponent' resolve perfeitamente essa questao por ser uma classe base 
     * amplamente suportada para todos os componentes visuais, incluindo Arranjos Verticais e Horizontais.
     * Alem disso, capitalizei a primeira letra dos metodos para seguir o padrao correto do AI2.
     */
    @SimpleFunction(description = "Cria a WebView dentro de um arranjo (Vertical ou Horizontal).")
    public void CreateWebView(AndroidViewComponent arrangement) {
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
    public void LoadUrl(String url) {
        webView.loadUrl(url);
    }

    /* Recarrega a pagina */
    @SimpleFunction(description = "Recarrega a pagina atual.")
    public void Reload() {
        webView.reload();
    }

    /* Volta para a pagina anterior */
    @SimpleFunction(description = "Retorna para a pagina anterior.")
    public void GoBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }

    /* Avanca para a proxima pagina */
    @SimpleFunction(description = "Avanca para a proxima pagina.")
    public void GoForward() {
        if (webView.canGoForward()) {
            webView.goForward();
        }
    }
}