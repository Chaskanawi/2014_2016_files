#include <iostream>
#ifndef SDI_NODE_H
#define SDI_NODE_H
namespace SDI
{
	template <class T> class Node
	{
	public:
		float storedValue;
		Node<T>* next;
		Node<T>* previous;
	private:
	};
}
#endif
