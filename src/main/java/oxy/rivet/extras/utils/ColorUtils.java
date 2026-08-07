package oxy.rivet.extras.utils;

import net.lenni0451.commons.color.Color;
import net.lenni0451.commons.math.MathUtils;

public class ColorUtils {
    public static Color interpolate(final float progress, final Color color1, final Color color2) {
        return Color.fromRGBA(
                MathUtils.clamp((int) (color1.getRed() + (color2.getRed() - color1.getRed()) * progress), 0, 255),
                MathUtils.clamp((int) (color1.getGreen() + (color2.getGreen() - color1.getGreen()) * progress), 0, 255),
                MathUtils.clamp((int) (color1.getBlue() + (color2.getBlue() - color1.getBlue()) * progress), 0, 255),
                MathUtils.clamp((int) (color1.getAlpha() + (color2.getAlpha() - color1.getAlpha()) * progress), 0, 255)
        );
    }
}
