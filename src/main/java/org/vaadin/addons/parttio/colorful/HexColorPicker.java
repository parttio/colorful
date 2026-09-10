package org.vaadin.addons.parttio.colorful;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.internal.StateTree;
import in.virit.color.HexColor;

@NpmPackage(value = "react-colorful", version = "5.6.1")
@JsModule("./hexcolorpicker-connector.tsx")
@Tag("hex-color-picker") // The root element could be div, but why not give it a more descriptive name, even if it isn't an actual web component...
public class HexColorPicker extends CustomField<HexColor> {

    private static HexColor DEFAULT_COLOR = HexColor.of("#000000");
    boolean clientSideInitialized = false;
    private StateTree.ExecutionRegistration initReg;
    private HexColor newValue = DEFAULT_COLOR;

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
            // call init method from hexcolorpicker-connector.tsx
            // that renders the React component to this element
            getElement().executeJs("window.hexcolorpickerConnectorInit($0, $1)", getElement(), newValue.toString());
            // start listening events that push data from the event listener
            getElement().addEventListener("color-change", e -> {
                        var newValue = e.getEventData().get("event.hex").stringValue();
                        setModelValue(HexColor.of(newValue), true);
                    })
                    .addEventData("event.hex")
                    .debounce(200); // limit events sent to server, if e.g. the poing in the "colormap" is being dragged
            clientSideInitialized = true;
        });
    }

    @Override
    protected HexColor generateModelValue() {
        return newValue;
    }

    public HexColorPicker() {
    }

    @Override
    protected void setPresentationValue(HexColor newPresentationValue) {
        newValue = newPresentationValue;
        if(newPresentationValue == null) {
            return;
        }
        if(clientSideInitialized) {
            // Updating an existing picker, set with JS
            getElement().executeJs(("this._c.setValue($0)"), newPresentationValue.toString());
        } else {
            // NOOP, new color will be set during initialization, see onAttach
        }
    }

}
