#include "InfraLib.h"
#include "AirCompare.h"
#include "AirInfo.h"

//根据传输进来的码值、类型以及品牌、返回对应在数据表中的数据
unsigned short getInfraCompareResult(unsigned char *data,unsigned char type,unsigned int brand)
{
	unsigned short tmp = 0;
	switch(type)
	{
	case TYPE_AIR:
		tmp = getAirCompareResult(data,brand);
		break;
	}
	return tmp;
}

//得到某个类型的所有品牌的长度
unsigned short getBrandLenByType(unsigned char type)
{
	switch (type)
	{
	case TYPE_AIR:
		return TYPE_AIR_LEN;
	}
	return 0;
}

//得到某个类型的某个品牌的名字
unsigned short getBrandNameById(unsigned char type, unsigned short id, unsigned char language, char *name)
{
	switch (type)
	{
	case TYPE_AIR:
		return getAirBrandNameById(id, language, name);
	}
	return 0;
}

//得到某个品牌产品的长度
unsigned short getBrandProductLenById(unsigned char type, unsigned short id)
{
	switch (type)
	{
	case TYPE_AIR:
		return getAirBrandProductLenById(id);
	}
	return 0;
}

//得到空调的指令
unsigned short getAirControlData(unsigned char *data, unsigned short id, unsigned char temp, unsigned char airflowRate,
	unsigned char manualFlow, unsigned char autoFlow, unsigned char onoff, unsigned char key, unsigned char model)
{
	return getAirCommandData(data, id, temp, airflowRate, manualFlow, autoFlow, onoff, key, model);
}









