package application.logic;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Frame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8381491485480484482L;

	private Timer timer, wait;

	private JPanel mainPanel;

	private Deck d;
	private Player p;
	private AI bot1;
		
	private CardLayout cl;
	private boolean renderWin, renderDefeat;
	
	private JButton backToMenu;
	
	public Frame(String title) {
		super(title);
		d = new Deck(null);
		p = new Player(d,null, "me");
		bot1 = new AI(d, null,"Fred");
		
		renderWin = false;
		
		setMainPanel();
		
		backToMenu = new JButton("Go back");
		backToMenu.setVisible(false);
		backToMenu.setBounds(400, 100, 100, 50);
		backToMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.next(mainPanel);
				backToMenu.setVisible(false);
			}
			
		});
		
	//	mainPanel.add(backToMenu);
		
		setResizable(false);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		
		wait = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				p.played = false;
			}
			
		});
		wait.setRepeats(false);
		
		ActionListener update = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// for TESTING purposes
				if(Player.whatToDo.equals("skip") || Player.whatToDo.equals("draw"))
					System.out.println(Player.whatToDo);
				
				if(p.getHand().isEmpty()) {
					timer.stop();
					renderWin = true;
					cl.show(mainPanel, "2");
				}
				else if(bot1.getHand().isEmpty()) {
					timer.stop();
					renderDefeat = true;
					cl.show(mainPanel, "2");
				}
				
				p.verifyPlayableCards();
				
				//boardPanel.update();
				
				if(!p.played) {
				//	boardPanel.updateTopCard();
				}else {
					wait.start();
					
				}
				
				repaint();
		
			}
			
		};
		
		timer = new Timer(20, update);
		//timer.start();
	}
	
	private void setMainPanel() {
		mainPanel = new JPanel();
		//mainPanel.setPreferredSize(new Dimension(Reference.PWIDTH - 100, Reference.PHEIGHT));
		//mainPanel.setBounds(0, 0, Reference.PWIDTH - 100, Reference.PHEIGHT);
		
		cl = new CardLayout();
		/*cl.addLayoutComponent(menuPanel, "0");
		cl.addLayoutComponent(boardPanel, "1");
		cl.addLayoutComponent(GOPanel, "2");*/
		mainPanel.setLayout(cl);
		
		/*mainPanel.add(menuPanel);
		mainPanel.add(boardPanel);
		mainPanel.add(GOPanel);*/
		
		mainPanel.setBackground(Color.white);
		//mainPanel.setPreferredSize( new Dimension(Reference.PWIDTH, Reference.PHEIGHT));
		 
		mainPanel.setVisible(true);
		mainPanel.setFocusable(true);
		mainPanel.requestFocus();
		
		this.add(mainPanel);
	}
	
	public void startGame() {
		if(renderDefeat || renderWin) {
			renderWin = false;
			renderDefeat = false;
		
			//+ vymaz karty obema, vyresetuj obe zpravy (Player.whatToDo && AI.whatToDo)
			p.eraseHand();
			bot1.eraseHand();
			
			Player.whatToDo = "nothing";
			AI.whatToDo = "nothing";
			
			d.initDeck();
			p.createHand();
			bot1.createHand();
		}
		
		this.cl.next(mainPanel);
		this.timer.start();
	}
	
	public void toMenu() {
		cl.show(mainPanel, "0");
	}
	
	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
	}

	public boolean isRenderWin() {
		return renderWin;
	}

	public boolean isRenderDefeat() {
		return renderDefeat;
	}
	
}
