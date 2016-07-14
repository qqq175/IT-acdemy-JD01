package org.qqq175.it_academy.jd1.hw3.sorter;
import java.util.Arrays;
/**
 * Sort matrix columns to arrange main diagonal elements by ascending
 * @author qqq175
 *
 */
public class MatrixSorterI {
/**
 * main method
 * @param args
 */
	public static void main(String[] args) {
		int[][] matrix =  { { 31, 15, 12, 22, 2, 14 }, 
					{ 10, 17, 8, 33 , 3, 12},
					{ 4, 18, 19, 27 , 4, 0},
					{ 32, 17, 31, 25 , 5, 26},
					{17, 25, 45, 41, 19, 35},
					{39, 25, 47, 24, -2, 37}};
		long start = System.currentTimeMillis();  //made some benchmark
		int row = 0, col = 0;
		int currentElementValue =  Integer.MIN_VALUE;
		//the order array stores current order of columns, if column position isn't  known - value = -1,
		//order format [initialMatrixColumnNumber1, initialMatrixColumnNumber2, initialMatrixColumnNumber3, initialMatrixColumnNumber4]
		int[] order = new int[matrix.length];	
		//avaliableCols save current state of using matrix column
		//if some element of column are include to order[] value is false
		//example: matrix is 4x4, order[2, 0, -1, -1] -> availableCols = [false, true, false, true]
		boolean[] availavleCols = new boolean[matrix.length];
		for(int i =0; i < order.length; i++){  //initialize arrays
			order[i] = -1;
			availavleCols[i] = true;
		}
		
		while(col < matrix.length){
			if (row == 0) //if back to first row
			{
				currentElementValue = Integer.MIN_VALUE;  //uses MIN_value to ensure that currentElementValue will lesser or equal to any int
			} else {  //else load last valid Element to currentElement
				currentElementValue = matrix[row-1][order[row -1]];
			}
			//searching a new order
			while(col < matrix.length){
				if(availavleCols[col]){		//if column isn't used
					if(matrix[row][col] > currentElementValue){  //if in next row matrix element [row,col] bigger than current element (from previous row)
						currentElementValue = matrix[row][col];   //make it current elem.
						availavleCols[col] = false;		//make column unavailable
						order[row] = col;		//store next column to order
						row++;  //go to the next row
						col = 0;  //search from first (index 0) column in next row
					} else {  //if matrix[row][col] <= currentElementValue
						col++;
					}
				} else //if column is used
					col++;
			}
			  /* if col > max column index(matrix.length)  - we didn't find the bigger element  in row, 
			   * except row == matrix.length - that means that we reached the and and find a way*/
			while(col == matrix.length && row != matrix.length)
			{
				/*if  col == matrix.length - means that we didn't fint any in this row
				 * if row > 0 - we didn't reach end of matrix
				 */
				if(row > 0) 
				{
				row--;	//go one row up
				availavleCols[order[row]] = true;  //make last found element's column is avaliable again
				col = order[row] +1; //resume search with next column in previous row
				order[row] = -1;  //delete last found column from order
				} else   //if row == 0 and col == matrix.length  - we didn't find way in a whole matrix
				{
					break;
				}
			}
		}
		System.out.println("found new order is " + Arrays.toString(order) + " in "
				+ (System.currentTimeMillis()-start) + " ms\n");  //print new order and benchmark
		if (order[0] != -1)
		{
			matrix = reorderMatrix(matrix, order);
			printMatrix(matrix);
		} else {
			System.out.print("Указанную матрицу невозможно упорядочить согласно условиям задачи.");
		}
	}
	
	/**
	 * Get matrix NxM and print it
	 * @param matrix - int array [n][m]
	 */
	public static void printMatrix(int[][] matrix)
	{
		for(int [] line : matrix)
		{
			System.out.print("|");
			for(int elem: line){
				System.out.printf("% 5d ", elem);
			}
			System.out.println("\t|");
		}
	}
	/**
	 * Get  <i>matrix NxN</i> and <i>newOrder[N]</i>, <br> where 
	 * <b><i>newOrder[i]</i></b> (i=0..N) is number of column in initial matrix,
	 *<br> and <b><i>i</i></b> is number of that column in reordered array.
	 * @param matrix - int array [N][N]
	 * @param newOrder - int array[N]
	 * @return reordered array [N][N]
	 */
	public static int[][] reorderMatrix(int[][] matrix, int [] newOrder){
		int[][] reorderedMatrix = new int[matrix.length][matrix.length];  //create new matrix
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0;  j< matrix.length; j++){
				reorderedMatrix[i][j] = matrix[i][newOrder[j]];			//fill new matrix
			}
		}
		
		return reorderedMatrix;  
	}
}