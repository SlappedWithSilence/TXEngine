package mckeken.io;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
import java.util.HashMap;

import mckeken.main.Manager;
import mckeken.player.Player;
import mckeken.item.*;
import mckeken.room.*;

public class Load {

	// Checks for a saved game
	// TODO: Implement
	public static boolean hasSave() {
		return true;
	}

	// Loads a saved game from disk
	// TODO: Implement
	public static void loadGame() {

	}

	// Sets up and configures a new game state
	// TODO: Implement
	public static void initializeNewGame() {
		Manager.player = new Player();
	}

	// Loads items from disk
	// TODO: Implement
	// Notes:
	// - Modes: 0 generic | 1: consumable | 2: usable | 3: wearable | 4:wieldable
	//
	//
	public static HashMap<Integer, Item> loadItems() {

		HashMap<Integer, Item> itemList = new HashMap<Integer, Item>();

		try {

			DocumentBuilderFactory dBfactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dBfactory.newDocumentBuilder();
			// Fetch XML File
			Document document = builder.parse(new File("students.xml"));
			document.getDocumentElement().normalize();
			//Get root node
			Element root = document.getDocumentElement();
			System.out.println(root.getNodeName());
			//Get all students
			NodeList nList = document.getElementsByTagName("student");
			System.out.println(".................................");

			for (int i = 0; i < nList.getLength(); i++)
			{
				Node node = nList.item(i);
				System.out.println();    //Just a separator
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					//Print each student's detail
					Element element = (Element) node;

				}
			}
		} catch (IOException e) {

		} catch (ParserConfigurationException e) {

		} catch (org.xml.sax.SAXException e) {
			
		}

		return itemList;
	}

	// Loads rooms from disk
	// TODOL Implement
	public static HashMap<Integer, Room> loadRooms() {
		HashMap<Integer, Room> roomList = new HashMap<Integer, Room>();

		return roomList;
	}
}
