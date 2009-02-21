package {
	import flash.display.*;
	import flash.geom.*;
	import flash.text.*;
	
	import mx.core.*;

	public class Grid extends Sprite
	{
		private var m_squares:Array;
		private var m_sWidth:Number;
		private var m_sHeight:Number;
		private var m_color:Number;
		private var m_borderThickness:Number;
		private var m_bgColor:Number;
		private var m_bgOpacity:Number;
		private var m_tiles:Array;
		public var m_nTiles:int;
		private var m_nSquares:int;
		private var m_cellSize:int;
		private var m_borderOpacity:Number;
		private var m_endTile:Tile;
		public function Grid (screenWidth:Number=400, 
							  screenHeight:Number=400, 
							  cellSize:Number = 20,
							  color:Number=0xff0000, 
							  bgColor:Number=0xffffff, 
							  bgOpacity:Number=.1)				  
		{
			
			m_sWidth = screenWidth;
			m_sHeight = screenHeight;
			m_color=color;
			m_bgColor=bgColor;
			m_bgOpacity=bgOpacity;
			m_cellSize = cellSize;
			m_squares = new Array();
			m_nSquares = 0;
			m_nTiles = 0;
			m_tiles = new Array();
			
			this.createTiles();
		 	var map:Map = new Map(m_tiles);
		 	this.drawGrid();
		 	
		 	
		 	m_endTile = m_tiles[4][0];
			 for (var i:int=0; i < m_tiles.length; i++)
				for ( var j:int=0; j < m_tiles[i].length ; j++)
				{
					m_tiles[i][j].calculateF(m_endTile);
				}
		 	
		 	
		}

			public function createTiles():void
			{
				
					for (var index:int; index*m_cellSize < m_sWidth; index++)
						m_tiles[index]= new Array();
					
					for (var i:int=0; i*m_cellSize < m_sWidth; i++)
						for ( var j:int=0; j*m_cellSize < m_sHeight ; j++)
						{
							
							//draw it
							
							
							//create Tile for building or what not
							
							m_tiles[i].push(new Tile(m_tiles,i,j,i*m_cellSize,j*m_cellSize,false,0xffffff));
							m_nTiles++;
						}
					
					for (var i5:int=0; i5 < m_tiles.length-1; i5++)
						for ( var j5:int=0; j5 < m_tiles[0].length ; j5++)
						{

							
							//trace(m_tiles[i5][j5]);
							m_tiles[i5][j5].setAdjacentTiles(m_tiles,i5,j5);

						}
			}
		public function inGrid(x:int, y:int):Boolean
		{
			if( x < 0 || x >= m_sWidth )
				return false;
			if( y < 0 || y >= m_sHeight )
				return false;
			return true;
		}
			
			public function drawGrid():void
			{
					this.graphics.clear();
					
					//for each tile	
					for (var i:int=0; i < m_tiles.length; i++)
						for ( var j:int=0; j < m_tiles[i].length ; j++)
						{		
							
							var square:Shape = new Shape();
							this.graphics.lineStyle(1,m_color,1);
							this.graphics.beginFill(m_tiles[i][j].getBgColor(),m_bgOpacity);
							this.graphics.drawRect(i*m_cellSize,j*m_cellSize,m_cellSize,m_cellSize);
							this.graphics.endFill();
							m_squares[i] = square;
							
							
		
							//draw tile ID's for debug info
							var str:String=m_tiles[i][j].createIDString();
							var drawstr:DrawString = new DrawString(m_tiles[i][j].getX(),m_tiles[i][j].getY(),30,20,str);
							addChild(drawstr);
							
						}
						
			}

			public function getTiles():Array
			{
				return this.m_tiles;
			}
			public function highlightTile():void
			{;}
			public function findTile(mouseX:Number,mouseY:Number):Tile
			{
				if(mouseX > m_sWidth || mouseY > m_sHeight || mouseX < 0 || mouseY < 0)
					return null;
				
				var i:int= (int)(mouseY/this.m_cellSize);
				var j:int= (int)(mouseX/this.m_cellSize);
				
				
				trace("tile x= "+i+"  "+"tile y= "+j);
				return m_tiles[j][i];
			
			}
		}
		
	
		
		
		
	}
