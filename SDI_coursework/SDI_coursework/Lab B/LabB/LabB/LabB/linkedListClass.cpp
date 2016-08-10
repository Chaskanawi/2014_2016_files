#include <iostream>
#include "linkedListClass.h"
#include "NodeHeader.h"


using namespace std;
using namespace SDI;

//constructor
template <class T> LinkedList<T>::LinkedList()
{
	//SDI_DEBUG called
#ifdef SDI_DEBUG_FCALL
	std::cerr << "Function Call\tSDI::LinkedList()" << std::endl;
#endif
	head = nullptr;
	tail = nullptr;
}

//destructor
template <class T> LinkedList<T>::~LinkedList()
{
	//SDI_DEBUG called
#ifdef SDI_DEBUG_FCALL
	std::cerr << "Function Call\tSDI::LinkedList()" << std::endl;
#endif
	//delete pointers
	recurDeletePntr(head);
	recurDeletePntr(tail);
	//set to null
	head = nullptr;
	tail = nullptr;
}

template <class T> void LinkedList<T>::recurDeletePntr(Node* current)
{
	//delete (head)
	//delete (tail) Back up destruct
	if (current != nullptr)
		recurDeletePntr(current->next);
		delete current;	
}

//insert head
template <class T> void LinkedList<T>::insertHead(T value)
{
	if (head == nullptr)
	{
		tail = head = new Node();
		tail->storedValue = value;
		tail->next = nullptr;
		tail->previous = head;
	}
	else
	{
		Node* temp = new Node();
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
		Node* temp = new Node();
		temp->storedValue = value;
		temp->next = nullptr;
		temp->previous = head;
		head = temp;
	}
	else
	{
		Node* current = head;
		while (current->next != nullptr)
		{
			current = current->next;
		}
		Node* temp = new Node();
		temp->storedValue = value;
		temp->next = nullptr;
		temp->previous = current;

		current->next = temp;
	}
}



//remove values
template <class T> bool LinkedList<T>::remove(T value)
{
	Node* current = head;
	while (current != nullptr)
		if (current == head)
		{
		if (current->storedValue == value)
		{
			head = current->next;
			delete current;
			return 1;
		}
		}
		else
		{
			if (current->next != nullptr)
			{
				if (current->next->storedValue == value)
				{
					SDI::Node* temp = current->next;
					current->next = current->next->next;
					delete temp;
					return 1;
				}
			}
		}
	return 0;
};


//search function
template <class T> bool LinkedList<T>::search(T value)
{
	SDI::Node* current = head;
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
template <class T> void LinkedList<T>::printlist()
{
	if (head == nullptr)
	{
		cout << "Nothing to print.\n\n" << endl;
	}
	Node* temp = head;
	while (temp != nullptr)
	{
		cout << temp->storedValue<< endl;
		temp = temp->next;
	}
}


//add additional features here
//finish sort and add function toString
template <class T> bool LinkedList<T>::sort(int counter)
{
	int count = size();
	if(count <= 1)
		return 0;

	if (head != 0)
	{
		Node* current = head;
		Node* prev = 0;
		Node* temp = nullptr;

		int changeFlag = 0;

		for (int i = 0; i <= counter; i++)
		{
			while(current -> next != 0)
			{
				temp = current->next;
				if (current->storedValue > temp->storedValue)
				{
					changeFlag = 1;
					current->next = temp->next;
					temp->next = current;
					if (prev != 0)
						prev->next = temp;
					prev = temp;
					if (head == current)
						head = temp;
					if (current->next == 0)
						tail = current;
				}
				else
				{
					prev = current;
					current = current->next;
				}
			}
			if (changeFlag == 0)
				break;
			else
			{
				prev = 0;
				current = head;
				changeFlag = 0;
			}
		}
	}
}//end sort


template <class T> int LinkedList<T>::size()
{
	int counter = 0;
	Node* current = head;
	/* traverse the list */
	while (current != nullptr)
	{
		/* Update the counter */
		counter++;
		/* move along to the next node */
		current = current->next;
	}
	return counter;
}




