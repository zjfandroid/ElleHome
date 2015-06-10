#include "LearnInOut.h"
#include <stdbool.h>

//==========================================
#define	commond0f0h			0xf0
#define	commond0f1h			0xf1
#define	commond0f2h			0xf2
#define	csumcodeformat		0x8

#ifndef kal_uint8
typedef unsigned char kal_uint8;
#endif
typedef unsigned short U16;

kal_uint8 learn_data_in_out(unsigned char*learn_data_out);
kal_uint8 get_remote_study_data(unsigned char *cmd_data);
kal_uint8 send_remote_study_data(unsigned char *cmd_data, U16 cmd_lnth);
unsigned int keytogglebit(unsigned int *wavedat,unsigned int *indatsum);
unsigned int JudgeToggleBit(unsigned int *wavedat,unsigned int *indatsum);
unsigned int acmpbandc(unsigned	int	cmpdat1,unsigned int cmpdat2,unsigned int cmpdat3);
bool cmpaequbtog(unsigned int cmpdat1,unsigned int cmpdat2,unsigned	int	flagw);
void changetogglebit(unsigned int *wavedat,unsigned	int	*indatsum,unsigned int togtmp9);
bool compdata(unsigned int *wavedat,unsigned int *indatsum);
bool Modifywave(unsigned int *wavedat,unsigned int *indatsum);
void	delfeng(unsigned int *wavedat,unsigned int *indatsum);
bool modifywavem708(unsigned int *wavedat,unsigned int *indatsum);
bool getfigure(unsigned int	*wavedat, unsigned int *indatsum);
bool judgesame(unsigned int *dat1, unsigned int *dat2, unsigned int sum, unsigned int flag);
bool	cmpdata(unsigned int cmpdat1,unsigned int cmpdat2,unsigned int flag);
void Some_Data_Right_Move1(unsigned	int	*wavedat,unsigned	int	*indatsum,unsigned	int	tmpx);
unsigned int All_Send_Data_Right_Move(unsigned	int	*wavedat,unsigned	int	*indatsum,unsigned	int	tmpx);
void Reload_Data_Buff(unsigned int inhb,unsigned int inlb,unsigned int *wavedat);
unsigned char g_i2c_cmd_buffer[112] = "";
unsigned char learn_data_out[112] = "";
//wjs;========================

unsigned char const	STandantdata[16]=
{
	0xD4,0x01,0x38,0x01,0xD3,0x01,0x36,0x01,
	0x22,0x01,0x83,0x00,0xB7,0x01,0x37,0x01
};

//wjs;===============
unsigned char const	ToggleBit_Place[7]=
{
	0x06,0x04,0x0A,0x06,0x10,0x04,0x02
};
//wjs;=============
unsigned char const	code_format[csumcodeformat][87]=
{
	{
		0x00,0x00,0x28,0x00,0xFA,0x00,
		0x77,0x01,0x00,0x00,0x28,0x00,0x96,0x01,
		0x13,0x02,0x00,0x00,0xF0,0x00,0x00,0x0C,
		0x00,0xF0,0xA8,0x00,0x25,0x01,0xF0,0x00,
		0x00,0x0C,0x00,0xF0,0xA8,0x00,0x25,0x01,
		0xF1,0x02,0xF1,0x08,0xF2,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00
	},
	{
		0x00,0x00,0x28,
		0x00,0xD4,0x00,0x64,0x01,0x00,0x00,0x28,
		0x00,0x70,0x01,0x1F,0x02,0x00,0x00,0xF1,
		0x05,0xF1,0x06,0xF2,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
	},
	{
		0x16,
		0x00,0x23,0x00,0x3E,0x00,0xBB,0x00,0x16,
		0x00,0x23,0x00,0xDA,0x00,0x57,0x01,0x00,
		0x00,0xF1,0x05,0xF1,0x07,0xF2,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
	},
	{
		0x01,0x00,0x37,0x00,
		0xDA,0x00,0x70,0x01,0x00,0x00,0x37,0x00,
		0x83,0x01,0x13,0x02,0x00,0x00,0xF0,0x01,
		0x00,0x37,0x00,0xF0,0x8C,0x00,0x28,0x01,
		0xF0,0x01,0x00,0x37,0x00,0xF0,0x8C,0x00,
		0x28,0x01,0xF1,0x04,0xF1,0x06,0xF2,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00
	},
	{
		0x12,0x00,
		0x24,0x00,0x12,0x00,0x24,0x00,0x44,0x00,
		0x1B,0x00,0x2B,0x00,0x1B,0x00,0x01,0x00,
		0xF0,0x6A,0x00,0xE7,0x00,0xF0,0x30,0x00,
		0x3D,0x00,0xF0,0x12,0x00,0x24,0x00,0xF0,
		0x12,0x00,0x24,0x00,0xF0,0x12,0x00,0x24,
		0x00,0xF0,0x12,0x00,0x24,0x00,0xF0,0x12,
		0x00,0x24,0x00,0xF0,0x12,0x00,0x24,0x00,
		0xF0,0x12,0x00,0x24,0x00,0xF0,0x12,0x00,
		0x24,0x00,0xF0,0x31,0x00,0x3D,0x00,0xF0,
		0x31,0x00,0x3D,0x00,0xF1,0x08,0xF1,0x08,
		0xF1,0x08,0xF1,0x08,0xF2
	},
	{
		0x12,0x00,0x24,
		0x00,0x12,0x00,0x24,0x00,0x5A,0x00,0x37,
		0x00,0x3E,0x00,0x1B,0x00,0x01,0x00,0xF0,
		0x6A,0x00,0xE7,0x00,0xF0,0x31,0x00,0x3D,
		0x00,0xF1,0x04,0xF0,0x31,0x00,0x3D,0x00,
		0xF0,0x31,0x00,0x3D,0x00,0xF1,0x08,0xF1,
		0x08,0xF2,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00
	},
	{
		0x28,
		0x00,0x41,0x00,0x28,0x00,0x41,0x00,0x76,
		0x00,0x34,0x00,0x5D,0x00,0x34,0x00,0x01,
		0x01,0xF1,0x08,0xF1,0x06,0xF2,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
	},
	{
		0x12,0x00,0x2B,0x00,0x12,0x00,0x2B,0x00,
		0x12,0x00,0x2B,0x00,0x3F,0x00,0x1F,0x00,
		0x01,0x00,0xF0,0x12,0x00,0x2B,0x00,0xF0,
		0x76,0x00,0xC8,0x00,0xF0,0x12,0x00,0x2B,
		0x00,0xF0,0x2F,0x00,0x5D,0x00,0xF0,0x2F,
		0x00,0x5D,0x00,0xF0,0x12,0x00,0x2B,0x00,
		0xF1,0x0D,0xF0,0x12,0x00,0x2B,0x00,0xF0,
		0x38,0x01,0x23,0x0F,0xF0,0x12,0x00,0x2B,
		0x00,0xF0,0x76,0x00,0xC8,0x00,0xF2,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
		0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
	}
};


//in,out:	learn_data_out[] ���ݴ������������������ص�110���ֽڣ��õ��ǵ�112���ֽڣ���Ӧһ������һ��ֵ,��ѧϰ����ǰ��Ҫ��0
kal_uint8	learn_data_in_out(unsigned char*learn_data_out)
{	
	kal_uint8 	tempw;
	kal_uint8 	tempct;				
	char	data_check=0;	
	unsigned char	 g_remote_study_data_buf[110];	
	int i;
	tempct=learn_data_out[1];		
	
	if(!tempct)
	{
		for(i=0; i<112;i++)
		{
			learn_data_out[i]=0;
		}
		return 0;
	}
	else
	{
		get_remote_study_data((unsigned char *)learn_data_out);	
		for(i=0; i<109;i++)
		{
			g_remote_study_data_buf[i] = learn_data_out[i];
		}
		send_remote_study_data((unsigned char *)g_remote_study_data_buf,109);	
	
		for(tempw=0; tempw<112; tempw++)
		{
			learn_data_out[tempw] = g_i2c_cmd_buffer[tempw];
		}
		//***************************���ӣ����10��15���ֽ�ȫΪ0ʱΪ�������ݣ�������0
		for(tempw=10; tempw<15; tempw++)	
		{
			data_check+=learn_data_out[tempw];		
		}	
						
		if(learn_data_out[3]<5||learn_data_out[5]<0x10||learn_data_out[5]>0x80)	//ԭ����if(!data_check)
		{
			for(i=0; i<112;i++)
			{
				 learn_data_out[i]=0;
			}
			return 0;
		}		
	}
	
}


//===============================================
kal_uint8 get_remote_study_data(unsigned char *cmd_data)
{

	unsigned int irprocessbuf[111];
	kal_uint8 temp;
	unsigned int datalength;

	for(temp=0; temp<110; temp++)
	{
		irprocessbuf[temp] = (unsigned int)cmd_data[temp];		
	}		
	datalength = 109+1;

	if(0!=(compdata(irprocessbuf, &datalength)))
	{
		return 0;
	}
	else
	{
		for(temp=0; temp<109; temp++)		
		{
			cmd_data[temp] = (unsigned char )irprocessbuf[temp+1];		
		}				
	}
	return datalength;
		
}



//******************************************************************************/
kal_uint8	g_toggle_bit=0;

kal_uint8 send_remote_study_data(unsigned char *cmd_data, U16 cmd_lnth)		
{
	unsigned int sendlrndat[109+1], sendlrndatsum;
	//char g_i2c_cmd_buffer[109+3] = "";
	unsigned int toggle;
	kal_uint8 temp;
	char checksum = 0x32;	
	sendlrndatsum = cmd_lnth;
	for(temp=0; temp<cmd_lnth; temp++)			
		sendlrndat[temp+1] = cmd_data[temp];		

	sendlrndat[0] = 0x00;
	sendlrndatsum = 110;

	g_toggle_bit++;
	if(g_toggle_bit&=0x01)
		sendlrndat[1]^=0x10;

	toggle=keytogglebit(sendlrndat,&sendlrndatsum);	

	g_i2c_cmd_buffer[0] = 0X30;		
	g_i2c_cmd_buffer[1] =0x02;		

	for(temp=0; temp<cmd_lnth; temp++)
	{
		g_i2c_cmd_buffer[2+temp] = sendlrndat[temp+1];
		checksum += g_i2c_cmd_buffer[2+temp];
	}

	g_i2c_cmd_buffer[111] = checksum;
	return cmd_lnth;

}
/////=========================================================
//void	keytogglebit(unsigned int *wavedat,unsigned int *indatsum)
unsigned int keytogglebit(unsigned int *wavedat,unsigned int *indatsum)		
{
	unsigned int tmp1;
	unsigned int tmp2;

	if((wavedat[1]&&0x07)==0)
		return	0;
		
	else
	{
		wavedat[1]^=0x10;
		tmp1=JudgeToggleBit(wavedat,indatsum);		//wjs;Y
		if(0!=tmp1)
		{
			changetogglebit(wavedat,indatsum,tmp1);	//wjs;Y
		}
		tmp2=wavedat[1];
		tmp2&=0xf0;
		tmp2|=tmp1;
	}
	return	tmp2;
}

////------------------------------------------------------------------

unsigned int cnty,cntx;

unsigned int JudgeToggleBit(unsigned	int	*wavedat,unsigned	int	*indatsum)
{
	unsigned int togtmp12=0,togtmp13=0;
	unsigned int togtmp6,togtmp5,togtmp4,togtmp3,togtmp2,togtmp1,togtmp14,togtmp15,togtmp16;
	unsigned int cmpa,cmpb,cmpc;
	unsigned int cnt1;
	unsigned int outcnt1;
	bool cmpbcf,r_flag13,progf1;
	
	for(outcnt1=csumcodeformat;outcnt1!=0;outcnt1--)
	{
		if(0==code_format[csumcodeformat-outcnt1][16])
		{			//wjs;Y
			togtmp14=18;	//TOGTMP14
			cntx=5;		//---x
			while(1)
			{	
				togtmp16=cntx;
				togtmp6=0;
				togtmp5=wavedat[cntx++];
				if(togtmp5>=0x80)
				{
					togtmp6=togtmp5;
					togtmp5=wavedat[cntx++];
				}
				if(commond0f0h==code_format[csumcodeformat-outcnt1][togtmp14])
				{
					togtmp3=code_format[csumcodeformat-outcnt1][togtmp14+1];
					togtmp4=code_format[csumcodeformat-outcnt1][togtmp14+2];
					togtmp1=code_format[csumcodeformat-outcnt1][togtmp14+3];
					togtmp2=code_format[csumcodeformat-outcnt1][togtmp14+4];
					togtmp14+=5;
					cmpa=togtmp6&0x7f;
					cmpa*=0x100;
					cmpa+=togtmp5;
					cmpb=togtmp4&0x7f;
					cmpb*=0x100;
					cmpb+=togtmp3;
					cmpc=togtmp2&0x7f;
					cmpc*=0x100;
					cmpc+=togtmp1;
					if(0==acmpbandc(cmpa,cmpb,cmpc))
						continue;
					else
						break;
				}
				else if(commond0f1h==code_format[csumcodeformat-outcnt1][togtmp14])
				{
					togtmp15=code_format[csumcodeformat-outcnt1][togtmp14+1];	//TOGTMP15
					togtmp14+=2;
					cmpbcf=0;
					while(1)
					{	//ToggleBit_Judge_BIT
						cnt1=0;
						togtmp3=code_format[csumcodeformat-outcnt1][cnt1++];
						togtmp4=code_format[csumcodeformat-outcnt1][cnt1++];
						togtmp1=code_format[csumcodeformat-outcnt1][cnt1++];
						togtmp2=code_format[csumcodeformat-outcnt1][cnt1++];
						cmpa=togtmp6&0x7f;
						cmpa*=0x100;
						cmpa+=togtmp5;
						cmpb=togtmp4&0x7f;
						cmpb*=0x100;
						cmpb+=togtmp3;
						cmpc=togtmp2&0x7f;
						cmpc*=0x100;
						cmpc+=togtmp1;
						if(0==acmpbandc(cmpa,cmpb,cmpc))
						{
							togtmp6=0;
							togtmp5=wavedat[cntx++];
							if(togtmp5>=0x80)
							{
								togtmp6=togtmp5;
								togtmp5=wavedat[cntx++];
							}
							cnt1=4;
							togtmp3=code_format[csumcodeformat-outcnt1][cnt1++];
							togtmp4=code_format[csumcodeformat-outcnt1][cnt1++];
							togtmp1=code_format[csumcodeformat-outcnt1][cnt1++];
							togtmp2=code_format[csumcodeformat-outcnt1][cnt1++];
							cmpa=togtmp6&0x7f;
							cmpa*=0x100;
							cmpa+=togtmp5;
							cmpb=togtmp4&0x7f;
							cmpb*=0x100;
							cmpb+=togtmp3;
							cmpc=togtmp2&0x7f;
							cmpc*=0x100;
							cmpc+=togtmp1;
							if(0==acmpbandc(cmpa,cmpb,cmpc))
							{	
								togtmp15--;
								if(0==togtmp15)
								{	
									cmpbcf=1;
									break;
								}
								else
								{
									togtmp16=cntx;
									togtmp6=0;
									togtmp5=wavedat[cntx++];
									if(togtmp5>=0x80)
									{
										togtmp6=togtmp5;
										togtmp5=wavedat[cntx++];
									}
								}
							}
							else
							{		
								cntx=togtmp16;
								togtmp6=0;
								togtmp5=wavedat[cntx++];
								if(togtmp5>=0x80)
								{
									togtmp6=togtmp5;
									togtmp5=wavedat[cntx++];
								}
								cnt1=8;
								togtmp3=code_format[csumcodeformat-outcnt1][cnt1++];
								togtmp4=code_format[csumcodeformat-outcnt1][cnt1++];
								togtmp1=code_format[csumcodeformat-outcnt1][cnt1++];
								togtmp2=code_format[csumcodeformat-outcnt1][cnt1++];
								cmpa=togtmp6&0x7f;
								cmpa*=0x100;
								cmpa+=togtmp5;
								cmpb=togtmp4&0x7f;
								cmpb*=0x100;
								cmpb+=togtmp3;
								cmpc=togtmp2&0x7f;
								cmpc*=0x100;
								cmpc+=togtmp1;
								if(0==acmpbandc(cmpa,cmpb,cmpc))
								{	//ToggleBit_Judge_OneBITHigh1
									togtmp6=0;
									togtmp5=wavedat[cntx++];
									if(togtmp5>=0x80)
									{
										togtmp6=togtmp5;
										togtmp5=wavedat[cntx++];
									}
									cnt1=12;
									togtmp3=code_format[csumcodeformat-outcnt1][cnt1++];
									togtmp4=code_format[csumcodeformat-outcnt1][cnt1++];
									togtmp1=code_format[csumcodeformat-outcnt1][cnt1++];
									togtmp2=code_format[csumcodeformat-outcnt1][cnt1++];
									cmpa=togtmp6&0x7f;
									cmpa*=0x100;
									cmpa+=togtmp5;
									cmpb=togtmp4&0x7f;
									cmpb*=0x100;
									cmpb+=togtmp3;
									cmpc=togtmp2&0x7f;
									cmpc*=0x100;
									cmpc+=togtmp1;
									if(0==acmpbandc(cmpa,cmpb,cmpc))
									{
										//ToggleBit_Judge_OneBIT
										togtmp15--;
										if(0==togtmp15)
										{	
											cmpbcf=1;
											break;
										}
										else
										{
											togtmp16=cntx;
											togtmp6=0;
											togtmp5=wavedat[cntx++];
											if(togtmp5>=0x80)
											{
												togtmp6=togtmp5;
												togtmp5=wavedat[cntx++];
											}
										}
									}
									else	
										break;
								}
								else		
									break;							
							}
						}
						else
						{		
							cntx=togtmp16;
							togtmp6=0;
							togtmp5=wavedat[cntx++];
							if(togtmp5>=0x80)
							{
								togtmp6=togtmp5;
								togtmp5=wavedat[cntx++];
							}
							cnt1=8;
							togtmp3=code_format[csumcodeformat-outcnt1][cnt1++];
							togtmp4=code_format[csumcodeformat-outcnt1][cnt1++];
							togtmp1=code_format[csumcodeformat-outcnt1][cnt1++];
							togtmp2=code_format[csumcodeformat-outcnt1][cnt1++];
							cmpa=togtmp6&0x7f;
							cmpa*=0x100;
							cmpa+=togtmp5;
							cmpb=togtmp4&0x7f;
							cmpb*=0x100;
							cmpb+=togtmp3;
							cmpc=togtmp2&0x7f;
							cmpc*=0x100;
							cmpc+=togtmp1;
							if(0==acmpbandc(cmpa,cmpb,cmpc))
							{	
								togtmp6=0;
								togtmp5=wavedat[cntx++];
								if(togtmp5>=0x80)
								{
									togtmp6=togtmp5;
									togtmp5=wavedat[cntx++];
								}
								cnt1=12;
								togtmp3=code_format[csumcodeformat-outcnt1][cnt1++];
								togtmp4=code_format[csumcodeformat-outcnt1][cnt1++];
								togtmp1=code_format[csumcodeformat-outcnt1][cnt1++];
								togtmp2=code_format[csumcodeformat-outcnt1][cnt1++];
								cmpa=togtmp6&0x7f;
								cmpa*=0x100;
								cmpa+=togtmp5;
								cmpb=togtmp4&0x7f;
								cmpb*=0x100;
								cmpb+=togtmp3;
								cmpc=togtmp2&0x7f;
								cmpc*=0x100;
								cmpc+=togtmp1;
								if(0==acmpbandc(cmpa,cmpb,cmpc))
								{									
									togtmp15--;
									if(0==togtmp15)
									{	
										cmpbcf=1;
										break;
									}
									else
									{
										togtmp16=cntx;
										togtmp6=0;
										togtmp5=wavedat[cntx++];
										if(togtmp5>=0x80)
										{
											togtmp6=togtmp5;
											togtmp5=wavedat[cntx++];
										}
									}
								}
								else	
									break;
							}
							else		
								break;
						}
					}
					if(0==cmpbcf)
						break;
				}
				else if(commond0f2h==code_format[csumcodeformat-outcnt1][togtmp14])
				{
					return	outcnt1;
				}
				else
					break;
			}
		}
		else if(1==code_format[csumcodeformat-outcnt1][16])
		{
			togtmp14=18;	
			cntx=5;		
			togtmp16=code_format[csumcodeformat-outcnt1][17];	
			r_flag13=0;
			while(1)
			{	
				if(0==r_flag13)
				{
					togtmp6=0;
					togtmp5=wavedat[cntx++];
					if(togtmp5>=0x80)
					{
						togtmp6=togtmp5;
						togtmp5=wavedat[cntx++];
					}
				}
				if(commond0f0h==code_format[csumcodeformat-outcnt1][togtmp14])
				{
					togtmp3=code_format[csumcodeformat-outcnt1][togtmp14+1];
					togtmp4=code_format[csumcodeformat-outcnt1][togtmp14+2];
					togtmp1=code_format[csumcodeformat-outcnt1][togtmp14+3];
					togtmp2=code_format[csumcodeformat-outcnt1][togtmp14+4];
					togtmp14+=5;
					cmpa=togtmp6&0x7f;
					cmpa*=0x100;
					cmpa+=togtmp5;
					cmpb=togtmp4&0x7f;
					cmpb*=0x100;
					cmpb+=togtmp3;
					cmpc=togtmp2&0x7f;
					cmpc*=0x100;
					cmpc+=togtmp1;
					if(0==acmpbandc(cmpa,cmpb,cmpc))
					{						
						r_flag13=0;
						continue;
					}
					else if(0x0f==acmpbandc(cmpa,cmpb,cmpc)||r_flag13)
					{						
						break;
					}
					else
					{
						cmpb+=cmpc;
						cmpb/=2;
						cmpa-=cmpb;
						togtmp6=cmpa/0x100;
						togtmp5=cmpa%0x100;
						if(togtmp6>0||togtmp5>0x7f)
							togtmp6|=0x80;
						r_flag13=1;
						continue;
					}
				}
				else if(commond0f1h==code_format[csumcodeformat-outcnt1][togtmp14])
				{
					//nopuslehe3010
					//nodatahe3010
					togtmp15=code_format[csumcodeformat-outcnt1][togtmp14+1];	//togtmp15
					togtmp15*=2;
					if((togtmp16&0x1)==1)
						togtmp15--;
					togtmp14+=2;
					cnt1=0;
					togtmp3=code_format[csumcodeformat-outcnt1][cnt1++];
					togtmp4=code_format[csumcodeformat-outcnt1][cnt1++];
					togtmp1=code_format[csumcodeformat-outcnt1][cnt1++];
					togtmp2=code_format[csumcodeformat-outcnt1][cnt1++];
					progf1=0;
					while(1)
					{	//ToggleBit_Judge_3010BIT0
						cmpa=togtmp6&0x7f;
						cmpa*=0x100;
						cmpa+=togtmp5;
						cmpb=togtmp4&0x7f;
						cmpb*=0x100;
						cmpb+=togtmp3;
						cmpc=togtmp2&0x7f;
						cmpc*=0x100;
						cmpc+=togtmp1;
						if(0==acmpbandc(cmpa,cmpb,cmpc))
						{						
							//ToggleBit_Judge_3010OneBIT
							r_flag13=0;
							//ToggleBit_Judge_3010OneBIT3
							togtmp15--;
							if(0==togtmp15)
								break;
							else
							{
								if(0==r_flag13)
								{
									togtmp6=0;
									togtmp5=wavedat[cntx++];
									if(togtmp5>=0x80)
									{
										togtmp6=togtmp5;
										togtmp5=wavedat[cntx++];
									}
								}
							}
							//goto	ToggleBit_Judge_3010BIT0
						}
						else if(0x0f==acmpbandc(cmpa,cmpb,cmpc)||r_flag13)
						{
							//noendhe3010
							//ToggleBit_Judge_Next_Format
							progf1=1;
							break;
						}
						else
						{
							cmpb=code_format[csumcodeformat-outcnt1][15]&0x7f;
							cmpb*=0x100;
							cmpb+=code_format[csumcodeformat-outcnt1][14];
							cmpa-=cmpb;
							togtmp6=cmpa/0x100;
							togtmp5=cmpa%0x100;
//							if(togtmp6>0||togtmp5>0x7f)
//								togtmp6|=0x80;
							r_flag13=1;
							//ToggleBit_Judge_3010OneBIT3
							togtmp15--;
							if(0==togtmp15)
								break;
							else
							{
								if(0==r_flag13)
								{
									togtmp6=0;
									togtmp5=wavedat[cntx++];
									if(togtmp5>=0x80)
									{
										togtmp6=togtmp5;
										togtmp5=wavedat[cntx++];
									}
								}
							}
							//goto	ToggleBit_Judge_3010BIT0
						}
					}
					if(progf1)
						break;
				}
				else if(commond0f2h==code_format[csumcodeformat-outcnt1][togtmp14])
				{
					return	outcnt1;
				}
				else
					//ToggleBit_Judge_Next_Format
					break;
			}
		}
		else
		{
			return	0;
		}
	}
	return	0;
}
//wjs;================
unsigned int acmpbandc(unsigned	int	cmpdat1,unsigned	int	cmpdat2,unsigned	int	cmpdat3)
{
	
	if((cmpdat1>=cmpdat2)&&(cmpdat3>=cmpdat1))
		return	0;
	else	if(cmpdat3<cmpdat1)
		return	0xf0;
	else
		return	0x0f;
}
	
//=====================
bool	cmpaequbtog(unsigned	int	cmpdat1,unsigned	int	cmpdat2,unsigned	int	flagw)
{
	unsigned	int	tmpd1,tmpd2;
	
	if(cmpdat1<0x20){
		if(cmpdat2>=0x100)
			return	1;
		if(cmpdat2>=cmpdat1){
			tmpd2=cmpdat2/2;
			tmpd2/=2;
			tmpd1=cmpdat2-cmpdat1;
			cmpdat2=cmpdat1;
		}
		else{
			tmpd2=cmpdat1/2;
			tmpd2/=2;
			tmpd1=cmpdat1-cmpdat2;
		}
		if(cmpdat2>=0x10){
			if(tmpd1>=tmpd2)
				return	1;
			else
				return	0;
		}
		else{
			if(0==flagw){
				if(cmpdat2>=5){
					if(tmpd1>=tmpd2)
						return	1;
					else
						return	0;
				}
				else
					return	0;
			}
			else{
				if(tmpd1>=5)
					return	1;
				else
					return	0;
			}
			
		}
	}
	else{
		tmpd1=cmpdat1/2;
		tmpd1/=2;
		tmpd1/=2;
		if(cmpdat1>=cmpdat2)
			tmpd2=cmpdat1-cmpdat2;
		else
			tmpd2=cmpdat2-cmpdat1;
		if(tmpd2>=tmpd1)
			return	1;
		else
			return	0;
	}
	
}	



//==========
void	changetogglebit(unsigned int *wavedat,unsigned	int	*indatsum,unsigned	int	togtmp9)
{
	unsigned	int	togtmp6,togtmp5,togtmp4,togtmp3,togtmp2,togtmp1;
	unsigned	int	togtmp14,togtmp15,togtmp16,togtmp17,togtmp7,togtmp8,togtmp12;
	unsigned	int	cmpa,cmpb,cmpc;
	unsigned	int	cnt1,tmpd1,tmpd2;
	unsigned	int	togt9;
//	unsigned	int	cnty,cntx;
	bool		r_flag13;
	bool		r_flag17;
	bool		r_flag10;
	bool		r_flag11;

	if(1==togtmp9||0==togtmp9)
		return;
	else	if(togtmp9>=5){
		togtmp9=8-togtmp9;
		togtmp1=ToggleBit_Place[togtmp9];		//wjs;y
		cntx=5;
		for(cnt1=togtmp1;cnt1!=0;cnt1--){
			togtmp14=cntx;
			togtmp6=0;
			togtmp5=wavedat[cntx++];
			if(togtmp5>=0x80){
				togtmp6=togtmp5;
				togtmp5=wavedat[cntx++];
			}
		}
		cnty=0;
		while(1){
			togtmp4=0;
			togtmp3=wavedat[cntx++];
			if(togtmp3>=0x80){
				togtmp4=togtmp3;
				togtmp3=wavedat[cntx++];
			}
			togtmp4=0;
			togtmp3=wavedat[cntx++];
			if(togtmp3>=0x80){
				togtmp4=togtmp3;
				togtmp3=wavedat[cntx++];
			}
			togtmp7=togtmp3;
			togtmp8=togtmp4;
			tmpd1=togtmp6&0x7f;
			tmpd1*=0x100;
			tmpd1+=togtmp5;
			tmpd2=togtmp4&0x7f;	//TOGTMP13
			tmpd2*=0x100;
			tmpd2+=togtmp3;
			cnt1=wavedat[1]&0x80;
			if(0!=cmpaequbtog(tmpd1,tmpd2,cnt1))
				break;
		}
		if(tmpd2<tmpd1){
			xiaoyu1:
			if(0!=(wavedat[1]&0x10))
				return;
			else{
				//xiaoyudayu4:
				goto	xiaoyudayu4;
			}
		}
		else{
			//xiaoyudayu1:
			tmpd2-=tmpd1;
			cntx=togtmp9*2;
			cntx*=2;
			cntx++;
			togtmp4=STandantdata[cntx--];
			togtmp3=STandantdata[cntx--];
			tmpd1=togtmp4&0x7f;
			tmpd1*=0x100;
			tmpd1+=togtmp3;
			if(tmpd1>=tmpd2){
				xiaoyudayu5:
				if(0==(wavedat[1]&0x10))
					return;
				else{
					xiaoyudayu4:
					cntx=togtmp14;
					togtmp15=wavedat[cntx];
					if(togtmp7>=0x80||togtmp8!=0){
						//PrecossToggleBit_3004_b4:
						if(togtmp15<0x80){
							Some_Data_Right_Move1(wavedat,indatsum,togtmp14);
						}
						cntx=togtmp14;
						wavedat[cntx++]=togtmp8|0x80;
						wavedat[cntx++]=togtmp7;
						return;
					}
					else{
						//PrecossToggleBit_3004_b3:
						if(togtmp15>=0x80)
							wavedat[cntx++]=0;
						wavedat[cntx++]=togtmp7;
						return;
					}
				}
			}
			else{
				//getstandatatogl:
				cntx=togtmp9*2;
				cntx*=2;
				togtmp7=STandantdata[cntx++];
				togtmp3=togtmp7;
				togtmp8=STandantdata[cntx];
				togtmp4=togtmp8;
				tmpd1=togtmp6&0x7f;
				tmpd1*=0x100;
				tmpd1+=togtmp5;
				tmpd2=togtmp4&0x7f;
				tmpd2*=0x100;
				tmpd2+=togtmp3;
				cnt1=wavedat[1]&0x80;
				if(0==cmpaequbtog(tmpd1,tmpd2,cnt1)){
					cntx=togtmp9*2;
					cntx*=2;
					cntx+=2;
					togtmp7=STandantdata[cntx++];
					togtmp3=togtmp7;
					togtmp8=STandantdata[cntx];
					togtmp4=togtmp8;
					goto	xiaoyu1;
				}
				else
					goto	xiaoyudayu5;
			}
		}
	}
	else{
		togt9=togtmp9;
		togtmp9=ToggleBit_Place[8-togtmp9];
		togtmp14=18;
		cntx=5;
		togtmp16=code_format[csumcodeformat-togt9][17];
		r_flag13=0;
		r_flag17=1;
		r_flag10=1;
		PrecossToggleBit_One_3010:
		{
			if(0==r_flag13){
				togtmp17=cntx;
				togtmp6=0;
				togtmp5=wavedat[cntx++];
				if(togtmp5>=0x80){
					togtmp6=togtmp5;
					togtmp5=wavedat[cntx++];
				}
			}
			if(commond0f0h==code_format[csumcodeformat-togt9][togtmp14]){
				togtmp3=code_format[csumcodeformat-togt9][togtmp14+1];
				togtmp4=code_format[csumcodeformat-togt9][togtmp14+2];
				togtmp1=code_format[csumcodeformat-togt9][togtmp14+3];
				togtmp2=code_format[csumcodeformat-togt9][togtmp14+4];
				togtmp14+=5;
				cmpa=togtmp6&0x7f;
				cmpa*=0x100;
				cmpa+=togtmp5;
				cmpb=togtmp4&0x7f;
				cmpb*=0x100;
				cmpb+=togtmp3;
				cmpc=togtmp2&0x7f;
				cmpc*=0x100;
				cmpc+=togtmp1;
				if(0==acmpbandc(cmpa,cmpb,cmpc)){
					//PrecossToggleBit_One_30102:
					r_flag17^=1;
					r_flag13=0;
					goto	PrecossToggleBit_One_3010;
				}
				else	if(0x0f==acmpbandc(cmpa,cmpb,cmpc)||r_flag13==1){
					return;
				}
				else{
					tmpd1=cmpc+cmpb;
					tmpd1/=2;
					cmpa-=tmpd1;
					togtmp6=cmpa/0x100;
					togtmp5=cmpa%0x100;
					if(togtmp6>0||togtmp5>0x7f)
						togtmp6|=0x80;
					r_flag13=1;
					goto	PrecossToggleBit_One_3010;
				}
			}
			else	if(commond0f1h==code_format[csumcodeformat-togt9][togtmp14]){
				//PrecossToggleBit_nodatahe3010:
				togtmp15=code_format[csumcodeformat-togt9][togtmp14+1];
				togtmp14+=2;
				togtmp3=code_format[csumcodeformat-togt9][0];
				togtmp4=code_format[csumcodeformat-togt9][1];
				togtmp1=code_format[csumcodeformat-togt9][2];
				togtmp2=code_format[csumcodeformat-togt9][3];
				r_flag11=1;
			PrecossToggleBit_3010BIT0:
				cmpa=togtmp6&0x7f;
				cmpa*=0x100;
				cmpa+=togtmp5;
				cmpb=togtmp4&0x7f;
				cmpb*=0x100;
				cmpb+=togtmp3;
				cmpc=togtmp2&0x7f;
				cmpc*=0x100;
				cmpc+=togtmp1;
				if(0==acmpbandc(cmpa,cmpb,cmpc)){
				//PrecossToggleBit_3010OneBIT:
					r_flag17^=1;
					r_flag13=0;
					goto	PrecossToggleBit_3010OneBIT3;
				}
				else{
					cmpb=code_format[csumcodeformat-togt9][15]&0x7f;
					cmpb*=0x100;
					cmpb+=code_format[csumcodeformat-togt9][14];
					cmpa-=cmpb;
					togtmp6=cmpa/0x100;
					togtmp5=cmpa%0x100;
					if(togtmp6>0||togtmp5>0x7f)
						togtmp6|=0x80;
					r_flag13=1;
				PrecossToggleBit_3010OneBIT3:
					if(1!=togtmp16||r_flag10==0){
					//PrecossToggleBit_nolost1bit:
						r_flag11^=1;
						if(0==r_flag11)
							goto	PrecossToggleBit_3010OneBIT2;
						else
							goto	PrecossToggleBit_3010OneBIT4;
					}
					else{
						r_flag10=0;
					PrecossToggleBit_3010OneBIT4:
						r_flag11=1;
						togtmp9--;
						if(0==togtmp9){
						//Get_ToggleBit_Place_3010:
							//if((r_flag17==1&&0!=(wavedat[1]&0x10))||(r_flag17==0&&0==(wavedat[1]&0x10)))
							if((r_flag17==1&&0==(wavedat[1]&0x10))||(r_flag17==0&&0!=(wavedat[1]&0x10)))
								return;
							else{
							//thisbitis3:
								r_flag10=1;
								tmpd2=0;
								togtmp17=All_Send_Data_Right_Move(wavedat,indatsum,togtmp17);
								togtmp3=code_format[csumcodeformat-togt9][10];
								togtmp4=code_format[csumcodeformat-togt9][11];
								togtmp6=0;
								togtmp5=wavedat[cnty++];
								if(togtmp5>=0x80){
									togtmp6=togtmp5;
									togtmp5=wavedat[cnty++];
								}
								togtmp1=code_format[csumcodeformat-togt9][12];
								togtmp2=code_format[csumcodeformat-togt9][13];
								cmpa=togtmp6&0x7f;
								cmpa*=0x100;
								cmpa+=togtmp5;
								cmpb=togtmp2&0x7f;
								cmpb*=0x100;
								cmpb+=togtmp1;
								if(cmpa>=cmpb)	//ACMPB
									r_flag13=1;
								else
									r_flag13=0;
							Get_ToggleBit_Place_3010Start:
								if(0==r_flag13){
									cmpb=togtmp4&0x7f;
									cmpb*=0x100;
									cmpb+=togtmp3;
									cmpa+=cmpb;
									togtmp1=code_format[csumcodeformat-togt9][8];
									togtmp2=code_format[csumcodeformat-togt9][9];
									cmpb=togtmp2&0x7f;
									cmpb*=0x100;
									cmpb+=togtmp1;
									togtmp6=cmpa/0x100;
									togtmp5=cmpa%0x100;
									if(togtmp6>0||togtmp5>0x7f)
										togtmp6|=0x80;
									togtmp12=cmpa%0x100;
									if(cmpa>=cmpb)	//ACMPB2
										r_flag13=1;
									else
										r_flag13=0;
									if(r_flag13){
										//ASUBB:
										togtmp1=code_format[csumcodeformat-togt9][14];
										togtmp2=code_format[csumcodeformat-togt9][15];
										cmpb=togtmp2&0x7f;
										cmpb*=0x100;
										cmpb+=togtmp1;
										cmpa-=cmpb;
										togtmp6=cmpa/0x100;
										togtmp5=cmpa%0x100;
										if(togtmp6>0||togtmp5>0x7f)
											togtmp6|=0x80;
										togtmp12=cmpa%0x100;
									}
									//PToggleBit_3010nsbc1:
									Reload_Data_Buff(togtmp6,togtmp12,wavedat);
									togtmp6=0;
									togtmp5=wavedat[cnty++];
									if(togtmp5>=0x80){
										togtmp6=togtmp5;
										togtmp5=wavedat[cnty++];
									}
									wavedat[2]--;
									wavedat[3]--;
									goto	PToggleBit_3010_next_pulse;
								}
								else{
								//PToggleBit_3010sbc:
									wavedat[2]++;
									wavedat[3]++;
									r_flag10^=1;
									if(r_flag10==1){
										togtmp12=togtmp3;
										Reload_Data_Buff(togtmp4,togtmp12,wavedat);
										cmpa=togtmp6&0x7f;
										cmpa*=0x100;
										cmpa+=togtmp5;
										cmpb=togtmp4&0x7f;
										cmpb*=0x100;
										cmpb+=togtmp3;
										cmpa-=cmpb;
										togtmp6=cmpa/0x100;
										togtmp5=cmpa%0x100;
										if(togtmp6>0||togtmp5>0x7f)
											togtmp6|=0x80;
										togtmp12=cmpa%0x100;
										Reload_Data_Buff(togtmp6,togtmp12,wavedat);
										goto	PToggleBit_3010_next_pulse;
									}
									else{
									//subfirwave1:
										cmpa=togtmp6&0x7f;
										cmpa*=0x100;
										cmpa+=togtmp5;
										cmpb=togtmp4&0x7f;
										cmpb*=0x100;
										cmpb+=togtmp3;
										cmpa-=cmpb;
										togtmp12=cmpa%0x100;
										Reload_Data_Buff(cmpa/0x100,togtmp12,wavedat);
										togtmp12=togtmp3;
										Reload_Data_Buff(togtmp4,togtmp12,wavedat);
									PToggleBit_3010_next_pulse:
										tmpd2++;
										if(tmpd2>=2){
										//All_Send_Data_Left_Move:
											cnt1=*indatsum-4;
											if(cnty<cnt1){
												while(cnty<cnt1){
													wavedat[cntx++]=wavedat[cnty++];
												}
											}
											for(cnt1=0;cnt1<4;cnt1++){
												wavedat[cntx++]=wavedat[cnty++];
											}
										}
										else{
											togtmp6=0;
											togtmp5=wavedat[cnty++];
											if(togtmp5>=0x80){
												togtmp6=togtmp5;
												togtmp5=wavedat[cnty++];
											}
											togtmp1=code_format[csumcodeformat-togt9][12];
											togtmp2=code_format[csumcodeformat-togt9][13];
											cmpa=togtmp6&0x7f;
											cmpa*=0x100;
											cmpa+=togtmp5;
											cmpb=togtmp2&0x7f;
											cmpb*=0x100;
											cmpb+=togtmp1;
											if(cmpa>=cmpb)	//ACMPB
												r_flag13=1;
											else
												r_flag13=0;
											goto	Get_ToggleBit_Place_3010Start;
										}
									}
								}
							}
						}
						else{
							togtmp15--;
							if(0==togtmp15)
								goto	PrecossToggleBit_One_3010;
							else{
							PrecossToggleBit_3010OneBIT2:
								if(0==r_flag13){
									togtmp17=cntx;
									togtmp6=0;
									togtmp5=wavedat[cntx++];
									if(togtmp5>=0x80){
										togtmp6=togtmp5;
										togtmp5=wavedat[cntx++];
									}
									goto	PrecossToggleBit_3010BIT0;
								}
								else
									goto	PrecossToggleBit_3010BIT0;
							}
						}
					}
				}
			}
			else{
				return;
			}
		}
	}
	return;
}




//**************************************************************

bool	compdata(unsigned	int	*wavedat,unsigned	int	*indatsum)
{
	unsigned	int		*intp1;
	unsigned	int		tmp1,tmp2;
	bool	flag1=0;

	intp1=indatsum;
	(*intp1)--;

	tmp1=wavedat[0];
	if(tmp1!=0)
	{
		
		return 1;
	}
	
	else
	{
		tmp1=wavedat[1];
		if(tmp1>=0x80)
		{
			flag1=Modifywave(wavedat,intp1);
			delfeng(wavedat,intp1);
		}
		else
		{
			flag1=modifywavem708(wavedat,intp1);
		}
		
		if(flag1==0)
		{
			flag1=getfigure(wavedat,intp1);
		}
		else
		{
			//kal_prompt_trace(MOD_MMI, "remote_study_data == Study_wave_error");
			return 1;
		}
	}
	
	return 0;
}

bool Modifywave(unsigned	int	*wavedat,unsigned	int	*indatsum)
{
	unsigned	int	lrn_flag,receive_time,repdatalen;
	long	waveld1=0,waveld2=0;
	unsigned	int	wavedd1,wavedd2;
	unsigned	int	cnt1,cnt2,cnt3,cnt4;

	if(wavedat[0]==0){
		lrn_flag=wavedat[1];			
		receive_time=wavedat[2];
		repdatalen=wavedat[3];
		cnt3=0;		//parity--//receive_time
		cnt1=5;		//point-y===cnt2---point-x
		for(cnt2=5;cnt2<*indatsum+1;cnt2++){
	
			wavedd1=wavedat[cnt2];
			if(wavedd1>=0x80){
				wavedd2=wavedd1&0x7f;
				cnt2++;
				if(cnt2 == *indatsum+1){
					wavedd1 = 0;
				}
				else{
				    wavedd1=wavedat[cnt2];
				}
			}
			else{
				wavedd2=0;
			}
			waveld1=wavedd2*0x100;
			waveld1+=wavedd1;
			if((cnt3&0x01)==0){
				if(waveld1>7){
					waveld1-=7;
				}
				else
					waveld1=2;
			}
			else{
				waveld1+=3;
			}
			wavedd2=waveld1/0x100;
			wavedd1=waveld1%0x100;
			if((wavedd2>0)||(wavedd1>=0x80)){
				wavedd2|=0x80;
				wavedat[cnt1++]=wavedd2;
				wavedat[cnt1++]=wavedd1;
			}
			else
				wavedat[cnt1++]=wavedd1;
			cnt3++;
			if(cnt3>=receive_time){
				receive_time++;
				break;
			}
		}
		receive_time--;
		cnt4=5;
		if(wavedat[5]>=0x80){				
			cnt4=7;
			if(wavedat[6]<253){
				wavedat[6]+=3;
			}
			else{
				wavedat[6]+=3;
				wavedat[6]-=0x100;
				wavedat[5]++;
			}
		}
		else{
			cnt4=6;
			if(wavedat[5]<126)
				wavedat[5]+=2;
			else
				wavedat[5]=127;
		}
		if(wavedat[cnt4]>=0x80){
			if(wavedat[cnt4+1]!=0){
				wavedat[cnt4+1]-=1;
			}
			else{
				wavedat[cnt4+1]=255;
				wavedat[cnt4]&=0x7f;
				wavedat[cnt4]--;
				wavedat[cnt4]|=0x80;
			}
		}
		else{
			if(wavedat[cnt4]<2){
				wavedat[cnt4]=2;
			}
		}
		return	0;
 	}
	else
		return	1;
	return	0;	//ok
}

void	delfeng(unsigned	int	*wavedat,unsigned	int	*indatsum)
{
	unsigned	int	lrn_flag,receive_time,repdatalen;
	long	waveld1=0,waveld2=0;
	unsigned	int	wavedd1,wavedd2,wavedd4=0,wavedd5,wavedd6;
	unsigned	int	cnt1,cnt2,cnt3;

	lrn_flag=wavedat[1];
	receive_time=wavedat[2];
	repdatalen=wavedat[3];
	cnt3=0;		//parity--//receive_time
	cnt1=5;		//point-y===cnt2---point-x
	for(cnt2=5;cnt2<*indatsum+1;cnt2++){
		wavedd1=wavedat[cnt2];
		wavedat[cnt1++]=wavedd1;
		if(wavedd1>=0x80){
			wavedd2=wavedd1&0x7f;
			cnt2++;
			if(cnt2 == *indatsum+1){
					wavedd1 = 0;
			}
			else{
			    wavedd1=wavedat[cnt2];
			}
			
			wavedat[cnt1++]=wavedd1;
		}
		else{
			wavedd2=0;
		}
		if((cnt3&0x01)==1){
			if(wavedd2==0){
				if(wavedd1<5){
					cnt1--;	//point-y
					if(wavedd4>=0x80)
						cnt1--;
					cnt1--;
					wavedd5=wavedat[cnt1];
					if(wavedd5>=0x80){
						wavedd6=wavedd5&0x7f;
						wavedd5=wavedat[cnt1+1];
					}
					else{
						wavedd6=0;
					}
					waveld1=wavedd2*0x100;
					waveld1+=wavedd1;
					waveld2=wavedd6*0x100;
					waveld2+=wavedd5;
					waveld1+=waveld2;

					wavedd5=wavedat[cnt2++];
					if(wavedd5>=0x80){
						wavedd6=wavedd5&0x7f;
						wavedd5=wavedat[cnt2++];
					}
					else{
						wavedd6=0;
					}
					waveld2=wavedd6*0x100;
					waveld2+=wavedd5;
					waveld1+=waveld2;
					wavedd2=waveld1/0x100;
					wavedd1=waveld1%0x100;
					if(wavedd2>=0x80){
						wavedd2|=0x80;
						wavedat[cnt1++]=wavedd2;
					}
					wavedat[cnt1++]=wavedd1;
					receive_time--;
					receive_time--;
					cnt3++;
				}
			}
		}
		wavedd4=wavedd2;
		cnt3++;
		if(cnt3>=receive_time){
			receive_time++;
			break;
		}
	}
	receive_time--;
	return;
}

//wjs;===============
bool modifywavem708(unsigned	int	*wavedat,unsigned	int	*indatsum)
{
	unsigned	int	lrn_flag,receive_time,repdatalen;
	long	waveld1=0,waveld2=0;
	unsigned	int	wavedd1,wavedd2,wavedd3;
	unsigned	int	cnt1,cnt2,cnt3,cnt4;

	if(wavedat[0]==0){
		lrn_flag=wavedat[1];
		receive_time=wavedat[2];
		repdatalen=wavedat[3];
		cnt3=0;		//parity--//receive_time
		cnt1=5;		//point-y===cnt2---point-x
		for(cnt2=5;cnt2<*indatsum+1;cnt2++){
			wavedd1=wavedat[cnt2];
			if((wavedd1&0xf0)==0xf0){
				wavedd3=wavedd1&0x0f;
				cnt2++;
				wavedd2=wavedat[cnt2];
				cnt2++;
				wavedd1=wavedat[cnt2];
			}
			else	if(wavedd1>=0x80){
				wavedd3=0;
				wavedd2=wavedd1&0x7f;
				cnt2++;
				wavedd1=wavedat[cnt2];
			}
			else{
				wavedd3=0;
				wavedd2=0;
			}
			waveld1=wavedd3*0x10000;
			waveld1+=wavedd2*0x100;
			waveld1+=wavedd1;
			waveld1+=2;
			waveld1/=16;
			if((cnt3&0x01)==0){
				if(waveld1>=256){
					waveld1-=1;
				}
				if(waveld1>128)
					return	1;	//�ز����ʹ���2048us--error
			}
			else{
				waveld1-=waveld2;
				if(waveld1<=2){
					waveld1=1;
				}
				else{
					waveld1-=2;
				}
			}
			waveld2=waveld1;
			wavedd2=waveld1/0x100;
			wavedd1=waveld1%0x100;
			if((wavedd2>0)||(wavedd1>=0x80)){
				wavedd2|=0x80;
				wavedat[cnt1++]=wavedd2;
				wavedat[cnt1++]=wavedd1;
			}
			else
				wavedat[cnt1++]=wavedd1;
			cnt3++;
			if(cnt3>=receive_time)
				break;
		}
		cnt4=5;
		if(wavedat[5]>=0x80){
			cnt4=7;
			if(wavedat[6]>=5){
				wavedat[6]-=5;
			}
			else{
				wavedat[6]+=0x100;
				wavedat[6]-=5;
				wavedat[5]&=0x7f;
				wavedat[5]--;
				wavedat[5]|=0x80;
			}
		}
		else{
			cnt4=6;
			if(wavedat[5]>3)
				wavedat[5]-=3;
			else
				wavedat[5]=1;
		}
		if(wavedat[cnt4]>=0x80){
			if(wavedat[cnt4+1]<252){
				wavedat[cnt4+1]+=4;
			}
			else{
				wavedat[cnt4+1]-=252;
				wavedat[cnt4]+=1;
			}
		}
		else{
			if(wavedat[cnt4]<0x7c){
				wavedat[cnt4]+=3;
			}
			else
				wavedat[cnt4]=0x7f;
		}
		return	0;
 	}
	else
		return	1;
	return	0;	//ok
}

bool getfigure(unsigned int	* wavedat, unsigned int * indatsum)
{
	unsigned	int	cnt1,cnt2,cnt3,cnt4;
	unsigned	int	maxdat[3],maxdatindex[3],maxwave,maxwave2,maxwindex,waved1;
	unsigned	int	r_figure;
	unsigned	int	lrn_flag,receive_time,repdatalen;

	if(wavedat[0]==0){
		cnt3=0;
		maxwave=0;
		for(cnt2=5;cnt2<*indatsum;cnt2++){
			waved1=wavedat[cnt2];
			if(waved1>=0x80){
				waved1=(waved1-0x80);
				waved1=waved1*0x100;	//waved1=waved1*0x80;
				cnt2++;
				waved1+=wavedat[cnt2];
			}
			if(maxwave<waved1){
				maxwave=waved1;
				maxwindex=cnt3;
			}
			cnt3++;
		}
		maxwave2=maxwave/8;
		maxwave2=maxwave-maxwave2;
		cnt1=0;
		maxdat[0]=0;
		maxdatindex[0]=0;
		maxdat[1]=0;
		maxdatindex[1]=0;
		maxdat[2]=0;
		maxdatindex[2]=0;
		cnt3=0;
		for(cnt2=5;cnt2<*indatsum;cnt2++){
			cnt4=cnt2;
			waved1=wavedat[cnt2];
			if(waved1>=0x80){
				waved1=(waved1-0x80);
				waved1=waved1*0x100;	//waved1=waved1*0x80;
				cnt2++;
				waved1+=wavedat[cnt2];
			}
			if(maxwave>=waved1&&maxwave2<=waved1){
				maxdatindex[cnt1]=cnt4;		//wave data index x
				maxdat[cnt1]=cnt3;		//wave number receive_time
				cnt1++;
				if(3<=cnt1)
					break;
			}
			cnt3++;
		}
		r_figure=0;
		lrn_flag=wavedat[1];
		receive_time=wavedat[2];
		repdatalen=wavedat[3];
		if((lrn_flag&0x40)!=00){
			if(maxdat[1]==0)
				repdatalen=receive_time;
			else{
				if(wavedat[maxdatindex[1]]<0x88){
					repdatalen=receive_time;
				}
				else{
					goto	nosinglewave2;
//					if(maxdat[0]==0)
//						maxdat[0]=receive_time;
//					r_figure=1;
//					receive_time=maxdat[0];
//					repdatalen=maxdat[0];
				}
			}
		}
		else{
			if(maxdat[1]==0){
				if(maxdat[0]==0)
					maxdat[0]=receive_time;
				r_figure=1;
				receive_time=maxdat[0];
				repdatalen=maxdat[0];
			}
			else{
			nosinglewave2:
				cnt4=maxdat[1]-maxdat[0];
				if(cnt4<2){
					if(maxdat[0]==0)
						maxdat[0]=receive_time;
					r_figure=1;
					receive_time=maxdat[0];
					repdatalen=maxdat[0];
				}
				else	if(cnt4>=maxdat[0]){
					unsigned	int	len,flag2=lrn_flag&0x80;
					unsigned	int	*ptdat2,*ptdat1=&wavedat[5];
					if(wavedat[maxdatindex[0]]>=0x80)
						ptdat2=&wavedat[maxdatindex[0]+2];
					else
						ptdat2=&wavedat[maxdatindex[0]+1];
					len=maxdat[0]-2;
					if(0==judgesame(ptdat1,ptdat2,len,flag2)){
						if(maxdat[0]==0)
							maxdat[0]=receive_time;
						r_figure=1;
						receive_time=maxdat[0];
						repdatalen=maxdat[0];
					}
					else{
						if(wavedat[maxdatindex[1]]>=0x80)
							ptdat2=&wavedat[maxdatindex[1]+2];
						else
							ptdat2=&wavedat[maxdatindex[1]+1];
						ptdat1=&wavedat[5];
						len=maxdat[0]-2;
						if(0==judgesame(ptdat1,ptdat2,len,flag2)){
							r_figure=3;
							receive_time=maxdat[0];
							repdatalen=maxdat[1];
						}
						else{
							r_figure=2;
							receive_time=maxdat[0];
							repdatalen=maxdat[1];
						}
					}
				}
				else{
					cnt3=maxdat[0]/2;
					if(cnt4>=cnt3){
						r_figure=2;
						receive_time=maxdat[0];
						repdatalen=maxdat[1];
					}
					else{
					//nosinglewave4
						unsigned	int	len,flag3=lrn_flag&0x80;
						unsigned	int	*ptdat2,*ptdat1=&wavedat[5];
						if(wavedat[maxdatindex[0]]>=0x80)
							ptdat2=&wavedat[maxdatindex[0]+1];
						else
							ptdat2=&wavedat[maxdatindex[0]];
						len=maxdat[0]-2;
						if(0==judgesame(ptdat1,ptdat2,len,flag3)){
							if(maxdat[0]==0)
								maxdat[0]=receive_time;
							r_figure=1;
							receive_time=maxdat[0];
							repdatalen=maxdat[0];
						}
						else{
							if(wavedat[maxdatindex[1]]>=0x80)
								ptdat2=&wavedat[maxdatindex[1]+1];
							else
								ptdat2=&wavedat[maxdatindex[1]];
							ptdat1=&wavedat[5];
							len=maxdat[0]-2;
							if(0==judgesame(ptdat1,ptdat2,len,flag3)){		//wjs;y
								r_figure=3;
								receive_time=maxdat[0];
								repdatalen=maxdat[1];
							}
							else{
								r_figure=2;
								receive_time=maxdat[0];
								repdatalen=maxdat[1];
							}
						}
					}
				}
			}
		}
		if(receive_time%2==1)
			receive_time++;
		if(repdatalen%2==1)
			repdatalen++;

 		r_figure&=0X07;
 		lrn_flag&=0X88;
		wavedat[1]=r_figure|lrn_flag;
		wavedat[2]=receive_time;
		wavedat[3]=repdatalen;
		return	0;
 	}
	else
		return	1;
	return	0;
}



//=========
bool judgesame(unsigned int * dat1, unsigned int * dat2, unsigned int sum, unsigned int flag)
	{
		unsigned int i,j,k,dd1,dd2;
		j=0;
		k=0;
		for(i=0;i<sum;i++)
		{
			dd1=dat1[k];
			if(dd1>=0x80)
			{
				dd1=(dd1-0x80);
				dd1=dd1*0x100;		//dd1=dd1*0x80;
				k++;
				dd1+=dat1[k];
			}
				k++;
			dd2=dat2[j];
			if(dd2>=0x80)
			{
				dd2=(dd2-0x80);
				dd2=dd2*0x100;		//dd2=dd2*0x80;
				j++;
				dd2+=dat2[j];
			}
				j++;
			if(cmpdata(dd1,dd2,flag))
				return	1;
		}
		return	0;
	}

//================
bool	cmpdata(unsigned	int	cmpdat1,unsigned	int	cmpdat2,unsigned	int	flag)
{
	unsigned	int	data1,data2,data3;
	if(cmpdat1>=32){
		data1=cmpdat1/8;
		if(cmpdat1>=cmpdat2)
			data2=cmpdat1-cmpdat2;
		else
			data2=cmpdat2-cmpdat1;
		if(data1>=data2)
			return	0;	//ok
		else
			return	1;	//fail
	}
	else{
		if(cmpdat2>128)
			return	1;	//fail
		if(cmpdat1>=cmpdat2){
			data1=cmpdat1/4;
			data2=cmpdat1-cmpdat2;
			data3=cmpdat2;
		}
		else{
			data1=cmpdat2/4;
			data2=cmpdat2-cmpdat1;
			data3=cmpdat1;
		}
		if(data3<16){
			if(0==flag){
				if(data3<5){
					data1=data1*2;
					if(data3<data1)
						return	1;	//fail
					else
						return	0;	//ok
				}
				else{
					if(data2<data1)
						return	0;	//ok
					else
						return	1;	//fail
				}
			}
			else{
				if(data2<5)
					return	0;	//ok
				else
					return	1;	//fail
			}
		}
		else{
			if(data2<data1)
				return	0;	//ok
			else
				return	1;	//fail
		}
		
	}
	return	0;
}

//////������������
void Some_Data_Right_Move1(unsigned	int	*wavedat,unsigned	int	*indatsum,unsigned	int	tmpx)
{
	cntx=*indatsum-1;
	cnty=*indatsum;

	if(cntx<tmpx)
		return;

	while(cntx>tmpx)
	{
		wavedat[cnty--]=wavedat[cntx--];
	}
	//if(cntx==tmpx)
	wavedat[cnty]=wavedat[cntx];
	return;
}


//====================
unsigned int All_Send_Data_Right_Move(unsigned	int	*wavedat,unsigned	int	*indatsum,unsigned	int	tmpx)
{
	unsigned	int	tmplen;
//	unsigned	int	cnty,cntx;
	
	cntx=*indatsum-4;
	tmplen=cntx;
	cnty=*indatsum;
	if(tmpx>=cntx){
		tmpx=cntx-1;
	}
	while(cntx>tmpx){
		wavedat[cnty--]=wavedat[cntx--];
	}
//	if(cntx==tmpx)
	wavedat[cnty]=wavedat[cntx];
	return	tmpx;
}


//====================
void	Reload_Data_Buff(unsigned int inhb,unsigned int inlb,unsigned int *wavedat)
{
	if(inhb==0){
		if(inlb>=0x80)
			wavedat[cntx++]=0x80;
		wavedat[cntx++]=inlb;
	}
	else{
		wavedat[cntx++]=inhb|0x80;
		wavedat[cntx++]=inlb;
	}
	return;
}

int testLearnInOut()		//test�����ã�ƽʱû��
{

}

unsigned char learnInOut(unsigned char *dataIn,unsigned char *dataOut)
{
	int i=0;
	for(i=0; i<110;i++)
		dataOut[i] = dataIn[i];
	return learn_data_in_out(dataOut);
}






