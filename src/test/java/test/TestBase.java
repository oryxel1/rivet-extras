package test;

import net.lenni0451.rivet.backend.text.Font;
import net.lenni0451.rivet.backend.thingl.GLFWApplication;
import net.lenni0451.rivet.backend.thingl.ThinGLAssetLoader;
import net.raphimc.thingl.resource.font.face.impl.FreeTypeFontFace;
import net.raphimc.thingl.resource.font.instance.FontInstance;
import net.raphimc.thingl.resource.font.instance.FontInstanceSet;
import net.raphimc.thingl.text.util.GlyphPredicate;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.SequencedMap;

public abstract class TestBase extends GLFWApplication {

//    static {
//        if (System.getProperty("os.name").contains("Linux")) {
//            GLFW.glfwInitHint(GLFW.GLFW_PLATFORM, GLFW.GLFW_PLATFORM_X11);
//        }
//    }

    public static FontInstanceSet createFont(final int size, final InputStream... streams) throws IOException {
        SequencedMap<FontInstance, GlyphPredicate> fonts = new LinkedHashMap<>();
        for (InputStream is : streams) {
            FontInstance font = new FreeTypeFontFace(is.readAllBytes()).getInstance(size);
            fonts.put(font, GlyphPredicate.all());
        }
        return new FontInstanceSet(fonts);
    }


    @Override
    protected Font createFont(final ThinGLAssetLoader assetLoader) throws Exception {
        return assetLoader.loadFont(TestBase.class.getResourceAsStream("/NotoSans-Regular.ttf"), 20);
    }

}