package com.lcy;

import java.util.Iterator;
import java.util.NoSuchElementException;



/**
 * @describe ArrayList的内部实现，摘自数据结构与算法分析：java语言描述，第四版，中文版
 */


public class MyArrayList<AnyType> implements Iterable<AnyType>{
	
	private static final int DEFAULT_CAPACITY = 10;					//ArrayList默认长度
	
	private int theSize;											//内部属性，ArrayList当前有效长度
	private AnyType[] theItems;										//用来存储ArrayList数据的数组
	
	public MyArrayList(){											//构造方法
		doClear();
	}
	
	public void clear() {
		doClear();
	}
	
	private void doClear() {										//清除ArrayList中的所有数据
		theSize = 0;
		ensureCapacity(DEFAULT_CAPACITY);
	}
	
	public void ensureCapacity(int newCapacity) {					//更新ArrayList容量大小
		if (newCapacity < theSize) {
			return;
		}
		
		AnyType[] old = theItems;									//如果需要增大ArrayList的容量，则新建一个数组，并将原始数组中的数据复制到新数组中
		theItems = (AnyType[]) new Object[newCapacity];
		for (int i = 0; i < size(); i++) {
			theItems[i] = old[i];
		}
	}
	
	public int size() {												//ArrayList的长度
		return theSize;
	}
	
	public boolean isEmpty() {										//判断ArrayList是否为空
		return size() == 0;
	}
	
	public void trimToSize() {										//不明白该方法的作用
		ensureCapacity(size());
	}
	
	public AnyType get(int index) {									//获取索引位置处的数据
		if (index < 0 || index >= size()) {					//如果索引小于0或大于size，抛出异常
			throw new ArrayIndexOutOfBoundsException();
		}
		return theItems[index];								//将引位置处的数据返回
	}
	
	public AnyType set(int index,AnyType newVal) {					//更新索引位置处的值,并将原始值返回
		if (index < 0 || index >= size()) {					//如果索引小于0或大于size，抛出异常
			throw new ArrayIndexOutOfBoundsException();
		}
		AnyType old = theItems[index];
		theItems[index] = newVal;
		return old;
	}
	
	public boolean add(AnyType value) {								//直接在ArrayList末尾追加数据
		add(size(), value);									//调用重载的add方法
		return true;
	}
	
	public void add(int index,AnyType newValue) {					//在指定索引位置处增加数据
		if (theItems.length == size()) {					//原始ArrayList数据长度不够需要进行扩容
			ensureCapacity(size() * 2 + 1);
		}
		for (int i = theSize; i > index; i--) {				//将指定索引位置处后的所有数据全部向后移一位
			theItems[i] = theItems[i - 1];
		}
		theItems[index] = newValue;							//更换指定位置数据
		theSize ++;											
	}
	
	public AnyType remove(int index) {								//删除指定索引位置处的数据
		if (index < 0 || index >= size()) {					//如果索引小于0或大于size，抛出异常
			throw new ArrayIndexOutOfBoundsException();
		}
		
		AnyType removeElement = theItems[index];			//删除指定索引位置处的数据，并将之后的所有数据向前移一位
		for(int i = index;i < size() - 1;i ++){
			theItems[i] = theItems[i + 1];
		}
		theSize --;
		return removeElement;
	}
	
	
	public Iterator<AnyType> iterator() {							//获取迭代器对象
		return new ArrayListIterator();
	}
	
	private class ArrayListIterator implements Iterator<AnyType>{		//创建内部类
		
		private int current = 0;
		
		@Override
		public boolean hasNext() {									//判断是否还有下一个数据
			// TODO Auto-generated method stub
			return current < size();
		}

		@Override
		public AnyType next() {										//取得下一个数据
			// TODO Auto-generated method stub
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return theItems[ current++ ];
		}
		
		public void remove() {										//删除当前元素
			MyArrayList.this.remove( --current);
		}
		
	}
}
