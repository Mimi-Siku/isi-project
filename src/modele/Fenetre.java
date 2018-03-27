import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.naming.event.NamespaceChangeListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.omg.Messaging.SyncScopeHelper;



public class Fenetre extends JFrame {
	private JPanel container = new JPanel();
	// Bouton Ville
	String[] tab = {"New York", "Atlanta", "Chicago", "More"};
	private JComboBox combo = new JComboBox(tab);
	private JLabel label = new JLabel("Ville");
	// Bouton Age
	String[] tab2 = {"10", "20", "30", "More"};
	private JComboBox combo2 = new JComboBox(tab2);
	private JLabel label2 = new JLabel("Age");
	
	// Bouton Requete
	private JButton bouton = new JButton("Lancer Requete");

	public Fenetre(int nbBouton, String[] names){
		// Base de la Fenetre 
		this.setTitle("View");
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		container.setBackground(Color.white);
		container.setLayout(new BorderLayout());
		/* Debut version dynamique
		for(int i=0;i<nbBouton;i++) {
			
			combos.get(i).add(new JComboBox(names.));// Reparer tab2 par tab dynamique
		}
		for(JComboBox combo : combos) {
			combo.setPreferredSize(new Dimension(100, 20));
		}
		*/
		// Ajout bouton Ville
		JPanel top = new JPanel();
		top.add(label);
		top.add(combo);
		container.add(top, BorderLayout.NORTH);
		this.setContentPane(container);
		this.setVisible(true);
		
		// Ajouton Bouton Age
		JPanel top2 = new JPanel();
		top2.add(label2);
		top2.add(combo2);
		container.add(top2, BorderLayout.CENTER);
		this.setContentPane(container);
		this.setVisible(true); 
		// Ajouter Bouton Requete
		JPanel panel = new JPanel();
		panel.add(bouton);
		panel.setLayout(new FlowLayout());
		
		// Ecoute bouton Ville
	    combo.addItemListener(new ItemState());
	    combo.addActionListener(new ItemAction());
	    combo.setPreferredSize(new Dimension(100, 20));
	    combo.setForeground(Color.blue);
	    
	    // Ecoute Bouton Age
	    combo2.addItemListener(new ItemState());
	    combo2.addActionListener(new ItemAction());
	    combo2.setPreferredSize(new Dimension(100, 20));
	    combo2.setForeground(Color.green);
	    // Ecoute Bouton Requete
	    bouton.addActionListener(new ItemAction2());
	}
	 //Classe interne impl�mentant l'interface ItemListener
	  class ItemState implements ItemListener{
	    public void itemStateChanged(ItemEvent e) {
	      System.out.println("�v�nement d�clench� sur : " + e.getItem());
;	      // TODO Envoyer le résultat vers la view?
	    }               
	  }
	  class ItemAction implements ActionListener{
		 
		    public void actionPerformed(ActionEvent e ) {
		    	JComboBox combo =(JComboBox) e.getSource();
		      System.out.println("ActionListener : action sur " + combo.getSelectedItem()); // Changer combo par le bon bouton qui change
		   // TODO Envoyer le résultat vers la view ici aussi non?
		      	Fenetre.this.toString(combo.getSelectedItem());// Envoi �a � la Vue OU stock le et creer un bouton qui ordonne l'envoi
		    } 
	  }
	  class ItemAction2 implements ActionListener{
			 
		    public void actionPerformed(ActionEvent e ) {
		    	JButton button =(JButton) e.getSource();
		      System.out.println("ActionListener : action sur " + button.getActionCommand()); // Changer combo par le bon bouton qui change
		   // TODO Envoyer le résultat vers la view ici aussi non?
		      	Fenetre.this.toString(button.getActionCommand());// Envoi �a � la Vue OU stock le et creer un bouton qui ordonne l'envoi
		    } 
	  }
	  
	  /*class ItemAction2 implements ItemListener{
		  JButton button;// Pour savoir quelle bouton a agit je dois stocker le bouton
		  Integer requete;	// Boolean pour savoir si c'est le bouton de Requete qui a été activé
		  public ItemAction2(JButton button, int requete){
			  this.button=button;
			  this.requete=requete;
		  }
		    public void actionPerformed(ActionEvent e ) {
		      System.out.println("ActionListener : action sur " + combo.getSelectedItem()); // Changer combo par le bon bouton qui change
		   //Envoyer la vue la quete
		      	Fenetre.this.toString(combo.getSelectedItem());// Envoi �a � la Vue OU stock le et creer un bouton qui ordonne l'envoi
		    }
		  }*/
	  
	public void toString(Object objet) {
		// TODO Auto-generated method stub
		System.out.println(objet.toString()); 
	}
	public static void main(String[] Args)
    {
		String[] names = {"ville","age"};
        Fenetre fenetre1 = new Fenetre(2, names);
    }

}