package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.BattleManager;
import controller.ItemManager;
import model.dao.ItemDao;
import model.vo.Ball;
import model.vo.Item;
import model.vo.Recovery;
import model.vo.User;

public class UserInvenPage extends JPanel {
	//0217-01
	private JPanel uivp;
	private MainFrame mf;
	private BattlePage bp;
	private UserMenuPage ump;
	private User user;
	private JPanel oldPage;
	private Map m;
	private BattleManager bm = new BattleManager();
	ArrayList<Item> itemList;
	ArrayList<Item> imgList;
	ArrayList<Item> iInfo;

	private JButton backButton = new JButton(new ImageIcon(("images/userMenuImages/backButton.PNG")));
	private JButton useButton = new JButton(new ImageIcon(("images/userMenuImages/useButton.PNG")));
	private JLabel userInvenPageBackLabel = new JLabel(new ImageIcon("images/userMenuImages/userInvenPageBack.png"));
	private JLabel itemInfoLabel = new JLabel();

	private ItemDao id = new ItemDao();

	public UserInvenPage(MainFrame mf,JPanel oldPage,User user,Map m, BattleManager bm) {
		this.mf = mf;
		this.ump = ump;
		this.uivp = this;
		this.user = user;
		this.oldPage = oldPage; // JPanel�� �޾� userMenuPage, BattlePage�� ������ ����
		this.itemList= (ArrayList<Item>) user.getUi_list();
		this.imgList= (ArrayList<Item>) user.getUi_list();
		this.iInfo= (ArrayList<Item>) user.getUi_list();
		
		userInvenPageBackLabel.setBounds(0, 0, 1024, 768);

		String[] iNameList = new String[itemList.size()];
		for(int i=0 ; i<iNameList.length ; i++) {
			iNameList[i] = itemList.get(i).getiName();
		}
		String[] iAmount = new String[itemList.size()];
		for(int i=0 ; i<iNameList.length ; i++) {
			iAmount[i] = itemList.get(i).getiAmount()+"";
		}
		//������������ �̹��� ����---sm

		ImageIcon[] iImgList = new ImageIcon[imgList.size()];
		int num = 0;
		for(int i=0 ; i<iImgList.length ; i++) {
			iImgList[i] = new ImageIcon("images/itemImages/i00" + itemList.get(i).getiNo() + ".png");
			num++;
		}

		JList itemName = new JList(iNameList);
		itemName.setFont(new Font(getName(),Font.BOLD,17));
		itemName.setBounds(140, 130, 320, 480);
		itemName.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		itemInfoLabel.setBounds(580, 130, 300, 300);
		itemInfoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		JLabel itemImg = new JLabel();
		itemImg.setBounds(710, 130, 50, 50);

		String[] jta = new String[itemList.size()];
		JTextField jtaText = new JTextField(290);

		JLabel jl = new JLabel();
		jl.setBounds(585, 200, 290, 150);

		Item searchItem = null;

		String grade;
		int x=100;
		int y=100;

		for(int i=0; i<itemList.size(); i++) {
			searchItem = itemList.get(i);
			int iNum = searchItem.getiNo();
			if(searchItem.getiType() == 0) {

			}
			jta[i]=itemList.get(i).getiName() + ".\n"
					+itemList.get(i).getiInfo() + "\n";
			jl = new JLabel(jta[i]);
		}

		itemName.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				itemImg.setIcon(iImgList[itemName.getSelectedIndex()]);
			}
		});
		itemName.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				jtaText.setText(jta[itemName.getSelectedIndex()]+"");
				jtaText.setBounds(585, 200, 290, 150);
				jtaText.setEditable(false);
				jtaText.setHorizontalAlignment(JTextField.CENTER);
			}
		});
		this.add(jtaText);	    
		this.add(jl);
		this.add(itemImg);

		this.setLayout(null);
		this.setBackground(Color.WHITE);
		JLabel label = new JLabel();
		label.setText("���� ������");

		mf.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == UserMenuPage.ESC) {
					mf.remove(uivp);
					uivp.setVisible(false);
				}
			}
		});


		JList itemAmountList = new JList(iAmount);
		itemAmountList.setFont(new Font(getName(),Font.BOLD,17));
		itemAmountList.setBounds(470, 130,50,480);
		itemAmountList.setEnabled(false);
		itemAmountList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));



		useButton.setBounds(580, 460, 300, 150);
		useButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				useButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				useButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				//������ �������� ������ ������������ �Ѿ
				String userItemName = null;
				userItemName = itemName.getSelectedValue()+""; //������ ������ ����
				if(!(userItemName.equals("null"))) {
					uivp.setVisible(false);
					mf.remove(uivp);
				} else {
					JOptionPane.showMessageDialog(null, "�������� �������ּ���", "����", JOptionPane.WARNING_MESSAGE);
				}
				ItemDao iList = new ItemDao();
				Item checkItem = null;
				checkItem = new ItemManager(user).itemReturn(userItemName);



				//��Ʋ���������� ������ ����ϴ°��
				if(oldPage instanceof BattlePage) {
					if(checkItem instanceof Ball) {
						//���������
						if(!(bm.catchP(user, oldPage, m, checkItem))) {
							bm.setCtn(bm.getCtn() + 1);
							oldPage.setVisible(true);
						//�������
						} else {
							
							bm.setCtn(bm.getCtn() + 1);
							oldPage.setVisible(false);
							mf.remove(oldPage);
							m.setVisible(true);
							m.setCantmove(false);
						}
					} else if(checkItem instanceof Recovery) {
						for(int i=0; i<itemList.size(); i++) {
							//������ �����۰� �������� ���������� ��ȣ�� �޾� �޼ҵ� ����
							if(itemList.get(i).getiName().equals(userItemName)) {
								uivp.setVisible(false);
								mf.add(new PInfoPage(mf, uivp, user,userItemName)); //0217-01;
								/////������ ����� ������������ ���ƿ����� ȭ�� ������Ʈ ����
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "������ ����� �� ���� �������Դϴ�", "����", JOptionPane.WARNING_MESSAGE);
						mf.add(uivp);
						uivp.setVisible(true);
					}
					//�����޴����� �����ۻ��
				}else {
					if(checkItem instanceof Ball) {
						JOptionPane.showMessageDialog(null, "�������� ��Ʋ�߿��� ����� �� �ֽ��ϴ�", "����", JOptionPane.WARNING_MESSAGE);
						mf.add(uivp);
						uivp.setVisible(true);
					} else {
						for(int i=0; i<itemList.size(); i++) {
							//������ �����۰� �������� ���������� ��ȣ�� �޾� �޼ҵ� ����
							if(itemList.get(i).getiName().equals(userItemName)) {
								uivp.setVisible(false);
								mf.add(new PInfoPage(mf, uivp, user,userItemName)); //0217-01;
								/////������ ����� ������������ ���ƿ����� ȭ�� ������Ʈ ����
							}
						}
					}
				}
			}
		});
		this.add(useButton);

		label.setBounds(450, 20, 200, 40);
		backButton.setBounds(905, 657, 90, 60);
		//�ڷΰ��� ��ư
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				backButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				//Ŭ���� �̺�Ʈ
				mf.remove(uivp);
				oldPage.setVisible(true);
				mf.requestFocus();
			}
		});
		this.add(backButton);
		this.add(itemInfoLabel);
		this.add(itemName);
		this.add(itemAmountList);
		this.add(userInvenPageBackLabel);
		this.add(label);
	}
	public JPanel getOldPage() {
		return this.oldPage;
	}

}
