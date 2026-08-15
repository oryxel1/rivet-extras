package oxy.rivet.extras.components;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.backend.render.Renderer;
import net.lenni0451.rivet.backend.thingl.texture.ThinGLGPUTexture;
import net.lenni0451.rivet.component.Component;
import net.lenni0451.rivet.input.mouse.MouseButton;
import net.lenni0451.rivet.input.mouse.MouseButtonEvent;
import net.lenni0451.rivet.math.Size;
import net.raphimc.thingl.gl.resource.image.texture.impl.Texture2D;
import oxy.rivet.extras.utils.AltThemeOption;
import oxy.rivet.extras.utils.ColorUtils;

@RequiredArgsConstructor
@Accessors(fluent = true, chain = true, makeFinal = true)
public class ButtonImage extends Component {
    @Getter
    private final ThinGLGPUTexture texture;

    private final ClickListener clickListener;

    @Getter
    private final AltThemeOption<Color> hoverColor = new AltThemeOption<>(this, Color.WHITE);

    @Getter
    private final AltThemeOption<Integer> blendDuration = new AltThemeOption<>(this, 800);

    private long animationResetTime = -1;

    public ButtonImage(final Texture2D texture, final ClickListener clickListener) {
        this.texture = new ThinGLGPUTexture(texture);
        this.clickListener = clickListener;
    }

    @Override
    protected boolean onMouseUpInternal(MouseButtonEvent event, Size size) {
        if (event.button() == MouseButton.LEFT) {
            clickListener.onClick(event);
            return true;
        }

        return false;
    }

    @Override
    public void renderInternal(final Renderer renderer, final Size size) {
        long duration = (System.currentTimeMillis() - Math.abs(animationResetTime));
        float progress = Math.min(duration / (float) blendDuration.value(), 1);
        final Color color;
        if (animationResetTime < 0) {
            color = ColorUtils.interpolate(progress, hoverColor.value(), Color.WHITE);
        } else {
            color = ColorUtils.interpolate(progress, Color.WHITE, hoverColor.value());
        }

        renderer.image(this.texture, 0, 0, size.width(), size.height(), color);
    }

    @Override
    public Size computeIdealSize(final Size constraints) {
        return new Size(this.texture.width(), this.texture.height());
    }

    @Override
    protected void onMouseEnterInternal() {
        animationResetTime = System.currentTimeMillis();
    }

    @Override
    protected void onMouseLeaveInternal() {
        animationResetTime = -System.currentTimeMillis();
    }

    @FunctionalInterface
    public interface ClickListener {
        void onClick(final MouseButtonEvent event);
    }
}
