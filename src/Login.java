import javax.swing.*;
public class Login {

//    public static void main(String[] args){
//
//        Login lo=new Login();
//        lo.showUI();
//
//    }
    public void showUI(){
        javax.swing.JFrame jf= new javax.swing.JFrame();
        jf.setTitle("登录界面");
        jf.setSize(300,400);
        jf.setDefaultCloseOperation(3);
        jf.setLocationRelativeTo(null);

        java.awt.FlowLayout flow=new java.awt.FlowLayout();
        jf.setLayout(flow);

        javax.swing.ImageIcon icon = new javax.swing.ImageIcon("UI.jpg");
        javax.swing.JLabel jla= new javax.swing.JLabel(icon);
        java.awt.Dimension dm0=new java.awt.Dimension(280,200);
        jla.setPreferredSize(dm0);
        jf.add(jla);

        javax.swing.JTextField jtf=new javax.swing.JTextField();
        java.awt.Dimension dm1=new java.awt.Dimension(280,30);
        jtf.setPreferredSize(dm1);
        jf.add(jtf);

        javax.swing.JTextField jtf2=new javax.swing.JTextField();
        java.awt.Dimension dm2=new java.awt.Dimension(280,30);
        jtf2.setPreferredSize(dm2);
        jf.add(jtf2);

        javax.swing.JCheckBox jcb3 = new javax.swing.JCheckBox("忘记密码");
        jf.add(jcb3);

        javax.swing.JCheckBox jcb4 = new javax.swing.JCheckBox("记住密码");
        jf.add(jcb4);

        javax.swing.JButton jbu = new javax.swing.JButton("登陆");
        java.awt.Dimension dm3=new java.awt.Dimension(80,30);
        jbu.setPreferredSize(dm3);
        jf.add(jbu);
        //给按钮添加动作监听器方法
        //创建一个监听器
        jbu.addActionListener((e -> {
            String name = jtf.getText();
            String password=jtf2.getText();
            //验证账号和密码，如果正确显示登陆成功的界面
            //账号123对应密码789
            if("admin".equals(name)&&"123456".equals(password)){
                //

//                 jf.setVisible(false);
                Server.f=true;
                jf.setVisible(false);
                System.out.println(Server.f);
                JOptionPane.showMessageDialog(null, "登陆成功", "Title",JOptionPane.PLAIN_MESSAGE);

                //新的界面（跟旧的差不多后面有代码）

            }else{
                //登录失败界面写这里
                JOptionPane.showMessageDialog(null, "用户名或密码错误", "Title",JOptionPane.WARNING_MESSAGE);

            }
        }));
        //把监听器加在“登录”按钮上

        jf.setVisible(true);


    }
}