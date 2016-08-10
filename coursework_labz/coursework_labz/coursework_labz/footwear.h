#ifndef FOOTWEAR_H
#define FOOTWEAR_H 

#include <string>
#include <List>
#include <vector>

typedef std::string BootHeight;

using namespace std;


namespace SDI
{
	struct Description
	{
		std::string modelNumber_;
		std::string boot_shoe_;
		std::string brand_;
		std::string footwearType_;
		std::string soleStyles_;
		std::string colour_;
		std::string fastenType_;
		std::string size_;
		std::string footwearStyle_;
	};

	struct ShoeStruct
	{
		std::string openToed;
	};

	struct BootStruct
	{
		BootHeight bootHeight;
	};

	class Footwear
	{
	public:
		Footwear(Description footwearDescription_);
		~Footwear();
		virtual std::string GetString();
		virtual std::vector<std::string> GetData();
		virtual bool search(const std::string ID);
		std::string getModelID();

	private:
		std::string modelNumber;
		std::string brand;
		std::string boot_shoe;
		std::string soleType;
		std::string colour;
		std::string fastenType;
		std::string size;
		std::string footwearStyle;
	};

	class Boots : public Footwear
	{
	private:
		BootHeight bootHeight_;
		//boot styles: Chelsea, kneee, ankle, thigh, Jodhpur, rigger
	public:
		Boots(BootStruct bootStruct, Description description);
		std::vector<std::string> GetData();
		std::string GetString();
	};
	
	class Shoes : public Footwear
	{
	//shoe styles: flipflop, court, sandal, trainer, slipper
	public:
		Shoes(ShoeStruct shoeStruct, Description description);
		std::vector<std::string> GetData();
		std::string GetString();
	private:
		std::string openToeShoe;
	};

	class FileManager
	{
	public:
		FileManager();
		~FileManager();
		//functions
		void Browse();
		void Load();
		void Update();
		void Search();
		void Add();
		void Remove();
		void SearchStock();
	};
	//add more structs for boots and shoes
	class Stock
	{
	public:
		std::string footwearIdentifier;
		int stockLevel_;
		std::string footwearColour;
		double size_;
		Stock();
		~Stock();
		void populateStock(std::string footwearSize_,double size, std::string colour_, int stockLevel);
	};
}
#endif