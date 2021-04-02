package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import model.Card;

public class Logic {
	
	static Card[] handOne;
	static Card[] handTwo;
	static ArrayList<ArrayList<Card>> handPairs;
	static int handOneStrength;
	static int handTwoStrength;
	
	public static void main(String args[]){
		System.out.println("Hello to a simple poker program");
		System.out.println("To have a go at it write play");
		System.out.println("To quit write quit");
		try {
			consolReader();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	static void consolReader() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String userInput = reader.readLine();
		switch(userInput) {
			case "play":
			    makeHands();
				play();
				consolReader();
			case "quit":
				break;
			default: System.out.println("Unrecognized command");
				consolReader();
		}
	}
	
	static String rankText(int i) {
		switch(i) {
		case 1: return "Ace";
		case 2: return "Two";
		case 3: return "Three";
		case 4: return "Four";
		case 5: return "Five";
		case 6: return "Six";
		case 7: return "Seven";
		case 8: return "Eight";
		case 9: return "Nine";
		case 10: return "Ten";
		case 11: return "Jack";
		case 12: return "Queen";
		case 13: return "King";
		default: return "what?";
		}
	}
	
	static String suitText(int i) {
		switch(i) {
		case 1: return "Spades";
		case 2: return "Hearts";
		case 3: return "Diamonds";
		case 4: return "Clubs ";
		default: return "what?";
		}
	}
	
	static void play() {
		handPairs = new ArrayList<ArrayList<Card>>();
		Arrays.sort(handOne, (a,b) -> Integer.compare(a.rank, b.rank));
	    Arrays.sort(handTwo, (a,b) -> Integer.compare(a.rank, b.rank));
	    handOneStrength = determentHandStrength(handOne);
	    handTwoStrength = determentHandStrength(handTwo);
	    whoWins();
	}
	
	static void makeHands() {
		Random random = new Random();
		handOne =  new Card[5];
	    
		System.out.println("First Player Hand:");
	    handOne[0] = new Card(random.nextInt(13)+1, random.nextInt(4)+1);
	    System.out.println(rankText(handOne[0].rank)+" of "+suitText(handOne[0].suit));
	    handOne[1] = new Card(random.nextInt(13)+1, random.nextInt(4)+1);
	    System.out.println(rankText(handOne[1].rank)+" of "+suitText(handOne[1].suit));
	    handOne[2] = new Card(random.nextInt(13)+1, random.nextInt(4)+1);
	    System.out.println(rankText(handOne[2].rank)+" of "+suitText(handOne[2].suit));
	    handOne[3] = new Card(random.nextInt(13)+1, random.nextInt(4)+1);
	    System.out.println(rankText(handOne[3].rank)+" of "+suitText(handOne[3].suit));
	    handOne[4] = new Card(random.nextInt(13)+1, random.nextInt(4)+1);
	    System.out.println(rankText(handOne[4].rank)+" of "+suitText(handOne[4].suit));

	    handTwo = new Card[5];
	    
	    System.out.println("Second Player Hand:");
	    handTwo[0] = new Card(random.nextInt(13)+1, random.nextInt(4)+1);
	    System.out.println(rankText(handTwo[0].rank)+" of "+suitText(handTwo[0].suit));
	    handTwo[1] = new Card(random.nextInt(13)+1, random.nextInt(4)+1);
	    System.out.println(rankText(handTwo[1].rank)+" of "+suitText(handTwo[1].suit));
	    handTwo[2] = new Card(random.nextInt(13)+1, random.nextInt(4)+1);
	    System.out.println(rankText(handTwo[2].rank)+" of "+suitText(handTwo[2].suit));
	    handTwo[3] = new Card(random.nextInt(13)+1, random.nextInt(4)+1);
	    System.out.println(rankText(handTwo[3].rank)+" of "+suitText(handTwo[3].suit));
	    handTwo[4] = new Card(random.nextInt(13)+1, random.nextInt(4)+1);
	    System.out.println(rankText(handTwo[4].rank)+" of "+suitText(handTwo[4].suit));
	}
	
	static int HighCardPairsOne(int index) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<handPairs.get(0).size();i++) {
			for(int j=0;j<5;j++) {
				if(handOne[j].rank!=handPairs.get(0).get(i).rank) {
					list.add(handOne[j].rank);
				}
			}
		}
		Collections.sort(list);
		return list.get(index);
	}
	
	static int HighCardPairsTwo(int index) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=0; i<handPairs.get(1).size();i++) {
			for(int j=0;j<5;j++) {
				if(handTwo[j].rank!=handPairs.get(1).get(i).rank) {
					list.add(handTwo[j].rank);
				}
			}
		}
		Collections.sort(list);
		return list.get(index);
	}
	
	static int whoWinsHighCard(int j) {
		int result = 3;
		for(int i=0; i<j;) {
			switch(Integer.compare(HighCardPairsOne(i), HighCardPairsTwo(i))) {
			case 1: result = 1;
				i = j;
				break;
			case -1: result = 2;
				i = j;
				break;
			case 0: i++;
				break;
			}
		}
		return result;
	}
	
	static void sortAces() {
		for(int i=0; i<handPairs.get(0).size();i++) {
			if(handPairs.get(0).get(i).rank==1) {
				handPairs.get(0).get(i).rank=14;
			}
		}
		for(int i=0; i<handPairs.get(1).size();i++) {
			if(handPairs.get(1).get(i).rank==1) {
				handPairs.get(1).get(i).rank=14;
			}
		}
		sortAcesInHand(handOne);
		sortAcesInHand(handTwo);
	}
	
	static void sortAcesInHand(Card[] hand) {
		Card[] handToCheck = hand;
		for(int i=0; i<5; i++) {
			if(handToCheck[i].rank==1) {
				handToCheck[i].rank=14;
			}
		}
	}
	
	static void whoWins() {
		switch (Integer.compare(handOneStrength, handTwoStrength)) {
			case 1: System.out.println("Player One Wins With A " + hands(handOneStrength));
				break;
			case -1: System.out.println("Player Two Wins With A " + hands(handTwoStrength));
				break;
			case 0: switch(itIsATie()) {
					case 1: System.out.println("Player One Wins With A Stronger " + hands(handOneStrength));
					break;
					case 2: System.out.println("Player Two Wins With A Stronger " + hands(handTwoStrength));
					break;
					case 3: System.out.println("It Is A Tie");
					break;
				}
				break;
		}
	}
	
	static int whoWinsTwoPairs() {
		int result = 3;
		ArrayList<Integer> listOne = new ArrayList<Integer>();  
		listOne.add(handPairs.get(0).get(0).rank);  
		listOne.add(handPairs.get(0).get(1).rank);
		Collections.sort(listOne);
		ArrayList<Integer> listTwo = new ArrayList<Integer>();  
		listTwo.add(handPairs.get(1).get(0).rank);  
		listTwo.add(handPairs.get(1).get(1).rank);
		Collections.sort(listTwo);
		switch(Integer.compare(listOne.get(0), listTwo.get(0))){
		case 1: result = 1;
			break;
		case -1: result = 2;
			break;
		case 0: switch(Integer.compare(listOne.get(1), listTwo.get(1))){
			case 1: result = 1;
				break;
			case -1: result = 2;
				break;
			case 0: result = whoWinsHighCard(1);
				break;
			}
		}
		return result;
	}
	
	static int itIsATie() {
		int result = 0;
		switch(handOneStrength) {
			case 1: sortAce();
				switch(highCard()) {
					case 1: result = 1;
						break;
					case 2: result = 2;
						break;
					case 3: result = 3;
						break;
				}
				break;
			case 2: sortAces();
				switch(Integer.compare(handPairs.get(0).get(0).rank, handPairs.get(1).get(0).rank)){
				case 1: result = 1;
					break;
				case -1: result = 2;
					break;
				case 0: result = whoWinsHighCard(3);
					break;
				}
				break;
			case 3: sortAces();
				result = whoWinsTwoPairs();
				break;
			case 4: sortAces();
				switch(Integer.compare(handPairs.get(0).get(0).rank, handPairs.get(1).get(0).rank)) {
				case 1: result = 1;
					break;
				case -1: result = 2;
					break;
				case 0: result = whoWinsHighCard(2);
					break;
				}
				break;
			case 5: switch(highCard()) {
				case 1: result = 1;
					break;
				case 2: result = 2;
					break;
				case 3: result = 3;
					break;
				}
				break;
			case 6: sortAce();
				switch(highCard()) {
					case 1: result = 1;
						break;
					case 2: result = 2;
						break;
					case 3: result = 3;
						break;
				}
				break;
			case 7: sortAce();
				result = whoWinsFullHouse();
				break;
			case 8:sortAces();
				switch(Integer.compare(handPairs.get(0).get(0).rank, handPairs.get(1).get(0).rank)) {
				case 1: result = 1;
					break;
				case -1: result = 2;
					break;
				case 0: result = whoWinsHighCard(1);
					break;
				}
				break;
			case 9:switch(highCard()) {
				case 1: result = 1;
					break;
				case 2: result = 2;
					break;
				case 3: result = 3;
					break;
				}
				break;
			default: result = 3;
		}
		return result;
	}
	
	static int whoWinsFullHouse() {
		int result = 3;
		switch(checkFullHouse(0)+"-"+checkFullHouse(1)) {
			case "true-true": switch(Integer.compare(handPairs.get(0).get(1).rank, handPairs.get(1).get(1).rank)) {
				case 1: result = 1;
					break;
				case -1: result = 2;
					break;
				case 0:switch(Integer.compare(handPairs.get(0).get(0).rank, handPairs.get(1).get(0).rank)) {
					case 1: result = 1;
						break;
					case -1: result = 2;
						break;
					case 0: break;
					}
				}
				break;
			case "true-false": switch(Integer.compare(handPairs.get(0).get(1).rank, handPairs.get(1).get(0).rank)) {
				case 1: result = 1;
					break;
				case -1: result = 2;
					break;
				case 0:switch(Integer.compare(handPairs.get(0).get(0).rank, handPairs.get(1).get(3).rank)) {
					case 1: result = 1;
						break;
					case -1: result = 2;
						break;
					case 0: break;
					}
				}
				break;
			case "false-true": switch(Integer.compare(handPairs.get(0).get(0).rank, handPairs.get(1).get(1).rank)) {
				case 1: result = 1;
					break;
				case -1: result = 2;
					break;
				case 0:switch(Integer.compare(handPairs.get(0).get(3).rank, handPairs.get(1).get(0).rank)) {
					case 1: result = 1;
						break;
					case -1: result = 2;
						break;
					case 0: break;
					}
				}
				break;
			default: switch(Integer.compare(handPairs.get(0).get(0).rank, handPairs.get(1).get(0).rank)) {
				case 1: result = 1;
					break;
				case -1:  result = 2;
					break;
				case 0:switch(Integer.compare(handPairs.get(0).get(3).rank, handPairs.get(1).get(3).rank)) {
					case 1: result = 1;
						break;
					case -1: result = 2;
						break;
					case 0: break;
					}
				}
				break;
		}
		return result;
	}
	
	static boolean checkFullHouse(int hand) {
		if(handPairs.get(hand).get(0)!=handPairs.get(hand).get(1)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	static void sortAce() {
		if(handOne[0].rank==1) {
			handOne[0].rank=14;
			Card temp = handOne[4];
			handOne[4] = handOne[0];
			handOne[0] = temp;
		}
		if(handTwo[0].rank==1) {
			handTwo[0].rank=14;
			Card temp = handTwo[4];
			handTwo[4] = handTwo[0];
			handTwo[0] = temp;
		}
	}
	
	static int highCard() {
		int result=0;
		for(int i=4;i>-1;) {
			switch(Integer.compare(handOne[i].rank, handTwo[i].rank)) {
				case 1: result = 1;
					i=-1;
					break;
				case -1: result = 2;
					i=-1;
					break;
				case 0: if(i!=0) {
						i--;
					}
					else {
						result = 3;
					}
					break;
			}
		}
		return result;
	}
	
	static String hands(int strength) {
		switch (strength) {
			case 1: return "High Card";
			case 2: return "Pair";
			case 3: return "Two Pair";
			case 4: return "Three Of The Kind";
			case 5: return "Straight";
			case 6: return "Flush";
			case 7: return "Full House";
			case 8: return "Four Of The Kind";
			case 9: return  "Straight Flush";
			default: return "Royal Flush";
		}
	}
	
	static int determentHandStrength(Card[] hand) {
		Card[] handToCheck = hand;
		switch (checkSuit(handToCheck)+"-"+checkStraight(handToCheck)) {
			case "true-true": if(handToCheck[4].rank==13) {
					return 10;
				}
				else {
					return 11;
				}
			case "true-false": return 6;
			case "false-true": return 5;
			default: return checkPairs(handToCheck);
		}
	}
	
	static boolean checkSuit(Card[] hand) {
		Card[] handToCheck = hand;
		if(handToCheck[0].suit==handToCheck[1].suit & handToCheck[0].suit==handToCheck[2].suit & handToCheck[0].suit==handToCheck[3].suit & handToCheck[0].suit==handToCheck[4].suit) {
			return true;
		}
		else {
			return false;
		}
	}
	
	static int checkPairs(Card[] hand) {
		Card[] handToCheck = hand;
		ArrayList<Card> cardPairs = new ArrayList<Card>();
		int pairs = 0;
		for(int i=0;i<4;i++){
			for(int j=4;j>i;j--){
				if(Integer.compare(handToCheck[i].rank, handToCheck[j].rank)==0) {
					cardPairs.add(handToCheck[i]);
					pairs++;
				}
			}
		}
		handPairs.add(cardPairs);
		switch(pairs){
			case 1: return 2;
			case 2: return 3;
			case 3: return 4;
			case 4: return 7;
			case 6: return 8;
			default: return 1;
		}
	}
	
	static Boolean checkStraight(Card[] hand) {
		Card[] handToCheck = hand;
		if((handToCheck[0].rank+1==handToCheck[1].rank && handToCheck[1].rank+1==handToCheck[2].rank && handToCheck[2].rank+1==handToCheck[3].rank && handToCheck[3].rank+1==handToCheck[4].rank)||(handToCheck[0].rank==1 && handToCheck[1].rank==10 && handToCheck[2].rank==11 && handToCheck[3].rank==12 && handToCheck[4].rank==13)) {
			return true;
		}
		else {
			return false;
		}
	}
}