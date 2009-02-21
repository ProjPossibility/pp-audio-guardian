package
{
	public class Map
	{
		private var m_text:String;
		private var m_tiles:Array;
		
		
		public function Map(tiles:Array, text:String="BBBBBB\nBBBBBB\nSPPPPE\nSPPPPE\nBBBBBB\nBBBBBB")
		{
			m_tiles = tiles;
			m_text = text;
			decodeMap();
			
			//checkMap();
		}
		public function checkMap():Boolean
		{
			//convert the string into a 2D array

			var temp:Array = new Array();
			for (var i:int=0; m_text.charAt(i) != '\n'; i++)
				temp[i] = new Array();
			
			i=0;
			var row:int=0;
			var col:int=0;
			var reqcol:int;
			while(i < m_text.length)
			{
				var c:String= m_text.charAt(i)
				//check for bad characters
				if(c != '\n' && c!= 'B' && c!='S' && c!='E' && c!='P' && c!='N')
					return false;
				else if( c == '\n')
				{
					//check to make sure all rows are the same length
					if(col ==0)
						reqcol=col;
					else if(col<reqcol)
						return false;
						
					col=0;
					row++;
				}
				else
					temp[col][row] = c;
				i++;
			}					
			//good
			//now check whatever u need
			
			//must have Start position on the edge
			var hasS:Boolean=false;
			var hasE:Boolean=false;
			for (var i:int=0; i< temp.length; i++)
				{
					//check top
					if(temp[i][0] == "S")
						hasS=true;
					else if(temp[i][0] == "E")
						hasE=true;
						
					//check bottom
					if(temp[i][(temp[0].length-1)] == "S")
						hasS=true;
					else if(temp[i][(temp[0].length-1)] == "E")
						hasE=true;
				}
			for (var i:int=0; i < temp[0].length; i++)
			{
				//check left
				if(temp[0][i] == "S")
					hasS=true;	
				else if(temp[0][i] == "S")
					hasS=true;
					
				//check right	
				if(temp[(temp.length-1)][i] == "S")
					hasS=true;
				else if(temp[(temp.length-1)][i] == "E")
					hasE=true;
			}
			//finished S and E check, return false if failed
			if(!hasS || !hasE)
				return false;
				
			//check for connection between start and end
			
			
			
			return true;
		}
		//recursively check for complete path
		public function checkPath(arr:Array, startRow:int, startCol:int):Boolean
		{
			var nextNodes:Array = new Array();
			
			
			
			for(var i:int=0;i<nextNodes.length;i++)
			{
				var node = nextNodes[i];
			
				if(node == "E")
					return true;
				else if(node == "P")
		//			return checkPath(arr, curRow,curCol);
			;
			}	
			return false;
			

		}
		
		
		public function decodeMap()
		{
			var row:int = 0;
			var column:int = 0;
			
			for(var i:int=0;i<m_text.length && column<m_tiles.length && row<m_tiles[0].length;i++)	
				if( m_text.charAt(i) == 'B')
				{
					m_tiles[column][row].setBuildable();
					column++;
				}
				else if( m_text.charAt(i) == 'S')
				{
					m_tiles[column][row].setStart();
					column++;
				}
				else if (m_text.charAt(i) == 'P')
				{
					m_tiles[column][row].setPassable();
					column++;
				}
				else if( m_text.charAt(i) == 'N')
				{
					m_tiles[column][row].setNasty();
					column++;
				}
				else if( m_text.charAt(i) == '\n')
				{
					row++;
					column=0;
				}
				else if( m_text.charAt(i) == 'E')
				{
					m_tiles[column][row].setEnd();
					column++;
				}
				else
					;//do nothing
			
			
			
		}
	}
}