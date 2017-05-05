import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * Created by olisa_000 on 05.05.17.
 */
public class ConnectionGui extends JFrame implements ActionListener{


    private JButton reconnectBtn;
    private JTextField ipTxt;
    private JTextArea logArea;

    ConnectionGui(){
        setLayout(new BorderLayout());

        Container c = getContentPane();

        ipTxt = new JTextField("");
        ipTxt.setEditable(false);
        c.add(ipTxt,BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEnabled(false);

        c.add( new JScrollPane(logArea),BorderLayout.CENTER);

        reconnectBtn = new JButton("reconnect");
        reconnectBtn.addActionListener(this);
        c.add(reconnectBtn,BorderLayout.SOUTH);

        setTitle("Fritz!Box reconnect");
        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        logArea.append(FritzUtils.reconnect()+"\n");
        ipTxt.setText("getting new ip");
        Runnable r = () -> {FritzUtils.getIp(ipTxt); logArea.append("new IP found!\n");};
        new Thread(r).start();
    }

}

