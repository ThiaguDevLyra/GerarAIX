package com.lyra.studio;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.*;

@DesignerComponent(
    version = 1,
    description = "Um Custom WebViewer que se adapta automaticamente ao teclado virtual do dispositivo.",
    category = ComponentCategory.EXTENSION,
    nonVisible = false,
    iconName = "images/extension.png"
)
@SimpleObject(external = true)
public class AdaptiveWebViewer extends AndroidViewComponent {

    private WebView webView;
    private Activity activity;

    public AdaptiveWebViewer(ComponentContainer container) {
        super(container);
        this.activity = container.$context();
        
        // Inicializa o WebView customizado
        this.webView = new WebView(activity);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setDomStorageEnabled(true);
        this.webView.setWebViewClient(new WebViewClient());
        
        // Adiciona o componente na tela atual
        container.$add(this);
        
        // Configura a adaptabilidade ao teclado
        setupKeyboardAdaptation();
    }

    @Override
    public View getView() {
        return webView;
    }

    private void setupKeyboardAdaptation() {
        // Define o modo do teclado para redimensionar a janela automaticamente
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        // Pega a view principal (raiz) para monitorar mudancas de layout
        final View rootView = activity.findViewById(android.R.id.content);
        
        // Adiciona um listener global para recalcular o padding do WebView quando o teclado aparecer
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = rootView.getRootView().getHeight();
                
                // Calcula a altura que o teclado esta ocupando
                int keypadHeight = screenHeight - r.bottom;
                
                // Se a altura do teclado for maior que 15% da tela, consideramos aberto
                if (keypadHeight > screenHeight * 0.15) {
                    // Teclado visivel: aplica padding inferior para o conteudo nao ser coberto
                    webView.setPadding(0, 0, 0, keypadHeight);
                } else {
                    // Teclado invisivel: remove o padding
                    webView.setPadding(0, 0, 0, 0);
                }
            }
        });
    }

    @SimpleFunction(description = "Acessa a URL especificada no WebViewer Adaptavel.")
    public void GoToUrl(String url) {
        webView.loadUrl(url);
    }

    @SimpleFunction(description = "Recarrega a pagina web atual.")
    public void Reload() {
        webView.reload();
    }
}
