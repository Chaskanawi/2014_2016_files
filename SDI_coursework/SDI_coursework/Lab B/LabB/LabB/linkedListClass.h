#include <iostream>
#include "NodeHeader.h"
#ifndef SDI_LINKED_LIST_H
#define SDI_LINKED_LIST_H

 namespace SDI
{	
	 template <class T> class LinkedList
	 {
	private:
		Node<T>* head;
		Node<T>* tail;
		void recurDeletePntr(Node<T>* current);
	public:
		//constructor
		LinkedList();
		LinkedList(LinkedList& tbCopied);
		~LinkedList();
		//basic functionality
		int remove(T value);
		bool search(T value);
		//further functionality
		void insertHead(T value);
		void insertTail(T value);
		int size();
		void ascendingSort();
		void descendingSort();
		void printList();
	};
}
#endif
