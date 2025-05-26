package org.vaadin.addons.parttio.colorful;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import in.virit.color.NamedColor;
import in.virit.color.RgbColor;

@Route
public class RgbaColorPickerView extends VerticalLayout implements AfterNavigationObserver {

    private final RgbaColorPicker colorPicker;

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        // Set initial value after the UI is attached
        colorPicker.setValue(new RgbColor(255, 0, 0, 0.5));
    }

    public class ColorValueDisplay  extends Paragraph {
        public ColorValueDisplay() {
            getStyle().setFont("bold 30px sans-serif");
        }

        public void setColor(RgbColor color) {
            setText(color.toString());
            getStyle().setColor(color.toString());
        }
    }

    public RgbaColorPickerView() {
        colorPicker = new RgbaColorPicker();
        add(colorPicker);
        colorPicker.addValueChangeListener(event -> {
            Notification.show("Color changed: " + event.getValue().toString());
        });

        var p = new ColorValueDisplay();
        add(p);
        add(new HorizontalLayout(
                new Button("Show value", e -> {
                    p.setColor(colorPicker.getValue());
                }),
                new Button("Make green", e -> {
                    colorPicker.setValue(new RgbColor(0,255,0, 0.8));
                })
            )
        );

    }
}
