#include <iostream>
#include "Array.h"
#include "Array.cpp"

using namespace std;

int main()
{
	SDI::Array<int> myArray;
	int choice = -1;
	while (choice != 0)
	{
		system("CLS");
		cout << "Press 1 to add something to the array\n" << endl;
		cout << "Press 2 to delete something from the array\n" << endl;
		cout << "Press 3 to sort the array.\n" << endl;
		cout << "Press 4 to print array.\n" << endl;
		cout << "Press 5 to delete the array.\n" << endl;
		cout << "Press 6 to search the array.\n" << endl;
		cout << "Press 7 to save the array.\n" << endl;
		cout << "Press 8 to load the array. \n" << endl;
		cout << "Press 0 to exit program.\n" << endl;
		cout << "Please enter an option:\t";
		cin >> choice;
		switch (choice)
		{
		case 1:
			system("CLS");
			cout << "Add something to the array.\n" << endl;
			int addValue_;
			cin >> addValue_;
			myArray.add(addValue_); 
			break;

		case 2:
			int searchValue;
			int foundValue;
			system("CLS");
			cout << "Remove something from the array: "; 
			cin >> searchValue;
			foundValue = myArray.search(searchValue);
			myArray.remove(foundValue);
			break;
		case 3:
			system("CLS");
			cout << "Sort the array.\n" << endl;
			myArray.sortArray();
			break;
		case 4:
			system("CLS");
			cout << "View contents of the array.\n" << endl;
			myArray.printArray();
			break;
		case 5:
			system("CLS");
			cout << "Wiped data from array.\n" << endl;
			myArray.deleteArray();
			break;
		case 6:
			system("CLS");
			cout << "Search the array: ";
			int searchVal;
			int foundVal;
			cin >> searchVal;
			foundVal = myArray.search(searchValue);
			cout << "\n\nFound value: " << foundVal;
			break;
		case 7:
			system("CLS");
			cout << "Save the array.\n" << endl;
			myArray.save();
			break;
		case 8:
			system("CLS");
			cout << "Load the array.\n" << endl;
			myArray.load();
			break;
		case 0:
			cout << "\n\nProgram will now exit." << endl;
			break;
			return 0;
		default:
			cout << "\n\nPlease enter a valid value\n\n" << endl;
			break;
		}
	}
}