import React, { Component } from 'react';
import { createRoot } from 'react-dom/client';
import { HexColorPicker } from "react-colorful";

window.hexcolorpickerConnectorInit = (element, initialValue)  => {
    const root = createRoot(element);
    var onChange = (hex) => {
        // fire a custom event that is listened by the
        // actual Java component
        const event = new Event("color-change");
        event.hex = hex;
        element.dispatchEvent(event);
    };
    root.render(<HexColorPicker color={initialValue} onChange={onChange}/>);

    // Add hook for the server side setValue method
    element._c = {
        setValue: value => {
            root.render(<HexColorPicker color={value} onChange={onChange}/>);
        }
    }

}
