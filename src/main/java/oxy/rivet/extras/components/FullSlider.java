package oxy.rivet.extras.components;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.text.Font;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.event.ListenerList;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.input.mouse.MouseMoveEvent;
import net.lenni0451.rivet.math.Size;
import net.lenni0451.rivet.text.model.TextOrigin;
import net.lenni0451.rivet.utils.FormatUtils;
import net.lenni0451.rivet.utils.MathUtils;
import oxy.rivet.extras.utils.AltThemeOption;

import java.text.DecimalFormat;
import java.util.function.Consumer;

@Accessors(fluent = true)
public class FullSlider extends Component {
    @Getter
    private final AltThemeOption<Color> backgroundColor = new AltThemeOption<>(this, Color.fromRGB(50, 50, 50));
    @Getter
    private final AltThemeOption<Color> outlineColor = new AltThemeOption<>(this, Color.WHITE);
    @Getter
    private final AltThemeOption<Color> textColor = new AltThemeOption<>(this, Color.WHITE);
    @Getter
    private final AltThemeOption<Color> color = new AltThemeOption<>(this, Color.fromRGB(71, 114, 179));

    @Getter
    private final AltThemeOption<Float> cornerRadius = new AltThemeOption<>(this, 5f);
    @Getter
    private final AltThemeOption<Float> outlineWidth = new AltThemeOption<>(this, 2f);

    @Getter
    private final AltThemeOption<String> valueFormat = new AltThemeOption<>(this, "%,f");

    @Getter
    private final ListenerList<Consumer<Double>> valueChangeListener = new ListenerList<>();

    @Getter
    private double value, min, max;

    @Setter
    private double step;

    public FullSlider(final double min, final double max, final double step, final double value) {
        this.min = min;
        this.max = max;
        this.step = step;
        this.value = value;
    }

    @Getter
    private Font font;
    public final FullSlider font(final Font font) {
        if (this.font != font) {
            this.font = font;
            if (this.parent() != null) {
                this.parent().requestLayoutRecalculation();
            }
        }
        return this;
    }

    public final FullSlider max(final double max) {
        if (this.max != max) {
            this.max = max;
            this.value = Math.clamp(value, min, max);
            valueChangeListener.call(c -> c.accept(this.value));
        }
        return this;
    }

    public final FullSlider min(final double min) {
        if (this.min != min) {
            this.min = min;
            this.value = Math.clamp(value, min, max);
            valueChangeListener.call(c -> c.accept(this.value));
        }
        return this;
    }

    public final FullSlider value(final double value) {
        if (this.value != value) {
            this.value = Math.clamp(value, min, max);
            valueChangeListener.call(c -> c.accept(this.value));
        }
        return this;
    }

    @Override
    public void renderInternal(Renderer renderer, Size size) {
        renderer.fillRoundedRect(0, 0, size.width(), size.height(), cornerRadius.value(), backgroundColor.value());

        float ratio = (float) (value / max);
        renderer.fillRoundedRect(0, 0, size.width() * ratio, size.height(), cornerRadius.value(), color.value());
        renderer.outlineRoundedRect(0, 0, size.width(), size.height(), cornerRadius.value(), outlineWidth.value(), outlineColor.value());

        final String value = String.format(FormatUtils.formatDecimalString(valueFormat.value(), step), this.value);
        renderer.text(usedFont().shapeText(value, textColor.value()), 5, size.height() / 2f, TextOrigin.Horizontal.VISUAL_LEFT, TextOrigin.Vertical.VISUAL_CENTER);
    }

    @Override
    protected boolean onMouseMoveInternal(MouseMoveEvent event, Size size) {
        if (event.isHeld(MouseButton.LEFT)) {
            update(size, event.x());
        }

        return true;
    }

    @Override
    protected boolean onMouseUpInternal(MouseButtonEvent event, Size size) {
        if (event.button() == MouseButton.LEFT) {
            update(size, event.x());
        }

        return true;
    }

    private void update(Size size, float x) {
        float factor = x / size.width();
        if (factor < 0 || factor > 1) {
            return;
        }
        double newValue = this.min + factor * (this.max - this.min);
        newValue = MathUtils.snap(newValue, this.min, this.max, this.step);
        this.value = newValue;
        this.valueChangeListener.call(c -> c.accept(value));
    }

    protected final Font usedFont() {
        return this.font != null ? this.font : this.rivet().backend().font();
    }

    @Override
    public Size computeIdealSize(Size size) {
        return new Size(usedFont().height() * 10, usedFont().height() * 2);
    }
}
