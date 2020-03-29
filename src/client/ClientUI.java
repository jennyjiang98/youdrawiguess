package client;  
  
import java.awt.BasicStroke; 
 
import java.awt.BorderLayout;  
import java.awt.Color;  
import java.awt.Dimension;  
import java.awt.Graphics;  
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;  
import java.awt.event.MouseListener;  
import java.awt.event.MouseMotionListener;  
import java.io.IOException;  
import java.net.Socket;  
import java.net.UnknownHostException;  
  
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;  
import javax.swing.JComboBox;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JOptionPane;  
import javax.swing.JPanel;  
import javax.swing.JScrollPane;  
import javax.swing.JTextArea;  
import javax.swing.JTextField;

import client.ClientMenu.aLabel;  
  

public class ClientUI extends JFrame implements Runnable{  
  
	  
    public JButton sendBtn;
    public JButton startBtn;
    public JButton exitBtn;
    public JButton helpBtn;
    public JLabel contant;  
    public JPanel drawPanel;  
    public JPanel colorPanel;  
    public JPanel waitPanel;  
    public JPanel menuPanel;  
    public JPanel drawLeftPanel;  
    public JPanel centerPanel;  
    public JTextField jtf;  
    public JTextArea jta;  
    public Graphics2D g;  
    public Color color;  
    public ClientCtroller control;  
    public Socket socket;  
    public int x1, y1;  
    public BasicStroke strock;  
    public JComboBox<Integer> box;  
  
    
    //��ʼ�������ʱ��ʼ�����ͻ��˶���  
    public ClientUI (){  
    	this.setTitle("�㻭�Ҳ�");  
        this.setSize(700, 500);  
        this.setDefaultCloseOperation(3);  
        this.setLocationRelativeTo(null);  
        
        // �ȴ����  
        waitPanel = new JPanel();
        
        
        waitPanel.setBackground(Color.white);
        waitPanel.setLayout(null);
        JLabel label = new JLabel("����Ϊ��Ѱ�ҷ���...",JLabel.CENTER);
        label.setBounds(0, 29, 684, 29);
        label.setFont(new java.awt.Font("Dialog", 1, 20));
        label.setOpaque(false);
        waitPanel.add(label);  
        
        
        ImageIcon image=new ImageIcon("wait.gif");
        Image img = image.getImage();  
        img = img.getScaledInstance(350, 350, Image.SCALE_DEFAULT);  
        image.setImage(img);  
        JLabel jlb = new JLabel(image);
        jlb.setBounds(0, 0, 700, 500);
        jlb.setOpaque(false);
        waitPanel.add(jlb);    //������� JLabel ����� JPanel ����
        waitPanel.setOpaque(false);  
        getContentPane().add(waitPanel);  
        Image iimage = new ImageIcon("bg2.jpg").getImage();// ���Ǳ���ͼƬ .png .jpg .gif �ȸ�ʽ��ͼƬ������  
        iimage=iimage.getScaledInstance(this.getWidth(),this.getHeight(), Image.SCALE_DEFAULT); //������ϱ߻�ɫ�����
        JLabel imgLabel = new aLabel(iimage);// ������ͼ����"��ǩ"�  
        this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));// ע�������ǹؼ�����������ǩ��ӵ�jfram��LayeredPane����  
        //Container cp = ;  
        ((JPanel)this.getContentPane()).setOpaque(false);  // ע����������������Ϊ͸��������LayeredPane����еı���������ʾ������  
        imgLabel.setBounds(0, 0, this.getWidth(),this.getHeight());// ���ñ�����ǩ��λ��
        // �������  
//      addDrawPanel();  
//      addGuessPanel();  
       // this.setVisible(true);  
       /// this.repaint();//????????????
        // �����ͻ��˿���������  
        
    }  
    public class aLabel extends JLabel {  
        private Image image;  
        public aLabel(Image image){  
            this.image = image;  
        }  
        @Override  
        public void paintComponent(Graphics g){  
            super.paintComponent(g);  
            int x = this.getWidth();  
            int y = this.getHeight();  
 
            g.drawImage(image, 0, 0, x, y, null);  
        }  
    }
    public void run() {//�̱߳���һֱserverͨ�� zidongrun
        Connect();
        System.out.print("end cli thread");
    }
    public void Connect()
    {
    	try {  
            socket = new Socket("localhost", 15132);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
   
        control = new ClientCtroller(socket, this);  
        control.dealwith();  
        dispose();
    }
    //��ӻ����ĺ���  
    public void addDrawPanel() {  
        drawPanel = new JPanel();  
        drawPanel.setLayout(new BorderLayout());  
        // ���������������  
        drawLeftPanel = new JPanel();  
        drawLeftPanel.setLayout(new BorderLayout());  
          
        //��߰���м����  
        centerPanel = new JPanel();  
        centerPanel.setBackground(Color.WHITE);  
        //������µ���ɫ���  
        colorPanel = new JPanel();  
          
        //����ɫ������ÿղ���  
        colorPanel.setLayout(null);  
        colorPanel.setBackground(Color.gray);  
        colorPanel.setPreferredSize(new Dimension(0,60));  
          
        //��ɫ������ɫ��ť  
        Color [] colors={Color.red,Color.black,Color.orange,Color.green,  
                Color.pink,Color.blue,Color.cyan,Color.magenta,Color.lightGray,Color.white};  
          
        ImageIcon image = new ImageIcon("my.png");
        Image img = image.getImage();  
        img = img.getScaledInstance(30, 30, Image.SCALE_DEFAULT);  
        image.setImage(img);
        
        //��ɫ��ť���  
        ActionListener btnlistener = new ActionListener() {  
              
            public void actionPerformed(ActionEvent e) {  
                  
                JButton bt =(JButton)e.getSource();  
                if (bt.getIcon() == image)
                	color = Color.white;
                else
                	color =bt.getBackground();  
            }  
        };  
     
        for (int i = 0; i < colors.length; i++) {  
            JButton btn = new JButton();  
            if (i != colors.length - 1)
            	btn.setBackground(colors[i]);  
            else
            {
               
                btn.setIcon(image);
            }
            btn.addActionListener(btnlistener);  
            btn.setBounds(40+i*30, 15, 30, 30);  
            colorPanel.add(btn);  
        }  
          
        
        //��ӻ��ʴ�ϸ  
        box =new JComboBox<Integer>();  
        box.setBounds(380, 15, 80, 30);  
        for (int i = 0; i < 10; i++) {  
            Integer intdata = new Integer(i+1);  
            box.addItem(intdata);  
        }  
        colorPanel.add(box);  
          
        JPanel drawRightPanel = new JPanel();  
  
        drawRightPanel.setLayout(new BorderLayout());  
        drawRightPanel.setPreferredSize(new Dimension(200, 0));  
        // �����ĵ������  
        JPanel buttonPanel = new JPanel();  
        buttonPanel.setPreferredSize(new Dimension(0, 50));  
        jta = new JTextArea();  
        jta.setLineWrap(true);  
        JScrollPane jsp = new JScrollPane(jta);  
        jtf = new JTextField(11);  
        contant = new JLabel();  
        sendBtn = new JButton();  
        sendBtn.setText("����");  
        sendBtn.addActionListener(al);  
        buttonPanel.add(jtf);  
        buttonPanel.add(sendBtn);  
  
        drawRightPanel.add(jsp);  
        drawRightPanel.add(buttonPanel, BorderLayout.SOUTH);  
  
        contant.setPreferredSize(new Dimension(0, 20));  
        drawLeftPanel.add(contant, BorderLayout.NORTH);  
        drawLeftPanel.add(centerPanel, BorderLayout.CENTER);  
        drawLeftPanel.add(colorPanel, BorderLayout.SOUTH);  
        drawPanel.add(drawLeftPanel);  
        drawPanel.add(drawRightPanel, BorderLayout.EAST);  
        getContentPane().add(drawPanel);  
        centerPanel.addMouseListener(ma);  
        centerPanel.addMouseMotionListener(ma);  
        this.setVisible(true);  
        g = (Graphics2D)centerPanel.getGraphics();  
    }  
      
    //��Ӳ����ĺ���  
    public void addGuessPanel() {  
        contant.setText("�µ���ʾ��Ϣ");  
        sendBtn.setEnabled(false);  
        drawLeftPanel.remove(colorPanel);  
        drawLeftPanel.repaint();  
        this.setVisible(true);  
    }  
  
    //��������  
    MouseAdapter ma = new MouseAdapter() {  
          
        public void mousePressed(MouseEvent e) {  
            x1 = e.getX();  
            y1 = e.getY();  
        };  
          
        public void mouseEntered(MouseEvent e) {  
            if(color==null){  
                color=Color.black;  
            }  
              
//          System.out.println(i);  
            g.setColor(color);  
              
        };  
  
        public void mouseDragged(MouseEvent e) {  
            int width=(int)box.getSelectedItem();  
            strock = new BasicStroke(width);  
            g.setStroke(strock);  
              
            int x2 = e.getX();  
            int y2 = e.getY();  
            g.drawLine(x1, y1, x2, y2);  
//          x1 = x2;  
//          y1 = y2;  
            try {  
                  
                control.sendMsg1(socket.getOutputStream(), x1, y1, x2, y2,g.getColor().getRGB(),width);  
                x1 = x2;  
                y1 = y2;  
            } catch (IOException e1) {  
            }  
        };  
          
    };  
      
    //���ͼ�����  
    ActionListener al =new ActionListener() {  
          
        public void actionPerformed(ActionEvent e) {  
            //��ȡ���Ϳ������  
            String str = jtf.getText();  
            if(str==null || str.equals("")){  
                JOptionPane.showMessageDialog(null, "�������ݲ���Ϊ�գ�");  
            }else{  
                try {  
                control.dos.writeUTF(str);  
                jtf.setText("");  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
                  
        }  
    } ; 
    ActionListener al_start =new ActionListener() {  
        
        public void actionPerformed(ActionEvent e) {  
            
        }  
    };
    ActionListener al_exit =new ActionListener() {  
        
        public void actionPerformed(ActionEvent e) {  
            System.exit(0);
        }  
    };
    ActionListener al_help =new ActionListener() {  
        
        public void actionPerformed(ActionEvent e) {  
        	JOptionPane.showMessageDialog(null, "�㻭�Ҳ������", "����", JOptionPane.INFORMATION_MESSAGE);
        }  
    };
    
}  