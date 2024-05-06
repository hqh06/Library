import com.qihang.librarymanage.utils.DatabaseUtils;
import com.qihang.librarymanage.utils.Theme;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;


public class Test extends JFrame {
    public Test() {
        new Theme(1);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2; // 计算水平位置
        int y = (screenSize.height - getHeight()) / 2; // 计算垂直位置

        setTitle("管理员界面");
        setBounds(x - 650, y - 375, 1300, 750); // 让窗口在屏幕居中
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 设置点击X结束程序
        getContentPane().setLayout(null);
        test1();
        setVisible(true);
//        JOptionPane.showMessageDialog(null, "请选择相关书籍");

    }

    public void test1(){
//        String[] options = { "Option 1", "Option 2", "Option 3" };
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setBounds(10,10,200,50);
        comboBox.addItem("111");
        comboBox.addItem("222");
        comboBox.addItem("333");
        getContentPane().add(comboBox);
    }

    public void shujuku(){
        // 创建一个DatabaseConnect 对象
        DatabaseUtils databaseUtils = new DatabaseUtils();

        // 初始化一个 Connection 对象，用于存储数据库连接
        Connection connection = null;

        try {
            // 获取数据库连接
            connection = databaseUtils.getConnection();
        } catch (Exception e) {
            // 如果获取数据库连接时出现异常，打印异常堆栈信息
            e.printStackTrace();
        } finally {
            try {
                // 在 finally 块中，无论是否出现异常，都尝试关闭数据库连接
                databaseUtils.closeConnection(connection);
            } catch (SQLException e) {
                // 如果关闭数据库连接时出现 SQLException，打印异常堆栈信息
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new Test();

    }
}
