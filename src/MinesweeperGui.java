import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class MinesweeperGui extends JFrame implements SwingConstants, ActionListener {

	private JPanel contentPane;
	private JPanel gamePanel;
	
	private boolean[][] flagged;
	private boolean[][] revealed;
	private final String TITLE = "Minesweeper";
	private int rows = 20;
	private int columns = rows;
	private int numBombs = 25;
	
	Grid gameGrid = new Grid(rows, columns, numBombs);
	Font font = new Font("SansSerif", Font.BOLD, 20);
	Font bombFont = new Font("SansSerif", Font.PLAIN, 10);


	/**
	 * Create the frame.
	 */
	public MinesweeperGui() {
		setResizable(false);
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 1000, 1000);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(34, 139, 34));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		gamePanel = new JPanel();
		gamePanel.setBackground(new Color(205, 133, 63));
		gamePanel.setBounds(45, 35, 900, 900);
		contentPane.add(gamePanel);
		gamePanel.setLayout(new GridLayout(rows, columns, 0, 0));
		
		JLabel gameInfo1 = new JLabel(rows + " x " + columns);
		gameInfo1.setHorizontalAlignment(SwingConstants.CENTER);
		gameInfo1.setFont(new Font("SansSerif", Font.BOLD, 15));
		gameInfo1.setBounds(45, 11, 70, 25);
		contentPane.add(gameInfo1);
		
		JLabel gameInfo2 = new JLabel("Number Of Bombs: " + numBombs);
		gameInfo2.setHorizontalAlignment(SwingConstants.CENTER);
		gameInfo2.setForeground(new Color(248, 248, 255));
		gameInfo2.setFont(new Font("SansSerif", Font.BOLD, 15));
		gameInfo2.setBounds(786, 11, 159, 25);
		contentPane.add(gameInfo2);
	}
	
	public void newGame(int rows, int columns, int numBombs) {
		gamePanel.removeAll();
        gamePanel.revalidate();
		gamePanel.repaint();
		gameGrid = new Grid(rows, columns, numBombs);
		init();
	}
	
	public void init() {
		flagged = new boolean[rows][columns];
		revealed = new boolean[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				flagged[i][j] = false;
				revealed[i][j] = false;
			}
		}
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				gamePanel.add((createLabel(i, j)));
			}
		}
	}
	
	private JPanel createLabel(int row, int column) {
		JPanel cell = new JPanel();
		JLabel cellLabel = new JLabel();
		int bombCount = gameGrid.getCountGrid()[row][column];
		boolean isBomb = gameGrid.isBombAtLocation(row, column);
		cellLabel.setHorizontalAlignment(CENTER);
		cellLabel.setFont(font);
		
		if (!isBomb) {
			cellLabel.setText(String.valueOf(bombCount));
			
			if (bombCount == 1) {
				cellLabel.setForeground(Color.white);
			}
			else if (bombCount == 2) {
				cellLabel.setForeground(Color.yellow);
			}
			else if (bombCount == 3) {
				cellLabel.setForeground(Color.orange);
			}
			else if (bombCount == 4) {
				cellLabel.setForeground(Color.red);
			}
			else if (bombCount == 5) {
				cellLabel.setForeground(Color.magenta);
			}
			else if (bombCount == 6) {
				cellLabel.setForeground(Color.blue);
			}
			else if (bombCount == 0) {
				cellLabel.setText("");
			}
		}
		else {
			// BOMB LABEL SETUP
			cellLabel.setText("TNT");
			cellLabel.setFont(bombFont);
			cellLabel.setForeground(Color.black);
			cellLabel.setBackground(Color.red);
			cellLabel.setOpaque(true);
		}
		Border blackline = BorderFactory.createLineBorder(new Color(181, 119, 58));
		cell.setLayout(new BorderLayout());
		cell.setBorder(blackline);
		cellLabel.setLayout(new BorderLayout());
		cellLabel.add(createButton(row, column));
		cell.setOpaque(false);
		cell.add(cellLabel);
		
		return cell;
	}
	
	private JButton createButton(int row, int column) {
		JButton cellButton = new JButton();
		cellButton.setBackground(new Color(34, 139, 34));
		cellButton.setActionCommand(row + "," + column);
		cellButton.setToolTipText(row + "," + column);
		cellButton.addActionListener(this);
		
		// FLAG SYSTEM
		cellButton.addMouseListener(new MouseAdapter(){
            boolean pressed;
            int x = Integer.parseInt(cellButton.getToolTipText().split(",")[0]);
			int y = Integer.parseInt(cellButton.getToolTipText().split(",")[1]);

            

            @Override
            public void mouseReleased(MouseEvent e) {
            	cellButton.getModel().setArmed(false);
            	pressed = true;

                if (pressed) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                    	if (flagged[x][y] == false) {
	                    	cellButton.setText("F");
	                    	cellButton.setForeground(Color.red);
	                    	flagged[x][y] = true;
	                    	pressed = false;
                    	}
                    	else {
                    		cellButton.setText("");
                    		flagged[x][y] = false;
                    		pressed = false;
                    	}
                    }
                }
            }               
        });
		
		return cellButton;
	}
	
	private void endGame(int win) {
		Component[] compList = gamePanel.getComponents();
		
		for (Component c : compList) {
			JPanel p = (JPanel)c;
			JLabel l = (JLabel)p.getComponent(0);
			l.getComponent(0).setVisible(false);
			l.getComponent(0).setEnabled(false);
		}
		gamePanel.revalidate();
		gamePanel.repaint();
		
		int result;
		
		if (win == 0) {
			result = JOptionPane.showConfirmDialog(contentPane, "YOU LOSE. Do you want to play again?", TITLE, JOptionPane.YES_NO_OPTION);
		}
		else {
			result = JOptionPane.showConfirmDialog(contentPane, "YOU WIN. Do you want to play again?", TITLE, JOptionPane.YES_NO_OPTION);
		}
		
		switch (result) {
        case JOptionPane.YES_OPTION:
        System.out.println("Yes");
        newGame(rows, columns, numBombs);
        break;
        case JOptionPane.NO_OPTION:
        System.out.println("No");
        System.exit(0);
        break;
        case JOptionPane.CLOSED_OPTION:
        System.out.println("Closed");
        System.exit(0);
        break;
     }
	}
	
	private void checkAdjCells(int row, int column) {
		for (int i = row - 1; i < row + 2; i++){ // Check cell top and bottom
	        if(i < 0 || i >= gameGrid.getCountGrid().length) continue; // Check if cell is out of bounds
		        if (!gameGrid.isBombAtLocation(i, column)) { // If no bomb reveal
		        	if (revealed[i][column] == false) {
		        		reveal(i, column);
		        	}
		        }
		}
		
		for (int i = column - 1; i < column + 2; i++){ // Check cell left and right
	        if(i < 0 || i >= gameGrid.getCountGrid().length) continue; // Check if cell is out of bounds
		        if (!gameGrid.isBombAtLocation(row, i)) { // If no bomb reveal
		        	if (revealed[row][i] == false) {
		        		reveal(row, i);
		        	}
		        }
		}
	}
	        
	
	private void reveal(int row, int column) {
		JButton button = null;
		Component[] compList = gamePanel.getComponents();
		for (Component c : compList) {
			JPanel p = (JPanel)c;
			JLabel l = (JLabel)p.getComponent(0);
			button = (JButton)l.getComponent(0);
			int x = Integer.parseInt(button.getToolTipText().split(",")[0]);
			int y = Integer.parseInt(button.getToolTipText().split(",")[1]);
			if (x == row && y == column) {
				break;
			}
		}
		button.setVisible(false);
		button.setEnabled(false);
		revealed[row][column] = true;
		gamePanel.revalidate();
		gamePanel.repaint();
		
		//Check if zero
		if (gameGrid.getCountAtLocation(row, column) == 0) {
			checkAdjCells(row, column);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String cmd = event.getActionCommand();
		int row = Integer.parseInt(cmd.split(",")[0]);
		int column = Integer.parseInt(cmd.split(",")[1]);
			//Check for Bomb
			if (gameGrid.isBombAtLocation(row, column)) {
				endGame(0);
			}
			else {
				reveal(row, column);
			}
			
			// CHECK IF ALL LABELS ARE REVEALED
			int revealedCount = 0;
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					if (revealed[i][j] == true) {
						revealedCount++;
						if (revealedCount == ((rows * columns) - numBombs)) {
							endGame(1);
						}
					}
				}
			}
		}
}
