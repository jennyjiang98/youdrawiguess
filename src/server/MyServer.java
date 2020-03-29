package server;  
  
import java.io.IOException;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.util.ArrayList;

import data.MyDataBase;  
  
public class MyServer 
{  
    // �����̳߳�  
    public static ArrayList<Room> pool = new ArrayList<Room>();
    // ��������
    public static int poolSize = 0;
    // �㻭�Ҳ´������ݿ�
    public static MyDataBase db = new MyDataBase();  
    // ��ʼ��������
    // ��ʼ������������
    // �������Ӵ����ͻ����߳�
    // ���ݿͻ����̴߳�����ս����
    // ���ݷ���״̬�������ͻ��˴����߳�
    public void initServer() 
    {  
    	
        try {  
            //��������������  
            ServerSocket server=new ServerSocket(15132);  
            System.out.println("�������Ѿ������������˿�15132����");  
            while(true){  
                //ѭ�����տͻ��˵�����  
                Socket socket=server.accept();  
                System.out.println("�ͻ���������......");  
                //����һ���µ��̴߳���  
                ServerThread st = new ServerThread(socket); 
                Room temp;
                
                if (poolSize == 0 || (pool.get(poolSize - 1)).size == 2)
                {
                	temp = new Room(); 	
                   	pool.add(temp); 
                	poolSize++;
                }
                else
                	temp = pool.get(poolSize - 1);               	
                temp.size++;
                temp.list.add(st);
                st.roomNum = poolSize - 1;              	            
                System.out.println("��������" + temp.size);  
                //���������ͻ����������ʱ����ʼ��Ϸ  
                
                if(temp.size == 2){  
                    //�����ݿ��л�ȡһ����¼  
                    String guessinfo = db.getInfo();  
                    temp.infos = guessinfo.split("#");  
                    //��һ��������ǻ�  
                    temp.list.get(0).name="draw";  
                    //�ڶ�������������ǲ�  
                    temp.list.get(1).name="guess";  
                      
                    temp.list.get(0).start();  
                    temp.list.get(1).start();  
                    System.out.println("�����߳�......");  

                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}  

