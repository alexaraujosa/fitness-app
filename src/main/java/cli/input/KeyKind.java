package cli.input;

public enum KeyKind {
    /**
     * This value corresponds to a regular character 'typed', usually alphanumeric or a symbol. The one special case
     * here is the enter key which could be expected to be returned as a '\n' character but is actually returned as a
     * separate {@code KeyType} (see below). Tab, backspace and some others works this way too.
     */
    CHARACTER,
    ESCAPE,
    BACKSPACE,
    ARROW_LEFT,
    ARROW_RIGHT,
    ARROW_UP,
    ARROW_DOWN,
    INSERT,
    DELETE,
    HOME,
    END,
    PAGE_UP,
    PAGE_DOWN,
    TAB,
    ENTER,
    F1,
    F2,
    F3,
    F4,
    F5,
    F6,
    F7,
    F8,
    F9,
    F10,
    F11,
    F12,
    KEY_TYPE,

    /**
     * Internal value for reporting the current cursor position.
     */
    INTERNAL_CURSOR_LOCATION,
    /**
     * Input has reached EOF.
     */
    EOF
}
