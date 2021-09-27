package com.ysw.service;

import com.ysw.model.Canvas;
import com.ysw.model.Coordinate;

import java.util.Arrays;

import static com.ysw.model.SearchType.BFS;
import static com.ysw.model.SearchType.DFS;
import static java.util.Objects.isNull;

public class CanvasService {

  public Canvas executeCommand(Canvas canvas, String command) {
    String[] commandParts = command.split(" ");
    String commandType = commandParts[0];
    if (!Arrays.asList("C", "L", "R", "B", "Q").contains(commandType)) {
      throw new RuntimeException("Invalid Command Type");
    }
    switch (commandType) {
      case "C":
        if (commandParts.length != 3) {
          throw new IllegalArgumentException("Invalid Create Canvas command");
        }
        Integer width = Integer.parseInt(commandParts[1]);
        Integer height = Integer.parseInt(commandParts[2]);
        canvas = new Canvas(width, height);
        break;
      case "L":
        if (isNull(canvas) || commandParts.length != 5) {
          throw new IllegalArgumentException("Invalid DrawLine Canvas command");
        }
        Integer lx1 = Integer.parseInt(commandParts[1]);
        Integer ly1 = Integer.parseInt(commandParts[2]);
        Coordinate point1 = new Coordinate(lx1, ly1);
        Integer lx2 = Integer.parseInt(commandParts[3]);
        Integer ly2 = Integer.parseInt(commandParts[4]);
        Coordinate point2 = new Coordinate(lx2, ly2);
        canvas.drawLine(point1, point2);
        break;
      case "R":
        if (isNull(canvas) || commandParts.length != 5) {
          throw new IllegalArgumentException("Invalid Draw Rectangle Canvas command");
        }
        Integer rx1 = Integer.parseInt(commandParts[1]);
        Integer ry1 = Integer.parseInt(commandParts[2]);
        Integer rx2 = Integer.parseInt(commandParts[3]);
        Integer ry2 = Integer.parseInt(commandParts[4]);
        Coordinate vertex1 = new Coordinate(rx1, ry1);
        Coordinate vertex3 = new Coordinate(rx2, ry2);
        canvas.drawRectangle(vertex1, vertex3);
        break;
      case "B":
        if (isNull(canvas) || commandParts.length != 4) {
          throw new IllegalArgumentException("Invalid Fill Canvas command");
        }
        Integer bx = Integer.parseInt(commandParts[1]);
        Integer by = Integer.parseInt(commandParts[2]);
        String color = String.valueOf(commandParts[3].charAt(0));
//        canvas.fill(new Coordinate(bx, by), color, DFS);
        canvas.fill(new Coordinate(bx, by), color, BFS);
        break;
      case "Q":
        System.exit(0);
        break;
      default:
        throw new IllegalArgumentException("Invalid command");
    }
    return canvas;
  }
}
