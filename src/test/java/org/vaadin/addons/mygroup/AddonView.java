package org.vaadin.addons.mygroup;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
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
        add(new Button("Show value", e-> {
            p.setText(colorPicker.getValue());
            p.getStyle().setColor(colorPicker.getValue());
        }));
    }
}
