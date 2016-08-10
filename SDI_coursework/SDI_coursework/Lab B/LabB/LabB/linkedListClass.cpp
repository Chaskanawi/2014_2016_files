#include <iostream>
#include "linkedListClass.h"
#include "NodeHeader.h"


using namespace std;
using namespace SDI;

//constructor
template <class T> LinkedList<T>::LinkedList()
{
#ifdef SDI_DEBUG_FCALL
	std::cerr << "Function Call\tSDI::LinkedList()" << std::endl;
#endif
	head = nullptr;
	tail = nullptr;
}

//destructor
template <class T> LinkedList<T>::~LinkedList()
{
#ifdef SDI_DEBUG_FCALL
	std::cerr << "Function Call\tSDI::LinkedList()" << std::endl;
#endif
	//delete pointers
	recurDeletePntr(head);
	recurDeletePntr(tail);
	head = nullptr;
	tail = nullptr;
}
//delete all pointers
template <class T> void LinkedList<T>::recurDeletePntr(Node<T>* current)
{
	if (current != nullptr)
		recurDeletePntr(current->next);
		delete current;	
}

//insert head
template <class T> void LinkedList<T>::insertHead(T value)
{
	if (head == nullptr)
	{
		tail = head = new Node<T>;
		tail->storedValue = value;
		tail->next = nullptr;
		tail->previous = head;
	}
	else
	{
		Node<T>* temp = new Node<T>;
		temp->storedValue = value;
		temp->next = nullptr;
		temp->next = head;
		head = temp;
	}
}

//insert tail
template <class T> void LinkedList<T>::insertTail(T value)
{
	if (head == nullptr)
	{
		Node<T>* temp = new Node<T>;
		temp->storedValue = value;
		temp->next = nullptr;
		temp->previous = head;
		head = temp;
	}
	else
	{
		Node<T>* current = head;
		while (current->next != nullptr)
		{
			current = current->next;
		}
		Node<T>* temp = new Node<T>;
		temp->storedValue = value;
		temp->next = nullptr;
		temp->previous = current;
		current->next = temp;
	}
}


//remove values
template <class T> int LinkedList<T>::remove(T value)
{
	Node<T>* current = head; 

	while (current != nullptr)
	{
		if (current->storedValue == value)
		{
			if (current->next != nullptr)
			{
				current->next->previous = current->previous;
			}
			if (current->previous != nullptr)
			{
				current->previous->next = current->next;
			}
			if (current == head)
			{
				head = head->next;
			}
			if (current == tail)
			{
				tail = tail->previous;
			}
			//catch all pointers to current then delete pointers
			delete current;
			return 1;
		}
		current = current->next;
	}
	return 0;
};


//search function
template <class T> bool LinkedList<T>::search(T value)
{
	SDI::Node<T>* current = head;
	while (current != nullptr)
	{
		if (current->storedValue == value)
		{
			return 1;
		}
	}
	return 0;
}

//print function
template <class T> void LinkedList<T>::printList()
{
	if (head == nullptr)
	{
		cout << "Nothing to print.\n\n" << endl;
	}
	Node<T>* temp = head;
	while (temp != nullptr)
	{
		cout << temp->storedValue<< endl;
		temp = temp->next;
	}
}

//sort ascending order
template <class T> void LinkedList<T>::ascendingSort()
{
	if (head != nullptr)
	{
		Node<T> *i = head;
		while (i != tail)
		{
			Node<T> *j = i->next;
			while (j != nullptr)
			{
				if (i->storedValue > j->storedValue)
				{
					Node<T> *temp = new Node < T > ;
					temp->storedValue = j->storedValue;
					j->storedValue = i->storedValue;
					i->storedValue = temp->storedValue;
					delete temp;
				}
				j = j->next;
			}
			i = i->next;
		}
		printList();
	}
}
//descending sort 
template <class T> void LinkedList<T>::descendingSort()
{
	{
		if (head != nullptr)
		{
			Node<T> *i = head;
			while (i != tail)
			{
				Node<T> *j = i->next;
				while (j != nullptr)
				{
					if (i->storedValue < j->storedValue)
					{
						Node<T> *temp = new Node < T >;
						temp->storedValue = j->storedValue;
						j->storedValue = i->storedValue;
						i->storedValue = temp->storedValue;
						delete temp;
					}
					j = j->next;
				}
				i = i->next;
			}
			printList();
		}
	}
}
//size of the linked list
template <class T> int LinkedList<T>::size()
{
	int counter = 0;
	Node<T>* current = head;
	while (current != nullptr)
	{
		counter++;
		current = current->next;
	}
	return counter;
}




