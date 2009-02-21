package
{
	public class Creep extends Entity
	{
		[Embed(source='creep.png')]
		private var creepimg:Class;
		private var grid:Grid;
		private var	img:Image = new Image(new creepimg(),0,0,20,20);
		private var life:Number = 100;
		private var xspeed:int = 5;
		private var slowed:Boolean = false;
		
		public function Creep(x:int, y:int, g:Grid)
		{
			grid = g;
			var imgarray:Array= new Array();
			imgarray.push(img);
			super(imgarray,x,y,2);
			img.setX(super.getX());
			img.setY(super.getY());
		}
		
		public function slowDown(x:int):void
		{
			xspeed -= x;
			slowed = true;	
		}
		
		public function isSlowed():Boolean
		{
			return slowed;
		}
		
		public override function step():void
		{
			if(life<=0)
				killMe();
			
			super.x = super.x+xspeed;
			img.setX(super.getX());
			img.setY(super.getY());
			if( slowed )
			{
				slowed = false;
				if( xspeed < 0 )
				{
					xspeed = -5;
				}
				else
				{
					xspeed = 5;
				}
			}	
			if(! grid.inGrid(super.x, super.y) )
			{
				xspeed = -xspeed;
			}	
		}
		
		public function getLife():int
		{
			return life;
		}
		
		public function damage(amt:Number):void
		{
			life-=amt;
		}
		
		public function getSpeed():int
		{
			return xspeed;
		}
	}
}