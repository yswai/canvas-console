package com.ysw.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinate {

  private Integer x;
  private Integer y;

  public boolean isBetween(Coordinate point1, Coordinate point12) {
    List<Integer> xSorted = Stream.of(point1.getX(), point12.getX()).sorted().collect(Collectors.toList());
    List<Integer> ySorted = Stream.of(point1.getY(), point12.getY()).sorted().collect(Collectors.toList());
    if (xSorted.get(0) <= this.x && this.x <= xSorted.get(1)) {
      return ySorted.get(0) <= this.y && this.y <= ySorted.get(1);
    }
    return false;
  }

}
