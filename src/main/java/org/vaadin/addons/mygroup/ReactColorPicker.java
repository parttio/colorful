package org.vaadin.addons.mygroup;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;

@NpmPackage(value = "react", version = "18.2.0")
@NpmPackage(value = "react-dom", version = "18.2.0")
@NpmPackage(value = "react-colorful", version = "5.6.1")
@JsModule("./rcp-connector.js")
@Tag("react-color-picker")
public class ReactColorPicker extends Component {

    private String value = "#ff0d00";

    public ReactColorPicker() {
        getElement().executeJs("window.rcpConnectorInit($0, $1)", getElement(), value);
        getElement().addEventListener("color-change", e -> {
                    this.value = e.getEventData().getString("event.hex");
                    // TODO implement actual event and Component level
                    // listeners & saner API
                })
                .addEventData("event.hex")
                .debounce(1000);
    }

    public String getValue() {
        return value;
    }
}
