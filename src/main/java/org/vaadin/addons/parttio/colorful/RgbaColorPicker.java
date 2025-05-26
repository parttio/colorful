package org.vaadin.addons.parttio.colorful;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.internal.StateTree;
import elemental.json.JsonObject;
import in.virit.color.NamedColor;
import in.virit.color.RgbColor;

@NpmPackage(value = "react", version = "18.2.0")
@NpmPackage(value = "react-dom", version = "18.2.0")
@NpmPackage(value = "react-colorful", version = "5.6.1")
@JsModule("./rgbacolorpicker-connector.tsx")
public class RgbaColorPicker extends CustomField<RgbColor> {

    private static RgbColor DEFAULT_COLOR = NamedColor.BLACK.toRgbColor();

    private RgbColor newValue = DEFAULT_COLOR;
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
            getElement().executeJs("window.rgbacolorpickerConnectorInit($0, %s)".formatted(rgbaJson(newValue)), getElement());
            // start listening events that push data from the event listener
            getElement().addEventListener("color-change", e -> {
                        JsonObject json = e.getEventData().getObject("event.rgba");
                        var newValue = new RgbColor(
                                (int) json.getNumber("r"),
                                (int) json.getNumber("g"),
                                (int) json.getNumber("b"),
                                json.getNumber("a")
                        );

                        setModelValue(newValue, true);
                    })
                    .addEventData("event.rgba")
                    .debounce(200); // limit events sent to server, if e.g. the poing in the "colormap" is being dragged
            clientSideInitialized = true;
        });
    }

    @Override
    protected RgbColor generateModelValue() {
        return newValue;
    }

    @Override
    protected void setPresentationValue(RgbColor newPresentationValue) {
        newValue = newPresentationValue;
        if(newPresentationValue == null) {
            return;
        }
        if(clientSideInitialized) {
            // Updating an existing picker, set with JS
            String json = rgbaJson(newPresentationValue);
            // Because of things, we must use setTimeout
            getElement().executeJs("const el = this; setTimeout(() => el._c.setValue(%s), 50)".formatted(json));
        } else {
            // NOOP, new color will be set during initialization, see onAttach
        }
    }

    private static String rgbaJson(RgbColor newPresentationValue) {
        String json = "{ r: %s, g: %s, b: %s, a: %s }".formatted(
                newPresentationValue.r(),
                newPresentationValue.g(),
                newPresentationValue.b(),
                newPresentationValue.a()+"");
        return json;
    }
}