package poui;

import java.lang.*;
import processing.core.*;
import processing.event.MouseEvent;
import java.util.ArrayList;

public class OUI {
  PGraphics ts;
  PApplet p;

  ArrayList<Renderable> rList = new ArrayList();
  ArrayList<Touchable> tList = new ArrayList();

  Touchable lockedTouchable = null;
  Touchable hoveredTouchable = null;
  int hoveredActionIndex = 0;
  int lockedActionIndex = 0;

  public static final int DRAG_INDEX = 0;
  public static final int ROTATE_INDEX = 1;
  public static final int ENLARGE_INDEX = 2;
  public static final int NO_INDEX = 15;

  public OUI(PApplet _parent) {
    this.p = _parent;
    p.registerMethod("mouseEvent", this);

    initTouchSurface();
  }

  public void setTouchables(ArrayList<? extends Touchable> _tList) {
    this.tList.addAll(_tList);
    renderTouchSurface();
  }

  public void addTouchable(Touchable _t) {
    this.tList.add(_t);
    renderTouchSurface();
  }

  public void setRenderables(ArrayList<? extends Renderable> _rList) {
    this.rList.addAll(_rList);
  }

  public void addRenderable(Renderable _r) {
    this.rList.add(_r);
  }

  private void initTouchSurface() {
    ts = p.createGraphics(p.sketchWidth(), p.sketchHeight());
    ts.noSmooth();
    ts.beginDraw();
    ts.background(255, 255, 255);
    ts.endDraw();
  }

  public void render() {
    for (Renderable r : this.rList) {
      p.pushMatrix();
      r.applyTransformation(p.g);
      r.render();
      p.popMatrix();
    }
  }

  public void renderTouchSurface() {
    ts.beginDraw();
    ts.background(255);

    int index = 0;

    for (Touchable t : this.tList) {
      ts.pushMatrix();
      t.applyTransformation(ts);

      for (int i = 0; i < 4; i++) {
        int c = Helper.createColor(i, index);
        t.renderToTouchSurface(ts, i, c);
      }
      ts.popMatrix();

      index++;
    }

    ts.endDraw();
    ts.loadPixels();
  }

  public void mouseEvent(MouseEvent event) {
    int mx = event.getX();
    int my = event.getY();


    switch(event.getAction()) {

    case MouseEvent.MOVE:
      this.mouseMoved(mx, my);
      break;
    case MouseEvent.PRESS:
      this.mousePressed(mx, my);
      break;
    case MouseEvent.DRAG:
      this.mouseDragged(mx, my);
      break;
    case MouseEvent.RELEASE:
      this.mouseReleased(mx, my);
      break;
    }
  }

  public void mouseMoved(int mx, int my) {
    int w = p.sketchWidth();
    int c = ts.pixels[my * w + mx];

    int ai = Helper.getActionIndex(c);
    int oi = Helper.getObjectIndex(c);

    if (ai != NO_INDEX) {
      if (hoveredTouchable == null) {
        this.hoveredTouchable = this.tList.get(oi);
        this.hoveredActionIndex = ai;
        this.hoveredTouchable.onHover(hoveredActionIndex, true);
      } else if (this.tList.get(oi) != hoveredTouchable) {
        //move out
        this.hoveredTouchable.onHover(hoveredActionIndex, false);
        this.hoveredTouchable = this.tList.get(oi);
        this.hoveredActionIndex = ai;
        this.hoveredTouchable.onHover(hoveredActionIndex, true);
      }
    } else {
      if (this.hoveredTouchable != null) this.hoveredTouchable.onHover(hoveredActionIndex, false);
      this.hoveredTouchable = null;
    }
  }

  public void mousePressed(int mx, int my) {
    int w = p.sketchWidth();
    int c = ts.pixels[my * w + mx];

    int ai = Helper.getActionIndex(c);
    int oi = Helper.getObjectIndex(c);

    if (ai != NO_INDEX) {
      this.lockedTouchable = this.tList.get(oi);
      this.lockedActionIndex = ai;
    }
  }

  public void mouseReleased(int mx, int my) {
    if (lockedTouchable != null) {
      this.lockedTouchable.onDrag(lockedActionIndex, false);
      renderTouchSurface();
    }
    this.lockedTouchable = null;
  }

  public void mouseDragged(int mx, int my) {
    if (this.lockedTouchable != null) {
      this.lockedTouchable.onDrag(lockedActionIndex, true);
    }
  }
}