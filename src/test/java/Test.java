import javax.swing.JFrame;


public class Test extends JFrame {
    public Test() throws Exception {

    }

    public static void main(String[] args) {
        try{
            new Test();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
