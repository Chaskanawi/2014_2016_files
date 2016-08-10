#include <iostream>
#include "linkedListClass.h"
#include "linkedListClass.cpp"

using namespace SDI;
using namespace std;

int main()
{
	LinkedList<int> myList;
	//variables
	int response = -1;
	int startOrEnd = 0;
	int insertValue;
	int searchValue;
	int removeValue;
	int counter = 0;

	while (response != 0)
	{
		system("CLS");
		cout << "Welcome to the linked list program.\n" << endl;
		cout << "Press 1 - Insert Value " << endl;
		cout << "Press 2 - Remove Value " << endl;
		cout << "Press 3 - Search Value " << endl;
		cout << "Press 4 - Display Value " << endl;
		cout << "Press 5 - Sort List ascending " << endl;
		cout << "Press 6 - Sort List decending " << endl;
		cout << "Press 0 - To exit program " << endl;
		cout << "Please select an option: \t";
		cin >> response;
		switch (response)
		{
		case 1:
			system("CLS");
			while (startOrEnd == 0)
			{
				cout << "Would you like to insert a value to the start or end of the list?" << endl;
				cout << "1 - start\n2 - end" << endl;
				cin >> startOrEnd;

				if ((startOrEnd == 1) || (startOrEnd == 2))
				{
					cout << "Enter the value to insert: ";
					cin >> insertValue;
					if (startOrEnd == 1)
					{
						myList.insertHead(insertValue);
						counter++;
						break;
					}
					else if (startOrEnd == 2)
					{
						myList.insertTail(insertValue);
						counter++;
						break;
					}
				}
				else
				{
					cout << "Not a valid option" << endl;
					startOrEnd = 0;
				}
			}
			startOrEnd = 0;
			break;
		case 2:
			system("CLS");
			cout << "Enter a value you wish to remove from the list: ";
			cin >> removeValue;
			myList.remove(removeValue);
			counter--;
			break;
		case 3:
			system("CLS");
			cout << "Enter a value to search for: ";
			cin >> searchValue;
			myList.search(searchValue);
			break;
		case 4:
			system("CLS");
			myList.printList();
			break;
		case 5:
			system("CLS");
			myList.ascendingSort();
			break;
		case 6:
			system("CLS");
			myList.descendingSort();
			break;
		default:
			cout << "\nIncorrect option!" << endl;
		}
	}
	return 0;
}