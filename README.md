# Colorful - Vaadin React Add-on example project

This is an add-on example that use React. It creates a trivial Java API (without any proper typing or field interfaces) around [a React component](https://github.com/omgovich/react-colorful) that is essentially a color picker.

As discussed earlier for example in [Building Java API for JavaScript libraries - The lightweight approach](https://vaadin.com/blog/building-java-api-for-javascript-libraries), Vaadin by no means requires a Web Component as a counterpart for a Java Component implementation. Here we use a JS component that uses React.

Integration is fairly similar to any other JS component. The special thing with React is JSX (or its TypeScript counterpart TSX). 

I actually generated my first draft of the integration without JSX, as that is possible and as the integration part with Vaadin is tiny. JSX/TSX is not really needed that much as this is creating a wrapper.

As I figured out that Vite (the front-end build tooling used by Vaadin) supports JSX out of the box, I then tried renaming the .js file into .jsx, changed programmatic usage into JSX and to my surprise all worked, *until* I actually tried the packaged add-on in an application project. When trying to create a PR to Vaadin to support .jsx files in add-ons, I noticed from the sources that .tsx files *ARE already* supported. And as TS is JS, I just renamed the file to .tsx and now all works, even if you want to do some customisation or mashups with JSX 😎

To repeat the essential finding: use .tsx as file name postfix now, even if you wouldn't use TS, when building a re-usable add-on. And in my philosophy, you always should do that. If you plan to embed some React component only within your application project, .jsx works there fine as well.

A PR to make also .jsx supported: https://github.com/vaadin/flow/pull/17820

The add-on is also available as a pre-built for actual usage via [Vaadin Directory](https://vaadin.com/directory/).

## Development instructions

### Important Files 
* HexColorPicker.java: this is the addon-on component class. This provides the Java API for Vaadin developers to use.
* hexcolorpicker-connector.tsx: The tiny glue of JS that renders the React component into the client-side counterpart element fo the HexColorPicker.java
* RgbaColorPicker.java & friends: The slightly different picker with a typed value on the Java API

### Deployment

Starting the test/demo server:
```
mvn jetty:run -Pdevelopment
```

This deploys demo at http://localhost:8080
 
### Integration test

If you have the development server running, you can run the AddonIT test directly from IDE.

From CLI, 8080 port must be free and then execute `mvn verify -Pit`.
