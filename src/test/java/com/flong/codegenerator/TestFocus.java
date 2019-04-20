package com.flong.codegenerator;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class TestFocus {
	public static void main(String [] argv){
		final JFrame frame = new JFrame(){
			public boolean isFocused(){//重载JFrame
				return false;
			}
		};
		frame.setSize(600, 600);
		final JButton btn1 = new JButton("a");
		final JButton btn2 = new JButton("b");
		
		btn1.addActionListener(new ActionListener(){
			//点击按钮a后，自动将焦点转移到按钮b
			public void actionPerformed(ActionEvent arg0) {
				Component c = frame.getMostRecentFocusOwner();
				System.out.println("点击前-->button b 是否获得焦点:"+btn2.equals(c));  
		    	btn2.requestFocus();
		    	c = frame.getMostRecentFocusOwner();
		  		System.out.println("点击后-->button b 是否获得焦点:"+btn2.equals(c));  
			}
			
		});
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(btn1, BorderLayout.NORTH);
		frame.getContentPane().add(btn2, BorderLayout.SOUTH);
		frame.setVisible(true);
	}

}

