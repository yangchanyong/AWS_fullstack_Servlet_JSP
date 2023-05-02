package com.chanyongyang.jsp.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.ToString;

public class BoxMain {
	public static void main(String[] args) {
		Box<String> box = new Box<>();
		box.push("1");
		box.push("2");
		box.push("3");
		
		System.out.println(box.pop());
		System.out.println(box.pop());
		System.out.println(box.pop());
//		
//		new Comparator<String>() {
//
//			@Override
//			public int compare(String o1, String o2) {
//				// TODO Auto-generated method stub
//				return 0;
//			}
			
//		};
//		box.setItem("abcde");
//		System.out.println(box);
//		System.out.println(box.getItem());
//		
//		Box<Fruit> fruitBox = new Box<>();
//		fruitBox.setItem(new Apple());
	}
}

@ToString
class Box<T> {
//	private T item;
//	
//	public void setItem(T item) {
//		this.item = item;
//	}
//	
//	public T getItem() {
//		return item;
//	}
//	
	private List<T> items = new ArrayList<>();
	
	public void push(T item) {
		items.add(item);
	}
	
	public T pop() {
		return items.remove(items.size()-1);
	}


	
// unshift 0번째에 추가
	public void unshift(T item) {
		items.add(0, item);
	}
	
// shift 0번째 빼기
	public T shift() {
		return items.remove(0);
	}
	
}






abstract class Fruit {}

class Apple extends Fruit {}

class Grepe extends Fruit {}