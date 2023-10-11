import { createElement } from 'react';
import { createRoot } from 'react-dom/client';
import { HexColorPicker } from "react-colorful";

window.rcpConnectorInit = (el, initialValue)  => {
    const root = createRoot(el);
    //const [color, setColor] = useState("#ff0d00");
    var setColor = (hex) => {
        // fire a custom event that is listened by the
        // actual Java component
        const event = new Event("color-change");
        event.hex = hex;
        el.dispatchEvent(event);
    };
    // Note, not using JSX didn't want to touch the build
    root.render(createElement(HexColorPicker, {"color": initialValue, "onChange" : setColor}, null));
}
