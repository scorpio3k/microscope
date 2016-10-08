package org.scorpio.microscope.enginex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;
import java.sql.Driver;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.scorpio.microscope.enginex.common.MLogApi;
import org.scorpio.microscope.enginex.common.MLogFileImpl;
import org.scorpio.microscope.enginex.common.MLogQuery;
import org.scorpio.microscope.enginex.core.MVersion;
import org.scorpio.microscope.enginex.util.ServerInfo;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

final public class GUI extends JFrame {
    private static final long serialVersionUID = 481056515549303734L;

    private Map driverMap = new HashMap(1);

    private static boolean isDisabled;

    private JTextArea textArea;

    private JCheckBox isOutConsoleCheckBox;

    private JCheckBox isFormatSqlCheckBox;

    private JCheckBox autoClearCheckBox;

    private JCheckBox excludeSPCheckBox;

    private JCheckBox excludeN9PubMsgCheckBox;

    private JCheckBox excludeCommitMonitorCheckBox;

    private JCheckBox logFileBox;

    private JTextField logFileField;

    private JCheckBox includePackageBox;

    private JTextField includePackageField;

    private JCheckBox excludeStrBox;

    private JTextField excludeStrField;

    private JTextArea stateArea;

    private int count;

    private boolean saveFlag = false;

    private JTextArea infoArea;
    // ����ϵͳ��Ϣ���߳�
    private UpdateInfoThread updateInfoThread = new UpdateInfoThread();

    private MLogApi p6Log = null;

    public GUI() {
        super();
        this.setTitle(MVersion.getVersionTip());
        setBounds(20, 20, 1100, 650);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                minExtendedState();
            }
        });
        init();
        setVisible(true);
        minExtendedState();
    }

    public void minExtendedState() {
        this.setExtendedState(JFrame.ICONIFIED);
    }

    public void init() {
        try {
            Image image = ImageIO.read(this.getClass().getResource("/img/logo.gif"));
            this.setIconImage(image);

            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final JPanel upPanel = new JPanel();
        upPanel.setPreferredSize(new Dimension(0, 60));
        upPanel.setLayout(null);
        getContentPane().add(upPanel, BorderLayout.NORTH);

        Font font = new Font("����", Font.PLAIN, 12);
        final JButton button = new JButton();
        button.setBounds(5, 1, 73, 58);
        button.setFocusPainted(false);
        button.setText("���");
        button.setFont(font);
        upPanel.add(button);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cleanTextArea();
            }
        });
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 3) {
                    button.setText("�Ҷ��ˣ�");
                    new Timer(true).schedule(new TimerTask() {

                        public void run() {
                            button.setIcon(new ImageIcon(this.getClass().getResource("/img/egg.gif")));
                            button.setText(null);
                            new Timer(true).schedule(new TimerTask() {

                                public void run() {
                                    button.setIcon(null);
                                    button.setText("���");
                                }
                            }, 10000);
                        }
                    }, 1000);
                }
            }
        });

        isOutConsoleCheckBox = new JCheckBox();
        isOutConsoleCheckBox.setText("����̨���");
        isOutConsoleCheckBox.setBounds(80, 5, 100, 26);
        isOutConsoleCheckBox.setFont(font);
        isOutConsoleCheckBox.setSelected(false);
        upPanel.add(isOutConsoleCheckBox);

        isFormatSqlCheckBox = new JCheckBox();
        isFormatSqlCheckBox.setText("��ʽ��SQL");
        isFormatSqlCheckBox.setBounds(180, 5, 100, 26);
        isFormatSqlCheckBox.setFont(font);
        isFormatSqlCheckBox.setToolTipText("ȥ���Ʊ��");
        isFormatSqlCheckBox.setSelected(true);
        upPanel.add(isFormatSqlCheckBox);

        autoClearCheckBox = new JCheckBox();
        autoClearCheckBox.setSelected(true);
        autoClearCheckBox.setFont(font);
        autoClearCheckBox.setText("�Զ����");
        autoClearCheckBox.setToolTipText("����100000���ַ�ʱ�Զ����");
        autoClearCheckBox.setBounds(280, 5, 90, 26);
        upPanel.add(autoClearCheckBox);

        excludeSPCheckBox = new JCheckBox();
        excludeSPCheckBox.setSelected(true);
        excludeSPCheckBox.setFont(font);
        excludeSPCheckBox.setText("����SP���");
        excludeSPCheckBox.setToolTipText("����SmartPage������SQL���");
        excludeSPCheckBox.setBounds(370, 5, 100, 26);
        upPanel.add(excludeSPCheckBox);

        excludeN9PubMsgCheckBox = new JCheckBox();
        excludeN9PubMsgCheckBox.setSelected(true);
        excludeN9PubMsgCheckBox.setFont(font);
        excludeN9PubMsgCheckBox.setText("����N9��Ϣ");
        excludeN9PubMsgCheckBox.setToolTipText("����N9��JMS��Ϣ���в�����SQL���");
        excludeN9PubMsgCheckBox.setBounds(470, 5, 100, 26);
        upPanel.add(excludeN9PubMsgCheckBox);

        logFileBox = new JCheckBox();
        logFileBox.setText("������ļ�");
        logFileBox.setToolTipText("�Ƚ�Ӱ������");
        logFileBox.setFont(font);
        logFileBox.setBounds(570, 5, 100, 26);
        upPanel.add(logFileBox);
        logFileBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFlag = !saveFlag;
                try {
                    if (p6Log != null) {
                        MLogQuery.removeP6Log(p6Log);
                        p6Log = null;
                    }
                    if (saveFlag) {
                        File f = new File(logFileField.getText());
                        if (f.isDirectory() && f.exists()) {
                            p6Log = new MLogFileImpl(f);
                            MLogQuery.addP6Log(p6Log);
                        } else {
                            JOptionPane.showMessageDialog(upPanel, "ָ����Ŀ¼���Ϸ�");
                        }
                    }
                } catch (Exception e1) {
                    if (p6Log != null) {
                        MLogQuery.removeP6Log(p6Log);
                        p6Log = null;
                    }
                }
            }
        });
        logFileField = new JTextField();
        logFileField.setBounds(670, 7, 250, 22);
        logFileField.setText("C:/");
        upPanel.add(logFileField);

        excludeCommitMonitorCheckBox = new JCheckBox();
        excludeCommitMonitorCheckBox.setText("�ų������ύ���");
        excludeCommitMonitorCheckBox.setBounds(80, 30, 140, 26);
        excludeCommitMonitorCheckBox.setFont(font);
        excludeCommitMonitorCheckBox.setToolTipText("�ų�����commit�ļ����Ϣ");
        excludeCommitMonitorCheckBox.setSelected(true);
        upPanel.add(excludeCommitMonitorCheckBox);

        includePackageBox = new JCheckBox();
        includePackageBox.setText("����������");
        includePackageBox.setBounds(230, 30, 100, 26);
        includePackageBox.setFont(font);
        includePackageBox.setToolTipText("");
        includePackageBox.setSelected(false);
        includePackageBox.setToolTipText("����:com.nstc.cpm��ConfirmReceiveOrderAction��ǰ����Ҫ��ѡ�����������ࡱ��ѡ�򣬴����û�Ӱ������");
        upPanel.add(includePackageBox);

        includePackageField = new JTextField();
        includePackageField.setBounds(330, 33, 220, 22);
        includePackageField.setText("");
        upPanel.add(includePackageField);

        excludeStrBox = new JCheckBox();
        excludeStrBox.setText("�ų��ַ���");
        excludeStrBox.setBounds(570, 30, 100, 26);
        excludeStrBox.setFont(font);
        excludeStrBox.setToolTipText("�����SQL������������������ݣ����ִ�Сд���򲻽�����ʾ");
        excludeStrBox.setSelected(true);
        upPanel.add(excludeStrBox);

        excludeStrField = new JTextField();
        excludeStrField.setBounds(670, 33, 250, 22);
        excludeStrField.setText("SELECT 1 FROM DUAL");
        upPanel.add(excludeStrField);

        final JPanel mainJPlanel = new JPanel();
        mainJPlanel.setLayout(new BorderLayout());
        textArea = new JTextArea();
        textArea.setFont(new Font("����", Font.PLAIN, 13));
        mainJPlanel.add(textArea);
        getContentPane().add(mainJPlanel, BorderLayout.CENTER);
        JScrollPane textAreaJScrollPane = new JScrollPane();
        textAreaJScrollPane.setViewportView(textArea);
        mainJPlanel.add(textAreaJScrollPane);

        JPanel bottomJPlanel = new JPanel();
        bottomJPlanel.setLayout(new BorderLayout());
        stateArea = new JTextArea();
        stateArea.setFont(font);
        stateArea.setEditable(false);
        JScrollPane stateAreaJScrollPane = new JScrollPane();
        stateAreaJScrollPane.setPreferredSize(new Dimension(0, 90));
        bottomJPlanel.add(stateArea);
        stateAreaJScrollPane.setViewportView(stateArea);
        bottomJPlanel.add(stateAreaJScrollPane);
        getContentPane().add(bottomJPlanel, BorderLayout.SOUTH);

        JPanel weastJPlanel = new JPanel();
        weastJPlanel.setLayout(new BorderLayout());
        infoArea = new JTextArea();
        infoArea.setFont(font);
        infoArea.setEditable(false);
        JScrollPane infoAreaJScrollPane = new JScrollPane();
        infoAreaJScrollPane.setPreferredSize(new Dimension(290, 0));
        weastJPlanel.add(infoArea);
        infoAreaJScrollPane.setViewportView(infoArea);
        weastJPlanel.add(infoAreaJScrollPane);
        getContentPane().add(infoAreaJScrollPane, BorderLayout.WEST);
        updateInfoThread.start();
    }

    public void cleanTextArea() {
        count = 0;
        textArea.setText("");
    }

    public void addDriverInfo(String realUrl, Driver passthru,
                              java.util.Properties p1) {
        String user = p1.getProperty("user");
        String password = p1.getProperty("password");
        String key = realUrl + "_" + user + "_" + password + "_";
        if (!driverMap.containsKey(key)) {
            String path = parseJarFile(passthru.getClass().getResource(
                    getClassName(passthru.getClass())));
            String msg = "  URL:" + realUrl + "  �û�:" + user + "  ����:******  �����ļ�·��:" + path + "\r\n";
            driverMap.put(key, msg);
            stateArea.append(msg);
        }
    }

    public void appendAtTextArea(String sql1, String sql2) {
        if (isDisabled == false) {
            String sql = null;
            if (isFormatSqlCheckBox.isSelected()) {
                sql = sql1;
            } else {
                sql = sql2;
            }

            if (excludeSPCheckBox.isSelected()) {
                if (sql != null && (sql.indexOf("SMB_") > 0)) {
                    return;
                }
            }

            if (excludeN9PubMsgCheckBox.isSelected()) {
                if (sql != null && (sql.indexOf("PUB_MESSAGE_CONTENT") > 0)) {
                    return;
                }
            }

            if (excludeCommitMonitorCheckBox.isSelected()) {
                if (sql != null && (sql.indexOf(",MD=commit,") > 0)) {
                    return;
                }
            }

            if (excludeStrBox.isSelected() && excludeStrField.getText() != null && !excludeStrField.getText().trim().equals("")) {
                if (sql != null && (sql.indexOf(excludeStrField.getText()) > 0)) {
                    return;
                }
            }

            if (includePackageBox.isSelected() && includePackageField.getText() != null && !includePackageField.getText().trim().equals("")) {
                StackTraceElement[] sts = Thread.currentThread().getStackTrace();
                if (sts.length > 0) {
                    boolean ok = false;
                    for (int i = sts.length - 1; i >= 0; i--) {
                        StackTraceElement st = sts[i];
                        if (st.toString().indexOf(includePackageField.getText()) >= 0) {
                            ok = true;
                            break;
                        }
                    }
                    if (!ok) return;
                }
            }

            textArea.append(sql);
            if (isOutConsoleCheckBox.isSelected()) {
                System.out.print(sql);
            }
            count += sql.length();
            if (autoClearCheckBox.isSelected())
                autoCleanTextArea();
        }
    }

    public void autoCleanTextArea() {
        if (count > 100000)
            cleanTextArea();
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextArea getInfoArea() {
        return infoArea;
    }

    public static String getClassName(Class clazz) {
        String packageName = clazz.getPackage().getName();
        String className = clazz.getName();
        return className.substring(packageName.length() + 1) + ".class";
    }

    public static String parseJarFile(URL path) {
        String file = path == null ? "" : path.getFile();
        try {
            int idx = file.lastIndexOf("jar!");
            if (idx == -1)
                return file;
            file = file.substring(0, idx + 3);
            return file.replaceFirst("file:/", "");
        } catch (Exception e) {
            return file;
        }
    }

    public static void main(String args[]) {
        try {
            new GUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class UpdateInfoThread extends Thread {
        public void run() {
            while (true) {
                try {
                    getInfoArea().setText(
                            "������Ϣÿ30�����һ��\r\n\r\n" + ServerInfo.buildReport());
                    super.sleep(30000);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
