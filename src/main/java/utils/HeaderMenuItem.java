package utils;

import java.awt.desktop.AboutEvent;

public enum HeaderMenuItem {
    HOME("//a[text()='HOME']"),
    ABOUT("//a[text()='ABOUT']"),
    LOGIN("//a[text()='LOGIN']"),
    CONTACTS("//a[text()='CONTACTS']"),
    ADD("//a[text()='ADD']"),
    SIGN_OUT("//button[text()='Sign Out']");
    private final String locator;

    HeaderMenuItem(String locator) {
        this.locator = locator;
    }
    public String getLocator(){
        return locator;
    }
}