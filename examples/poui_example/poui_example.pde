import poui.*;

int w = 100;
int h = 200;

OUI ui;

void setup() {
  size(600, 600, FX2D);
  ui = new OUI(this);
  R r = new R();
  ui.addRenderable(r);
  ui.addTouchable(r);
}

void draw() {
  background(255);
  ui.render();
}

class R implements Renderable, Touchable {
  int w, h;
  boolean dragged = false;
  public R() {
    this.w = 100;
    this.h = 200;
  }

  public void render() {
    rectMode(CENTER);
    
    if(dragged) strokeWeight(2); else strokeWeight(1);

    rect(width / 2, height / 2, w, h);
    ellipse(width / 2 + w /2, height / 2 + h / 2, 20, 20);
  }

  public void onHover(int actionIndex, boolean in) {
    if(in) cursor(HAND); else cursor(ARROW);
  }
  public void onClick(int actionIndex, boolean in) {
  }
  public void onDrag(int actionIndex, boolean in) {
    dragged = in;
    if (in) { 
      
      this.w -= pmouseX - mouseX;
      this.h -= pmouseY - mouseY;
    };
  }

  public void renderToTouchSurface(PGraphics context, int actionIndex, int c) {
    context.fill(c);
    context.ellipse(width / 2 + w /2, height / 2 + h / 2, 20, 20);
  }
  public void applyTransformation(PGraphics _c) {
  }
}