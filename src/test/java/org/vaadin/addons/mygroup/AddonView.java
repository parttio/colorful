package org.vaadin.addons.mygroup;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class AddonView extends VerticalLayout {

    public AddonView() {
        var colorPicker = new HexColorPicker();
        add(colorPicker);
        var p = new Paragraph();
        p.getStyle().setFont("bold 30px sans-serif");
        add(p);
        add(new HorizontalLayout(
                new Button("Show value", e -> {
                    p.setText(colorPicker.getValue());
                    p.getStyle().setColor(colorPicker.getValue());
                }),
                new Button("Make green", e -> {
                    colorPicker.setValue("#00ff00");
                })
            )
        );


        var another = new HexColorPicker();
        add(another);
        var p2 = new Paragraph();
        p2.getStyle().setFont("bold 30px sans-serif");
        add(p2);
        add(new Button("Show value", e -> {
            p2.setText(another.getValue());
            p2.getStyle().setColor(another.getValue());
        }));

    }
}
