public class Sucess {

    public void showUI(){
        javax.swing.JFrame jf= new javax.swing.JFrame();
        jf.setTitle("登录成功界面");
        jf.setSize(300,400);
        jf.setDefaultCloseOperation(3);
        jf.setLocationRelativeTo(null);

        java.awt.FlowLayout flow=new java.awt.FlowLayout();
        jf.setLayout(flow);

        javax.swing.ImageIcon icon = new javax.swing.ImageIcon("D:\\Picture\\02.jpg");
        javax.swing.JLabel jla= new javax.swing.JLabel(icon);
        java.awt.Dimension dm0=new java.awt.Dimension(280,200);
        jla.setPreferredSize(dm0);
        jf.add(jla);

        jf.setVisible(true);
    }
}