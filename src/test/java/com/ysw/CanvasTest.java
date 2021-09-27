package com.ysw;

import com.ysw.model.Canvas;
import com.ysw.service.CanvasService;
import org.junit.Test;

public class CanvasTest {

  private CanvasService canvasService = new CanvasService();

  @Test
  public void testCanvas() throws InterruptedException {
    Canvas canvas = canvasService.executeCommand(null, "C 20 4");
    canvas.print();
    canvas = canvasService.executeCommand(canvas, "L 1 2 6 2");
    canvas.print();
    canvas = canvasService.executeCommand(canvas, "L 6 3 6 4");
    canvas.print();
    canvas = canvasService.executeCommand(canvas, "R 14 1 18 3");
    canvas.print();
    canvas = canvasService.executeCommand(canvas, "B 10 3 o");
    canvas.print();
  }

  @Test
  public void testCanvasLarge() throws InterruptedException {
    Canvas canvas = canvasService.executeCommand(null, "C 500 500");
    canvas = canvasService.executeCommand(canvas, "B 100 100 o");
    canvas.print();
  }
}
