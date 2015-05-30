#include "AirInfo.h"
#include "AirCompare.h"
#include <stdio.h>

unsigned short getAirBrandNameById(unsigned short id, unsigned char language, char *name)
{
	if (language)
	{
		switch (id)
		{
		case AIR_BRAND_MIDEA:
			strcpy(name, "美的");
			return strlen("美的");
		case AIR_BRAND_TOSHIBA:
			strcpy(name, "东芝");
			return strlen("东芝");
		case AIR_BRAND_HAIER:
			strcpy(name, "海尔");
			return strlen("海尔");
		case AIR_BRAND_GREE:
			strcpy(name, "格力");
			return strlen("格力");
		}
	}
	
}

unsigned short getAirBrandProductLenById(unsigned short id)
{
	switch (id)
	{
	case AIR_BRAND_MIDEA:
		return AIR_BRAND_MIDEA_LEN;
	case AIR_BRAND_TOSHIBA:
		return AIR_BRAND_MIDEA_LEN;
	case AIR_BRAND_HAIER:
		return AIR_BRAND_HAIER_LEN;
	case AIR_BRAND_GREE:
		return AIR_BRAND_GREE_LEN;
	}
}


