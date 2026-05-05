package com.lyra.studio;

import com.google.appinventor.components.annotations.*;
import com.google.appinventor.components.common.*;
import com.google.appinventor.components.runtime.*;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

@DesignerComponent(version = 1, description = "Extensão de WebViewer Avançado com adaptação para teclado virtual.", category = ComponentCategory.EXTENSION, nonVisible = false, iconName = "images/extension.png")
@SimpleObject(external = true)
public class AdvancedWebViewer extends AndroidViewComponent {

    private WebView webView;
    private Activity activity;
    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;

    public AdvancedWebViewer(ComponentContainer container) {
        super(container);
        this.activity = container.$context();
        this.webView = new WebView(this.activity);

        // Configurações avançadas e simplificadas
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        // Adiciona a visualização ao contêiner
        container.$add(this);

        // Ativa a adaptação ao teclado virtual
        setupKeyboardResize();
    }

    @Override
    public View getView() {
        return webView;
    }

    // Configura o ouvinte para redimensionar a tela quando o teclado abrir
    private void setupKeyboardResize() {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    // Verifica a diferença de altura e ajusta o layout
    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            
            // Se a diferença for maior que 1/4 da tela, o teclado provavelmente está visível
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                // Teclado oculto
                frameLayoutParams.height = usableHeightSansKeyboard;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    // Calcula a altura visível real
    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

    @SimpleFunction(description = "Abre um site a partir do URL fornecido.")
    public void GoToUrl(String url) {
        webView.loadUrl(url);
    }

    @SimpleFunction(description = "Recarrega a página atual.")
    public void Reload() {
        webView.reload();
    }

    @SimpleFunction(description = "Volta para a página anterior, se possível.")
    public void GoBack() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }
}