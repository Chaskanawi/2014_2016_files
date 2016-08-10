#ifndef SDI_ARRAY_H
#define SDI_ARRAY_H
namespace SDI
{
	template <class T> class Array
	{
	public:
		//struct
		Array();
		Array(const Array &);
		~Array();
		//public
		void sortArray();
		void add(T data); 
		void remove(int);
		int search(int);
		void printArray(); 
		void save();
		void load();
		void deleteArray();
	private:
		void deAlllocateMem(); 
		void allocateMem(); 
		int size_; 
		int top_; 
		T* myArray; 
	};
}
#endif