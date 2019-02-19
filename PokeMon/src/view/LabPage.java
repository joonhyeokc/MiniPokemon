package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.w3c.dom.views.AbstractView;

import controller.MCManager;
import model.dao.PokemonDao;
import model.dao.SkillDao;
import model.vo.Pokemon;
import model.vo.Skill;
import model.vo.User;

public class LabPage extends JPanel {
	
	private MainFrame mf;
	private LabPage lp;
	private JPanel oldPage;
	private Map m;
	private JButton backButton = new JButton(new ImageIcon("images/maketViewImages/marketViewBack.png"));
	private PokemonDao pd = new PokemonDao();
	private SkillDao sd = new SkillDao();
	
	   
	private User user;
	private int count = 1;
	private int count2 = 0;
	private boolean select = false;
	
	
	public LabPage(MainFrame mf, JPanel oldPage, User user) {
		this.lp = this;
		this.mf = mf;
		this.m=(Map)oldPage;
		this.user = user;
		
		//���ϸ� ���� �� �ڻ� ��ȭâ
		String[] talkList = new String[4];
		talkList[0] = "�ȳ�! " + user.getuName() + ". ȯ���ϳ�.";
		talkList[1] = "���� �����ϴϱ� ���ϸ� �� ������ ���������� �ϰ�.";
		talkList[2] = "���ϸ��� �� �������ϼ�. ���ϸ��� ���� �ٽ� ���� �ɾ��ְ�.";
		talkList[3] = "(�ڻ�� ����� ��ٸ��� �ִ�.)";
		
		//���ϸ� ���� �� �ڻ� ��ȭâ
		String[] talkList2 = new String[2];
		talkList2[0] = "���� ���ϸ��� �����! ������ �� Ű���ֱ� �ٶ��.";
		talkList2[1] = "���� ���� ������ �������� ����. ���� �濡 ������ �ѷ�������.";
		
	    JTextField tTf = new JTextField(40);
	    tTf.setOpaque(false);
	    JLabel temp = new JLabel(new ImageIcon("images/dial.png"));
	    temp.setBounds(60, 535, 900, 150);
	    temp.setLayout(new BorderLayout());
	    tTf.setText(talkList[0]);
	    tTf.setBounds(60, 535, 900, 150);
	    tTf.setEditable(false);
	    tTf.setHorizontalAlignment(JTextField.CENTER);
	    tTf.setFont(new Font("����ü", Font.BOLD, 25));

	    temp.add(tTf);
	    
	    ArrayList<Skill> pSkill = (ArrayList<Skill>) sd.getsList();
	    //��Ÿ�� ���ϸ� ����
	    int pNo = 0;
	    Pokemon[] poke = new Pokemon[3];
	    for(int i = 0; i < poke.length; i++) {
	    	poke[i] = pd.getpList().get(pNo);
	    	poke[i].setpLevel(5);
	    	poke[i].setpHp(150);
	    	poke[i].setpMaxHp(150);
	    	poke[i].setpSpeed(20);
	    	poke[i].setGrade(3);
	    	poke[i].setpMaxExp(50);
	    	
	    	//���ϸ� ��ų����
	    	while(poke[i].getpSkill().size() < 5) {
	    		int random = new Random().nextInt(17)+1;
	    		int ctn = new Random().nextInt(2);
	    		
	    		if(poke[i].getpType() == sd.getsList().get(random).getsType() || sd.getsList().get(random).getsType() == 0) {
	    			if(ctn == 0) {
	    				poke[i].getpSkill().add(pSkill.get(random));
	    			}else {
	    				poke[i].getpSkill().add(pSkill.get(random));
	    			}
	    		}
	    	}
	    	pNo += 3;
	    }
	    
	    
	    //��Ÿ�� ���ϸ� �̹��� = �̻��ؾ�(1), ���̸�(4), ���α�(7)
	    ImageIcon[] pStartList = new ImageIcon[3];
	    JLabel[] pStart = new JLabel[pStartList.length];
	    int x=270;
	    int y=150;
	    int num = 1;
	    for(int i = 0; i < 3; i++) {
	    	  
	    pStartList[i] = new ImageIcon("images/poke/"+num+"F.gif");
	    pStart[i] = new JLabel(pStartList[i]);
	    pStart[i].setBounds(x, y, 175, 300);
	    x+=148;
	    num += 3;
	    this.add(pStart[i]);
	    
	    }
	    
	    pStart[0].addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int result = 1;
				if(select == false) {
					result = JOptionPane.showConfirmDialog(null, poke[0].getpName() + "�� �������ðڽ��ϱ�?", "��Ÿ�� ���ϸ�",JOptionPane.YES_NO_OPTION);
				}
				
				if(result == 0) {
					user.getUp_list().add(0, poke[0]);
					select = true;
					System.out.println(select);
				}
			}
		});
	    
	    pStart[1].addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int result = 1;
				if(select == false) {
					result = JOptionPane.showConfirmDialog(null, poke[1].getpName() + "�� �������ðڽ��ϱ�?", "��Ÿ�� ���ϸ�",JOptionPane.YES_NO_OPTION);
				}
				
				if(result == 0) {
					user.getUp_list().add(0, poke[1]);
					select = true;
					System.out.println(select);
				}
				
			}
		});
	    
	    pStart[2].addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int result = 1;
				if(select == false) {
					result = JOptionPane.showConfirmDialog(null, poke[2].getpName() + "�� �������ðڽ��ϱ�?", "��Ÿ�� ���ϸ�",JOptionPane.YES_NO_OPTION);
				}
				
				if(result == 0) {
					user.getUp_list().add(0, poke[2]);
					select = true;
					System.out.println(select);
				}
			}
		});
	      
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
				
				if(select == true) {
					if(count2 < talkList2.length) {
						tTf.remove(tTf);
						tTf.setText(talkList2[count2]);
						count2++;
					}
				}
			}
		});
		
	    backButton.setBounds(905, 270, 90, 60);
		//�ڷΰ��� ��ư
	    backButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				//Ŭ���� �̺�Ʈ
				mf.remove(lp);
				m.setVisible(true);
				mf.requestFocus();
				m.setEscCtn(0);
			}
		});
		
		this.add(backButton);
		this.add(temp);
				
		this.setBackground(Color.white);
		this.setVisible(true);
		this.setLayout(null);
		this.setBounds(0, 0, 1024, 768);
		mf.add(this);
	}

}
