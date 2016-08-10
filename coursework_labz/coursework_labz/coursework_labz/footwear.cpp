#ifndef FOOTWEAR_CPP
#define FOOTWEAR_CPP

#include <iostream>
#include <map>
#include "footwear.h"
#include "Database.h"
#include <iterator>
#include <fstream>
#include <vector>
#include <sstream>

namespace SDI
{
	Footwear::Footwear(Description footwearDescription_) 
	{
		modelNumber = footwearDescription_.modelNumber_;
		brand = footwearDescription_.brand_;
		colour = footwearDescription_.colour_;
		boot_shoe = footwearDescription_.footwearType_;
		soleType = footwearDescription_.soleStyles_;
		footwearStyle = footwearDescription_.footwearStyle_;
		size = footwearDescription_.size_;
		fastenType = footwearDescription_.fastenType_;
	}
	Footwear::~Footwear(){}

	FileManager::FileManager()
	{
#ifdef SDI_DEBUG_FUNCTION_CALLED
		std::cerr << "Function Call\tSDI::FileManager()" << std::endl;
#endif
		std::cout << "Constructor Called\n\n" << std::endl;

		//initialise maps ADD HERE
		//Initialise();
	}

	bool Footwear::search(const std::string modelID)
	{
		std::size_t found = modelNumber.find(modelID);
		return (found != std::string::npos);
	}

	FileManager::~FileManager()
	{
#ifndef SDI_DEBUG_FUNCTION_CALLED
		std::cerr << "Function Call\tSDI::~FileManager" << std::endl;
#endif
		//de-allocate map ADD HERE
	}

	std::string Footwear::GetString()
	{
		std::string row;
		row += ("ID: " + modelNumber + "\nFootwear type: " + boot_shoe + "\nBrand: " + brand + "\nDescription: " + footwearStyle + "\nSole type: " + soleType + "\nColour: " + colour + "\nFasten type: " + fastenType + "\nSize: " + size);
		return row;
	}

	std::vector<std::string> Footwear::GetData()
	{
		std::vector<std::string> row;
		std::string st;

		st = modelNumber;
		row.push_back(st);

		st = boot_shoe;
		row.push_back(st);

		st = brand;
		row.push_back(st);

		st = footwearStyle;
		row.push_back(st);

		st = soleType;
		row.push_back(st);

		st = colour;
		row.push_back(st);

		st = fastenType;
		row.push_back(st);

		st = size;
		row.push_back(st);

		return row;
	}

	//shoe::shoe
	Shoes::Shoes(ShoeStruct ss, Description footwearDescription_) : Footwear(footwearDescription_)
	{
		openToeShoe = ss.openToed;
	}

	std::string Shoes::GetString()
	{
		std::string result = Footwear::GetString();
		result += ("\nToe: " + openToeShoe);
		return result;
	}

	std::vector<std::string> Shoes::GetData()
	{
		std::vector<std::string> result = Footwear::GetData();
		std::string st;

		st = openToeShoe;
		result.push_back(st);

		return result;
	}

	std::string Footwear::getModelID()
	{
		return modelNumber;
	}

	//boot::boot
	Boots::Boots(BootStruct boots, Description footwearDescription_) : Footwear(footwearDescription_)
	{
		bootHeight_ = boots.bootHeight;
	}

	std::string Boots::GetString()
	{
		std::string result = Footwear::GetString();
		result += ("\nBoot Height: " + bootHeight_);
		return result;
	}

	std::vector<std::string> Boots::GetData()
	{
		std::vector<std::string> row = Footwear::GetData();
		std::string st;

		st = bootHeight_;
		row.push_back(st);

		return row;
	}

	Stock::Stock()
	{

	}

	Stock::~Stock()
	{

	}

	void Stock::populateStock(std::string shoeOrBoot_, double size, std::string colour_, int stockLevel)
	{
		footwearIdentifier = shoeOrBoot_;
		size_ = size;
		footwearColour = colour_;
		stockLevel_ = stockLevel;
	}
#pragma region File Manager
	void FileManager::Update()
	{

	}

	void FileManager::Add()
	{

	}

	void FileManager::Remove()
	{

	}

	void FileManager::Search()
	{

	}

	void FileManager::SearchStock()
	{

	}
#pragma endregion
}
#endif