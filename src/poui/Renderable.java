package poui;

import processing.core.PGraphics;

public interface Renderable {
  public void render(PGraphics context);
  public void applyTransformation(PGraphics _c);
}