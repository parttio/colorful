package org.vaadin.addons.mygroup;

// { r: 200, g: 150, b: 35, a: 0.5 }
record RgbaColor(int r, int g, int b, double a) {

    @Override
    public String toString() {
        return "{ r: %s, g: %s, b: %s, a: %s }".formatted(r, g, b, a);
    }

    public String toCssColor() {
        return "rgba(%s,%s,%s,%s)".formatted(r, g, b, a);
    }
}
