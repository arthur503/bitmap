package com.arthur.delicious.bitmap;

public class BinaryBitmap {
	
	
	
	public static void main(String[] argv){
		
		BinaryBitmap bbt = new BinaryBitmap(100);
		System.out.println("get value:"+bbt.getBitValue(10));
		System.out.println("add value:"+bbt.addBitValue(10));
		System.out.println("get value:"+bbt.getBitValue(10));
		System.out.println("add value:"+bbt.addBitValue(10));
		System.out.println("get value:"+bbt.getBitValue(10));
		System.out.println("add value:"+bbt.addBitValue(10));
		System.out.println("get value:"+bbt.getBitValue(10));
		System.out.println("add value:"+bbt.addBitValue(10));
		System.out.println("get value:"+bbt.getBitValue(10));
		System.out.println("add value:"+bbt.addBitValue(10));
		System.out.println("del value:"+bbt.delBitValue(10));
		System.out.println("add value:"+bbt.addBitValue(10));
		System.out.println("del value:"+bbt.delBitValue(10));
		System.out.println("add value:"+bbt.addBitValue(10));
		System.out.println("del value:"+bbt.delBitValue(10));
		System.out.println("add value:"+bbt.addBitValue(10));
		System.out.println("del value:"+bbt.delBitValue(10));
		System.out.println("get value:"+bbt.getBitValue(10));
		System.out.println("del value:"+bbt.delBitValue(10));
		System.out.println("get value:"+bbt.getBitValue(10));
		System.out.println("del value:"+bbt.delBitValue(10));
		System.out.println("get value:"+bbt.getBitValue(10));
		System.out.println("del value:"+bbt.delBitValue(10));
		System.out.println("get value:"+bbt.getBitValue(10));
		System.out.println("del value:"+bbt.delBitValue(10));
		System.out.println("get value:"+bbt.getBitValue(10));
		System.out.println("del value:"+bbt.delBitValue(10));
		System.out.println("get value:"+bbt.getBitValue(10));
	
		
	}
	
	
	private int capacity;
	private int[] bitmap;
	private int BITS_PER_INT = 32;
	private int BITS_PER_BUCKET = 2;
	
	
	/**
	 * BinaryBitmap, keep data with 2 bits. 
	 * bit value range is [0~3].
	 * position range [0, capacity-1].
	 * @param capacity
	 */
	public BinaryBitmap(int capacity){
		this.capacity = capacity;
		this.bitmap = new int[getIntIndex(capacity-1)+1];
//		System.out.println("capacity:"+capacity+"\tbitmap length:"+bitmap.length);
	}

	public int getIntIndex(int position){
		if(position < 0){
			throw new IllegalArgumentException(""+position);
		}
		if(capacity != 0 && position >= capacity){
			throw new IllegalArgumentException(""+position);			
		}
		return position / BITS_PER_INT;
			
	}
	
	public int getBitIndex(int position){
		if(position < 0){
			throw new IllegalArgumentException(""+position);
		}
		if(capacity != 0 && position >= capacity){
			throw new IllegalArgumentException(""+position);			
		}
		return position % BITS_PER_INT;
	}
	
	/**
	 * Get bit value. 
	 * Position is between [0, capacity-1].
	 * @param position
	 * @return
	 */
	public int getBitValue(int position){
		int int_index = getIntIndex(position);
		int bit_index = getBitIndex(position);
		
		int start = (BITS_PER_INT-1) - bit_index;
		int startMask;
		if(bit_index == 0){
			startMask = -1;
		}else{
			startMask = (1 << (start+1)) - 1;
		}
		
		int end = start - (BITS_PER_BUCKET-1);
		int endMask = (1 << end) - 1;
		
		int startValue = bitmap[int_index] & startMask;
		int endValue = bitmap[int_index] & endMask;
		
		int result = (startValue - endValue) >> end;
		return result;
	}
	
	/**
	 * Add bit value until "getBitValue(position) == (1 << BITS_PER_BUCKET) - 1".
	 * @param position
	 * @return
	 */
	public int addBitValue(int position){		
		if(getBitValue(position) == (1 << BITS_PER_BUCKET) - 1){
			return (1 << BITS_PER_BUCKET) - 1;
		}
		
		int int_index = getIntIndex(position);
		int bit_index = getBitIndex(position);
		
		int start = (BITS_PER_INT-1) - bit_index;
		int startMask;
		if(bit_index == 0){
			startMask = -1;
		}else{
			startMask = (1 << (start+1)) - 1;
		}
		
		int end = start - (BITS_PER_BUCKET-1);
		int endMask = (1 << end) - 1;
		
		int startValue = bitmap[int_index] & startMask;
		int endValue = bitmap[int_index] & endMask;
		
		bitmap[int_index] = (((startValue - endValue) >> end) + 1) << end + endValue;
		
		return getBitValue(position);
		
	}
	
	/**
	 * Delete bit value until "getBitValue(position) == 0".
	 * @param position
	 * @return
	 */
	public int delBitValue(int position){			
		if(getBitValue(position) == 0){
			return 0;
		}
		
		int int_index = getIntIndex(position);
		int bit_index = getBitIndex(position);
		
		int start = (BITS_PER_INT-1) - bit_index;
		int startMask;
		if(bit_index == 0){
			startMask = -1;
		}else{
			startMask = (1 << (start+1)) - 1;
		}
		
		int end = start - (BITS_PER_BUCKET-1);
		int endMask = (1 << end) - 1;
		
		int startValue = bitmap[int_index] & startMask;
		int endValue = bitmap[int_index] & endMask;
		
		bitmap[int_index] = (((startValue - endValue) >> end) - 1) << end + endValue;
		
		return getBitValue(position);
		
	}
	


}
