package com.ysw.model;

import com.ysw.util.SetLinkedBlockingQueue;
import lombok.Data;

import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.ysw.model.SearchType.BFS;
import static com.ysw.model.SearchType.DFS;
import static java.util.Optional.ofNullable;

@Data
public class Canvas {

  private final int width;
  private final int height;
  private final String[][] canvasArea;
  private final String horizontalLine;
  private boolean useDepthFirstSearch;

  public Canvas(int width, int height) {
    this.width = width;
    this.height = height;
    this.canvasArea = new String[width][height];
    this.horizontalLine = IntStream.rangeClosed(0, width+1)
        .mapToObj(i -> "-").collect(Collectors.joining(""));
  }

  public void drawLine(Coordinate point1, Coordinate point2) {
    if (!point1.getX().equals(point2.getX()) && !point1.getY().equals(point2.getY())) {
      throw new IllegalArgumentException("Unable to draw line, only straight lines supported");
    }
    for (int y = 0 ; y < height ; y++) {
      for (int x = 0 ; x < width ; x++) {
        Coordinate currentCoordinate = new Coordinate(x+1, y+1);
        if (currentCoordinate.isBetween(point1, point2)) {
          canvasArea[x][y] = "X";
        }
      }
    }
  }

  public void drawRectangle(Coordinate vertex1, Coordinate vertex3) {
    Coordinate vertex2 = new Coordinate(vertex1.getX(), vertex3.getY());
    Coordinate vertex4 = new Coordinate(vertex3.getX(), vertex1.getY());
    drawLine(vertex1, vertex2);
    drawLine(vertex2, vertex3);
    drawLine(vertex3, vertex4);
    drawLine(vertex1, vertex4);
  }

  public void fill(Coordinate pointEnd, String color, SearchType searchType) throws InterruptedException {
    String existingColorOnPoint = canvasArea[pointEnd.getX()-1][pointEnd.getY()-1];
    if (DFS.equals(searchType)) {
      Stack<Coordinate> stack = new Stack<>();
      stack.add(new Coordinate(pointEnd.getX() - 1, pointEnd.getY() - 1));

      while (!stack.isEmpty()) {
        Coordinate pop = stack.pop();
        if (canvasArea[pop.getX()][pop.getY()] == existingColorOnPoint) {
          canvasArea[pop.getX()][pop.getY()] = color;
        }
        if (pop.getX() - 1 >= 0 && canvasArea[pop.getX() - 1][pop.getY()] == existingColorOnPoint) {
          stack.add(new Coordinate(pop.getX() - 1, pop.getY()));
        }
        if (pop.getX() + 1 < width && canvasArea[pop.getX() + 1][pop.getY()] == existingColorOnPoint) {
          stack.add(new Coordinate(pop.getX() + 1, pop.getY()));
        }
        if (pop.getY() - 1 >= 0 && canvasArea[pop.getX()][pop.getY() - 1] == existingColorOnPoint) {
          stack.add(new Coordinate(pop.getX(), pop.getY() - 1));
        }
        if (pop.getY() + 1 < height && canvasArea[pop.getX()][pop.getY() + 1] == existingColorOnPoint) {
          stack.add(new Coordinate(pop.getX(), pop.getY() + 1));
        }
      }
    } else if (BFS.equals(searchType)){
      SetLinkedBlockingQueue<Coordinate> queue = new SetLinkedBlockingQueue<>();
      queue.add(new Coordinate(pointEnd.getX() - 1, pointEnd.getY() - 1));

      while (!queue.isEmpty()) {
        Coordinate dequeued = queue.take();
        if (canvasArea[dequeued.getX()][dequeued.getY()] == existingColorOnPoint) {
          canvasArea[dequeued.getX()][dequeued.getY()] = color;
        }
        if (dequeued.getX() - 1 >= 0 && canvasArea[dequeued.getX() - 1][dequeued.getY()] == existingColorOnPoint) {
          queue.add(new Coordinate(dequeued.getX() - 1, dequeued.getY()));
        }
        if (dequeued.getX() + 1 < width && canvasArea[dequeued.getX() + 1][dequeued.getY()] == existingColorOnPoint) {
          queue.add(new Coordinate(dequeued.getX() + 1, dequeued.getY()));
        }
        if (dequeued.getY() - 1 >= 0 && canvasArea[dequeued.getX()][dequeued.getY() - 1] == existingColorOnPoint) {
          queue.add(new Coordinate(dequeued.getX(), dequeued.getY() - 1));
        }
        if (dequeued.getY() + 1 < height && canvasArea[dequeued.getX()][dequeued.getY() + 1] == existingColorOnPoint) {
          queue.add(new Coordinate(dequeued.getX(), dequeued.getY() + 1));
        }
      }
    }
  }

  public String getPrintOutput() {
    StringBuilder sb = new StringBuilder(horizontalLine).append("\n");
    for (int y = 0 ; y < height ; y++) {
      sb.append("|");
      for (int x = 0 ; x < width ; x++) {
        sb.append(ofNullable(canvasArea[x][y]).orElse(" "));
      }
      sb.append("|").append("\n");
    }
    sb.append(horizontalLine).append("\n");
    return sb.toString();
  }

  public void print() {
    System.out.print(this.getPrintOutput());
  }

}
