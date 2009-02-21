package {
	import flash.display.Sprite;
	import flash.events.*;
	import flash.text.*;
	public class TowerDefense extends Sprite
	{
		private var steps:int = 0;
		public var env:Array = new Array();
		private var grid:Grid;
		
		public function TowerDefense()
		{
			//create and draw the grid
			
			grid=new Grid(800,200,40,0x0,0xffffff,0);
			addChild(grid);

			grid.getTiles()[0][0].setBuildable();
			trace(grid.getTiles()[0][0].createIDString());
			
			//
			// create cursor
			//env.push(new Cursor(300,300,this));
			
			// add images
			for(var i:int=0;i<env.length;i++){
				var temp:Entity = (Entity)(env[i]);
				var imgs:Array = temp.getImages();
				for(var j:int=0;j<imgs.length;j++){
					addChild((Image)(imgs[j]).getImage());
				}
			}

			
			//set functions
			stage.addEventListener(Event.ENTER_FRAME,step);
			//stage.addEventListener(MouseEvent.CLICK,addCreep);
			stage.addEventListener(MouseEvent.CLICK,addTurret);
		}
		
		//step event
		public function step(event:Event):void{
			this.graphics.clear();

			steps++;
			if(steps%5==0){
				//auto add creep
				
				var creep:Creep = new Creep(10,Math.random()*(height-10), grid);
				if(grid.inGrid(creep.getX(),creep.getY()))
				{
					env.push(creep);
					var imgarray:Array = creep.getImages();
					for(var i:int=0;i<imgarray.length;i++){
						addChild((Image)(imgarray[i]).getImage());
					}
				}
			}
			for(var i:int=0;i<env.length;i++){
				var temp:Entity = (Entity)(env[i]);
				temp.step();
				if(temp.newChildren()){
					var children:Array = temp.getChildren();
					for(var j:int=0;j<children.length;j++){
						var child:Entity = (Entity)(children[j]);
						env.push(child);
						var imgarray:Array = child.getImages();
						for(var k:int=0;k<imgarray.length;k++){
							addChild((Image)(imgarray[k]).getImage());
						}	
					}
				}
				//draw life bar
				if(temp.getTypeID()==2){
					var creep:Creep = (Creep)(temp);
					this.graphics.beginFill(0x000000,1);
					this.graphics.drawRect(creep.getX()-10,creep.getY()-20,20,2);
					this.graphics.beginFill(0xFF0000,1);
					this.graphics.drawRect(creep.getX()-10,creep.getY()-20,(int)(creep.getLife()/100*20),2);
				}
				
				if(temp.isKill()){
					var imgarray = temp.getImages();
					for(var j:int=0;j<imgarray.length;j++){
						removeChild((Image)(imgarray[j]).getImage());
					}
					env.splice(i,1);
				}
			}
		}
		
		//add creep on click
		public function addCreep(event:Event):void{
			var creep:Creep = new Creep(mouseX,mouseY,grid);
			env.push(creep);
			var imgarray:Array = creep.getImages();
			for(var i:int=0;i<imgarray.length;i++){
				addChild((Image)(imgarray[i]).getImage());
			}
		}
		public function addTurret(event:Event):Boolean
		{
			var curTile:Tile= grid.findTile(stage.mouseX, stage.mouseY);
			if(!(curTile))
				return false;
			
			var turret:ML1 = new ML1(curTile.getXCenter(),curTile.getYCenter(),env);
			if(curTile.addTurret((Entity)(turret)))
			{
				env.push(turret);
				var imgarray:Array = turret.getImages();
				for(var i:int=0;i<imgarray.length;i++){
					addChild((Image)(imgarray[i]).getImage());
				}
			}
			else
				return false;
			
			
			
			return true;

		}
	}
}
