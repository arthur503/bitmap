package com.arthur.delicious.bitmap;


public class BucketBitMap {

	private int[] bitmap;
	private int capacity;
	private int BIT_NUM_PER_BUCKET = 2;
	private int BIT_NUM_PER_INT = 32;
	private int bit_index;
	private int int_index;
	private int bit_num;
	private int int_num;

	
	/**
	 * New a BitMap with capacity and bit_num_per_bucket.
	 * Position is between [0, capacity-1].
	 * Recorded max value is:(1 << BIT_NUM_PER_BUCKET) - 1); 	
	 * 但是，对于BIT_NUM_PER_BUCKET = 4，8等这样的数字， （1 << BIT_NUM_PER_BUCKET）-1，会当做有符号数处理，得到负数。————待解决。
	 * @param capacity
	 * @param bit_num_per_bucket
	 */
	public BucketBitMap(int capacity){
		this(capacity,2);
	}
	
	/**
	 * only bit_num_per_bucket={1,2,4,8} is supported now. 
	 * Others can not be divide by 8
	 * @param capacity
	 * @param bit_num_per_bucket
	 */
	
	public BucketBitMap(int capacity, int bit_num_per_bucket){
		this.capacity = capacity;
		this.BIT_NUM_PER_BUCKET = bit_num_per_bucket;
		this.bit_num = capacity * bit_num_per_bucket;
		this.int_num = getIntIndex(capacity - 1)+1;
		bitmap = new int[int_num];
		System.out.println("Create New Bitmap. capacity:"+capacity+".bit_num_per_bucket:"+bit_num_per_bucket+". int_num:"+int_num);
	}
	
	public int getIntIndex(int position){
		if(position == 0){
			return 0;
		}
		if(position < 0 || position >= this.capacity){
			throw new IllegalArgumentException("position should between [0-"+(position-1)+"]. "+position);
		}
		
		bit_index = (position * BIT_NUM_PER_BUCKET) % BIT_NUM_PER_INT ;
		if(bit_index == 0){
			return position * BIT_NUM_PER_BUCKET / BIT_NUM_PER_INT - 1;
		}else{
			return position * BIT_NUM_PER_BUCKET / BIT_NUM_PER_INT; 
		}
		
	}
	
	public int getBitValue(int position){
		int_index = getIntIndex(position);
		bit_index = (position * BIT_NUM_PER_BUCKET) % BIT_NUM_PER_INT ;
		int start = BIT_NUM_PER_INT - 1 - bit_index;
		int end = start - (BIT_NUM_PER_BUCKET - 1);
		
		int startMask;
		if(bit_index == 0){
			startMask = -1;
		}else{
			startMask = (1 << (start+1)) - 1;
		}
		
		int endMask = (1 << end) - 1;
		int result = ((bitmap[int_index] & startMask) - (bitmap[int_index] & endMask)) >> end;
		return result;
	}
	
	/**
	 * add bitvalue。
	 * Recorded max value is:(1 << (BIT_NUM_PER_BUCKET-1)) - 1);
	 * return new getBitValue(position).
	 * @param position
	 * @return
	 */
	public int addBitValue(int position){
		int_index = getIntIndex(position);
		bit_index = (position * BIT_NUM_PER_BUCKET) % BIT_NUM_PER_INT ;
		int start = BIT_NUM_PER_INT - 1 - bit_index;
		int end = start - (BIT_NUM_PER_BUCKET - 1);
/*		System.out.println("position "+position+" int_index:"+int_index);
		System.out.println("position "+position+" bit_index:"+bit_index);
		System.out.println("position "+position+" start:"+start);
		System.out.println("position "+position+" end:"+end);*/
		
		int bitvalue = getBitValue(position);
		if(bitvalue != ((1 << BIT_NUM_PER_BUCKET) - 1)){
//			int startMask = (1 << (start+1)) - 1;
			int endMask = (1 << end) - 1;
			int endValue = bitmap[int_index] & endMask;
			bitmap[int_index] = (((bitmap[int_index] >> end) + 1) << end) + endValue;  
			/*
			System.out.println("position "+position+" ((bitmap[int_index] >> end):"+Integer.toBinaryString(((bitmap[int_index] >> end))));
			System.out.println("position "+position+" endMask:"+Integer.toBinaryString(endMask));
			System.out.println("position "+position+" endValue:"+Integer.toBinaryString(endValue));
			System.out.println("position "+position+" bitmap[int_index]:"+Integer.toBinaryString(bitmap[int_index]));
		*/
		}
		return getBitValue(position);		
	}
	

	
	/**
	 * Decrease bitvalue.
	 * @param position
	 * @return
	 */
	public int decreaseBitValue(int position){
		int_index = getIntIndex(position);
		bit_index = (position * BIT_NUM_PER_BUCKET) % BIT_NUM_PER_INT ;
		int start = BIT_NUM_PER_INT - 1 - bit_index;
		int end = start - (BIT_NUM_PER_BUCKET - 1);
		
		int bitvalue = getBitValue(position);
		if(bitvalue != 0){
//			int startMask = (1 << (start+1)) - 1;
			int endMask = (1 << end) - 1;
			int endValue = bitmap[int_index] & endMask;
			bitmap[int_index] = (((bitmap[int_index] >> end) - 1) << end) + endValue;  
		}
		return getBitValue(position);		
	}
}
