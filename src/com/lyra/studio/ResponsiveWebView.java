package com.lyra.studio;

import android.app.Activity;
import android.view.View;
import android.view.Window; /* CORRECAO: Importacao da classe Window adicionada para evitar erros de tipo nao resolvido */
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings; /* CORRECAO: Importacao da classe WebSettings adicionada */
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.*;

@DesignerComponent(version = 1, description = "Componente WebView que se ajusta automaticamente ao teclado virtual.", category = ComponentCategory.EXTENSION, nonVisible = false, iconName = "images/extension.png")
@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.INTERNET") /* CORRECAO: Adicionada a permissao de internet obrigatoria para o WebView funcionar adequadamente */
public class ResponsiveWebView extends AndroidViewComponent {
    private WebView webView;
    private Activity activity;

    /* Construtor do componente */
    public ResponsiveWebView(ComponentContainer container) {
        super(container);
        this.activity = container.$context();

        /* Inicializa o WebView */
        this.webView = new WebView(this.activity);
        
        /* CORRECAO: Referencia correta a classe WebSettings importada */
        WebSettings settings = this.webView.getSettings();
        settings.setJavaScriptEnabled(true); /* Habilita o JavaScript */
        settings.setDomStorageEnabled(true); /* Habilita o DOM */
        
        this.webView.setWebViewClient(new WebViewClient());
        this.webView.setWebChromeClient(new WebChromeClient());

        /* Adiciona a View ao container */
        container.$add(this);

        /* Ajusta a janela da Activity para redimensionar quando o teclado abrir */
        /* CORRECAO: Referencia correta a classe Window importada */
        Window window = this.activity.getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    /* Retorna a view nativa */
    @Override
    public View getView() {
        return webView;
    }

    /* Funcao para carregar uma URL */
    /* CORRECAO: Metodo renomeado para camelCase (obrigatorio pelas regras de Checkstyle do compilador App Inventor) */
    @SimpleFunction(description = "Carrega uma URL no WebView.")
    public void irParaUrl(String url) {
        webView.loadUrl(url);
    }

    /* Funcao para recarregar a pagina */
    /* CORRECAO: Metodo renomeado para camelCase */
    @SimpleFunction(description = "Recarrega a pagina atual.")
    public void recarregar() {
        webView.reload();
    }

    /* Funcao para alternar o modo de ajuste do teclado */
    /* CORRECAO: Metodo renomeado para camelCase */
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
