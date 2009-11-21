package org.ndot
{

	public class BitMapUtil
	{
		public function BitMapUtil()
		{
		}

		//将一个字节所能表示的整数，转换为16进制表示，茹 255 -> FF
		public static function int2hex(intVar:int):String
		{
			var HEXMAP:Array=new Array("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F");
			var result:String="";
			if (intVar < 16)
			{
				result=HEXMAP[0] + HEXMAP[intVar];
			}
			else
			{
				var leftInt:int=intVar / 16;
				var rightInt:int=intVar % 16
				result=HEXMAP[leftInt] + HEXMAP[rightInt];
			}
			return result;
		}

		//将HEX表示的数值转换为整数
		public static function hex2int(hexStr:String):int
		{
			var intVar:int=0;
			var leftStr:String=hexStr.substring(0, 1);
			var rightStr:String=hexStr.substr(1, 1);
			intVar+=getMapValue(leftStr, 16);
			intVar+=getMapValue(rightStr, 1);
			return intVar;
		}

		private static function getMapValue(str:String, hexBase:int):int
		{
			var intVar:int=0;
			switch (str.toUpperCase())
			{
				case "1":
					intVar+=1 * hexBase;
					break;
				case "2":
					intVar+=2 * hexBase;
					break;
				case "3":
					intVar+=3 * hexBase;
					break;
				case "4":
					intVar+=4 * hexBase;
					break;
				case "5":
					intVar+=5 * hexBase;
					break;
				case "6":
					intVar+=6 * hexBase;
					break;
				case "7":
					intVar+=7 * hexBase;
					break;
				case "8":
					intVar+=8 * hexBase;
					break;
				case "9":
					intVar+=9 * hexBase;
					break;
				case "A":
					intVar+=10 * hexBase;
					break;
				case "B":
					intVar+=11 * hexBase;
					break;
				case "C":
					intVar+=12 * hexBase;
					break;
				case "D":
					intVar+=13 * hexBase;
					break;
				case "E":
					intVar+=14 * hexBase;
					break;
				case "F":
					intVar+=15 * hexBase;
					break;
				default:
					intVar+=0;
			}

			return intVar;
		}

	}
}