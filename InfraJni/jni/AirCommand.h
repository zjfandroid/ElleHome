#ifndef _AIR_COMMAND_
#define _AIR_COMMAND_

#define AIR_TEMP_19			0X13
#define AIR_TEMP_20			0X14
#define AIR_TEMP_21			0X15
#define AIR_TEMP_22			0X16
#define AIR_TEMP_23			0X17
#define AIR_TEMP_24			0X18
#define AIR_TEMP_25			0X19
#define AIR_TEMP_26			0X1a
#define AIR_TEMP_27			0X1b
#define AIR_TEMP_28			0X1c
#define AIR_TEMP_29			0X1d
#define AIR_TEMP_30			0X1e

#define AIR_FLOW_RATE_AUTO	0x01
#define AIR_FLOW_RATE_LOW	0x02
#define AIR_FLOW_RATE_MID	0x03
#define AIR_FLOW_RATE_HIGH	0x04

#define AIR_FLOW_MANUAL_DOWN	0x03
#define AIR_FLOW_MANUAL_MID		0x02
#define AIR_FLOW_MANUAL_UP		0x01

#define AIR_FLOW_AUTO_ON		0x01
#define AIR_FLOW_AUTO_OFF		0x00

#define AIR_ONOFF_ON			0x01
#define AIR_ONOFF_OFF			0x00

#define AIR_KEY_ONOFF			0x01
#define AIR_KEY_MODEL			0x02
#define AIR_KEY_FLOW			0x03
#define AIR_KEY_MANUAL_FLOW		0x04
#define AIR_KEY_AUTO_FLOW		0x05
#define AIR_KEY_TEMP_ADD		0x06
#define AIR_KEY_TEMP_REDUCE		0x07

#define AIR_MODEL_AUTO			0x01
#define AIR_MODEL_COLD			0x02
#define AIR_MODEL_DRY			0x03
#define AIR_MODEL_WIND			0x04
#define AIR_MODEL_HOT			0x05

//得到指令的类容
unsigned short getAirCommandData(unsigned char *data,unsigned short id,unsigned char temp,unsigned char airflowRate,
	unsigned char manualFlow,unsigned char autoFlow,unsigned char onoff,unsigned char key,unsigned char model);

void testAirCommand(void);
void testMediaCommand(void);

#endif