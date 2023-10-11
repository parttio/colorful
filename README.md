# Colorful - Vaadin React Add-on example project

This is an add-on example that use React. It creates a trivial Java API (without any proper typing or field interfaces) around [a React component](https://github.com/omgovich/react-colorful) that is essentially a color picker.

As discussed earlier for example in [Building Java API for JavaScript libraries - The lightweight approach](https://vaadin.com/blog/building-java-api-for-javascript-libraries), Vaadin by no means requires a Web Component as a counterpart for a Java Component implementation. Here we use a JS component that uses React.

Integration is fairly similar to any other JS component. The special thing with React is JSX (or its TypeScript counterpart TSX). 

I actually generated my first draft of the integration without JSX, as that is possible and as the integration part with Vaadin is tiny. JSX/TSX is not really needed that much as this is creating a wrapper.

As I figured out that Vite (the front-end build tooling used by Vaadin) supports JSX out of the box, I then tried renaming the .js file into .jsx, changed programmatic usage into JSX and to my surprise all worked, *until* I actually tried the packaged add-on in an application project. When trying to create a PR to Vaadin to support .jsx files in add-ons, I noticed from the sources that .tsx files *ARE already* supported. And as TS is JS, I just renamed the file to .tsx and now all works, even if you want to do some customisation or mashups with JSX 😎

To repeat the essential finding: use .tsx as file name postfix now, even if you wouldn't use TS, when building a re-usable add-on. And in my philosophy, you always should do that. If you plan to embed some React component only within your application project, .jsx works there fine as well.

A PR to make also .jsx supported: https://github.com/vaadin/flow/pull/17820

Currently not published to Maven/Directory, as this is 
more of an integration example, instead of an add-on, but make an issue about publishing if you'd find this useful as an actual add-on.

## Development instructions

### Important Files 
* TheAddon.java: this is the addon-on component class. You can add more classes if you wish, including other Components.
* TestView.java: A View class that let's you test the component you are building. This and other classes in the test folder will not be packaged during the build. You can add more test view classes in this package.
* assembly/: this folder includes configuration for packaging the project into a JAR so that it works well with other Vaadin projects and the Vaadin Directory. There is usually no need to modify these files, unless you need to add JAR manifest entries.

If you are using static resources such as images, JS (e.g. templates) and CSS files the correct location for them is under the `/src/main/resources/META-INF/resources/frontend` directory and is described here [Resource Cheat Sheet](https://vaadin.com/docs/v14/flow/importing-dependencies/tutorial-ways-of-importing.html#resource-cheat-sheet)in more details. 

### Deployment

Starting the test/demo server:
```
mvn jetty:run -Pdevelopment
```

This deploys demo at http://localhost:8080
 
### Integration test

To run Integration Tests, execute `mvn verify -Pit`.

Tests run by default in `headless` mode, to avoid browser windows to be opened for every test.
This behaviour is always disabled when running the tests in debug mode in the IDE
or when running maven with the `-Dmaven.failsafe.debug` sytem property.
On normal execution, headless mode can be deactivated using the `-Dtest.headless=false` system property.
