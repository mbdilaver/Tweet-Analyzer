import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JDesktopPane;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.SpringLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyGUI extends JFrame {
	private JTextField textField;
	private TweetAnalyzer twaa;
	private String prediction;
	public MyGUI() {
		TweetAnalyzer twa = new TweetAnalyzer();
		this.prediction = null;
		setResizable(false);
		getContentPane().setBackground(new Color(153, 153, 204));
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		textField = new JTextField();
		textField.setFont(new Font("Calibri", Font.PLAIN, 12));
		springLayout.putConstraint(SpringLayout.SOUTH, textField, -91, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(textField);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setColumns(10);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, textField);
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -6, SpringLayout.NORTH, textField);
		springLayout.putConstraint(SpringLayout.EAST, panel, 0, SpringLayout.EAST, textField);
		panel.setBackground(new Color(153, 153, 204));
		getContentPane().add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel label = new JLabel("Enter your tweet text");
		panel.add(label);
		springLayout.putConstraint(SpringLayout.NORTH, label, 56, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, label, 79, SpringLayout.WEST, getContentPane());
		label.setForeground(new Color(255, 255, 255));
		label.setFont(new Font("Calibri", Font.PLAIN, 18));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		springLayout.putConstraint(SpringLayout.SOUTH, label, -20, SpringLayout.NORTH, textField);
		
		JPanel panel_1 = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel_1, 22, SpringLayout.SOUTH, textField);
		panel_1.setBackground(new Color(153, 153, 204));
		getContentPane().add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton submitButton = new JButton("Submit");
		panel_1.add(submitButton);
		springLayout.putConstraint(SpringLayout.NORTH, submitButton, -201, SpringLayout.SOUTH, getContentPane());
		submitButton.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				String button_text = submitButton.getText();
				if (button_text.equals("Submit")) {
					String text = textField.getText();
					Tweet tweet = new Tweet(text);
					prediction = twa.evaluateTestOnTweet("model-lemma-first-naive.model", tweet);
					
					if (prediction.equals("pos")) {
						label.setText("This is a positive tweet");
					}
					else if (prediction.equals("neg")) {
						label.setText("This is a negative tweet");
					}
					submitButton.setText("Again");
					textField.setEnabled(false);
				}
				else if (button_text.equals("Again")) {
					label.setText("Enter your tweet text");
					textField.setText("");
					submitButton.setText("Submit");
					textField.setEnabled(true);
				}
				
			}
		});
		submitButton.setForeground(new Color(153, 153, 204));
		submitButton.setBackground(new Color(255, 255, 255));
		submitButton.setFont(new Font("Calibri", Font.PLAIN, 18));
		springLayout.putConstraint(SpringLayout.WEST, submitButton, 0, SpringLayout.WEST, textField);
		springLayout.putConstraint(SpringLayout.SOUTH, submitButton, -9, SpringLayout.NORTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, panel_1, 0, SpringLayout.WEST, submitButton);
		springLayout.putConstraint(SpringLayout.EAST, panel_1, 294, SpringLayout.WEST, submitButton);
		
		this.setSize(320, 240);
		this.setVisible(true);
		
	}
}
