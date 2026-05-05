package com.lyra.studio;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.*;

@DesignerComponent(version = 1, description = "Um WebViewer customizado que se adapta ao teclado virtual, nao permitindo que cubra o conteudo.", category = ComponentCategory.EXTENSION, nonVisible = false, iconName = "images/web.png")
@SimpleObject(external = true)
public class AdaptiveWebViewer extends AndroidViewComponent {
    private Context context;
    private Activity activity;
    private WebView webView;
    private FrameLayout container;

    public AdaptiveWebViewer(ComponentContainer container) {
        super(container);
        this.context = container.$context();
        this.activity = (Activity) context;
        
        /* Cria o conteiner principal que ira sofrer o ajuste de padding */
        this.container = new FrameLayout(context);
        
        /* Inicializa e configura o WebView customizado */
        this.webView = new WebView(context);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setDomStorageEnabled(true);
        this.webView.setWebViewClient(new WebViewClient());
        
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        this.webView.setLayoutParams(params);
        this.container.addView(this.webView);
        
        /* Adiciona o componente no App Inventor */
        container.$add(this);
        
        /* Inicia o monitoramento do teclado virtual */
        setupKeyboardListener();
    }

    @Override
    public View getView() {
        return container;
    }

    private void setupKeyboardListener() {
        final View rootView = activity.getWindow().getDecorView().getRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = rootView.getRootView().getHeight();
                
                /* Calcula a altura do teclado subbtraindo a area visivel da altura total */
                int keypadHeight = screenHeight - r.bottom;
                
                /* Se a diferenca for maior que 15% da tela, assume-se que o teclado abriu */
                if (keypadHeight > screenHeight * 0.15) {
                    /* Aplica um padding inferior no conteiner para levantar o WebView */
                    container.setPadding(0, 0, 0, keypadHeight);
                } else {
                    /* Remove o padding quando o teclado for fechado */
                    container.setPadding(0, 0, 0, 0);
                }
            }
        });
    }

    @SimpleFunction(description = "Carrega uma URL no WebViewer.")
    public void GoToUrl(String url) {
        if (webView != null) {
            webView.loadUrl(url);
        }
    }

    @SimpleFunction(description = "Volta para a pagina anterior, se possivel.")
    public void GoBack() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        }
    }

    @SimpleFunction(description = "Avanca para a proxima pagina, se possivel.")
    public void GoForward() {
        if (webView != null && webView.canGoForward()) {
            webView.goForward();
        }
    }
}