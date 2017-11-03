package com.learning.insane.wecheaters.model;

import java.util.ArrayList;

public class User {
    public static final String NAME = "name";
    public static final String FAV_SHORTCUTS = "favShortcuts";
    public static final String MAIL = "Mail";
    public static final String SHORTCUT_COUNT = "ShortcutCount";
    public static final String SHORTCUTS = "Shortcuts";

    public String name, id, email;
    public ArrayList<String> shortcuts;
}
