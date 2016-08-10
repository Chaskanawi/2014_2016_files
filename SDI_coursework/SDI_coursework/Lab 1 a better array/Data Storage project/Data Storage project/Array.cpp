//includes
#include "Array.h"
#include <iostream>
#include <array>
#include <string>
#include <fstream>
#include <algorithm>

//namespaces
using namespace std;
using namespace SDI;

//constructor
template <class T> Array<T>::Array()
{
#ifdef SDI_DEBUG_FCALL
	std::cerr << "Function Call\tSDI::Array()" << std::endl;
#endif // _DEBUG_FCALL
	cout << "Constructor called.\n\n" << endl;
	
	//define array
	size_ = 10;
	top_ = 0;
	myArray = new T[size_];
	//init array
	for (int i = 0; i <= size_; i++)
		myArray[i] = NULL;
}
//destructor 
template <class T> Array<T>::~Array()
{
#ifdef SDI_DEBUG_FCALL
	std::cerr << "Function Call\tSDI::Array()" << std::endl;
#endif // _DEBUG_FCALL
	delete[] myArray;
}
//sort
template <class T> void Array<T>::sortArray()
{
	sort(myArray, myArray + size_);
}
//Add
template <class T> void Array<T>::add(T addValue_)
{
	if (size_ <= top_) //if array is full
	{
		allocateMem(); 
	}
	myArray[top_] = addValue_;
	top_++;
}
//remove 
template <class T> void Array<T>::remove(int deleteValue)
{
	if (deleteValue == -1)
		return;
	else
		myArray[deleteValue] = NULL;
}
//Allocate memory 
template <class T> void Array<T>::allocateMem()
{
	size_ = size_ * 2;
	int *increased = new int[size_];
	for (int i = 0; i < size_; i++)
	{
		if (i > top_) //If values of array are uninitialised set to NULL
			increased[i] = NULL;
		else
			increased[i] = myArray[i];
	}
	myArray = increased;
	deAlllocateMem();
}
//print
template <class T> void Array<T>::printArray()
{
	for (int i = top_ - 1; i >= 0; i--)
		cout << myArray[i] << endl;
}
//search
template <class T> int Array<T>::search(int searchInput)
{
	int foundValue;
	for (int i = 0; i <= size_; i++)
	{
		if (searchInput == myArray[i])
		{
			cout << "\n\n" << searchInput << " " << (searchInput ? "was" : "was not") << " found in the array" << endl;
			foundValue = i;
			return foundValue;
		}
		cout << "Not found at " << i << endl;
	}
	return -1;
}
//save 
template <class T> void Array<T>::save()
{
	ofstream arrayFile("arrayFile.txt");
		for (int i = 0; i < top_; i++)
		{
			arrayFile << myArray[i] << "\n";
		}
	arrayFile.close();
}
//delete 
template <class T> void Array<T>::deleteArray() 
{
	for (int i = 0; i <= size_; i++)
	{
		myArray[i] = NULL;
	}
	top_ = 0;
	deAlllocateMem();
}
//load 
template <class T> void Array<T>::load()
{
	deleteArray();
	ifstream arrayFile("arrayFile.txt");
	while (!arrayFile.eof())
	{
		T line;
		arrayFile >> line;
		add(line);
	}
}

//de-allocate 
template < class T> void Array<T>::deAlllocateMem()
{
	//checks to see if all values of the array are null (user deleted array)
	int nullCounter_ = 0;
	for (int i = 0; i < size_; i++)
	{
		if (myArray[i] == NULL)
			nullCounter_++;
		else
			continue;
	}
	//if array has been deleted by user define array size 10 init to null
	if (nullCounter_ == size_)
	{
		size_ = 10;
		int *decreased = new int[size_];
		for (int j = 0; j < size_; j++)
		{
			decreased[j] = NULL; 
		}
		myArray = decreased;
		top_ = 0;
		return;
	}
	else // array has values stored. Decrease by a quater of the size.
	{
		size_ = (size_ / 4) * 3;
		int *decreasedArray = new int[size_];
		for (int k = 0; k < size_; k++)
		{
			decreasedArray[k] = myArray[k];
		}
		return;
	}
}