#ifndef DATABASE_CPP
#define DATABASE_CPP

#include "Database.h"
#include "footwear.h"
#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>

namespace SDI
{

	Database::Database()
	{
#ifdef SDI_DEBUG_FUNCTION_CALLED
		std::cerr << "Function Call\tSDI::Database()" << std::endl;
#endif
		std::cout << "Constructor Called\n\n" << std::endl;
		currentProduct = PHDProducts.begin();
	}

	Database::~Database()
	{
#ifndef SDI_DEBUG_FUNCTION_CALLED
		std::cerr << "Function Call\tSDI::~Database()" << std::endl;
#endif
		//de-allocate DB/ReadDB/ReadStock HERE
	}

	void Database::ReadDB()
	{
		std::ifstream file("footwear.csv");
		std::string value;
		std::vector<std::string> resultant;
		Description description;
		ShoeStruct ss;
		BootStruct boots;

		if (file.is_open())
		{
			while (file.good())
			{
				getline(file, value);
				resultant.push_back(value);
			}
			file.close();
		}

		for (std::string line : resultant)
		{
			std::vector<std::string> cells;
			size_t commaLocation = line.find(',');
			cells.push_back(line.substr(0, commaLocation));
			while (commaLocation != std::string::npos)
			{
				line = line.substr(commaLocation + 1, std::string::npos);
				commaLocation = line.find(',');
				cells.push_back(line.substr(0, commaLocation));
			}

			// ternary operator here + research
			if (cells[1] == "boots")
			{
				description = { cells[0], cells[1],cells[2], cells[3], cells[4], cells[5], cells[6], cells[7], cells[8] };
				addProduct(new Shoes(ss, description));
			}
			else if (cells[1] == "shoes")
			{
				description = { cells[0], cells[1], cells[2], cells[3], cells[4], cells[5], cells[6], cells[7], cells[8] };
				addProduct(new Boots(boots, description));
			}
		}
	}

	std::vector<Footwear*> Database::search(std::string ID)
	{
		std::vector<Footwear*> returnP;
		for (std::map<std::string, Footwear*>::iterator it = PHDProducts.begin(); it != PHDProducts.end(); it++)
		{
			if (it->second->search(ID))
			{
				returnP.push_back(it->second);
			}
		}
		return returnP;
	}



	void Database::addProduct(Footwear* data)
	{
		PHDProducts[data->getModelID()] = data;
	}

	Footwear* Database::Start()
	{
		currentProduct = PHDProducts.begin();
		return(currentProduct->second);
	}

	Footwear* Database::End()
	{
		std::map<std::string, Footwear*>::iterator endIteration = PHDProducts.end();
		endIteration--;
		currentProduct = endIteration;
		return(currentProduct->second);
	}

	Footwear* Database::Forward()
	{
		std::map<std::string, Footwear*>::iterator endIteration = PHDProducts.end();
		endIteration--;
		if (currentProduct != endIteration)
		{
			currentProduct++;
		}
		return (currentProduct->second);
	}

	Footwear* Database::Back()
	{
		if (currentProduct != PHDProducts.begin())
		{
			currentProduct--;
		}
		return (currentProduct->second);
	}

	void Database::ReadStock()
	{
		//open stock
		std::ifstream stockFile("Files/Stock.csv");

		std::string footwearColour;
		double footwearSize_;
		int stockLevel_;
		int footwearID_;
		std::string bootOrShoe_;
		//get line and data variables
		std::string line;
		std::string data;

		//create buffer and get a lineResult
		std::vector<std::string> vectBuffer;
		std::string lineResult;

		while (stockFile.good())
		{
			while (std::getline(stockFile, line))
			{
				std::stringstream lineStream(line);
				while (std::getline(lineStream, data, ','))
				{
					vectBuffer.push_back(data);
				}
			}
		}

		for (int i = 0; i < vectBuffer.size(); i++)
		{
			//stoi convert ID, stocklevel, Stod for double size, colour remaains string.
			footwearID_ = std::stoi(vectBuffer[i]);
			bootOrShoe_ = vectBuffer[i + 1];
			footwearSize_ = std::stod(vectBuffer[i + 2]);
			footwearColour = vectBuffer[i + 3];
			stockLevel_ = std::stoi(vectBuffer[i + 4]);

			//populate stock map
			stockMap[footwearID_].populateStock(bootOrShoe_, footwearSize_, footwearColour, stockLevel_);
		}
	}
}
#endif