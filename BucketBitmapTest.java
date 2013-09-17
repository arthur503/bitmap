package com.arthur.delicious.bitmap;

public class BucketBitmapTest {	
	
	
	public static void main(String[] argv){
		BucketBitMap bm = new BucketBitMap(100,7);
		int position = 40;
		System.out.println(">>Before add, bit value is:"+bm.getBitValue(position));
		
		for(int i=0;i<2000;i++){
			bm.addBitValue(position);
			System.out.println(">>After add, bit value is:"+bm.getBitValue(position));
		}
	
		
		bm.addBitValue(position);
		System.out.println(">>After add, bit value is:"+bm.getBitValue(position));
		bm.addBitValue(position);
		System.out.println(">>After add, bit value is:"+bm.getBitValue(position));
		bm.addBitValue(position);
		System.out.println(">>After add, bit value is:"+bm.getBitValue(position));
		bm.addBitValue(position);
		System.out.println(">>After add, bit value is:"+bm.getBitValue(position));
		bm.addBitValue(position);
		System.out.println(">>After add, bit value is:"+bm.getBitValue(position));
		bm.decreaseBitValue(position);
		System.out.println(">>After dec, bit value is:"+bm.getBitValue(position));
		bm.decreaseBitValue(position);
		System.out.println(">>After dec, bit value is:"+bm.getBitValue(position));
		bm.decreaseBitValue(position);
		System.out.println(">>After dec, bit value is:"+bm.getBitValue(position));
		bm.decreaseBitValue(position);
		System.out.println(">>After dec, bit value is:"+bm.getBitValue(position));
		bm.decreaseBitValue(position);
		System.out.println(">>After dec, bit value is:"+bm.getBitValue(position));
	}

}
