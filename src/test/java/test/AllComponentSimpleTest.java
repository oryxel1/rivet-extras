package test;

import net.lenni0451.commons.color.Color;
import net.lenni0451.rivet.Rivet;
import net.lenni0451.rivet.component.container.Container;
import net.lenni0451.rivet.layout.list.VerticalListLayout;
import oxy.rivet.extras.components.ColorWheelPicker;
import oxy.rivet.extras.components.FullSlider;
import oxy.rivet.extras.components.NumberPicker;
import oxy.rivet.extras.components.TypingLabel;

public class AllComponentSimpleTest extends TestBase {

    static void main(String[] args) {
        new AllComponentSimpleTest().run();
    }

    @Override
    protected void init(Rivet rivet) {
        Container container = new Container(VerticalListLayout.DEFAULT);

        container.add(new ColorWheelPicker(Color.WHITE));
        container.add(new FullSlider(0, 10, 0.1, 1));
        container.add(new TypingLabel("Test Typing Text"));
        container.add(new NumberPicker(0, 10, 0.1, 1));

        rivet.root().add(container);
    }
}
