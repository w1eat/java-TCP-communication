
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Client extends JFrame implements ActionListener {
       RSAExample rsa1;
       RSAExample rsa3;
       private String F="D:\\rsa.txt";
      private String FF="D:\\rsa2.txt";


//
//    {
//        try {
//            rsa1 =Server.rsa;
//        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    //    PublicKey p1;
    Socket socket;
    //按钮
    private JButton Sender = new JButton("发送");
    private JButton Sender2 = new JButton("发送公钥");
    //显示信息区域
    private JTextArea area = new JTextArea("聊天内容：" + '\n');
    //文本输入框
    private JTextField field = new JTextField("");
    //定义返回文本函数
    public JTextField getField() {
        return field;
    }

    public Client() {
        //初始化rsa密钥
        {
            try {
                rsa1 = new RSAExample();
                rsa3=new RSAExample();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

//        try {
////         rsa1.postPublicket(FF);
////         rsa1.getPublicKey(F);
//        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        InetAddress ip = null;
        try {
//            ip = InetAddress.getByName("192.168.0.101");
            ip = InetAddress.getByName("Localhost");
            int Port = 9999;
            this.socket = new Socket(ip, Port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //*************
        this.setTitle("客户端");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        area.setEditable(false);
        area.setLineWrap(true);
//        area.setBackground(Color.red);
        this.add(area, BorderLayout.CENTER);
        this.add(field, BorderLayout.NORTH);
        field.addActionListener(this);

        this.add(Sender, BorderLayout.SOUTH);   //设置窗口布局，发送按钮在下
        field.addActionListener(this);
        this.add(Sender,BorderLayout.SOUTH);   //设置窗口布局，发送按钮在下
        Sender2.setSize(80,30);

        this.add(Sender2,BorderLayout.AFTER_LINE_ENDS);

        Sender2.addActionListener((e -> {
            try {
                post();

            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        }));
        Sender.addActionListener(this);
        this.setSize(500, 700);
//        this.setLocationRelativeTo(null);
        this.setVisible(true);
        //***********************


        rsa3.publicKey=null;
        //创造并启动线程
        thread2 tt=new thread2();
        while (true) {
            tt.run();
//            System.out.println("11");
//            System.out.println("rsa3"+rsa3.publicKey);
            if(rsa3.publicKey!=null)break;
        }
        MyThread t=new MyThread();
        while (true) {
            t.run();

        }

    }
    //发送公钥
    public void post() throws Exception {
        OutputStream os=null;
        try {
            os = socket.getOutputStream();

        } catch (IOException r) {
            throw new RuntimeException(r);
        }
        PrintStream ps = new PrintStream(os);
        ps.println(rsa1.postPublicket(FF));
//        System.out.println(rsa1.postPublicket(FF));

    }
    public static void main(String[] args) throws Exception {

        Login login=new Login();
        login.showUI();
        thread t=new thread();
        while (true) {
                t.run();
        }

    }
public void get() throws Exception {
    InputStream is = null;
    try {
        is = socket.getInputStream();
    } catch (IOException e) {
        throw new RuntimeException(e);

    }

    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String str=br.readLine();
    byte[] pub=Base64.getDecoder().decode(str);
    KeyFactory keyFactory=KeyFactory.getInstance("RSA");
    X509EncodedKeySpec spec=new X509EncodedKeySpec(pub);
    PublicKey p=keyFactory.generatePublic(spec);
    rsa3.publicKey=p;
}
    @Override
    public void actionPerformed(ActionEvent e) {
        OutputStream os = null;
        try {
            os = socket.getOutputStream();
        } catch (IOException r) {
            throw new RuntimeException(r);
        }
        PrintStream ps = new PrintStream(os);
        String s2 = this.getField().getText();
        if(s2.isEmpty()){
            System.out.println("请输入有效信息");
        }
        else {
            try {
                byte[] ss=rsa1.Encode(s2,rsa3.publicKey);
//                System.out.println(ss.toString());
//                String s=new String(ss,"UTF-8");
                String s= Base64.getEncoder().encodeToString(ss);
                ps.println(s);
                System.out.println(s);
//                byte[] s=new String(ss,"UTF-8").getBytes();
//                System.out.println(rsa.Decode(ss));
            }
            catch (Exception ex) {

            }
//            ps.printl1n(s2);
//            System.out.println(s2);
        }
    }
    class MyThread extends Thread {

        @Override
        public void run() {
//            try {
//                get();
//            }
//            catch (Exception e) {
//                throw new RuntimeException(e);
//            }
            InputStream is = null;
            try {
                is = socket.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));


            try {
//                String str = br.readLine();
//                System.out.println(str);
//                area.append("服务器说:"+str+'\n');
                String str = br.readLine();
                System.out.println(str);
                byte s[]= Base64.getDecoder().decode(str);
//                System.out.println(Arrays.toString(s));
//                System.out.println(s.toString());
//                System.out.println(new String(s,"UTF-8"));
//                System.out.println(rsa1.publicKey);
                String c=rsa1.Decode(s);
                System.out.println("解密："+c);
                area.append("客户端说:"+c+'\n');
            } catch (Exception e) {

            }

//
        }
    }
    class thread2 extends Thread {
        @Override
        public void run() {
            try {
                get();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

}
class thread extends Thread {
    @Override
    public void run() {
        System.out.println("");
        if(Server.f==true) {
            Client Cl = new Client();
        }
    }
}