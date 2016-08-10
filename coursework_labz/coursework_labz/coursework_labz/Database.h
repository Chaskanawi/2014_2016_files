#ifndef DATABASE_H
#define DATABASE_H

#include "footwear.h"
#include <iostream>
#include <sstream>
#include <map>
#include <list>

namespace SDI
{
	typedef std::string Size;
	typedef std::string Colour;
	typedef std::string StockNumber;
	typedef std::string ID;

	class Database
	{
	private:
		std::map<std::string, Footwear*> PHDProducts;
		std::map<int, Stock> stockMap;
		std::map<std::string, Footwear*>::iterator currentProduct;

	public:
		Database();
		~Database();
		void ReadStock();
		void ReadDB();
		void addProduct(Footwear* data);
		std::vector<Footwear*> search(const std::string ID);
		//void WriteToDB();

		//Browse funtions.
		Footwear* Start();
		Footwear* End();
		Footwear* Back();
		Footwear* Forward();
	};
}
#endif