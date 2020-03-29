package server;  
  
import java.io.IOException;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.util.ArrayList;

import data.MyDataBase;  
  
public class MyServer 
{  
    // 房间线程池  
    public static ArrayList<Room> pool = new ArrayList<Room>();
    // 房间数量
    public static int poolSize = 0;
    // 你画我猜词条数据库
    public static MyDataBase db = new MyDataBase();  
    // 初始化服务器
    // 初始化监听描述符
    // 根据连接创建客户端线程
    // 根据客户端线程创建对战房间
    // 根据房间状态，启动客户端处理线程
    public void initServer() 
    {  
    	
        try {  
            //创建服务器对象  
            ServerSocket server=new ServerSocket(15132);  
            System.out.println("服务器已经启动，监听端口15132……");  
            while(true){  
                //循环接收客户端的连入  
                Socket socket=server.accept();  
                System.out.println("客户端已连入......");  
                //创建一个新的线程处理  
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
                System.out.println("连接数：" + temp.size);  
                //当有两个客户端连入进来时，开始游戏  
                
                if(temp.size == 2){  
                    //从数据库中获取一条记录  
                    String guessinfo = db.getInfo();  
                    temp.infos = guessinfo.split("#");  
                    //第一个连入的是画  
                    temp.list.get(0).name="draw";  
                    //第二个连入进来的是猜  
                    temp.list.get(1).name="guess";  
                      
                    temp.list.get(0).start();  
                    temp.list.get(1).start();  
                    System.out.println("启动线程......");  

                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}  

