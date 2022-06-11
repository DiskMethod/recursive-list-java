package structures;

import java.util.Iterator;

public class RecursiveList<T> implements ListInterface<T> {
	LLNode<T> head;
	int size;
	
	public RecursiveList() {
		head = null;
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public ListInterface<T> insertFirst(T elem) throws NullPointerException {
		if(elem == null) {
			throw new NullPointerException();
		}
		insertAt(0, elem);
		return this;
	}

	@Override
	public ListInterface<T> insertLast(T elem) throws NullPointerException {
		if(elem == null) {
			throw new NullPointerException();
		}
		if(isEmpty()) {
			insertAt(0, elem);
		} else {
			insertAt(size, elem);
		}
		return this;
	}
	
	@Override
	public ListInterface<T> insertAt(int index, T elem) throws NullPointerException, IndexOutOfBoundsException {
		if(elem == null) {
			throw new NullPointerException();
		} else if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		
		insertAt(index, elem, null, head);
		size++;
		return this;
	}
	
	private ListInterface<T> insertAt(int index, T elem, LLNode<T> prevNode, LLNode<T> currNode) {	
		if(index == 0) {
			if(prevNode == null) {
				LLNode<T> temp = new LLNode<T>(elem, head);
				head = temp;
			} else {
				LLNode<T> temp = new LLNode<T>(elem, currNode);
				prevNode.next = temp;
			}
		} else if (prevNode == null) {
			insertAt(--index, elem, currNode, currNode.next);
		} else {
			insertAt(--index, elem, prevNode.next, currNode.next);
		}
		return this;
	}

	@Override
	public T removeFirst() throws IllegalStateException {
		if(isEmpty()) {
			throw new IllegalStateException();
		}
		T data = getFirst();
		removeAt(0);
		return data;
	}

	@Override
	public T removeLast() throws IllegalStateException {
		if(isEmpty()) {
			throw new IllegalStateException();
		}
		T data = getLast();
		removeAt(size - 1);
		return data;
	}

	@Override
	public T removeAt(int i) throws IndexOutOfBoundsException {
		if(isEmpty() || i < 0 || i > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		T data = get(i);
		removeAt(i, null, head);
		size--;
		return data;
	}
	
	private void removeAt(int i, LLNode<T> prevNode, LLNode<T> currNode) {	
		if(i == 0) {
			if(prevNode == null) {
				head = head.next;
			} else {
				prevNode.next = currNode.next;
			}
		} else if (prevNode == null) {
			removeAt(--i, currNode, currNode.next);
		} else {
			removeAt(--i, prevNode.next, currNode.next);
		}
	}

	@Override
	public T getFirst() throws IllegalStateException {
		if(isEmpty()) {
			throw new IllegalStateException();
		}
		return get(0);
	}

	@Override
	public T getLast() throws IllegalStateException {
		if(isEmpty()) {
			throw new IllegalStateException();
		}
		return get(size - 1);
	}
	
	@Override
	public T get(int i) throws IndexOutOfBoundsException {
		if(isEmpty() || i < 0 || i > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		return get(i, head);
	}
	
	private T get(int i, LLNode<T> currNode) {
		if(i == 0) {
			return currNode.data;
		} else {
			return get(--i, currNode.next);
		}
	}

	@Override
	public boolean remove(T elem) throws NullPointerException {
		if(elem == null) {
			throw new NullPointerException();
		}
		int num = indexOf(elem);
		if(num == -1) {
			return false;
		}
		removeAt(num);
		return true;
	}

	@Override
	public int indexOf(T elem) throws NullPointerException {
		if(elem == null) {
			throw new NullPointerException();
		}
		return indexOf(elem, 0, head);
	}
	
	private int indexOf(T elem, int currIndex, LLNode<T> currNode) {
		if(currNode == null) {
			return -1;
		}
		
		if(currNode.data.equals(elem)) {
			return currIndex;
		} else {
			return indexOf(elem, ++currIndex, currNode.next);
		}
	}

	@Override
	public boolean isEmpty() {
		return size <= 0 ? true : false;
	}

	@Override
	public Iterator<T> iterator() {
		return new RecursiveListIterator<T>(head);
	}
	
	public class RecursiveListIterator<E> implements Iterator<E> {
		LLNode<E> head;
		
		public RecursiveListIterator(LLNode<E> head) {
			this.head = head;
		}
		
		@Override
		public boolean hasNext() {
			return head != null ? true : false;
		}

		@Override
		public E next() {
			E data = head.data;
			head = head.next;
			return data;
		}
	}
}

class LLNode<T> {
	public T data;
	public LLNode<T> next;
	
	public LLNode (T data) {
		this.data = data;
		next = null;
	}
	
	public LLNode (T data, LLNode<T> next) {
		this.data = data;
		this.next = next;
	}
}
