package poui;

import processing.core.PGraphics;


public interface Touchable {
  public void renderToTouchSurface(PGraphics context, int actionIndex, int color);
  public void applyTransformation(PGraphics _c);

  public void onHover(int actionIndex, boolean in);
  public void onClick(int actionIndex, boolean in);
  public void onDrag(int actionIndex, boolean in);
}