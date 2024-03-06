

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class Server extends JFrame implements ActionListener {
    RSAExample rsa;
    RSAExample rsa2;
    PublicKey p;
    private String F="D:\\rsa.txt";
    private String FF="D:\\rsa2.txt";



    public static boolean f=false;
     Socket socket;
    //定义按钮
    private JButton Sender = new JButton("发送");
    private JButton Sender2 = new JButton("发送公钥");
    //文本显示区域
    private JTextArea area = new JTextArea("聊天内容："+'\n');
    //发送文本区域
    private JTextField field = new JTextField("");
    private int Port = 9999;//端口
    private ServerSocket SS;
    public JTextField getField() {
        return field;
    }
    public Server(){
        //初始化rsa密钥
        try {
                rsa = new RSAExample();
                rsa2=new RSAExample();
            }
        catch (Exception e) {
                throw new RuntimeException(e);
        }
        //将公钥加密上传到F文件
//        try {
//            rsa.postPublicket(F);
//        }
//        catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        rsa2=rsa;

//端口初始化
        try{

            SS = new ServerSocket(Port);

            socket = SS.accept();

            String ipAddress = socket.getInetAddress().getHostAddress();

            System.out.println("客户端" + ipAddress + "连接");

        }catch(Exception ex){

        }
        //以下是界面设计****
        //*****************
        this.setTitle("服务器");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        area.setEditable(false);
        area.setLineWrap(true);
//      area.setBackground(Color.red);
        this.add(area, BorderLayout.CENTER);
        this.add(field,BorderLayout.NORTH);
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
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        //-*********************
//        thread2 thread2=new thread2();
//        while (true) {
//            thread2.run();
//            if(Server.f) {
//                break;
//            }
//        }

        //线程读取信息
        rsa2.publicKey=null;
        thread2 tt=new thread2();
        while (true) {
            tt.run();
//            System.out.println(11);
//            System.out.println("rsa2"+rsa2.publicKey);
            if(rsa2.publicKey!=null)break;
        }
        MyThread t=new MyThread();
        while (true) {
            t.run();
        }

    }

    public static void main(String[] args) throws Exception {

        Server Se=new Server();

    }
    //公钥
    public void post() throws Exception {
        OutputStream os=null;
        try {
            os = socket.getOutputStream();

        } catch (IOException r) {
            throw new RuntimeException(r);
        }
        PrintStream ps = new PrintStream(os);

        ps.println(rsa.postPublicket(F));

    }
//重写actionPerformed方法发送信息
    @Override
    public void actionPerformed(ActionEvent e) {

        String s2 = this.getField().getText();
        OutputStream os=null;
        try {
              os = socket.getOutputStream();

        } catch (IOException r) {
            throw new RuntimeException(r);
        }
        PrintStream ps = new PrintStream(os);
        //判断是否为空信息
        if(s2.isEmpty()){
            System.out.println("请输入有效信息");
        }
        else {
            try {
                byte[] ss=rsa.Encode(s2,rsa2.publicKey);
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
//             ps.println(s2);
//             System.out.println(s2);

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
//        System.out.println(str);
        byte[] pub=Base64.getDecoder().decode(str);
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec=new X509EncodedKeySpec(pub);
        PublicKey p=keyFactory.generatePublic(spec);
        System.out.println("p"+p);
        rsa2.publicKey=p;
    }

        //重写run方法接受数据并显示
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
                //接受数据并解密
                String str = br.readLine();
                byte s[]= Base64.getDecoder().decode(str);
                String c=rsa.Decode(s);
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
