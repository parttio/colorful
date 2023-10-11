package org.vaadin.addons.mygroup;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

@NpmPackage(value = "react", version = "18.2.0")
@NpmPackage(value = "react-dom", version = "18.2.0")
@NpmPackage(value = "react-colorful", version = "5.6.1")
@JsModule("./hexcolorpicker-connector.tsx")
@Tag("hex-color-picker") // The root element could be div, but why not give it a more descriptive name, even if it isn't an actual web componentn...
public class HexColorPicker extends Component {

    private String value = "#ff0d00";

    public HexColorPicker() {
        // call init method from hexcolorpicker-connector.tsx
        // that renders the React component to this element
        getElement().executeJs("window.hexcolorpickerConnectorInit($0, $1)", getElement(), value);
        // start listening events that push data from the event listener
        getElement().addEventListener("color-change", e -> {
                    this.value = e.getEventData().getString("event.hex");
                })
                .addEventData("event.hex")
                .debounce(1000); // limit events sent to server, if e.g. the poing in the "colormap" is being dragged
    }

    public String getValue() {
        return value;
    }
}
