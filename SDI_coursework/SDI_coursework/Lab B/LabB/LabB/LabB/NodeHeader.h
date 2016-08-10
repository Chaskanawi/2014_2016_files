#include <iostream>
#ifndef SDI_NODE_H
#define SDI_NODE_H
namespace SDI

{
	class Node
	{
	public:
		float storedValue;
		Node* next;
		Node* previous;
	private:
	};
}
#endif
