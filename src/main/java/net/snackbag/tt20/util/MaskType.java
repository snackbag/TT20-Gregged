package net.snackbag.tt20.util;

public enum MaskType {
    WHITELIST,
    BLACKLIST;

    public static MaskType fromString(String input) {
        switch (input.toLowerCase()) {
            case "whitelist":
                return WHITELIST;
            case "blacklist":
                return BLACKLIST;
            default:
                throw new IllegalArgumentException("'" + input + "' is not a valid mask type");
        }
    }
}