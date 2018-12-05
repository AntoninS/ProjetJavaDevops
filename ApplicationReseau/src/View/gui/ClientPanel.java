package View.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;

public class ClientPanel extends Parent{
	
	//saisie de texte
	TextArea textToSend;
	
	//affichage message re�u
	ScrollPane scrollReceivedText;
	TextFlow receivedText;
	
	//envoyerMessage
	Button sendBtn;
	
	//effacer zone de saisie
	Button clearBtn;
	
	public ClientPanel()
	{
		this.textToSend = new TextArea();
		this.scrollReceivedText = new ScrollPane();
		this.receivedText = new TextFlow();
		this.sendBtn = new Button();
		this.clearBtn = new Button();
		
		this.getChildren().add(scrollReceivedText);
		this.scrollReceivedText.setLayoutX(50);
		this.scrollReceivedText.setLayoutY(50);
		this.scrollReceivedText.setPrefWidth(400);
		this.scrollReceivedText.setPrefHeight(350);
		this.scrollReceivedText.setContent(receivedText);
		this.scrollReceivedText.vvalueProperty().bind(receivedText.heightProperty());
		
		this.getChildren().add(textToSend);
		this.textToSend.setLayoutX(50);
		this.textToSend.setLayoutY(450);
		this.textToSend.setPrefWidth(350);
		this.textToSend.setPrefHeight(150);
		this.textToSend.setVisible(true);
		
		this.getChildren().add(sendBtn);
		this.sendBtn.setLayoutX(415);
		this.sendBtn.setLayoutY(450);
		this.sendBtn.setPrefHeight(30);
		this.sendBtn.setPrefWidth(75);
		this.sendBtn.setText("Send");
		this.sendBtn.setVisible(true);
		this.clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				//TODO
			}
		});
		
		this.getChildren().add(clearBtn);
		this.clearBtn.setLayoutX(415);
		this.clearBtn.setLayoutY(570);
		this.clearBtn.setPrefHeight(30);
		this.clearBtn.setPrefWidth(75);
		this.clearBtn.setText("Clear");
		this.clearBtn.setVisible(true);
		this.clearBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				textToSend.clear();
			}
		});
		
		
	}
	
	

}
