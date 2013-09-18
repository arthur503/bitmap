package com.arthur.delicious.bitmap;


public class Bitmap {
	
	private int capacity = 0;
	private int BIT_PER_INT = 32;
	private int[] bitmap;
	
	public static void main(String[] argv){

		Bitmap bm = new Bitmap(100);
		System.out.println("get:"+bm.getBitValue(10));
		System.out.println("add:"+bm.addBitValue(10));
		System.out.println("get:"+bm.getBitValue(10));
		System.out.println("add:"+bm.addBitValue(10));
		System.out.println("get:"+bm.getBitValue(10));
		System.out.println("add:"+bm.addBitValue(10));
		System.out.println("get:"+bm.getBitValue(10));

		System.out.println("Expected Result:0,1,1,0,1,0,1");
	}
	
	/**
	 * use int[] to build a bitmap。
	 * from left to right to fulfill the bits。
	 * @param capacity
	 */
	public Bitmap(int capacity){
		this.capacity = capacity;
		this.bitmap = new int[getIntIndex(capacity)+1];
	}

	private int getIntIndex(int position) {
		// TODO Auto-generated method stub
		if(position <= 0){
			throw new IllegalArgumentException(""+position);
		}
		if(capacity != 0 && position > capacity){
			throw new IllegalArgumentException(""+position);
		}
		return (position - 1)/BIT_PER_INT;
	}
	
	/**
	 * add bit value in position.
	 * return true if add bit to 1 success. 
	 * return false if the bit have already been 1 before add.
	 * @param position
	 * @return
	 */
	public boolean addBitValue(int position){
		if(getBitValue(position) == 1){
			return false;
		}
		int int_index = getIntIndex(position);		//checked position > 0 in getIntIndex().
		int bit_index = (position-1) % BIT_PER_INT;		
		bitmap[int_index] =  (bitmap[int_index] | (1 << (31 - bit_index)));		//add use "|".
		return true;
	}

	/**
	 * get bit value in position.
	 * return bit value int position.
	 * @param position
	 * @return
	 */
	public int getBitValue(int position){
		int int_index = getIntIndex(position);
		int bit_index = (position-1) % BIT_PER_INT;		//have checked position > 0 in getIntIndex().
		return (bitmap[int_index] & (1 << (31 - bit_index))) >> (31 - bit_index);	//get use "&".
	}
}
