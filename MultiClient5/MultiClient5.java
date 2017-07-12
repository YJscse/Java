// �ܼ� ��Ƽä�� Ŭ���̾�Ʈ ���α׷�
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiClient5
{
	public static void main(String[] args) throws UnknownHostException, IOException
	{
		System.out.println("�̸��� �Է��� �ּ���.");
		Scanner s = new Scanner(System.in);
		String s_name = s.nextLine();

		PrintWriter out = null;

		try
		{
			//String ServerIP = "localhost";
			String ServerIP = args[0];
			Socket socket = new Socket(ServerIP, 9999); // ���� ��ü ����
			System.out.println("������ ������ �Ǿ����ϴ�......");

			// �������� ������ �޽����� ������� �ֿܼ� ����ϴ� ������.
			Thread receiver = new Receiver5(socket);
			receiver.start();

			// ����ڷκ��� ���� ���ڿ��� ������ �������ִ� ������ �ϴ� ������.
			Thread sender = new Sender5(socket, s_name);
			sender.start();
		}
		catch (Exception e)
		{
			System.out.println("����[MultiClient class] : " + e);
		}		
	}
}