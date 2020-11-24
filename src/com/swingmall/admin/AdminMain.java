package com.swingmall.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.swingmall.admin.board.Board;
import com.swingmall.admin.member.Member;
import com.swingmall.admin.order.Order;
import com.swingmall.admin.product.Product;
import com.swingmall.util.db.DBManager;

public class AdminMain extends JFrame{
	//��� ����
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 900;
	public static final int HOME= 0;
	public static final int PRODUCT = 1; //��ǰ����
	public static final int MEMBER = 2; //ȸ������
	public static final int ORDER = 3; //�ֹ�����
	public static final int BOARD = 4; //�Խ��ǰ���
	
	//���� �Խ���
	public static final int PRODUCT_DETAIL = 5;
	
	JPanel user_container; //������,����� ȭ���� �����������ִ� �����̳�
	JPanel p_content;//���⿡ ��� �������� �̸� �پ�����������
							//���� showPage�޼���� ������ ���� ����..
	JPanel p_navi; //������Ʈ�� �� �޴��� ������ �����̳� �г�
	
	String [] navi_title = {"Home","��ǰ����","ȸ������","�ֹ�����","�Խ��ǰ���","Log Out"};
	JButton [] navi = new JButton[navi_title.length]; //[][][][][] �迭����
	//������ ����
	Page[] page = new Page[5];
	
	
	JLabel la_footer ; //������ �ϴ��� ī�Ƕ���Ʈ ����
	private DBManager dbManager;
	private Connection con;

	

	public AdminMain() {
		dbManager = new DBManager();
		user_container = new JPanel();
		p_content = new JPanel();
		p_navi = new JPanel(); //��ư���� ���� �г�
		la_footer = new JLabel("SwingMall All rights reserved",SwingConstants.CENTER);
		
		con = dbManager.connect();
		if(con==null) {
			JOptionPane.showMessageDialog(this, "���ӺҰ�");
			System.exit(0);
		}else {
			this.setTitle("������ ����Դϴ�");
		}
		
		
		//���γ׺���̼� ����
		for (int i = 0; i < navi.length; i++) {
			navi[i] = new JButton(navi_title[i]);
			navi[i].setBackground(Color.BLACK);
			navi[i].setForeground(Color.WHITE);
			
			p_navi.add(navi[i]);
		}
		
		//������ ���� 
		page[HOME] = new Home(this);
		page[PRODUCT] = new Product(this);
		page[MEMBER] = new Member(this);
		page[ORDER] = new Order(this);
		page[BOARD] = new Board(this);
		
		
		//��Ÿ�� ����
		user_container.setPreferredSize(new Dimension(WIDTH,HEIGHT-50));
		user_container.setBackground(Color.white);
		la_footer.setPreferredSize(new Dimension(WIDTH,50));
		la_footer.setFont(new Font("Arial Black",Font.BOLD,19));
		
		//����
		user_container.setLayout(new BorderLayout());
		
		for (int i = 0; i < page.length; i++) {
			p_content.add(page[i]);
		}
		showPage(AdminMain.PRODUCT); //ó���� ���;��ϴ� ������ ����
		
		user_container.add(p_navi,BorderLayout.NORTH);
		user_container.add(p_content);//���Ϳ� ������ ����
		
		this.add(user_container);
		this.add(la_footer,BorderLayout.SOUTH);
		

		
		setSize(1200,900);
		setVisible(true);
		setLocationRelativeTo(null);
		
		//�����Ӱ� �����ʿ��� 
		this.addWindowListener(new WindowAdapter() {
			 @Override
			public void windowClosing(WindowEvent e) {
					dbManager.disConnect(con);
					System.exit(0);
			}
		});
		
		//�׺���̼ǹ�ư�� ������ ���� 
		for (int i = 0; i < navi.length; i++) {
			navi[i].addActionListener((e)->{
				Object obj = e.getSource();
				if( obj == navi[0]) {
					showPage(0);
				}else if(obj == navi[1]){
					showPage(1);
				}else if(obj == navi[2]) {
					showPage(2);
				}else if(obj == navi[3]) {
					showPage(3);
				}else if(obj == navi[4]) {
					showPage(4);
				}else if(obj ==navi[5]) {
					this.dispose();
				}
			});
		}
		
	}

	public Connection getCon() {
		return con;
	}

 

	public DBManager getDbManager() {
		return dbManager;
	}

 
	// ������ �������� �Ⱥ����� ������ �����ϴ� �޼���
	public void showPage(int showIndex) { // �Ű������� �����ְ� ���������� �ѹ� �ޱ�
		for (int i = 0; i < page.length; i++) { // ����������� �������
			if (i == showIndex) {
				page[i].setVisible(true); // ���̰� �� ������
			} else {
				page[i].setVisible(false); // �Ⱥ��̰� �� ������
			}
		}
	}
	
	
	public static void main(String[] args) {
		new AdminMain();
	}
	
}