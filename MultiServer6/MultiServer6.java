import java.io.*;
import java.net.*;
import java.util.*;

public class MultiServer6
{
	ServerSocket serverSocket = null;
	Socket socket = null;
	Map<String, PrintWriter> clientMap;
	
	// ������
	public MultiServer6()
	{
		// Ŭ�󸮾�Ʈ�� ��½�Ʈ���� ������ �ؽ��� ����.
		clientMap = new HashMap<String, PrintWriter>();
		//�ؽ��� ����ȭ ����.
		Collections.synchronizedMap(clientMap);
	}

	public void init()	
	{
		try
		{
			serverSocket = new ServerSocket(9999);	// 9999��Ʈ�� �������� ��ü����.
			System.out.println("������ ���۵Ǿ����ϴ�.");

			while(true)
			{
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress() + ":" + socket.getPort());
				
				Thread msr = new MultiServerT(socket); // ������ ����
				msr.start(); // ������ �õ�.
			}			
		}
		catch (Exception e)
		{			
			e.printStackTrace();
		}
		finally
		{
			try
			{
				serverSocket.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	// ���ӵ� ��� Ŭ���̾�Ʈ�鿡�� �޽����� ����.
	public void sendAllMsg(String msg)
	{
		// ��½�Ʈ���� ���������� ���ͼ� �ش� �޽����� ����Ѵ�.
		Iterator it = clientMap.keySet().iterator();

		while(it.hasNext())
		{
			try
			{
				PrintWriter it_out = (PrintWriter) clientMap.get(it.next());
				it_out.println(msg);
			}
			catch (Exception e)
			{
				System.out.println("����:"+e);
			}
		}
	}
	public static void main(String[] args)
	{
		// ������ü ����.
		MultiServer6 ms = new MultiServer6();
		ms.init();
	}

	// ================================================
	// ���� Ŭ����
	// Ŭ���̾�Ʈ�κ��� �о�� �޼����� �ٸ� Ŭ���̾�Ʈ(socket)�� ������ ������ �ϴ� �޼���
	class MultiServerT extends Thread
	{
		Socket socket;
		PrintWriter out = null;
		BufferedReader in = null;

		// ������ 
		public MultiServerT(Socket socket)
		{
			this.socket = socket;
			try
			{
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(this.socket.getInputStream() ));
			
			}
			catch (Exception e)
			{
				System.out.println("����:"+e);
			}
		}
	

		// �����带 ����ϱ� ���ؼ� run() �޼��� ������
		@Override
		public void run()
		{
			String name = ""; // Ŭ���̾�Ʈ�κ��� ���� �̸��� ������ ����.
			try
			{
				name = in.readLine(); // Ŭ���̾�Ʈ���� ó������ ������ �޽�����
									  // Ŭ���̾�Ʈ�� ����� �̸��̴�.
				sendAllMsg(name + "���� �����ϼ̽��ϴ�.");
				// ���� ��ü�� �������ִ� ������ �����ϰ� �ٸ� ����(Ŭ���̾�Ʈ)�鿡�� ������ �˸�.
				clientMap.put(name, out); // �ؽ��ʿ� Ű�� name���� ��½�Ʈ�� ��ü�� ����.
				System.out.println("���� ������ ���� " + clientMap.size() + "�� �Դϴ�.");

				// �Է½�Ʈ���� null�� �ƴϸ� �ݺ�.
				String s = "";
				while(in != null)
				{
					s = in.readLine();
					System.out.println(s);
					if(s.equals("q") || s.equals("Q"))
						break;
					sendAllMsg(s);			
				}
			//	System.out.println("Bye...");
			}		
			catch (Exception e)
			{
				System.out.println("����:" + e);
			}
			finally
			{
				// ���ܰ� �߻��Ҷ� ����. �ؽ��ʿ��� �ش� ������ ����.
				// ���� �����ϰų� ������ java.net.SocketException: ���ܹ߻�
				clientMap.remove(name);
				sendAllMsg(name + "���� �����ϼ̽��ϴ�.");
				System.out.println("���� ������ ���� " + clientMap.size() + "�� �Դϴ�.");	

				try	
				{
					in.close();
					out.close();

					socket.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}		
	}
}
