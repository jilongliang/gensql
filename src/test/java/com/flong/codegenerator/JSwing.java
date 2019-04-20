package com.flong.codegenerator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/***
 *@Author:liangjilong
 *@Date:2015年12月10日上午11:44:55
 *@Email:jilongliang@sina.com
 *@Version:1.0
 *@CopyRight(c)Flong jlongliang.
 *@Description:http://git.oschina.net/jilongliang/java-swing
 */
public class JSwing extends JFrame{
 
	
	public static void window(){
		final JFrame _this=new JFrame();

		final JButton btn1 = new JButton("OK");
		_this.getContentPane().add(btn1, BorderLayout.NORTH);
		_this.getContentPane().setLayout(new BorderLayout());
		
	
		
		
		_this.setTitle("下拉列表框使用");
		_this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_this.setBounds(100,100,250,100);
	    JPanel contentPane=new JPanel();
	    contentPane.setBorder(new EmptyBorder(5,5,5,5));
	    _this.setContentPane(contentPane);
	    contentPane.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
	    JLabel label=new JLabel("MySQL所有数据库:");
	    contentPane.add(label);
	    final JComboBox comboBox=new JComboBox();
	    
	    List<String> dataNames = DBHelperUtils.getDataNames();
	    
	    for(String db:dataNames){
	  	    comboBox.addItem(db);
	    }
	    
	    
	    contentPane.add(comboBox);
	    
	    comboBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				 int value =  e.getStateChange();
				 if(1==value){
					 System.out.println(">>>>>>>>>"+ comboBox.getName());
				 }
			}
	    	
	    });
	 
	    
	    _this.setVisible(true);
	  
	}
	
	public static void main(String[] args) {
		window();
	}
}
