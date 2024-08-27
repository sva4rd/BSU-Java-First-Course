package lab2;
import java.util.Scanner;

public class var_7 {
	
	public static String stringOperations(String str, char operation1, char operation2, String x) {
		int lIdx, rIdx;											//индексы нач левого члена и конца правого в строке
		for (int i = 0; i < str.length(); i++ ) {

			if (i == 0 && str.charAt(i) == '-')		//исключение
				continue;
			
			if(str.charAt(i) == operation1 || str.charAt(i) == operation2){	//если нашли операцию
				lIdx = i - 1;
				rIdx = i + 1;
				while (Character.isSpaceChar(str.charAt(lIdx--)));	//идем до члена мимо пробелов
				while (Character.isSpaceChar(str.charAt(rIdx++)));
				lIdx++;
				rIdx--;

				while (Character.isDigit(str.charAt(lIdx--))) {		//идем до начала левого члена
					if (lIdx == -1) {
						lIdx--;
						break;
					}
				}
				lIdx++;


				while (Character.isDigit(str.charAt(rIdx++))) {		//до конца правого
					if (rIdx == str.length()) {
						rIdx++;
						break;
					}
				}
				rIdx--;

				int rez;
				int l, r;

				if (lIdx >= 0 && (str.charAt(lIdx)=='X' || str.charAt(lIdx)=='x')) {	//проверка что член Х
					lIdx--;
					l = Integer.parseInt(x);
				}
				else
					l = Integer.parseInt(str.substring(lIdx + 1, i).trim());			//иначе число

				if (rIdx < str.length() && (str.charAt(rIdx)=='X' || str.charAt(rIdx)=='x')) {	//аналогично для правого
					rIdx++;
					r = Integer.parseInt(x);
				}
				else
					r = Integer.parseInt(str.substring(i + 1, rIdx).trim());


				if(str.charAt(i) == '*'){			//выполняем операцию
					rez = l * r;
				}
				else if(str.charAt(i) == '/'){	
					rez = l / r;
				}
				else if(str.charAt(i) == '+'){	
					if (lIdx == 0 && str.charAt(0)=='-') {	//исключ если в начале отриц число уже
						rez = -l + r;
						lIdx--;
					}
					else
						rez = l + r;
				}
				else{			
					if (lIdx == 0 && str.charAt(0)=='-') {
						rez = -l - r;
						lIdx--;
					}
					else
						rez = l - r;
				}

				str = str.substring(0, lIdx + 1) + Integer.toString(rez) + str.substring(rIdx, str.length() - rIdx + rIdx);
				i = lIdx;	//новая строка(замена членов их операцию) индекс - нач левого члена(тк строка изменилась)
			}

		}
		return str;
	}
	
	
	
	public static boolean stringCheck(String str) {						//проверка на корректность
		
		int idx = 0;
		int digitsCount = 0;
		int operationsCount = 0;
		while (idx < str.length()) {
			
			if(Character.isDigit(str.charAt(idx))) {				//нашли цифру и идем до конца числа
				digitsCount++;
				while (Character.isDigit(str.charAt(idx++))) {		
					if (idx == str.length()) { 
						idx++;
						break;
					}
				}
				idx--;
			}
			
			else if (str.charAt(idx)=='X' || str.charAt(idx)=='x') {//нашли икс
				digitsCount++;
				idx++;
			}
			
			else if (str.charAt(idx)=='+' || str.charAt(idx)=='-' || str.charAt(idx)=='*' //нашли знак
					|| str.charAt(idx)=='/') {
				operationsCount++;
				idx++;
			}
			
			else if (str.charAt(idx)==' ') {								//нашли пробел
				while (Character.isSpaceChar(str.charAt(idx++))) {		
					if (idx == str.length()) {
						idx++;
						break;
					}
				}
				idx--;
			}
			
			else											//если другой символ, то ошибка
				return false;
			
			if (operationsCount > digitsCount || operationsCount < digitsCount - 1)
				return false;		//если кол-во арифм знаков больше кол-ва чисел или их меньше чем кол-во чисел-1
		}
		
		if (operationsCount != digitsCount - 1)//знаков должно быть на 1 меньше чем чисел
			return false;
		return true;
	}
	
	

	public static void main(String[] args) {
		if ( args.length != 1 ) {									//проверка что введен Х
			System.err.println("Invalid number of arguments");
			System.exit(1);
		}
		Scanner in = new Scanner(System.in);


		while ( in.hasNextLine() ) {
			String str = in.nextLine();
			str = str.trim();										//убираем пробелы в начале и конце строки

			if (!stringCheck(str)) {								//проверяем строку на корректность
				System.err.println("Invalid string");
				continue;
			}
			
			str = stringOperations(str, '*', '/', args[0]);			//все * и / сначала
			str = stringOperations(str, '+', '-', args[0]);			//потом все + и -
			
			if (str.equalsIgnoreCase("x"))
				System.out.println("String result = " + args[0]);
			else
				System.out.println("String result = " + str);		//получили строку содержащую ответ
		}

		in.close();
		System.exit(0);
	}
}
