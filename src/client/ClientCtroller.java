package client;  
  
import java.awt.BasicStroke;  
import java.awt.Color;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.net.Socket;  
  
import javax.swing.JFrame;  
import javax.swing.JOptionPane;  
  
public class ClientCtroller {  
  
    public Socket socket;  
    public ClientUI ui;  
  
    public DataInputStream dis;  
    public DataOutputStream dos;  
      
//  public ClientCtroller(Socket socket) {  
//      this.socket=socket;  
//  }  
//    
    public ClientCtroller(Socket socket, JFrame ui) {  
        this.socket = socket;  
        this.ui =(ClientUI) ui;  
    }  
  
    public void dealwith() {  
        try {  
            InputStream ins =this.socket.getInputStream();  
            OutputStream ous =this.socket.getOutputStream();  
            dis=new DataInputStream(ins);  
            dos = new DataOutputStream(ous);  
            String msg = readMsg(socket.getInputStream());  
              
            if ("draw".equals(msg)) {  
                // �����draw����ͻ�����ʾ���Ľ���  
                ui.remove(ui.waitPanel);  
                ui.addDrawPanel();  
                ui.sendBtn.setEnabled(false);  
                ui.repaint();  
//              System.out.println("draw������ϱ�");  
                //����Ҫ������Ϣ  
                String drawinfo=dis.readUTF();  
                //��Ҫ������Ϣ��ӵ������  
                ui.contant.setText("���Ķ���Ϊ��"+drawinfo);  
                  
                while(true){  
                    String s=readMsg(ins);  
                    System.out.println("draw ���ܵ�����Ϣ��"+s);  
//                  System.out.println(s);  
                    //���շ������˷��͵ĲµĿͻ��˲µ���Ϣ  
                    if(!"data".equals(s)){  
                        String s1=dis.readUTF();  
                        System.out.println("s1��"+s);  
                        //����¶��ˣ����ڻ��Ŀͻ�����ʾ�¶���  
                        if("yes".equals(s1)){  
//                          JOptionPane.showMessageDialog(null, "��¶��ˣ�");  
                            ui.jta.append("�Է��¶���"+"\r\n");  
                            socket.close();
                            int res=JOptionPane.showConfirmDialog(null, "�Ƿ�����һ�֣�", "�㻭��̫���ˣ�", JOptionPane.YES_NO_OPTION);
                            if(res==JOptionPane.YES_OPTION){ 
                 
                            	/*SwingUtilities.invokeLater(()->{
                    			new ClientUI();
                    		});*/
             
                                ClientUI c=new ClientUI();
                                c.setVisible(true);
                                Thread t=new Thread(c);
                                t.start();
                                return;
                                
                            }else{
                                System.exit(0);
                            } 
                        }  
                    //���򻭵Ŀͻ�����ʾ�µĿͻ����˵���Ϣ  
                        else{  
                            ui.jta.append(s1+"\r\n");  
                        }  
                    }  
                }  
            }  
            if ("guess".equals(msg)) {  
                // ����ǲ£�����ʾ�µĿͻ���  
                String guessinfo=dis.readUTF();  
                ui.remove(ui.waitPanel);  
                ui.addDrawPanel();  
                ui.addGuessPanel();  
                ui.contant.setText(guessinfo);  
                ui.sendBtn.setEnabled(true);  
//              ui.repaint();  
//              System.out.println("guess������ϱ�");  
                while(true){  
                    //���շ������˵�����  
                    String info =readMsg(ins);  
                    //�����������Ϣ  
//                  System.out.println("�¿ͻ��˽��յ���ϢΪ:"+info);  
                    if("data".equals(info)){  
                        readMsg1(socket.getInputStream());  
                    }  
                    //������Լ����͵Ĳµ���Ϣ  
                    else if("msg".equals(info)){  
                        //�ٴζ�ȡһ����Ϣ  
                        String info2=dis.readUTF();  
                        System.out.println("info2"+info2);  
                        if("yes".equals(info2)){  
                            info2=dis.readUTF();  
                            ui.jta.append(info2+"\r\n");  
                            ui.jta.append("��ϲ��¶���......");  
                            socket.close();
                            int res=JOptionPane.showConfirmDialog(null, "�Ƿ�����һ�֣�", "��̫ǿ�ˣ�", JOptionPane.YES_NO_OPTION);
                            if(res==JOptionPane.YES_OPTION){ 
                 
                            	/*SwingUtilities.invokeLater(()->{
                    			new ClientUI();
                    		});*/
                                ClientUI c=new ClientUI();
                                c.setVisible(true);
                                Thread t=new Thread(c);
                                t.start();
                                return;//�Լ���û��return����
                                
                            }else{
                                System.exit(0);
                            } 
                        }  
                        else{  
                            System.out.println("���յ�����ϢΪ��"+info2);  
                            ui.jta.append(info2+"\r\n");  
                        }  
                    }  
//                      else{  
//                      String info3=dis.readUTF();  
//                  }  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
  
    // ������Ϣ�ĺ���  
    public void sendMsg(OutputStream os, String s) throws IOException {  
  
        // ��ͻ��������Ϣ  
        //  
        // System.out.println(s);  
        byte[] bytes = s.getBytes();  
        os.write(bytes);  
        os.write(13);  
        os.write(10);  
        os.flush();  
  
    }  
  
    // ��ȡ�ͻ����������ݵĺ���  
    public String readMsg(InputStream ins) throws Exception {  
        // ��ȡ�ͻ��˵���Ϣ  
        int value = ins.read();  
        // ��ȡ���� ��ȡ���س���13�����У�10��ʱֹͣ��  
        String str = "";  
        while (value != 10) {  
            // ����رտͻ���ʱ�᷵��-1ֵ  
            if (value == -1) {  
                throw new Exception();  
            }  
            str = str + ((char) value);  
            value = ins.read();  
        }  
        str = str.trim();  
        return str;  
    }  
  
    // ������Ϣ�ĺ���  
    public void sendMsg1(OutputStream os, int x1, int y1, int x2, int y2,int color,int width) throws IOException {  
  
        DataOutputStream dos = new DataOutputStream(os);  
//      dos.writeUTF("data");  
        dos.writeInt(x1);   
        dos.writeInt(y1);  
        dos.writeInt(x2);  
        dos.writeInt(y2);  
        dos.writeInt(color);  
        dos.writeInt(width);  
        dos.flush();  
  
    }  
      
    public void readMsg1(InputStream is) throws IOException {  
  
        DataInputStream dis = new DataInputStream (is);  
        int x1=dis.readInt();  
        int y1=dis.readInt();  
        int x2=dis.readInt();  
        int y2=dis.readInt();  
        int color =dis.readInt();  
        int width=dis.readInt();  
        Color c =new Color(color);  
        BasicStroke strock = new BasicStroke(width);  
        ui.g.setColor(c);  
        ui.g.setStroke(strock);  
//      System.out.println("ui:"+ui);  
        ui.g.drawLine(x1, y1, x2, y2);  
//      System.out.println("x1:"+x1+"y1:"+y1+"x2:"+x2+"y2:"+y2);  
  
    }  
  
}  