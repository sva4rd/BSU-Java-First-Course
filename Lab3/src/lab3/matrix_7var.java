package lab3;

import java.util.Scanner;
import java.util.Random;
import java.util.Date;

public class matrix_7var {	
	
	public static void rotation(int[][] arr, int[][] help_arr) {
		int n = arr.length;
		for (int i = 0; i<n;i++) {				//копируем исходную матрицу
			for (int j = 0; j < n;j++) {
				help_arr[i][j] = arr[i][j];
			}
		}
		for (int i = 0, t = 0; i<n;i++, t++) {			//заполняем в соотв с поворотом
			for (int j = n-1, f = 0; j >=0;j--, f++) {
				arr[f][t] = help_arr[i][j];
			}
		}
	}
	
	public static void matrixOutput(int[][] arr) {	//вывод
		int n = arr.length;						
		for(int i = 0; i < n; i++) {
            for( int j = 0; j < n; j++) {
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println();
        }
	}
	
	public static void rotationAndOutput(int[][] arr, int[][] help_arr, int degrees) {
		System.out.println();
        rotation(arr, help_arr);		//поворот
        System.out.println("Matrix rotated " + degrees + " degrees:");
        matrixOutput(arr);				//вывод
	}
	
	public static void main(String[] args) {
		
		System.out.print("Enter n: ");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.close();
        if ( n <= 1 ) {					//проверка n, мин двум матрица - 2 на 2
            System.err.println("Invalid n value, require n > 1");
            System.exit( 1 );
        }
        Random rnd = new Random((new Date()).getTime());
        
        System.out.println();
        System.out.println("Initial matrix:");
        
        int [][] arr = new int [n][n];
        int [][] help_arr = new int [n][n];
        
        for(int i = 0; i < n; i++) {
            for( int j = 0; j < n; j++) {
                arr[i][j] = rnd.nextInt() % ( n + 1 );		//заполняем рандомно от -n до n
                System.out.print(arr[i][j] + "\t");			//выводим
            }
            System.out.println();
        }
        
        rotationAndOutput(arr, help_arr, 90);			//поворачиваем на 90 и выводим
        rotationAndOutput(arr, help_arr, 180);			//еще на 90
        rotationAndOutput(arr, help_arr, 270);        	//и еще на 90
	}
}

//7 var

//Ввести с консоли n - размерность матрицы a[n][n]. Задать
//значения элементов матрицы в интервале значений от -n до n с
//помощью датчика случайных чисел. Повернуть матрицу на 90
//(180, 270) градусов против часовой стрелки. Распечатать
//исходную матрицу и результат.

