package server;

import java.util.ArrayList;

public class Room 
{
	// 房间内的客户端线程池
	public ArrayList<ServerThread> list =new ArrayList<ServerThread>();
	// 房间内的客户端数
	public int size = 0;
	// 该房间的猜测词条
	public String []infos; 
	// 猜的客户端是否已经猜中词条，游戏是否结束
    public boolean correct = false;
    
}


