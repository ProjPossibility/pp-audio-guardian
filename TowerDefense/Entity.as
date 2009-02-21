// an abstract class all entities in the game extends off of
package
{
	import flash.display.Bitmap;
	
	public class Entity
	{
		protected var images:Array; //images of the entity
		protected var x:int;
		protected var y:int;
		protected var typeID:int; //indicates what entity can be cast to
		protected var kill:Boolean=false;
		public function step():void{
			
		}
		
		// returns type ID of entity
		public function getTypeID():int{
			return typeID;
		}
		
		// returns x position of entity
		public function getX():int{
			return x;
		}
		
		// returns y position of entity
		public function getY():int{
			return y;
		}
		
		// returns appearance of entity
		public function getImages():Array{
			return images;
		}	
		
		// entity constructor
		public function Entity(pics:Array,x:int,y:int,tid:int)
		{
			images = pics;
			this.x = x;
			this.y = y;
			typeID=tid;
		}
		
		// check if kill flag is marked
		public function isKill():Boolean{
			return kill;
		}
		
		// accessed from inside object to kill self
		protected function killMe():void{
			kill = true;
		}
		
		// check if new children are created
		public function newChildren():Boolean{
			return false;
		}
		
		// get array of children created
		public function getChildren():Array{
			return new Array();
		}
		
	}
}