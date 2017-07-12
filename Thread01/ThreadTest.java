import java.io.*;
import java.lang.*;
import java.util.Scanner;

public class ThreadTest
{
	public static void main(String[] args)
	{
		System.out.println("숫자를 입력해 주세요.");
		Scanner s = new Scanner(System.in);
		String s_num = s.nextLine();
		int nNum = Integer.parseInt(s_num);

		try
		{
			int i = 0;
			while(i < nNum)
			{
				try
				{
					Thread.sleep(1000);
					i = i + 1;
					System.out.println("Tread : " + i);
				}
				catch (Exception e)
				{
					System.out.println("예외:" + e);
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		System.out.println("이름을 입력해 주세요.");
		String s_name = s.nextLine();
	}
}
