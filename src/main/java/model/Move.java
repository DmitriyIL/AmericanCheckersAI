
package model;

import java.awt.Point;

/**
 * The {@code Move} class represents a move and contains a weight associated
 * with the move.
 */
public class Move {
	
	/** The weight corresponding to an invalid move. */
	public static final double WEIGHT_INVALID = Double.NEGATIVE_INFINITY;

	/** The start tile index of the move. */
	private byte startIndex;
	
	/** The end tile index of the move. */
	private byte endIndex;
	
	/** The weight associated with the move. */
	private double weight;
	
	public Move(int startIndex, int endIndex) {
		setStartIndex(startIndex);
		setEndIndex(endIndex);
	}
	
	public Move(Point start, Point end) {
		setStartIndex(Board.toTileIndex(start));
		setEndIndex(Board.toTileIndex(end));
	}
	
	public int getStartIndex() {
		return startIndex;
	}
	
	public void setStartIndex(int startIndex) {
		this.startIndex = (byte) startIndex;
	}
	
	public int getEndIndex() {
		return endIndex;
	}
	
	public void setEndIndex(int endIndex) {
		this.endIndex = (byte) endIndex;
	}
	
	public Point getStart() {
		return Board.toPoint(startIndex);
	}
	
	public void setStart(Point start) {
		setStartIndex(Board.toTileIndex(start));
	}
	
	public Point getEnd() {
		return Board.toPoint(endIndex);
	}
	
	public void setEnd(Point end) {
		setEndIndex(Board.toTileIndex(end));
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public void changeWeight(double delta) {
		this.weight += delta;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[startIndex=" + startIndex + ", "
				+ "endIndex=" + endIndex + ", weight=" + weight + "]";
	}
}
