package com.evolutionnext.features.account.page;

import com.evolutionnext.e2e.support.BrowserSupport;

public final class WelcomePage {
    private static final String TAGLINE = "One focused session at a time.";

    private final BrowserSupport browser;

    WelcomePage(BrowserSupport browser) {
        this.browser = browser;
    }

    WelcomePage waitUntilVisible() {
        browser.waitForText(TAGLINE);
        return this;
    }

    public boolean showsTagline() {
        browser.waitForText(TAGLINE);
        return true;
    }
}
