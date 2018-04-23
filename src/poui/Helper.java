package poui;
import processing.core.*;

public final class Helper {
  public static int createColor(final int actionIndex, final int objectIndex) {
    int index = 255 << 32 - 8;
    int c = 0 | (index);

    c = encodeObjectIndex(objectIndex, c);
    c = encodeActionIndex(actionIndex, c);

    //System.out.println(
    //  String.format("%32s", Integer.toBinaryString(c)).replace(' ', '0')
    //  );
    //System.out.println(actionIndex +" " + getObjectIndex(c));
    
    return c;
  }

  public static int getActionIndex(final int c) {
    return (c >> 32 - 12) & 15;
  }

  public static int getObjectIndex(final int c) {
    return (c >> 0) & ((int)Math.pow(2, 32 - 12) - 1);
  }

  public static int encodeActionIndex(int index, int c) {
    if (index > 15) return c;

    //shift to left
    index = index << 32 - 12;

    //add in
    c = c | (index);

    return c;
  }

  public static int encodeObjectIndex(int index, int c) {

    //shift to left
    index = index << 0;

    //add in
    c = c | (index);

    return c;
  }
}