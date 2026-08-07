package oxy.rivet.extras.utils;

import java.util.function.Consumer;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.event.ListenerList;

@Accessors(fluent = true)
@Getter
public final class AltThemeOption<T> {
    private final Component component;
    private final ListenerList<Consumer<T>> initListener;
    private final ListenerList<Consumer<T>> changeListener;
    private T value;

    public AltThemeOption(Component component, T initialValue) {
        this.component = component;
        this.initListener = new ListenerList<>();
        this.changeListener = new ListenerList<>();
        component.addedListener().add(this::fireInitListener);
        component.themeChangedListener().add(this::fireInitListener);

        value = initialValue;
    }

    public AltThemeOption<T> set(T value) {
        this.value = value;
        this.fireChangeListener();
        return this;
    }

    private void fireInitListener() {
        this.initListener.call((c) -> c.accept(value));
    }

    private void fireChangeListener() {
        if (this.component.rivet() != null) {
            this.initListener.call((c) -> c.accept(value));
        }

        this.changeListener.call((c) -> c.accept(value));
    }
}
