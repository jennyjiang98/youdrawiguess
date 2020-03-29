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
        this.setOpaque(true);                    //设置控件不透明,若是false,那么就是透明
    }  
    //Draw the background again,继承自Jpanle,是Swing控件需要继承实现的方法,而不是AWT中的Paint()
    public void paintComponent(Graphics g)       //绘图类,详情可见博主的Java 下 java-Graphics 
    {  
        super.paintComponents(g);  
        g.drawImage(im,0,0,this.getWidth(),this.getHeight(),this);  //绘制指定图像中当前可用的图像。图像的左上角位于该图形上下文坐标空间的 (x, y)。图像中的透明像素不影响该处已存在的像素

    }  
}



public class ClientMenu extends JFrame {  
  
	public class RePanel extends JPanel{
	    protected void paintComponent(Graphics g){//重写paintComponent方法以实现jPanel加背景
	        super.paintComponent(g);                             
	        /*ImageIcon bg_icon = new ImageIcon("superman.gif");
	        Image bg_img = bg_icon.getImage();  
	        bg_img = bg_img.getScaledInstance(700, 500, Image.SCALE_DEFAULT);  
	        bg_icon.setImage(bg_img);  
	        JLabel bg_lb = new JLabel(bg_icon);    //创建一个带图片的 JLabel
	        bg_lb.setSize(700,500);    //设置 图片的横坐标、纵坐标、宽、高*/
	        ImageIcon image=new ImageIcon(getClass().getResource("superman.gif"));        //获取图像
	        image.setImage(image.getImage().getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_FAST)); //调整图像的分辨率以适应容器     
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
    	this.setTitle("你画我猜");  
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
    			startBtn.setText("哈哈");
    			startBtn.setBackground(color.orange);
    		}
    		@Override
    		public void mouseExited(MouseEvent e) {
    			startBtn.setText("开始");
    			startBtn.setBackground(null);
    		}
    	});
        startBtn.setText("开始");  
        
        
        exitBtn = new JButton();  
        exitBtn.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseEntered(MouseEvent arg0) {
    			exitBtn.setText("拜拜");
    			exitBtn.setBackground(color.green);
    		}
    		@Override
    		public void mouseExited(MouseEvent e) {
    			exitBtn.setText("退出");
    			exitBtn.setBackground(null);
    		}
    	});
        exitBtn.setText("退出");  
        
        
        helpBtn = new JButton();  
        helpBtn.setText("帮助");  
        
        
        panel.add(startBtn,BorderLayout.NORTH);
        panel.add(exitBtn,BorderLayout.CENTER);
        panel.add(helpBtn,BorderLayout.SOUTH);
    	panel.setOpaque(false);
    	menuPanel.add("South",panel);
    	JLabel label = new JLabel("你画我猜",JLabel.CENTER);  
        label.setFont(new Font("幼圆",Font.BOLD,50));
        menuPanel.add("North",label);  
        
        ImageIcon image=new ImageIcon("title.jpg");
        Image img = image.getImage();  
        img = img.getScaledInstance(500, 300, Image.SCALE_DEFAULT);  
        image.setImage(img);  
        JLabel jlb = new JLabel(image);    //创建一个带图片的 JLabel
        jlb.setSize(500,300);    //设置 图片的横坐标、纵坐标、宽、高
        menuPanel.add("Center",jlb);    //放置这个 JLabel 到你的 JPanel 上面
        
       /* ImageIcon imagef=new ImageIcon("flower.jpg");
        Image imf = imagef.getImage();  
        imf = imf.getScaledInstance(100, 400, Image.SCALE_DEFAULT);  
        imagef.setImage(imf);  
        JLabel jf = new JLabel(imagef);    //创建一个带图片的 JLabel
        jf.setSize(100,400);    //设置 图片的横坐标、纵坐标、宽、高
        menuPanel.add("East",jf);    //放置这个 JLabel 到你的 JPanel 上面
        menuPanel.add("West",jf);    //放置这个 JLabel 到你的 JPanel 上面*/
        
       // JLabel bg_label = new JLabel(image); //background为ImageIcon
     // 把标签的大小位置设置为图片刚好填充整个面板 
      //  bg_label.setBounds(0, 0, this.getWidth(), this.getHeight());
     //添加图片到frame的第二层(把背景图片添加到分层窗格的最底层作为背景)
     
     //把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
     //JPanel jPanel=new JPanel();
    // jPanel.add(bg_label,Integer.MIN_VALUE);
    // this.getLayeredPane().add(jPanel);
     //设置透明
     //jPanel.setOpaque(false);
    //menuPanel.setOpaque(false);
     
        startBtn.addActionListener(al_start);  
        exitBtn.addActionListener(al_exit);
        helpBtn.addActionListener(al_help);
        
        menuPanel.setOpaque(false);
        this.getContentPane().add(menuPanel); 
        //this.setVisible(true);  
        //this.repaint();
        Image iimage = new ImageIcon("bg.jpg").getImage();// 这是背景图片 .png .jpg .gif 等格式的图片都可以  
        JLabel imgLabel = new aLabel(iimage);// 将背景图放在"标签"里。  
        this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));// 注意这里是关键，将背景标签添加到jfram的LayeredPane面板里。  
        //Container cp = ;  
        ((JPanel)this.getContentPane()).setOpaque(false);  // 注意这里，将内容面板设为透明。这样LayeredPane面板中的背景才能显示出来。  
        imgLabel.setBounds(0, 0, this.getWidth(),this.getHeight());// 设置背景标签的位置
        
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
        	
        	setVisible(false);// 本窗口隐藏,
        	/*SwingUtilities.invokeLater(()->{
			new ClientUI();
		});*/
            ClientUI c=new ClientUI();
            c.setVisible(true);
            Thread t=new Thread(c);
            t.start();
            dispose();//本窗口销毁,释放内存资源
            
        }  
    };
    ActionListener al_exit =new ActionListener() {  
        
        public void actionPerformed(ActionEvent e) {  
            System.exit(0);
        }  
    };
    ActionListener al_help =new ActionListener() {  
        
        public void actionPerformed(ActionEvent e) {  
        	JOptionPane.showMessageDialog(null, "你画我猜真好玩", "帮助", JOptionPane.INFORMATION_MESSAGE);
        }  
    };
    
}  


