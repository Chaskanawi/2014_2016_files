#include "Database.h"
#include "footwear.h"
#include <iostream>

using namespace SDI;

Database database = Database();
FileManager fileManager;

int main()
{
	database.ReadDB();
	//fileManager.Browse();
	//database.ReadStock();
	Description footwearDescription_;
	ShoeStruct ss;
	BootStruct boots;
	std::string searchVal;
	int browseValue = 0;
	std::cout << "Welcome to PHD Footwear.\n\n" << std::endl;
	std::cout << database.Start()->GetString();

	while (browseValue != 4)
	{
		std::cout << "Options:\n1 - 1 = new\n2 = previous\n3 = search\n4 = exit.\n\nType in a value: " << std::endl;
		std::cin >> browseValue;
		system("CLS");
		switch (browseValue)
		{
		case 1:
			std::cout << database.Forward()->GetString();
			break;
		case 2:
			std::cout << database.Back()->GetString();
			break;
		case 3:
			std::cin >> searchVal;
			
		}
		std::cout << "End of program" << std::endl;
	}

	

	//till function search for stock
		//return Out of stock || Instock
	//fileManager.Search();
	//fileManager.SearchStock();
	//search stock in an sequential bi-directional manner (sort and search)
		//search for Key parts of the shoes they are looking for.
	
	//update changes
	//fileManager.Update();
	//database.WriteToDB();
	//Close propgram
	//database.~Database();
	//fileManager.~FileManager();
}
