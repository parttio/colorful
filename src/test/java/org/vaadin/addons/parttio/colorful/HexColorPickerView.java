package org.vaadin.addons.parttio.colorful;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import in.virit.color.HexColor;
import in.virit.color.RgbColor;

@Route
public class HexColorPickerView extends VerticalLayout {

    public class ColorValueDisplay  extends Paragraph {
        public ColorValueDisplay() {
            getStyle().setFont("bold 30px sans-serif");
        }

        public void setColor(HexColor color) {
            setText(color.toString());
            getStyle().setColor(color.toString());
        }
    }

    public HexColorPickerView() {
        var colorPicker = new HexColorPicker();
        add(colorPicker);
        var p = new ColorValueDisplay();
        add(p);
        add(new HorizontalLayout(
                new Button("Show value", e -> {
                    p.setColor(colorPicker.getValue());
                }),
                new Button("Make green", e -> {
                    colorPicker.setValue(HexColor.of("#00ff00"));
                })
            )
        );

        var another = new HexColorPicker();
        add(another);
        var p2 = new ColorValueDisplay();
        add(p2);
        add(new HorizontalLayout(
                new Button("Show value", e -> {
                    p2.setColor(another.getValue());
                }),
                new Button("Make green", e -> {
                    another.setValue(HexColor.of("#00ff00"));
                })
            )
        );
    }
}
