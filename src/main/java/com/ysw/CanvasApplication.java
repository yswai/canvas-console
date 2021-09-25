package com.ysw;

import com.ysw.model.Canvas;
import com.ysw.service.CanvasService;

import java.util.Scanner;

public class CanvasApplication {

  static CanvasService canvasService = new CanvasService();


  public static void main(String[] args) {
    System.out.println("Enter command:");
    Canvas canvas = null;
    while (true) {
      try {
        Scanner scanner = new Scanner(System.in);
        canvas = canvasService.executeCommand(canvas, scanner.nextLine());
        canvas.print();
      } catch (Exception ex) {
        System.out.println("Exception occurred: \n" + ex.getLocalizedMessage());
      }
      System.out.println("Enter command:");
    }

  }

}
