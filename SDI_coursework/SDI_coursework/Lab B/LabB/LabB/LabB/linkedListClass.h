#include <iostream>
#include "NodeHeader.h"
#ifndef SDI_LINKED_LIST_H
#define SDI_LINKED_LIST_H

 namespace SDI
{	
	 template <class T> class LinkedList
	{
		//variables here

	private:
		Node* head;
		Node* tail;
		void recurDeletePntr(Node* current);
		//functions here

	public:
		LinkedList();
		LinkedList(LinkedList& tbCopied);
		~LinkedList();
		//basic functionality
		//int insert(int value);
		bool remove(T value);
		bool search(T value);
		//further functionality
		void insertHead(T value);
		void insertTail(T value);
		//cout then sort
		int size();
		bool sort(int counter);
		//print
		void printlist();
		
	};
}
#endif
