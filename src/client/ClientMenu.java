package client;  
  
import java.awt.BasicStroke;  
import java.awt.BorderLayout;  
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
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

class BackgroundPanel extends JPanel  
{  
    Image im;  
    public BackgroundPanel(Image im)  
    {  
        this.im=im;  
        this.setOpaque(true);                    //���ÿؼ���͸��,����false,��ô����͸��
    }  
    //Draw the background again,�̳���Jpanle,��Swing�ؼ���Ҫ�̳�ʵ�ֵķ���,������AWT�е�Paint()
    public void paintComponent(Graphics g)       //��ͼ��,����ɼ�������Java �� java-Graphics 
    {  
        super.paintComponents(g);  
        g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);  //����ָ��ͼ���е�ǰ���õ�ͼ��ͼ������Ͻ�λ�ڸ�ͼ������������ռ�� (x, y)��ͼ���е�͸�����ز�Ӱ��ô��Ѵ��ڵ�����

    }  
}



public class ClientMenu extends JFrame {  
  
	public class RePanel extends JPanel{
	    protected void paintComponent(Graphics g){//��дpaintComponent������ʵ��jPanel�ӱ���
	        super.paintComponent(g);                             
	        /*ImageIcon bg_icon = new ImageIcon("superman.gif");
	        Image bg_img = bg_icon.getImage();  
	        bg_img = bg_img.getScaledInstance(700, 500, Image.SCALE_DEFAULT);  
	        bg_icon.setImage(bg_img);  
	        JLabel bg_lb = new JLabel(bg_icon);    //����һ����ͼƬ�� JLabel
	        bg_lb.setSize(700,500);    //���� ͼƬ�ĺ����ꡢ�����ꡢ����*/
	        ImageIcon image=new ImageIcon(getClass().getResource("superman.gif"));        //��ȡͼ��
	        image.setImage(image.getImage().getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_FAST)); //����ͼ��ķֱ�������Ӧ����     
	        image.paintIcon(this, g,0, 0);
	    }          
	}
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
    boolean flag;
    
    public ClientMenu() {
    	this.setTitle("�㻭�Ҳ�");  
        this.setSize(700, 500);  
        this.setDefaultCloseOperation(3);  
        this.setLocationRelativeTo(null);  
    	menuPanel = new JPanel(); 
    	JPanel panel=new JPanel();
    	
    	menuPanel.setLayout(new BorderLayout());
    	startBtn = new JButton();  
    	startBtn.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseEntered(MouseEvent arg0) {
    			startBtn.setText("����");
    			startBtn.setBackground(color.orange);
    		}
    		@Override
    		public void mouseExited(MouseEvent e) {
    			startBtn.setText("��ʼ");
    			startBtn.setBackground(null);
    		}
    	});
        startBtn.setText("��ʼ");  
        
        
        exitBtn = new JButton();  
        exitBtn.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseEntered(MouseEvent arg0) {
    			exitBtn.setText("�ݰ�");
    			exitBtn.setBackground(color.green);
    		}
    		@Override
    		public void mouseExited(MouseEvent e) {
    			exitBtn.setText("�˳�");
    			exitBtn.setBackground(null);
    		}
    	});
        exitBtn.setText("�˳�");  
        
        
        helpBtn = new JButton();  
        helpBtn.setText("����");  
        
        
        panel.add(startBtn,BorderLayout.NORTH);
        panel.add(exitBtn,BorderLayout.CENTER);
        panel.add(helpBtn,BorderLayout.SOUTH);
    	panel.setOpaque(false);
    	menuPanel.add("South",panel);
    	JLabel label = new JLabel("�㻭�Ҳ�",JLabel.CENTER);  
        label.setFont(new Font("��Բ",Font.BOLD,50));
        menuPanel.add("North",label);  
        
        ImageIcon image=new ImageIcon("title.jpg");
        Image img = image.getImage();  
        img = img.getScaledInstance(500, 300, Image.SCALE_DEFAULT);  
        image.setImage(img);  
        JLabel jlb = new JLabel(image);    //����һ����ͼƬ�� JLabel
        jlb.setSize(500,300);    //���� ͼƬ�ĺ����ꡢ�����ꡢ����
        menuPanel.add("Center",jlb);    //������� JLabel ����� JPanel ����
        
       /* ImageIcon imagef=new ImageIcon("flower.jpg");
        Image imf = imagef.getImage();  
        imf = imf.getScaledInstance(100, 400, Image.SCALE_DEFAULT);  
        imagef.setImage(imf);  
        JLabel jf = new JLabel(imagef);    //����һ����ͼƬ�� JLabel
        jf.setSize(100,400);    //���� ͼƬ�ĺ����ꡢ�����ꡢ����
        menuPanel.add("East",jf);    //������� JLabel ����� JPanel ����
        menuPanel.add("West",jf);    //������� JLabel ����� JPanel ����*/
        
       // JLabel bg_label = new JLabel(image); //backgroundΪImageIcon
     // �ѱ�ǩ�Ĵ�Сλ������ΪͼƬ�պ����������� 
      //  bg_label.setBounds(0, 0, this.getWidth(), this.getHeight());
     //���ͼƬ��frame�ĵڶ���(�ѱ���ͼƬ��ӵ��ֲ㴰�����ײ���Ϊ����)
     
     //�����ݴ���ת��ΪJPanel���������÷���setOpaque()��ʹ���ݴ���͸��
     //JPanel jPanel=new JPanel();
    // jPanel.add(bg_label,Integer.MIN_VALUE);
    // this.getLayeredPane().add(jPanel);
     //����͸��
     //jPanel.setOpaque(false);
    //menuPanel.setOpaque(false);
     
        startBtn.addActionListener(al_start);  
        exitBtn.addActionListener(al_exit);
        helpBtn.addActionListener(al_help);
        
        menuPanel.setOpaque(false);
        this.getContentPane().add(menuPanel); 
        //this.setVisible(true);  
        //this.repaint();
        Image iimage = new ImageIcon("bg.jpg").getImage();// ���Ǳ���ͼƬ .png .jpg .gif �ȸ�ʽ��ͼƬ������  
        JLabel imgLabel = new aLabel(iimage);// ������ͼ����"��ǩ"�  
        this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));// ע�������ǹؼ�����������ǩ��ӵ�jfram��LayeredPane����  
        //Container cp = ;  
        ((JPanel)this.getContentPane()).setOpaque(false);  // ע����������������Ϊ͸��������LayeredPane����еı���������ʾ������  
        imgLabel.setBounds(0, 0, this.getWidth(),this.getHeight());// ���ñ�����ǩ��λ��
        
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
    ActionListener al_start =new ActionListener() {  
        
        public void actionPerformed(ActionEvent e) { 
        	
        	setVisible(false);// ����������,
        	/*SwingUtilities.invokeLater(()->{
			new ClientUI();
		});*/
            ClientUI c=new ClientUI();
            c.setVisible(true);
            Thread t=new Thread(c);
            t.start();
            dispose();//����������,�ͷ��ڴ���Դ
            
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


