package
{
	import flash.display.Sprite;
	public class Tile
	{
		private var m_x:Number;
		private var m_y:Number;
		private var m_isBuildable:Boolean;
		private var m_isOccupied:Boolean;
		private var m_adjacentTiles:Array;
		private var m_isPassable:Boolean;
		private var m_turret:Entity;
		private var m_bgColor:Number;
		private var m_cellSize:Number;
		private var m_G:Number;
		private var m_H:Number;
		private var m_F:Number;
		private var m_curTileX:int;
		private var m_curTileY:int;
		private var m_isStart:Boolean=false;
		private var m_isEnd:Boolean=false;
		private var m_isNasty:Boolean=false;
		


		//x and y are upper left corner.
		public function Tile(tiles:Array,
							 curTileX:int, 
							 curTileY:int,
							 x:Number,
							 y:Number, 
							 build:Boolean=false,
							 bgColor:Number=0xffffff
							 )
		{
			m_x = x;
			m_y = y;
			m_curTileX = curTileX;
			m_curTileY = curTileY;
			m_cellSize = 40;
			m_G = 40.0;
			m_isBuildable= build;
			m_isOccupied = false;
			m_adjacentTiles = new Array();
			
		}
		public function createIDString():String
		{
			var temp:String = new String();
			temp = "";
			if(m_isPassable)
				temp+="P";
			if(m_isBuildable)
				temp+="B";
			if(m_isOccupied)
				temp+="O";
			if(m_isStart)
				temp+="S";
			if(m_isEnd)
				temp+="E";
			if(m_isNasty)
				temp+="N";
			if(temp.length == 0)
				temp+=".";
			return temp;
			
		}


		//*********************************************
		//Setters
		public function setBgColor(color:Number):void
		{
			m_bgColor = color;
		}
				public function setBuildable():void
		{
			m_isBuildable = true;
		}
		public function setPassable():void
		{
			m_isPassable = true;
		}
		public function setStart():void
		{
			m_isStart = true;
		}
		public function setEnd():void
		{
			m_isEnd = true;
		}
		public function setNasty():void
		{
			m_isNasty = true;
		}





		public function setAdjacentTiles (tiles:Array, curTileX:int, curTileY:int):void
		{
			if (!tiles || tiles.length == 0)
				return;
				
			if(curTileX > 0 && tiles[curTileX-1][curTileY].m_isOccupied == false)
			{
				m_adjacentTiles.push(tiles[curTileX-1][curTileY]);
			}
			
			if(curTileX < tiles[0].length - 1 && tiles[curTileX+1][curTileY].m_isOccupied == false)
			{
				m_adjacentTiles.push(tiles[curTileX+1][curTileY]);
			}
			
			if(curTileY > 0 && tiles[curTileX][curTileY-1].m_isOccupied == false)
			{
				m_adjacentTiles.push(tiles[curTileX][curTileY-1]);
			}
			
			if(curTileY < tiles[0].length - 1 && tiles[curTileX][curTileY+1].m_isOccupied == false)
			{
				m_adjacentTiles.push(tiles[curTileX][curTileY+1]);
			}
		}
		
		public function addTurret(turret:Entity):Boolean
		{
			if(this.m_isOccupied)
				return false;
			else
			{
				m_turret = turret;
				this.m_isOccupied = true;	
			}	
			if(!m_turret)
				return false;
			
			return true;
		}

		//********************************************
		//getters

		public function getBgColor():Number
		{
			return m_bgColor;
		}
		
		public function getXCenter():int
  		{
   			return m_x+m_cellSize/2;
  		}
  		public function getYCenter():int
  		{
   			return m_y+m_cellSize/2;
  		}
  		public function getX():Number
  		{
  			return m_x;
  		}
  		public function getY():Number
  		{
  			return m_y;
  		}
  		public function getCurX():int
  		{
  			return m_curTileX;
  		}
  		public function getCurY():int
  		{
  			return m_curTileY;
  		}
  		public function getF():Number
  		{
  			return m_F;
  		}
  		public function getAdjacentTiles():Array
  		{
  			return m_adjacentTiles
  		}
  		public function getOccupied():Boolean
  		{
  			return m_isOccupied;
  		}





		
  		
  		public function calculateF(endTile:Tile):void
  		{
  			m_H = 40.0 * (Math.abs(this.getXCenter() - endTile.getXCenter()) + Math.abs(this.getYCenter() - endTile.getYCenter()));
  			m_F = m_G + m_H;	
  		}

	}
}