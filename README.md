POUI - Processing Object User Interface is a barebone UI library that makes mouse-interactions with objects easy.

## Download

You can install the latest version of POUI from within Processing via the menus Sketch > Import Library… > Add Library…

## Usage

POUI consists of two interface classes `Renderable` and `Touchable` and a bit of glue code you have to implement.

Make sure your interactable object implements both interfaces.

## Interface Methods
These are the interface methods you have to implement.

Look at the example code in the processing examples menu under 
File > Examples > Contributed Libraries > Processing Object User Interface

```
void render();
// in here goes your render code for your object.

void applyTransformation(PGraphics _c);
// apply global opengl transformation before render

void renderToTouchSurface(PGraphics context, int actionIndex, int color);
// here you render hidden surface which will be touchable later.
// it is recommended to use same shapes at same positions as in your render() code.

void onHover(int actionIndex, boolean in);
// called when the mouse hovers over a touchable surface.

void onClick(int actionIndex, boolean in);
// called when a touchable surface is clicked.

void onDrag(int actionIndex, boolean in);
// called when a touchable surface is dragged.
```

