package org.vaadin.addons.mygroup;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import elemental.json.JsonObject;

@NpmPackage(value = "react", version = "18.2.0")
@NpmPackage(value = "react-dom", version = "18.2.0")
@NpmPackage(value = "react-colorful", version = "5.6.1")
@JsModule("./rgbacolorpicker-connector.tsx")
@Tag("rgba-color-picker") // The root element could be div, but why not give it a more descriptive name, even if it isn't an actual web component...
public class RgbaColorPicker extends Component {

    private RgbaColor value = new RgbaColor(255,0,0, 0.5);

    public RgbaColorPicker() {
        // call init method from rgbacolorpicker-connector.tsx
        // that renders the React component to this element
        getElement().executeJs("window.rgbacolorpickerConnectorInit($0, %s)".formatted(value), getElement());
        // start listening events that push data from the event listener
        getElement().addEventListener("color-change", e -> {
                    JsonObject json = e.getEventData().getObject("event.rgba");
                    this.value = new RgbaColor(
                            (int) json.getNumber("r"),
                            (int) json.getNumber("g"),
                            (int) json.getNumber("b"),
                            json.getNumber("a")
                    );
                })
                .addEventData("event.rgba")
                .debounce(1000); // limit events sent to server, if e.g. the poing in the "colormap" is being dragged
    }

    public RgbaColor getValue() {
        return value;
    }

    public void setValue(RgbaColor value) {
        this.value = value;
        getElement().executeJs(("this._c.setValue(%s)").formatted(value));
    }
}
