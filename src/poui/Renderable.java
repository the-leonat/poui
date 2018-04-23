package poui;

import processing.core.PGraphics;

public interface Renderable {
  public void render();
  public void applyTransformation(PGraphics _c);
}