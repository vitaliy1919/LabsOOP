package com.example.vitdmit.lab3colorlines.Logic;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

import com.example.vitdmit.lab3colorlines.R;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ColorLineLogic {
    private Cell field[][];
    private int[] nextColors = new int[3];
    private int[] ballsColors;
    private Context context;
    private int available;
    private int dimentions;
    private HashSet<Point> recentlyAdded = new HashSet<>();

    private void generateNextColors(int nextColors[]) {
        Random r = new Random();
        for (int i = 0; i < nextColors.length; i++)
            nextColors[i] = ballsColors[r.nextInt(ballsColors.length)];
    }
    public ColorLineLogic( Context context, Cell[][] field) {
        this.field = field;
        dimentions = field.length;
        available = dimentions*dimentions;
        this.context = context;
        ballsColors = context.getResources().getIntArray(R.array.balls);
        generateNextColors(nextColors);
    }

    public int[] getNextColors() {
        return nextColors;
    }

    private void eliminateHorizontalRow(int row, int endColumn, int length) {
        for (int i = endColumn - length + 1; i <= endColumn; i++)
            field[row][i].setUsed(false);
        available += length;
    }
    public boolean existsPath(Point a, Point b) {
        boolean [][]used = new boolean[dimentions][dimentions];
        for (boolean[] row: used)
            Arrays.fill(row, false);
        Point node = new Point(a);
        Point []directions = new Point[]{
                new Point(1, 0),
                new Point(0,1),
                new Point(-1, 0),
                new Point(0, -1)
        };
        LinkedList<Point> nodes = new LinkedList<>();
        nodes.add(node);
        while (!nodes.isEmpty()) {
            Point curNode = nodes.removeFirst();

            for (int i = 0; i < directions.length; i++) {
                Point dir = directions[i];
                int x = curNode.x + dir.x;
                int y = curNode.y + dir.y;
                Point newPoint = new Point(x, y);
                if (newPoint.x >= 0 && newPoint.x < dimentions && newPoint.y >= 0 && newPoint.y < dimentions &&
                        !used[x][y] && !field[x][y].isUsed()) {
                    used[x][y]= true;
                    if (newPoint.equals(b))
                        return true;
                    nodes.add(newPoint);
                }
            }
        }

        return false;
    }
    private  void eliminateVerticalRow(int endRow, int column, int length) {
        for (int i = endRow - length + 1; i <= endRow; i++)
            field[i][column].setUsed(false);
        available += length;
    }
    public int findConsecutiveBalls() {
        int curHorizontalLength = 1;
        int curVerticalLength = 1;
        for (int row = 0; row < dimentions; row++) {
            curHorizontalLength = 1;
            curVerticalLength = 1;
            for (int column = 0; column < dimentions - 1; column++) {
                if (field[row][column].isUsed() && field[row][column].getColor() == field[row][column + 1].getColor())
                    curHorizontalLength++;
                else {
                    if (curHorizontalLength >= 5) {
                        eliminateHorizontalRow(row, column, curHorizontalLength);
                        return curHorizontalLength;
                    }
                    curHorizontalLength = 1;
                }
                if (field[column][row].isUsed() && field[column][row].getColor() == field[column + 1][row].getColor())
                    curVerticalLength++;
                else {
                    if (curVerticalLength >= 5) {
                        eliminateVerticalRow(column, row, curVerticalLength);
                        return curVerticalLength;
                    }
                    curVerticalLength = 1;
                }

                if (column == dimentions - 2) {
                    if (curHorizontalLength >= 5) {
                        eliminateHorizontalRow(row, column + 1, curHorizontalLength);
                        return curHorizontalLength;
                    }
                    if (curVerticalLength >= 5) {
                        eliminateVerticalRow(column + 1, row, curVerticalLength);
                        return curVerticalLength;
                    }
                }
            }
        }

        return 0;
    }

    public boolean generateRandomBalls() {
        recentlyAdded.clear();
        if (available < 3)
            return false;
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            int color = nextColors[i];
            int pos = r.nextInt(available);
            int curPos = 0;

            for (int row = 0; row < dimentions; row++)
                for (int column = 0; column < dimentions; column++)
                    if (!field[row][column].isUsed()) {
                        if (curPos == pos) {
                            field[row][column].setColor(color);
                            field[row][column].setUsed(true);
                            recentlyAdded.add(new Point(row, column));
                            column = dimentions;
                            row = dimentions;
                        }
                        curPos++;
                    }
            available--;
        }
        if (available <= 0)
            return false;
        generateNextColors(nextColors);
        return true;
    }

    public HashSet<Point> getRecentlyAdded() {
        return recentlyAdded;
    }
}
