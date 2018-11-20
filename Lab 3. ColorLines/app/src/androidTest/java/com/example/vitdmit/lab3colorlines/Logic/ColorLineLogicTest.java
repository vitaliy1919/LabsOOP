package com.example.vitdmit.lab3colorlines.Logic;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class ColorLineLogicTest {
    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void generetesExactlyThreeBallsEachTime() {
        Cell [][] field = new Cell[9][9];
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field.length; j++)
                field[i][j] = new Cell();
        ColorLineLogic logic = new ColorLineLogic(appContext, field);
        int desiredUsedNumber = 0;
        int currentUsed = 0;
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field.length; j++)
                if (field[i][j].isUsed())
                    currentUsed++;
        assertEquals(desiredUsedNumber, currentUsed);
        while (logic.generateRandomBalls()) {
            desiredUsedNumber += 3;
            currentUsed = 0;
            for (int i = 0; i < field.length; i++)
                for (int j = 0; j < field.length; j++)
                    if (field[i][j].isUsed())
                        currentUsed++;
            assertEquals(desiredUsedNumber, currentUsed);
        }
    }

    @Test
    public void shoudRemoveHorizontalLine() {
        Cell [][] field = new Cell[9][9];
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field.length; j++)
                field[i][j] = new Cell();

        int row = 1;
        int color = 156;
        int column;
        for (column = 0; column < 5; column++) {
            field[row][column].setUsed(true);
            field[row][column].setColor(color);
        }
        ColorLineLogic logic = new ColorLineLogic(appContext, field);

        int result = logic.findConsecutiveBalls();
        assertEquals(5, result);
        for (column = 0; column < 5; column++) {
            assertFalse(field[row][column].isUsed());
        }
        row = 3;
        for (column = 0; column < 9; column++) {
            field[row][column].setUsed(true);
            field[row][column].setColor(color);
        }

        result = logic.findConsecutiveBalls();
        assertEquals(9, result);
        for (column = 0; column < 9; column++) {
            assertFalse(field[row][column].isUsed());
        }


        row = 8;

        for (column = 4; column < 9; column++) {
            field[row][column].setUsed(true);
            field[row][column].setColor(color);
        }

        result = logic.findConsecutiveBalls();
        assertEquals(5, result);
        for (column = 4; column < 9; column++) {
            assertFalse(field[row][column].isUsed());
        }

    }

    @Test
    public void shouldntRemoveSmallHorizontalLine() {
        Cell [][] field = new Cell[9][9];
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field.length; j++)
                field[i][j] = new Cell();

        int row = 1;
        int color = 156;
        int column;
        for (column = 0; column < 4; column++) {
            field[row][column].setUsed(true);
            field[row][column].setColor(color);
        }
        ColorLineLogic logic = new ColorLineLogic(appContext, field);

        int result = logic.findConsecutiveBalls();
        assertEquals(0, result);
        for (column = 0; column < 4; column++) {
            assertTrue(field[row][column].isUsed());

        }

        row = 8;
        for (column = 5; column < 9; column++) {
            field[row][column].setUsed(true);
            field[row][column].setColor(color);
        }
        result = logic.findConsecutiveBalls();
        assertEquals(0, result);

        for (column = 5; column < 9; column++) {
            assertTrue(field[row][column].isUsed());

        }


    }

    @Test
    public void shoudRemoveVerticalLine() {
        Cell [][] field = new Cell[9][9];
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field.length; j++)
                field[i][j] = new Cell();

        int row = 1;
        int color = 156;
        int column;
        for (column = 0; column < 5; column++) {
            field[column][row].setUsed(true);
            field[column][row].setColor(color);
        }
        ColorLineLogic logic = new ColorLineLogic(appContext, field);

        int result = logic.findConsecutiveBalls();
        assertEquals(5, result);
        for (column = 0; column < 5; column++) {
            assertFalse(field[column][row].isUsed());
        }
        row = 3;
        for (column = 0; column < 9; column++) {
            field[column][row].setUsed(true);
            field[column][row].setColor(color);
        }

        result = logic.findConsecutiveBalls();
        assertEquals(9, result);
        for (column = 0; column < 9; column++) {
            assertFalse(field[column][row].isUsed());
        }


        row = 8;

        for (column = 4; column < 9; column++) {
            field[column][row].setUsed(true);
            field[column][row].setColor(color);
        }

        result = logic.findConsecutiveBalls();
        assertEquals(5, result);
        for (column = 4; column < 9; column++) {
            assertFalse(field[column][row].isUsed());
        }

    }


    @Test
    public void shouldntRemoveSmallVerticalLine() {
        Cell [][] field = new Cell[9][9];
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field.length; j++)
                field[i][j] = new Cell();

        int row = 1;
        int color = 156;
        int column;
        for (column = 0; column < 4; column++) {
            field[column][row].setUsed(true);
            field[column][row].setColor(color);
        }
        ColorLineLogic logic = new ColorLineLogic(appContext, field);

        int result = logic.findConsecutiveBalls();
        assertEquals(0, result);
        for (column = 0; column < 4; column++) {
            assertTrue(field[column][row].isUsed());

        }

        row = 8;
        for (column = 5; column < 9; column++) {
            field[column][row].setUsed(true);
            field[column][row].setColor(color);
        }
        result = logic.findConsecutiveBalls();
        assertEquals(0, result);

        for (column = 5; column < 9; column++) {
            assertTrue(field[column][row].isUsed());

        }


    }
}