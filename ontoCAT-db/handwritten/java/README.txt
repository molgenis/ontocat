It is good practice to put each plugin in its own package.
The idea plugin consists of at least three files:
1) The plugin controller. This Java class extends from org.molgenis.assets.screen.PluginScreen
2) The plugin model. This Java class models the data to be shown on screen.
3) The plugin view. This is a Freemarker template that transatles between the controller/model and the view.
See the examples for how that works. 