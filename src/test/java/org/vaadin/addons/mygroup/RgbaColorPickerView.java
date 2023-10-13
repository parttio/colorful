package org.vaadin.addons.mygroup;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class RgbaColorPickerView extends VerticalLayout {

    public class ColorValueDisplay  extends Paragraph {
        public ColorValueDisplay() {
            getStyle().setFont("bold 30px sans-serif");
        }

        public void setColor(RgbaColor color) {
            setText(color.toString());
            getStyle().setColor(color.toCssColor());
        }
    }

    public RgbaColorPickerView() {
        var colorPicker = new RgbaColorPicker();
        add(colorPicker);
        var p = new ColorValueDisplay();
        add(p);
        add(new HorizontalLayout(
                new Button("Show value", e -> {
                    p.setColor(colorPicker.getValue());
                }),
                new Button("Make green", e -> {
                    colorPicker.setValue(new RgbaColor(0,255,0, 0.8));
                })
            )
        );

    }
}
