package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import model.dao.UserDao;
import model.vo.User;
import music.Music;

public class OpeningPage extends JPanel{

	private MainFrame mf;
	private OpeningPage op;
	private Map m;
	private Image droh = new ImageIcon("images/dr.png").getImage().getScaledInstance(300, 500, 0);
	private Image drpo = new ImageIcon("images/poke/7F.gif").getImage();
	private int alpha = 0;
	private int alpha2 = 0;
	private int increment = 10;
	private int starton = 0;
	private Music m_vill = new Music("opening.mp3", false);
	JLabel drlabel = new JLabel(new ImageIcon(droh));
	int x = 350,y = 20;
	private int count = 1;
	private String name = "d";
	private User user;
	
	public OpeningPage(MainFrame mf, User user) {

		/*this.mf = mf;
		this.op = this;
		this.setVisible(true);
		this.setBounds(0,0,1024,768);
		this.setBackground(Color.black);*/
		this.mf = mf;
		this.op = this;
		this.user = user;

		mf.add(this);

		//�ӽ�
		
		//ud.getUserList().get(0);

		this.setVisible(true);
		this.setBounds(0,0,1024,768);
		//mf.add(this);

		m_vill.start();
		UserDao ud = new UserDao(name);
		ud.saveUser();
		String[] talkList = new String[12];
		talkList[0] = "... ... ... ... ... ... ...";
		talkList[1] = "��! �̰� ������! �ڳ� �̸��� ����?";
		talkList[2] = "�ݰ���! " + name + ".";
		talkList[3] = "���ϸ����� ���迡 �� �Դܴ�!";
		talkList[4] = "�� �̸��� ���ڻ�";
		talkList[5] = "��ο��Դ� ���ϸ� �ڻ��� �˷��� �ִܴ�.";
		talkList[6] = "... ... ... ... ... ... ...";
		talkList[7] = "�� ���迡�� ���ϸ��̶� �Ҹ��� ����ü�� ��ó�� ��� �ִ�!";
		talkList[8] = "�츮 �ΰ��� ���ϸ�� ���� ���� �ϰ� ����.";
		talkList[9] = "���� �׷� ���ϸ���� �ڼ��� �˱� ���� ������ ��� �ϰ� �ִܴ�!";
		talkList[10] = "��.. ���� ������ ������ ����? �ϴ� �� �����ҷ� ����!";
		talkList[11] = "��.. ���� ������ ������ ����? �ϴ� �� �����ҷ� ����!";

		JTextField tTf = new JTextField(40);
		tTf.setOpaque(false);
		JLabel temp = new JLabel(new ImageIcon("images/dial.png"));
		temp.setBounds(60, 535, 900, 150);
		temp.setLayout(new BorderLayout());
		tTf.setText(talkList[0]);
		tTf.setBounds(60, 535, 900, 150);
		tTf.setEditable(false);
		tTf.setHorizontalAlignment(JTextField.CENTER);
		tTf.setFont(new Font("���ü", Font.BOLD, 25));
		// tTf.setForeground(Color.white);
		// tTf.setBackground(Color.black);

		

		tTf.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}

			//��ȭâ�� Ŭ���ϸ� ��簡 �Ѿ
			@Override
			public void mouseClicked(MouseEvent e) {
				if(count < talkList.length) {

					tTf.setText(talkList[count]);
					count++;
				}
				if(count == 7) {
					/*JLabel popo = new JLabel(new ImageIcon(drpo));
					System.out.println("dd");
					popo.setBounds(40, 50, 500, 500);
					popo.setVisible(true);
					op.add(popo);
					op.setComponentZOrder(popo, 0);*/

					pokeopen();

				}
				if(count == 2) {
					
					name = JOptionPane.showInputDialog("�̸��� �Է�");
					talkList[2] = "�ݰ���! " + name + ".";
					ud.getUserList().get(0).setuName(name);
					System.out.println(ud.getUserList().get(0).getuName());
					//setName(name);
					

				}
				if(count > 11) {
					mf.remove(op);
					FadeOut fade = new FadeOut(mf);
					m_vill.stop();
					m_vill = new Music("item1.mp3", false);
					m_vill.start();
					fade.fadeout(mf,ud.getUserList().get(0));
					mf.requestFocus();
					
				}
			}
		});
		this.add(tTf);
		this.add(temp);
		this.setBackground(new Color(255, 255, 255,255));


	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void opening(MainFrame mf) {
		drlabel.setBounds(x, y, 300, 500);
		drlabel.setForeground(new Color(0,0,0,0));
		op.add(drlabel);
		Timer timer = new Timer(30, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				alpha += increment;
				if (alpha > 255) {
					//increment = -increment;
					alpha += increment;
				}
				/*if (alpha <= 0) {
					alpha = 0;
					increment = -increment;
				}*/
				try {
					drlabel.setForeground(new Color(0, 0, 0, alpha));
					drlabel.setLocation(x + alpha, y);
				}catch(Exception e1){

				}
			}
		});

		timer.start();
		if(alpha == 255) {

			timer.stop();

		}

	}

	public void pokeopen() {
		
		new Music("ggo.mp3", false).start();
		JLabel popo = new JLabel(new ImageIcon(drpo));
		popo.setBounds(130, 170, 500, 500);
		popo.setVisible(true);
		op.add(popo);
		op.setComponentZOrder(popo, 0);
		new Timer(30, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				alpha2 += increment;
				if (alpha2 > 100) {
					//increment = -increment;
					alpha2 += increment;

				}
				/*if (alpha <= 0) {
					alpha = 0;
					increment = -increment;
				}*/
				try {
					popo.setForeground(new Color(0, 0, 0, alpha));
					popo.setLocation(x + alpha2, y);
					System.out.println("ddd");
				}catch(Exception e1){

				}
			}
		}).start();
		if(alpha2 == 255) {

		//	timer2.stop();

		}
	}
}
