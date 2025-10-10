package org.vaadin.addons.parttio.colorful;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.internal.StateTree;
import elemental.json.JsonObject;
import in.virit.color.Color;
import in.virit.color.NamedColor;
import in.virit.color.RgbColor;

@NpmPackage(value = "react", version = "18.2.0")
@NpmPackage(value = "react-dom", version = "18.2.0")
@NpmPackage(value = "react-colorful", version = "5.6.1")
@JsModule("./rgbacolorpicker-connector.tsx")
public class RgbaColorPicker extends CustomField<Color> {

    private static RgbColor DEFAULT_COLOR = NamedColor.BLACK.toRgbColor();

    private Color newValue = DEFAULT_COLOR;
    boolean clientSideInitialized = false;
    private StateTree.ExecutionRegistration initReg;

    public RgbaColorPicker() {
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        super.onDetach(detachEvent);
        clientSideInitialized = false;
        if(initReg != null) {
            // detached before attached, cancel initialization
            initReg.remove();
            initReg = null;
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        initReg = attachEvent.getUI().beforeClientResponse(this, context -> {
            // call init method from rgbacolorpicker-connector.tsx
            // that renders the React component to this element
            getElement().executeJs("window.rgbacolorpickerConnectorInit($0, $1)", getElement(), newValue);
            // start listening events that push data from the event listener
            record RgbaEventData(@JsonProperty("event.rgba") RgbColor rgba) {}
            getElement().addEventListener("color-change", e -> {
                        RgbColor newValue = e.getEventData(RgbaEventData.class).rgba();
                        setModelValue(newValue, true);
                    })
                    .addEventData("event.rgba")
                    .debounce(200); // limit events sent to server, if e.g. the poing in the "colormap" is being dragged
            clientSideInitialized = true;
        });
    }

    @Override
    protected Color generateModelValue() {
        return newValue;
    }

    @Override
    protected void setPresentationValue(Color newPresentationValue) {
        newValue = newPresentationValue;
        // Because of things, we must use setTimeout

        if(newPresentationValue == null) {
            return;
        }
        if(clientSideInitialized) {
            getElement().executeJs("const el = this; setTimeout(() => el._c.setValue($0), 50)", newPresentationValue);
        } else {
            // NOOP, new color will be set during initialization, see onAttach
        }
    }

}