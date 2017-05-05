import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by olisa_000 on 05.05.17.
 */
public class ConnectionGui extends JFrame implements ActionListener{


    private JButton reconnectBtn;
    private JTextField ipTxt;
    private JTextArea logArea;

    public ConnectionGui(){
        setLayout(new BorderLayout());

        Container c = getContentPane();

        ipTxt = new JTextField("aktuelle ip:");
        ipTxt.setEditable(false);
        c.add(ipTxt,BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEnabled(false);

        c.add( new JScrollPane(logArea),BorderLayout.CENTER);

        reconnectBtn = new JButton("reconnect");
        reconnectBtn.addActionListener(this);
        c.add(reconnectBtn,BorderLayout.SOUTH);

        setTitle("Olis reconnect");
        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logArea.append(FritzUtils.reconnect()+"\n");
        ipTxt.setText("getting new ip");
        Runnable r = () -> {new FritzUtils().getIp(ipTxt); logArea.append("new IP found!\n");};
        new Thread(r).start();
    }

}

